package org.example.hexlet;

import io.javalin.Javalin;

public class HelloWorld {
    public static void main(String[] args) {
        // Создаем приложение
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });
        // Описываем, что загрузится по адресу /
        app.get("/", ctx -> ctx.result("Hello World"));
        app.get("/hello", ctx -> {
            String name = ctx.queryParamAsClass("name", String.class).getOrDefault("Unknown");
            ctx.result("Hello, " + name);
        });
        app.get("/courses/{courseId}/lessons/{id}", ctx -> {
            var courseId = ctx.pathParam("courseId");
            var lessonId =  ctx.pathParam("id");
            ctx.result("Course ID: " + courseId + " Lesson ID: " + lessonId);
        });
        app.start(7070);
    }
}
