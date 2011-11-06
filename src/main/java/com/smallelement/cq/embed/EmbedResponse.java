package com.smallelement.cq.embed;

public interface EmbedResponse {

    String getType();

    String getVersion();

    String getTitle();

    String getAuthorName();

    String getProviderUrl();

    String getCacheAge();

    String getThumbnailUrl();

    int getThumbnailWidth();

    int getThumbnailHeight();

    String getUrl();

    String getHtml();

    int getWidth();

    int getHeight();
}