package tool.pingan_dependency.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP鐎广垺鍩涚粩顖氬簻閸斺晝琚�
 * 
 * @author gang.nie
 * @date 2015-04-14 14:56:07
 */
public class HttpsTrustClientUtil {

	private static Logger log = LoggerFactory.getLogger(HttpsTrustClientUtil.class);

	private static final String CHAR_SET = "UTF-8";

	private static CloseableHttpClient httpClient;
	private static int socketTimeout = 30000;
	private static int connectTimeout = 30000;
	private static int connectionRequestTimeout = 30000;
	private static int maxConnTotal = 200;
	private static int maxConnPerRoute = 100;
	
	public static int getMaxConnTotal() {
		return maxConnTotal;
	}

	public static void setMaxConnTotal(int maxConnTotal) {
		HttpsTrustClientUtil.maxConnTotal = maxConnTotal;
	}

	public static int getMaxConnPerRoute() {
		return maxConnPerRoute;
	}

	public static void setMaxConnPerRoute(int maxConnPerRoute) {
		HttpsTrustClientUtil.maxConnPerRoute = maxConnPerRoute;
	}

	static {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");

			X509TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);

			clientBuilder.setSslcontext(ctx);
			clientBuilder.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// HttpHost proxy = new HttpHost("10.59.103.238", 8080, "http");  

		RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout)//.setProxy(proxy)
				.setConnectionRequestTimeout(connectionRequestTimeout).build();

		httpClient = clientBuilder.setDefaultRequestConfig(config).setMaxConnTotal(maxConnTotal)
				.setMaxConnPerRoute(maxConnPerRoute).build();

		/*
		 * X509TrustManager trustManager = new X509TrustManager() { public void
		 * checkClientTrusted(X509Certificate[] chain, String authType) throws
		 * CertificateException { // Don't do anything. }
		 * 
		 * public void checkServerTrusted(X509Certificate[] chain, String
		 * authType) throws CertificateException { // Don't do anything. }
		 * 
		 * public X509Certificate[] getAcceptedIssuers() { // Don't do anything.
		 * return null; } }; // Now put the trust manager into an SSLContext.
		 * SSLContext sslcontext; try { sslcontext =
		 * SSLContext.getInstance("SSL"); sslcontext.init(null, new
		 * TrustManager[] { trustManager }, null); // Use the above SSLContext
		 * to create your socket factory // (I found trying to extend the
		 * factory a bit difficult due to a // call to createSocket with no
		 * arguments, a method which doesn't // exist anywhere I can find, but
		 * hey-ho). SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
		 * sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		 * httpClient.getConnectionManager().getSchemeRegistry() .register(new
		 * Scheme("https", sf, 34201)); } catch (Exception e) { 
		 * Auto-generated catch block e.printStackTrace(); }
		 */

	}

	public static CloseableHttpClient getClient() {
		return httpClient;
	}

	public static String get(String url) throws IOException {
		return get(url, null, null);
	}

	public static String get(String url, Map<String, String> map) throws IOException {
		return get(url, map, null);
	}

	public static String get(String url, String charset) throws IOException {
		return get(url, null, charset);
	}

	public static String get(String url, Map<String, String> paramsMap, String charset) throws IOException {
		if (url == null || url.isEmpty()) {
			return null;
		}
		charset = (charset == null ? CHAR_SET : charset);
		if (null != paramsMap && !paramsMap.isEmpty()) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> map : paramsMap.entrySet()) {
				params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
			}
			String querystring = URLEncodedUtils.format(params, charset);
			url += "?" + querystring;
		}
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept-Encoding", "*");
		CloseableHttpResponse response = getClient().execute(httpGet);
		// 閻樿鎷�閿燂拷鎴掔瑝娑擄拷锟介惃鍕磽鐢顦╅悶鍡嫹1閿燂拷锟斤拷
		return EntityUtils.toString(response.getEntity(), charset);
	}

	public static String delete(String url, Map<String, String> paramsMap, String charset) throws IOException {
		if (url == null || url.isEmpty()) {
			return null;
		}
		charset = (charset == null ? CHAR_SET : charset);
		if (null != paramsMap && !paramsMap.isEmpty()) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> map : paramsMap.entrySet()) {
				params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
			}
			String querystring = URLEncodedUtils.format(params, charset);
			url += "?" + querystring;
		}
		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.addHeader("Accept-Encoding", "*");
		CloseableHttpResponse response = getClient().execute(httpDelete);
		// 閻樿鎷�閿燂拷鎴掔瑝娑擄拷锟介惃鍕磽鐢顦╅悶鍡嫹1閿燂拷锟斤拷
		return EntityUtils.toString(response.getEntity(), charset);
	}

	public static String post(String url, String request) throws IOException {
		return post(url, request, null);
	}

	public static String post(String url, String request, String charset) throws IOException {
		if (url == null || url.isEmpty()) {
			return null;
		}
		charset = (charset == null ? CHAR_SET : charset);
		CloseableHttpResponse response = null;
		String res = null;
		try {
			StringEntity entity = new StringEntity(request, charset);
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			httpPost.addHeader("Accept-Encoding", "*");
			httpPost.setEntity(entity);
			response = getClient().execute(httpPost);
			res = EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return res;
	}

	public static String post(String url, Map<String, String> map) throws IOException {
		return post(url, map, null);
	}

	public static String post(String url, Map<String, String> paramsMap, String charset) throws IOException {
		if (url == null || url.isEmpty()) {
			return null;
		}
		charset = (charset == null ? CHAR_SET : charset);
		CloseableHttpResponse response = null;
		String res = null;
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> map : paramsMap.entrySet()) {
				params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, charset);
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Accept-Encoding", "*");
			httpPost.setEntity(formEntity);
			response = getClient().execute(httpPost);
			res = EntityUtils.toString(response.getEntity());
			// 閻樿鎷�閿燂拷鎴掔瑝娑擄拷锟介惃鍕磽鐢顦╅悶鍡嫹1閿燂拷锟斤拷
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException(res);
			}
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return res;
	}

	/**
	 * Put閺傜懓绱￠幓鎰唉
	 * 
	 * @param url
	 * @param paramsMap
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String put(String url, Map<String, String> paramsMap, String charset) throws IOException {
		if (url == null || url.isEmpty()) {
			return null;
		}
		charset = (charset == null ? CHAR_SET : charset);
		CloseableHttpResponse response = null;
		String res = null;
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> map : paramsMap.entrySet()) {
				params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, charset);
			HttpPut httpPut = new HttpPut(url);
			httpPut.addHeader("Accept-Encoding", "*");
			httpPut.setEntity(formEntity);
			response = getClient().execute(httpPut);
			res = EntityUtils.toString(response.getEntity());
			// 閻樿鎷�閿燂拷鎴掔瑝娑擄拷锟介惃鍕磽鐢顦╅悶鍡嫹1閿燂拷锟斤拷
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException(res);
			}
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return res;
	}

	/**
	 * Titel 閹绘劒姘TTP鐠囬攱鐪伴敍宀冨箯瀵版鎼锋惔锟斤拷application/x-www-form-urlencoded) Description
	 * 娴ｈ法鏁ameValuePair鐏忎浇顥婇崣鍌涙殶閿涘矉鎷�閿燂拷鐣屾暏娴滃簼绗呮潪鑺ユ瀮娴犺鎷�閿燂拷锟斤拷
	 * 
	 * @param serviceURI
	 *            閹恒儱褰涢崷鏉挎絻
	 * @param timeOut
	 *            鐡掑懏妞傞弮鍫曟？
	 * @param params
	 *            鐠囬攱鐪伴崣鍌涙殶
	 * @param charset
	 *            閸欏倹鏆熺紓鏍垳
	 * @return
	 * @author MOSHIHONG930
	 * @throws Exception
	 */
	public static CloseableHttpResponse submitPostHttpReq(final String serviceURI, final int timeOut,
			final Map<String, String> pmap, final String charset) throws Exception {


		// 闁秴宸荤亸浣筋棅閸欏倹鏆�
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		for (String key : pmap.keySet()) {
			NameValuePair pair = new BasicNameValuePair(key, pmap.get(key));
			formParams.add(pair);
		}

		// 鏉烆剚宕叉稉绡簉om Entity
		UrlEncodedFormEntity fromEntity = null;
		try {
			fromEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		}

		// 閸掓稑缂揾ttp post鐠囬攱鐪�
		HttpPost httpPost = new HttpPost(serviceURI);
		httpPost.setEntity(fromEntity);

		// 鐠佸墽鐤嗙拠閿嬬湴閸滃奔绱舵潏鎾圭Т閺冭埖妞傞梻锟斤拷
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut)
				.build();

		httpPost.setConfig(requestConfig);

		// 閹绘劒姘︾拠閿嬬湴閵嗕浇骞忛崣鏍ф惙鎼达拷锟�
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
		} catch (IOException e) {
			throw new Exception(e);
		}

		return response;
	}

	public static int getSocketTimeout() {
		return socketTimeout;
	}

	public static void setSocketTimeout(int socketTimeout) {
		HttpsTrustClientUtil.socketTimeout = socketTimeout;
	}

	public static int getConnectTimeout() {
		return connectTimeout;
	}

	public static void setConnectTimeout(int connectTimeout) {
		HttpsTrustClientUtil.connectTimeout = connectTimeout;
	}

	public static int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public static void setConnectionRequestTimeout(int connectionRequestTimeout) {
		HttpsTrustClientUtil.connectionRequestTimeout = connectionRequestTimeout;
	}

	public String postJsonWithProtocol10(String url, Object request) throws Exception{
			if (url == null || url.isEmpty()) {
				return null;
			}
			CloseableHttpResponse response = null;
			final String jsonString = JSONObject.toJSONString(request);//闂囷拷鐟曚椒绱舵潏鎾舵畱鐎电钖�
			log.info("Https url:{} post:{}",url,jsonString);
			long startTime = System.currentTimeMillis();
			try {
				HttpPost httpPost = new HttpPost(url);
				httpPost.setProtocolVersion(HttpVersion.HTTP_1_0);
				httpPost.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
				response = httpClient.execute(httpPost);
				return handleResponse(response);
			} catch (IOException e) {
				throw new IOException(e);
			} finally {
				log.info("https invoke cost:{}ms",System.currentTimeMillis()-startTime);
				if (response != null) {
					response.close();
				}
			}
	}

	public String postJson(String url, Object request) throws Exception{
		if (url == null || url.isEmpty()) {
			return null;
		}
		CloseableHttpResponse response = null;
		final String jsonString = JSONObject.toJSONString(request);//闂囷拷鐟曚椒绱舵潏鎾舵畱鐎电钖�
		log.info("Https url:{} post:{}",url,jsonString);
		long startTime = System.currentTimeMillis();
		try {
			HttpPost httpPost = new HttpPost(url);
			Header header = new BasicHeader("Accept","application/json");
			httpPost.setHeader(header );
			httpPost.setHeader("Content-type", "application/json;charset=utf-8");
			httpPost.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
			response = httpClient.execute(httpPost);
			return handleResponse(response);
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			log.info("https invoke cost:{}ms",System.currentTimeMillis()-startTime);
			if (response != null) {
				response.close();
			}
		}
}
	
	private String handleResponse(CloseableHttpResponse response) throws IOException{
		 try {
	            int statusCode = response.getStatusLine().getStatusCode();
	            log.debug("https post response code : {}", statusCode);
	            if (statusCode == HttpStatus.SC_OK) {
	                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
	                log.info("https post response:{}", result);
	                return result;
	            } else {
	                EntityUtils.consume(response.getEntity());
	                return null;
	            }
	        } finally {
	            response.close();
	            response = null;
	        }
	}

}
