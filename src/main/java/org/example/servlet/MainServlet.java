package org.example.servlet;

import com.google.gson.Gson;
import org.example.config.JavaConfig;
import org.example.model.Post;
import org.example.model.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init() {
    // отдаём список пакетов, в которых нужно искать аннотированные классы
    final var context = new AnnotationConfigApplicationContext(JavaConfig.class);
    // получаем по имени бина
    controller = (PostController) context.getBean("postController");
    // получаем по классу бина
    final var service = context.getBean(PostService.class);
    final var repository = context.getBean(PostRepository.class);

    //final var repository = new PostRepository();
    //final var service = new PostService(repository);
    //controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    //resp.getWriter().print("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      System.out.println(path);
      System.out.println(method);
      // primitive routing
      if (method.equals("GET") && path.equals("/api/posts")) {
        controller.all(resp);
        return;
      }
      if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/")+1));
        controller.getById(id, resp);
        return;
      }
      if (method.equals("POST") && path.equals("/api/posts")) {
        //System.out.println(req.getContentLength()+" ContentLength");
        //System.out.println(req.getContentType());
        String str = req.getReader().readLine();
        //System.out.println(str);
        final var gson = new Gson();
        final Post post = gson.fromJson(str, Post.class);
        //System.out.println(post.getContent());
        //controller.save(req.getReader(), resp);
        controller.save(str, resp);
        return;
      }
      if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
        // easy way
        System.out.println("@@@@@@@@@@@");
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/")+1));
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

