package com.smallelement.cq.embed;

import org.apache.sling.api.resource.Resource;

public interface EmbedService {

    EmbedResponse getEmbed(String url, int maxWidth, int maxHeight);

    EmbedResponse getEmbed(Resource resource);
}