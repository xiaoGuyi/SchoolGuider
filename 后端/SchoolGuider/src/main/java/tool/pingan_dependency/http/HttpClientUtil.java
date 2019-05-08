/**
 * PING AN INSURANCE (GROUP) COMPANY OF CHINA ,LTD. Copyright (c) 1988-2014 All Rights Reserved.
 */
package tool.pingan_dependency.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author YANGCHANGPENG869
 * @version $Id: HttpClientUtil.java, v 0.1 2014年12月24日 下午5:12:38 YANGCHANGPENG869 Exp $
 */
public class HttpClientUtil {

    static private final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    // private static final String DEFAULT_CHARSET = "UTF-8";

    private HttpClientFactory httpClientFactory;

    private int                 connTimeout;
    private int                 connRequestTimeout;
    private int                 socketTimeout;

    public String doGet(CloseableHttpClient httpclient, String url) {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("Parameter url is null.");
        }
        if (httpclient == null) {
            throw new NullPointerException("Parameter httpclient is null.");
        }
        logger.debug("http url : {}", url);
        return doGet(httpclient, url, null);
    }

    public String doGet(CloseableHttpClient httpclient, String baseUrl, Map<String, String> params) {
        if (StringUtils.isBlank(baseUrl)) {
            throw new NullPointerException("Parameter url is null.");
        }
        if (httpclient == null) {
            throw new NullPointerException("Parameter httpclient is null.");
        }
        logger.debug("http baseUrl : {}", baseUrl);
        String url = baseUrl;
        if (params != null && params.size() > 0) {
            url += "?" + buildGetParams(params);
        }
        long startTime = System.currentTimeMillis();
        try {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(get);
            return handleResponse(response);
        } catch (UnsupportedEncodingException e) {
            logger.error("Http get request set entity failure.");
            throw new RuntimeException("httpclientUtil exception", e);
        } catch (Exception e) {
            logger.error("Http get request to {} fail,exception msg {}", url, e.getMessage());
            throw new RuntimeException("httpclientUtil exception", e);
        } finally {
            logger.info("http get request cost:{} ms", (System.currentTimeMillis() - startTime));
        }
    }

    public String doGetWithHeader(CloseableHttpClient httpclient, String baseUrl,
                                  Map<String, String> headers) {
        if (StringUtils.isBlank(baseUrl)) {
            throw new NullPointerException("Parameter url is null.");
        }
        if (httpclient == null) {
            throw new NullPointerException("Parameter httpclient is null.");
        }
        logger.debug("http url : {}", baseUrl);
        HttpGet get = new HttpGet(baseUrl);
        if (headers != null) {
            setHttpHeaders(get, headers);
        }
        long startTime = System.currentTimeMillis();
        try {
            CloseableHttpResponse response = httpclient.execute(get);
            return handleResponse(response);
        } catch (UnsupportedEncodingException e) {
            logger.error("Http get request set entity failure.");
            throw new RuntimeException("httpclientUtil exception", e);
        } catch (Exception e) {
            logger.error("Http get request to {} fail,exception msg {}", baseUrl, e.getMessage());
            throw new RuntimeException("httpclientUtil exception", e);
        } finally {
            logger.info("http get request cost:{} ms", (System.currentTimeMillis() - startTime));
        }
    }

    private void setHttpHeaders(HttpRequestBase get, Map<String, String> headers) {
        if (headers != null) {
            logger.debug("Set http header:{}", headers);
            for (Map.Entry<String, String> e : headers.entrySet()) {
                String name = e.getKey();
                String value = e.getValue();
                get.setHeader(new BasicHeader(name, value));
            }
        }
    }

    private String buildGetParams(Map<String, String> params) {
        if (params != null) {
            logger.debug("Set get params:{}", params);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> e : params.entrySet()) {
                String name = e.getKey();
                String value = e.getValue();
                nvps.add(new BasicNameValuePair(name, value));
            }
            String result = URLEncodedUtils.format(nvps, Charsets.UTF_8);
            return result;
        }
        return "";
    }

    public String doGet(String baseUrl, Map<String, String> params) {
        return doGet(baseUrl, params, null);
    }

    public String doGet(String baseUrl, Map<String, String> params, Map<String, String> headers) {
        if (StringUtils.isBlank(baseUrl)) {
            return null;
        }
        logger.debug("http baseUrl:{}, params:{}", baseUrl, params);
        String url = "";
        if (params == null) {
        	url = baseUrl;
        } else {
        	url = baseUrl + "?" + buildGetParams(params);
        }
        return doGetWithHeader(buildHttpClient(), url, headers);
    }

    public String doGet(String url) {
        return doGet(url, null);
    }

    public String httpPostRequest(CloseableHttpClient httpclient, String url,
                                  Map<String, String> params) {
        String result = httpPostRequest(httpclient, url, params, null);
        return result;
    }

    public String httpPostJson(String url, String json) {
    	logger.info("http url:{}, params:{}", url, json);
    	Map<String, String> headers = new HashMap<String, String>();
    	headers.put("Content-Type", "application/json");
        return httpPostRequest(buildHttpClient(), url, json, headers);
    }

    public String httpPostJson(String url, String json, Map<String, String> headers) {
    	logger.info("http url:{}, params:{}, headers:{}", url, json, headers);
        return httpPostRequest(buildHttpClient(), url, json, headers);
    }
    
    public String httpPostJson(String url,Map<String,String> params){
    	logger.info("http url:{}, params:{}", url, params);
    	return httpPostRequest(buildHttpClient(), url, JSONObject.toJSONString(params), null);
    }
    
    public String httpPostString(String url, String requestStr) throws IOException {
        return httpPostString(buildHttpClient(), url, requestStr, null);
    }

    public String httpPostString(CloseableHttpClient httpclient, String url, String requestStr,
                                 Map<String, String> headers) throws IOException {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("Parameter url is null.");
        }
        if (httpclient == null) {
            throw new NullPointerException("Parameter httpclient is null.");
        }
        HttpPost post = new HttpPost(url);
        setHttpHeaders(post, headers);
        long startTime = System.currentTimeMillis();
        try {
            post.setEntity(new StringEntity(requestStr, ContentType.DEFAULT_TEXT));
            CloseableHttpResponse response = httpclient.execute(post);
            return handleResponse(response);
        } catch (UnsupportedEncodingException e) {
            logger.error("Http post String set entity failure.");
            throw new RuntimeException("httpclientUtil post exception", e);
        } catch (Exception e) {
            logger.error("Http post String to {} fail,exception msg {}", url, e.getMessage());
            throw new RuntimeException("httpclientUtil post exception", e);
        } finally {
            logger.info("http post String cost:{} ms", (System.currentTimeMillis() - startTime));
        }
    }

    public String httpPostRequest(CloseableHttpClient httpclient, String url, String json,
                                  Map<String, String> headers) {
        if (httpclient == null) {
            throw new NullPointerException("Parameter httpclient is null.");
        }
        HttpPost post = new HttpPost(url);
        // set request http headers
        setHttpHeaders(post, headers);
        long startTime = System.currentTimeMillis();
        try {
            post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = httpclient.execute(post);
            return handleResponse(response);
        } catch (UnsupportedEncodingException e) {
            logger.error("Http post request set entity failure.");
            throw new RuntimeException("httpclientUtil post exception", e);
        } catch (Exception e) {
            logger.error("Http post request to {} fail,exception msg {}", url, e.getMessage());
            throw new RuntimeException("httpclientUtil post exception", e);
        } finally {
            logger.info("http post request cost:{} ms", (System.currentTimeMillis() - startTime));
        }
    }

    public String httpPostRequest(CloseableHttpClient httpclient, String url,
                                  Map<String, String> params, Map<String, String> headers) {
        if (httpclient == null) {
            throw new NullPointerException("Parameter httpclient is null.");
        }

        logger.info("Http post request [{}] by params:{} and headers:{}", new Object[] { url,
                params, headers });

        HttpPost post = new HttpPost(url);
        // set request http headers
        setHttpHeaders(post, headers);
        // set request http params
        setHttpEntityRequest(post, params);

        long startTime = System.currentTimeMillis();

        try {
            CloseableHttpResponse response = httpclient.execute(post);
            return handleResponse(response);
        } catch (UnsupportedEncodingException e) {
            logger.error("Http post request set entity failure.");
            throw new RuntimeException("httpclientUtil post exception", e);
        } catch (Exception e) {
            logger.error("Http post request to {} fail,exception msg {}", url, e.getMessage());
            throw new RuntimeException("httpclientUtil post exception", e);
        } finally {
            logger.info("http post request cost:{} ms", (System.currentTimeMillis() - startTime));
        }
    }

    private String handleResponse(CloseableHttpResponse response) throws IOException {
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("http post response code : {}", statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("http post response:{}", result);
                return result;
            } else {
                //EntityUtils.consume(response.getEntity());
                //return null;
            	String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("http post response:{}", result);
                return result;
            }
        } finally {
            response.close();
        }
    }

    public CloseableHttpResponse getHttpResponseByHttpPost(String url, Map<String, String> params) {
        return getHttpResponseByHttpPost(url, params, null);
    }

    public CloseableHttpResponse getHttpResponseByHttpPost(String url, Map<String, String> params,
                                                           Map<String, String> headers) {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("http url is null");
        }
        logger.info("Http post request [{}] by params:{} and headers:{}", new Object[] { url,
                params, headers });
        HttpPost post = new HttpPost(url);
        // set request http headers
        setHttpHeaders(post, headers);
        // set request http params
        setHttpEntityRequest(post, params);
        long startTime = System.currentTimeMillis();
        try {
            CloseableHttpResponse response = buildHttpClient().execute(post);
            return response;
        } catch (Exception e) {
            logger.error("Http post request to {} fail,exception msg {}", url, e.getMessage());
            throw new RuntimeException("httpclientUtil post exception", e);
        } finally {
            logger.info("http post request cost:{} ms", (System.currentTimeMillis() - startTime));
        }
    }
    
    public CloseableHttpResponse getHttpResponseByJson(String url, String json) {
        return getHttpResponseByJson(url, json, null);
    }

    public CloseableHttpResponse getHttpResponseByJson(String url, String json,
                                                           Map<String, String> headers) {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("http url is null");
        }
        logger.info("Http post request [{}] by json:{} and headers:{}", new Object[] { url,	json, headers });
        HttpPost post = new HttpPost(url);
        // set request http headers
        setHttpHeaders(post, headers);
        long startTime = System.currentTimeMillis();
        try {
            post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = buildHttpClient().execute(post);
            return response;
        } catch (UnsupportedEncodingException e) {
            logger.error("Http post request set entity failure.");
            throw new RuntimeException("httpclientUtil post exception", e);
        } catch (Exception e) {
            logger.error("Http post request to {} fail,exception msg {}", url, e.getMessage());
            throw new RuntimeException("httpclientUtil post exception", e);
        } finally {
            logger.info("http post request cost:{} ms", (System.currentTimeMillis() - startTime));
        }
    }

    public String httpPostRequest(String url, Map<String, String> params) {
        return httpPostRequest(url, params, null);
    }

    public String httpPostRequest(String url, Map<String, String> params,
                                  Map<String, String> headers) {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("Parameter url is null.");
        }
        logger.debug("Http post request url:{} by params:{} and headers:{}", new Object[] { url,
                params, headers });
        String result = httpPostRequest(buildHttpClient(), url, params, headers);
        return result;
    }

    /**
     * 
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public String doPut(String url, Map<String, String> params, Map<String, String> headers) {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("Parameter url is null.");
        }
        logger.debug("Http post request url:{} by params:{} and headers:{}", new Object[] { url,
                params, headers });
        String result = doPut(buildHttpClient(), url, params, headers);
        return result;
    }

    public String doPut(CloseableHttpClient httpclient, String url, Map<String, String> params,
                        Map<String, String> headers) {
        if (httpclient == null) {
            throw new NullPointerException("Parameter httpclient is null.");
        }
        logger.info("Http put request [{}] by params:{} and headers:{}", new Object[] { url,
                params, headers });
        HttpPut put = new HttpPut(url);
        // set request http headers
        setHttpHeaders(put, headers);
        // set request http params
        setHttpEntityRequest(put, params);
        long startTime = System.currentTimeMillis();
        try {
            CloseableHttpResponse response = httpclient.execute(put);
            return handleResponse(response);
        } catch (UnsupportedEncodingException e) {
            logger.error("Http put request set entity failure.");
            throw new RuntimeException("httpclientUtil put exception", e);
        } catch (Exception e) {
            logger.error("Http put request to {} fail,exception msg {}", url, e.getMessage());
            throw new RuntimeException("httpclientUtil put exception", e);
        } finally {
            logger.info("http put request cost:{} ms", (System.currentTimeMillis() - startTime));
        }
    }

    public String doPut(String url, String json, Map<String, String> headers) {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("Parameter url is null.");
        }
        logger.debug("Http post request url:{} by json:{} and headers:{}", new Object[] { url,
                json, headers });
        String result = doPut(buildHttpClient(), url, json, headers);
        return result;
    }

    public String doPut(CloseableHttpClient httpclient, String url, String json,
                        Map<String, String> headers) {
        if (httpclient == null) {
            throw new NullPointerException("Parameter httpclient is null.");
        }
        logger.info("Http put request [{}] by json:{} and headers:{}", new Object[] { url, json,
                headers });
        HttpPut put = new HttpPut(url);
        // set request http headers
        setHttpHeaders(put, headers);
        // set request http body
        put.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        
        long startTime = System.currentTimeMillis();
        try {
            CloseableHttpResponse response = httpclient.execute(put);
            return handleResponse(response);
        } catch (UnsupportedEncodingException e) {
            logger.error("Http put request set entity failure.");
            throw new RuntimeException("httpclientUtil put exception", e);
        } catch (Exception e) {
            logger.error("Http put request to {} fail,exception msg {}", url, e.getMessage());
            throw new RuntimeException("httpclientUtil put exception", e);
        } finally {
            logger.info("http put request cost:{} ms", (System.currentTimeMillis() - startTime));
        }
    }

    /**
     * Titel 提交HTTP请求，获得响应(application/x-www-form-urlencoded) Description
     * 使用NameValuePair封装参数，适用于下载文件。
     * 
     * @param serviceURI
     *            接口地址
     * @param timeOut
     *            超时时间
     * @param pmap
     *            请求参数
     * @param charset
     *            参数编码
     * @return
     * @author MOSHIHONG930
     * @throws Exception
     */
    public CloseableHttpResponse submitPostHttpReq(final String serviceURI, final int timeOut,
                                                   final Map<String, String> pmap,
                                                   final String charset) throws Exception {
        logger.info("即将发起HTTP请求！serviceURI：" + serviceURI);
        // 遍历封装参数
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for (String key : pmap.keySet()) {
            NameValuePair pair = new BasicNameValuePair(key, pmap.get(key));
            formParams.add(pair);
        }
        // 转换为from Entity
        UrlEncodedFormEntity fromEntity = null;
        try {
            fromEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("创建UrlEncodedFormEntity异常！" + e.getMessage());
            throw new Exception(e);
        }
        // 创建http post请求
        HttpPost httpPost = new HttpPost(serviceURI);
        httpPost.setEntity(fromEntity);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut)
            .setConnectTimeout(timeOut).build();

        httpPost.setConfig(requestConfig);

        // 提交请求、获取响应
        logger.info("提交http请求！serviceURI：" + serviceURI);
        CloseableHttpClient httpclient = buildHttpClient();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
        } catch (IOException e) {
            logger.error("httpclient.execute异常！" + e.getMessage());
            throw new Exception(e);
        }

        logger.info("提交http请求！serviceURI：" + serviceURI);
        return response;
    }

    /**
     * 
     * @return
     */
    private CloseableHttpClient buildHttpClient() {
        if (connTimeout > 0 && connRequestTimeout > 0 && socketTimeout > 0) {
            return this.getClientFactory().build(connTimeout, connRequestTimeout, socketTimeout);
        } else {
            return this.getClientFactory().build();
        }
    }

    private void setHttpEntityRequest(HttpEntityEnclosingRequestBase request,
                                      Map<String, String> params) {
        if (params != null) {
            logger.debug("Set http entity params:{}", params);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> e : params.entrySet()) {
                String name = e.getKey();
                String value = e.getValue();
                nvps.add(new BasicNameValuePair(name, value));
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, Charsets.UTF_8));
        }
    }

    private HttpClientFactory getClientFactory() {
        if (httpClientFactory == null) {
            httpClientFactory = new HttpClientFactory();
        }
        return httpClientFactory;
    }

    public HttpClientFactory getHttpClientFactory() {
        return httpClientFactory;
    }

    public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public int getConnRequestTimeout() {
        return connRequestTimeout;
    }

    public void setConnRequestTimeout(int connRequestTimeout) {
        this.connRequestTimeout = connRequestTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

}
