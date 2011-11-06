package com.smallelement.cq.embed.impl;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

@Component(metatype=false)
@Service
@Properties({
    @Property(name="sling.servlet.resourceTypes", value={"smallelement/embed/components/embed"}),
    @Property(name="sling.servlet.extensions", value={"html"})
})
public class EmbedServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        Resource resource = request.getResource();

        ValueMap vm = resource.adaptTo(ValueMap.class);

        response.setContentType("text/html");
        response.getWriter().write(vm.get("html", "No Content Available"));
    }
}