package com.smallelement.cq.embed.impl;

import com.smallelement.cq.embed.EmbedService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.servlets.post.Modification;
import org.apache.sling.servlets.post.SlingPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
@Service
@Property(name="sling.servlet.resourceTypes", value={"smallelement/embed"})
public class EmbedPostProcessor implements SlingPostProcessor {

    @Reference
    EmbedService embedService;

    private static final Logger log = LoggerFactory.getLogger(EmbedPostProcessor.class);

    public void process(SlingHttpServletRequest slingHttpServletRequest,
                        List<Modification> modifications) throws Exception {
        if (embedService != null) {
            embedService.getEmbed(slingHttpServletRequest.getResource());
        }
    }
}
