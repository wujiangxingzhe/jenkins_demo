// src/main/java/com/example/SimpleWebServer/Main.java
package com.example.SimpleWebServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = new String(Files.readAllBytes(Paths.get("src/main/resources/index.html")));
        resp.setContentType("text/html");
        resp.setContentLength(response.length());
        OutputStream os = resp.getOutputStream();
        os.write(response.getBytes());
        os.close();
    }
}