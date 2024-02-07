package com.mertkaanguzel.flightsearch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;


@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/")
    public RedirectView redirectToSwaggerUI() {
        return new RedirectView("swagger-ui/index.html");
    }
}
