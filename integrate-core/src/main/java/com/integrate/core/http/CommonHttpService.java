package com.integrate.core.http;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.integrate.common.util.JsonUtils;
import com.integrate.common.util.XstreamUtils;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Future;

@Component
public class CommonHttpService extends AsyncHttpService {
    private Logger logger = LoggerFactory.getLogger(getClass());


    public <T> T sendPost(String url, String content, Class<T> clazz) {
        return sendPost(url, content, clazz, httpClient);
    }


    private <T> T sendPost(String url, String content, Class<T> clazz, AsyncHttpClient httpClient) {
        try {
            Future<Response> f = httpClient.preparePost(url)
                    .setBodyEncoding(Charsets.UTF_8.name()).setBody(content).execute();

            String responseBody = f.get().getResponseBody(Charsets.UTF_8.name());

            logger.info("sendPost url:{}, content:{}, response:{}", url, content, responseBody);
            return JsonUtils.readValue(responseBody, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T sendXmlPost(String url, Object xmlObj, Class<T> clazz) {
        String dataStr = XstreamUtils.objectToXml(xmlObj);

        try {
            Future<Response> f = httpClient.preparePost(url).setHeader("Content-Type", "text/xml")
                    .setBodyEncoding("UTF-8").setBody(dataStr).execute();

            String responseBody = f.get().getResponseBody("UTF-8");
            return XstreamUtils.convertFromXml(responseBody, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T sendGet(String url, Map<String, Object> map, Class<T> clazz) {
        return sendGet(url, map, clazz, httpClient);
    }


    private <T> T sendGet(String url, Map<String, Object> map, Class<T> clazz, AsyncHttpClient httpClient) {
        try {
            AsyncHttpClient.BoundRequestBuilder builder = httpClient.prepareGet(url).setBodyEncoding(Charsets.UTF_8.name());
            if(map!=null && map.size()>0) {
                for(Map.Entry<String,Object> entry: map.entrySet()) {
                    builder.addQueryParameter(entry.getKey(), entry.getValue().toString());
                }
            }

            Future<Response> f = builder.execute();
            String responseBody = f.get().getResponseBody(Charsets.UTF_8.name());

            logger.info("sendGet url:{}, content:{}, response:{}", url, JSON.toJSONString(map), responseBody);
            return JsonUtils.readValue(responseBody, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
