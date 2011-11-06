package com.smallelement.cq.embed.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.smallelement.cq.embed.EmbedResponse;
import com.smallelement.cq.embed.EmbedService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

@Component(immediate = true)
@Service(value = EmbedService.class)
public class EmbedlyService implements EmbedService {

    private static final Logger log = LoggerFactory.getLogger(EmbedlyService.class);

    @Property(value = "")
    static final String PROPERTY_API_KEY = "service.api_key";

    private String apiUrl = "http://api.embed.ly/1/oembed";

    private String apiKey = "";

    public EmbedlyService() {
    }

    public EmbedResponse getEmbed(String url, int maxWidth, int maxHeight) {
        return getResponse(url, maxWidth, maxHeight);
    }

    public EmbedResponse getEmbed(Resource resource) {
        EmbedResponse response = null;
        try {
            final String url = resource.adaptTo(ValueMap.class).get("embedUrl", "");

            response = getResponse(url, 500, 0);

            Map map = BeanUtils.describe(response);
            map.put("embedUrl", url);

            PersistableValueMap pvm = resource.adaptTo(PersistableValueMap.class);
            pvm.putAll(merge(pvm, map));
            pvm.save();
        } catch (Exception e) {
            log.error("error", e);
        }

        return response;
    }

    private EmbedResponse getResponse(String url, int maxWidth, int maxHeight) {
        log.info("here");
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) requestUrl(url, maxWidth, maxHeight).openConnection();
            connection.connect();
            if (connection.getResponseCode() == 200) {
                GsonBuilder gson = new GsonBuilder();
                gson.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                Reader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "UTF-8"));
                return gson.create().fromJson(reader, EmbedlyResponse.class);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
                connection = null;
            }
        }

        return null;
    }

    private URL requestUrl(String url, int maxWidth, int maxHeight)
            throws MalformedURLException {
        StringBuffer requestUrl = new StringBuffer(this.apiUrl)
                .append("?url=").append(url)
                .append("&key=").append(this.apiKey)
                .append("&format=").append("json");
        if (maxWidth > 0) {
            requestUrl.append("&maxwidth=").append(maxWidth);
        }
        if (maxHeight > 0) {
            requestUrl.append("&maxheight=").append(maxHeight);
        }

        return new URL(requestUrl.toString());
    }

    private static Map copy(Map source) {
        if (source == null) {
            return null;
        }

        Map result = new HashMap();
        result.putAll(source);
        return result;
    }

    private static Map merge(Map map1, Map map2) {
        Map result = null;

        if ((map1 == null) || (map1.size() == 0)) {
            result = copy(map2);
        } else if ((map2 == null) || (map2.size() == 0)) {
            result = copy(map1);
        } else {
            result = copy(map1);
            result.putAll(map2);
        }

        return result;
    }

    @Activate
    protected void activate(ComponentContext context) {
        Dictionary properties = context.getProperties();
        this.apiKey = (String) properties.get(PROPERTY_API_KEY);
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {

    }
}
