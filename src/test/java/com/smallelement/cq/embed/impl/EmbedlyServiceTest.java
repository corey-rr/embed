package com.smallelement.cq.embed.impl;

import com.smallelement.cq.embed.EmbedResponse;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbedlyServiceTest {

    private static final Logger log = LoggerFactory.getLogger(EmbedlyServiceTest.class);

    private static EmbedlyService embedlyService = new EmbedlyService();

    @Test
    public void getEmbed() throws Exception {
        //EmbedResponse response = embedlyService.getEmbed("http://vimeo.com/18150336", 500, 0);
        //assertTrue(response.getType().equals("video"));
    }
}
