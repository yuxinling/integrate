package com.integrate.core.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;

public class AsyncHttpService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected static AsyncHttpClient httpClient = new AsyncHttpClient(new AsyncHttpClientConfig.Builder().setMaxRequestRetry(2).setRequestTimeoutInMs(3000).build());
}
