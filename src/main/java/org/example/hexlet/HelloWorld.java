package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {

    static List<Course> coursesAll = List.of(new Course(1, "JTE1", "One"), new Course(2,"JTE2", "Two"), new Course(3, "JTE3", "Three"));


    public static void main(String[] args) {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        // Описываем, что загрузится по адресу /
        //app.get("/", ctx -> ctx.result("Hello World"));
        app.get("/", ctx -> ctx.render("index.jte"));
        app.get("/hello", ctx -> {
            String name = ctx.queryParamAsClass("name", String.class).getOrDefault("Unknown");
            ctx.result("Hello, " + name);
        });

        app.get("/courses", ctx -> {
            var courses = coursesAll;
            var header = "Programming Courses";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
        });

        app.get("/courses/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).getOrDefault(1);
            var course = coursesAll.get(id - 1); // not safe, but ok for jte tests
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get("/courses/{courseId}/lessons/{id}", ctx -> {
            var courseId = ctx.pathParam("courseId");
            var lessonId =  ctx.pathParam("id");
            ctx.contentType("text/html");
            ctx.result("<h1>Course ID: " + courseId + " Lesson ID: " + lessonId+"</h1>");
        });
        app.start(7070);
    }
}
