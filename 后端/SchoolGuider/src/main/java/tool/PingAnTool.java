package tool;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tool.pingan_dependency.http.HttpClientFactory;
import tool.pingan_dependency.http.HttpClientUtil;
import tool.pingan_dependency.utils.AESCBCUtils;
import tool.pingan_dependency.utils.Base64Utils;
import tool.pingan_dependency.utils.DateUtil;
import tool.pingan_dependency.utils.RSAUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码示例，仅供参考，如需生产环境使用，请自行优化。
 */
public class PingAnTool {
	
	static private final Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);
	
	private static String orgCode="IPMS_ZNYX";
	private static String channelId="N";
	/**
	 * 客户端RSA私钥
	 */
	private static String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN1yQiOuTmezwiC3K2PU+qcWsfWRF3CWbBPjt4Z4G21H4shXtcSw6gIoPaN7UK4MqVRy94aJ9C8kZEPLBZnPqZelk1fHD2JZO29zP20Ap5EUkpg6EtshK3Un35VzcorrWdAbvE7O5U7IfUBEBmGwZyE+fVJbeZOwT87R8KZNrPU5AgMBAAECgYEAo5M029pzvBKCgVwMRxAZnrca+UQVxmq2pvJ8rG2oBM8m68ouUH5GPRWwgkQi0o4zvle2E8FviCsfjRw4pOdKY4QV/YS7vOPI+vsPl0/UDoXIFTQeWv2V9O996a2zjWmLIS6wUcWTD4k8P6ZD0IQKRdzqQPg6woTKt23HicH3b6kCQQD57vn9Fol7kbWdutZARZSYPkZESDoh4rgx9pnYY9of00qc/KDv1lirlIYCsZyK3cn8yd7HBC6M69MKC/5L1OwzAkEA4tJFDA6P9IeaRinSJAEoe+noEaF4dV0jGV43GX2pgIwJ2SWyLSu5HiBi1tHc3r61oi14n0z9UsmBKAF3+N5s4wJBAKawkMAQEN9+Ha+11YEGJwWmuvx9Y3AIIqsH+jFM30RMij3La5ap0XeV1g7j4DDpnPZguJjpHBbWjWjs+MwglOsCQAeiiEx1mQbkIZ9G/RnVKCWzo+okA0gAEJRkBXrILhJzpEk46Zx3ok2PTYRpoAOna7m/yhWio8xLmBteoUkxQ30CQQCvgJPmpJDg+57gWF+OoXBtlIpl0ApX2xeP6dK1AqXePTD/Yr7+WAMEzeivorJ1bMQjM0Xkz9czYhDn5ie3/94A";
			
	/**
	 * 服务端RSA公钥
	 */
	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDqGVBctQCoMySiTxFZ/ls6lQOPiTKTky2udYkGUQF8gwLpQRDLh5WS3+3MHU+7cdizgiBO8C7+G4UzIvdRICTsYGIxS5mDqZROqJC+Jt5uVhHH7YCH3HLd5vOCDSJ2GQ4K6xxrOCcL/Hz5lw1SN0I3Vyxivy4J33N5U3ukWPGsnwIDAQAB";
	
	private static String apiUrl = "https://ipms.pingan.com/openapi/";
	
	
	/**
	 * 微表情识别接口
	 */
	@Test
	public void emotion() throws Exception {
		
        String fileBase64 = Base64Utils.encodeFile("E:\\Java\\IDEA_Personal_File\\xai\\src\\main\\webapp\\faceCrossAgefaceCrossAge1547898182.jpg");
        Map<String,Object> p = new HashMap<String,Object>();
        p.put("data", fileBase64);
        p.put("type", 0);
		String requestDataStr = JSONObject.toJSONString(p);
		
		String aesKey = getRandomCharacterAndNumber(16);
		
		/*
		 * 封装请求报文
		 */
		String requestJson = mkRequest(aesKey,requestDataStr);
		
		String responseStr =  new HttpClientUtil().httpPostJson(apiUrl+"ai/emotion", requestJson);
		JSONObject response = JSONObject.parseObject(responseStr);
		String responseCode = response.getString("responseCode");
		if("000000".equals(responseCode)){
			String responseDataStr = response.getString("responseData");
			/*
			 * 返回报文解密
			 */
			String responseData = AESCBCUtils.decrypt(responseDataStr, aesKey);
			logger.info("业务返回参数--{}",responseData);
            Pattern pattern = Pattern.compile( "\"emot\":(.*?),\"in_whitelist\"" );
            Matcher matcher = pattern.matcher( responseData );
            if( matcher.find() ) {
                System.out.println( matcher.group(1) );
            }
		}else{
			String responseMessage = response.getString("responseMessage");
			logger.error("responseMessage--{}",responseMessage);
		}
	}

    public String getEmotionMessage( String filePath ) throws Exception {

        String fileBase64 = Base64Utils.encodeFile(filePath );
        Map<String,Object> p = new HashMap<String,Object>();
        p.put("data", fileBase64);
        p.put("type", 0);
        String requestDataStr = JSONObject.toJSONString(p);

        String aesKey = getRandomCharacterAndNumber(16);

        /*
         * 封装请求报文
         */
        String requestJson = mkRequest(aesKey,requestDataStr);

        String responseStr =  new HttpClientUtil().httpPostJson(apiUrl+"ai/emotion", requestJson);
        JSONObject response = JSONObject.parseObject(responseStr);
        String responseCode = response.getString("responseCode");
        if("000000".equals(responseCode)){
            String responseDataStr = response.getString("responseData");
            /*
             * 返回报文解密
             */
            String responseData = AESCBCUtils.decrypt(responseDataStr, aesKey);
//            logger.info("业务返回参数--{}",responseData);
            Pattern pattern = Pattern.compile( "\"emot\":(.*?),\"in_whitelist\"" );
            Matcher matcher = pattern.matcher( responseData );
            if( matcher.find() ) {
                return matcher.group(1);
            }
        }else{
            String responseMessage = response.getString("responseMessage");
//            logger.error("responseMessage--{}",responseMessage);
        }
        return "fail";
    }
	
	/**
	 * 22种面相属性接口
	 */
	@Test
	public void mianxiang() throws Exception {
		
        String fileBase64 = Base64Utils.encodeFile("file/demo.jpg");
        Map<String,Object> p = new HashMap<String,Object>();
        p.put("data", fileBase64);
		String requestDataStr = JSONObject.toJSONString(p);
		
		String aesKey = getRandomCharacterAndNumber(16);
		
		/*
		 * 封装请求报文
		 */
		String requestJson = mkRequest(aesKey,requestDataStr);
		
		String responseStr =  new HttpClientUtil().httpPostJson(apiUrl+"ai/mianxiang", requestJson);
		JSONObject response = JSONObject.parseObject(responseStr);
		String responseCode = response.getString("responseCode");
		if("000000".equals(responseCode)){
			String responseDataStr = response.getString("responseData");
			/*
			 * 返回报文解密
			 */
			String responseData = AESCBCUtils.decrypt(responseDataStr, aesKey);
			logger.info("业务返回参数--{}",responseData);
		}else{
			String responseMessage = response.getString("responseMessage");
			logger.error("responseMessage--{}",responseMessage);
		}
	}
	
	/**
	 * 16Pf接口
	 */
	@Test
	public void pf() throws Exception {
		
        String fileBase64 = Base64Utils.encodeFile("file/demo.jpg");
        Map<String,Object> p = new HashMap<String,Object>();
        p.put("data", fileBase64);
		String requestDataStr = JSONObject.toJSONString(p);
		
		String aesKey = getRandomCharacterAndNumber(16);
		
		/*
		 * 封装请求报文
		 */
		String requestJson = mkRequest(aesKey,requestDataStr);
		
		String responseStr =  new HttpClientUtil().httpPostJson(apiUrl+"ai/pf", requestJson);
		JSONObject response = JSONObject.parseObject(responseStr);
		String responseCode = response.getString("responseCode");
		if("000000".equals(responseCode)){
			String responseDataStr = response.getString("responseData");
			/*
			 * 返回报文解密
			 */
			String responseData = AESCBCUtils.decrypt(responseDataStr, aesKey);
			logger.info("业务返回参数--{}",responseData);
		}else{
			String responseMessage = response.getString("responseMessage");
			logger.error("responseMessage--{}",responseMessage);
		}
	}
	
	/**
	 * 阅读理解接口
	 */
	@Test
	public void reading() throws Exception {
		
        Map<String,Object> p = new HashMap<String,Object>();
        p.put("file_name", "平安一年期重大疾病保险条款");
        p.put("query", "费用");
		String requestDataStr = JSONObject.toJSONString(p);
		
		String aesKey = getRandomCharacterAndNumber(16);
		
		/*
		 * 封装请求报文
		 */
		String requestJson = mkRequest(aesKey,requestDataStr);
		
		String responseStr =  new HttpClientUtil().httpPostJson(apiUrl+"ai/reading", requestJson);
		JSONObject response = JSONObject.parseObject(responseStr);
		String responseCode = response.getString("responseCode");
		if("000000".equals(responseCode)){
			String responseDataStr = response.getString("responseData");
			/*
			 * 返回报文解密
			 */
			String responseData = AESCBCUtils.decrypt(responseDataStr, aesKey);
			logger.info("业务返回参数--{}",responseData);
		}else{
			String responseMessage = response.getString("responseMessage");
			logger.error("responseMessage--{}",responseMessage);
		}
	}
	
	/**
	 * 1:1人脸比对接口
	 */
	@Test
	public void faceMatch() throws Exception {
		
        String fileBase64 = Base64Utils.encodeFile("file/demo.jpg");
        String fileBase64_2 = Base64Utils.encodeFile("file/demo2.jpg");
        Map<String,Object> p = new HashMap<String,Object>();
        p.put("img1", fileBase64);
        p.put("img2", fileBase64_2);
        
		String requestDataStr = JSONObject.toJSONString(p);
		
		String aesKey = getRandomCharacterAndNumber(16);
		
		/*
		 * 封装请求报文
		 */
		String requestJson = mkRequest(aesKey,requestDataStr);
		
		String responseStr =  new HttpClientUtil().httpPostJson(apiUrl+"ai/faceMatch", requestJson);
		JSONObject response = JSONObject.parseObject(responseStr);
		String responseCode = response.getString("responseCode");
		if("000000".equals(responseCode)){
			String responseDataStr = response.getString("responseData");
			/*
			 * 返回报文解密
			 */
			String responseData = AESCBCUtils.decrypt(responseDataStr, aesKey);
			logger.info("业务返回参数--{}",responseData);
		}else{
			String responseMessage = response.getString("responseMessage");
			logger.error("responseMessage--{}",responseMessage);
		}
	}
	
	/**
	 * 人脸检测接口
	 */
	@Test
	public void faceDetect() throws Exception {
		
        String fileBase64 = Base64Utils.encodeFile("file/demo.jpg");
        Map<String,Object> p = new HashMap<String,Object>();
        p.put("face_img", fileBase64);
		String requestDataStr = JSONObject.toJSONString(p);
		
		String aesKey = getRandomCharacterAndNumber(16);
		
		/*
		 * 封装请求报文
		 */
		String requestJson = mkRequest(aesKey,requestDataStr);
		
		String responseStr =  new HttpClientUtil().httpPostJson(apiUrl+"ai/faceDetect", requestJson);
		JSONObject response = JSONObject.parseObject(responseStr);
		String responseCode = response.getString("responseCode");
		if("000000".equals(responseCode)){
			String responseDataStr = response.getString("responseData");
			/*
			 * 返回报文解密
			 */
			String responseData = AESCBCUtils.decrypt(responseDataStr, aesKey);
			logger.info("业务返回参数--{}",responseData);
		}else{
			String responseMessage = response.getString("responseMessage");
			logger.error("responseMessage--{}",responseMessage);
		}
	}
	
	private static String mkRequest(String aesKey,String requestDataStr) {
		String format="json";
		logger.info("业务请求参数--{}",requestDataStr);
		String version = "1.0";
		String encodeKey = RSAUtils.encrypt(aesKey, publicKey);
		String requestData = AESCBCUtils.encrypt(requestDataStr, aesKey);
		String requestId = UUID.randomUUID().toString();
		String signMethod = "SHA256withRSA";
		String timestamp = DateUtil.format(new Date());
		
		Map<String,String> signDatas = new TreeMap<String,String>();
		signDatas.put("encodeKey", encodeKey);
		signDatas.put("format", format);
		signDatas.put("orgCode", orgCode);
		signDatas.put("requestData", requestData);
		signDatas.put("requestId", requestId);
		signDatas.put("version", version);
		signDatas.put("signMethod", signMethod);
		signDatas.put("timestamp", timestamp);
		signDatas.put("channelId", channelId);
		logger.info("参与签名的字符串--{}",JSONObject.toJSONString(signDatas));
		String rsaSign = RSAUtils.signwithsha256(JSONObject.toJSONString(signDatas), privateKey, "UTF-8");

		logger.info("签名值--{}",rsaSign);
		
		signDatas.put("sign", rsaSign);
		
		String requestJson = JSONObject.toJSONString(signDatas);
		logger.info("json请求报文--{}",requestJson);
		return requestJson;
	}
	
	private static String getRandomCharacterAndNumber(int length) {
       String val = "";
       Random random = new Random();
       for (int i = 0; i < length; i++) {
           String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字  
  
           if ("char".equalsIgnoreCase(charOrNum)) // 字符串  
           {  
               int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母  
               val += (char) (choice + random.nextInt(26));
           } else if ("num".equalsIgnoreCase(charOrNum)) // 数字  
           {  
               val += String.valueOf(random.nextInt(10));
           }  
       }
       return val;
   }

   public static void main( String[] args ) throws Exception {
	    String[] baseMsgs = {"[{\"confid\":0.244,\"type\":\"Optimism\"},{\"confid\":0.16,\"type\":\"Joy\"},{\"confid\":0.1,\"type\":\"Vitality\"},{\"confid\":0.1,\"type\":\"Apprehension\"},{\"confid\":0.057,\"type\":\"Harmony\"},{\"confid\":0.051,\"type\":\"Desire\"},{\"confid\":0.04,\"type\":\"Sincerity\"},{\"confid\":0.04,\"type\":\"Annoyance\"},{\"confid\":0.028,\"type\":\"Serenity\"},{\"confid\":0.025,\"type\":\"Depression\"},{\"confid\":0.021,\"type\":\"Submission\"},{\"confid\":0.018,\"type\":\"Acceptance\"},{\"confid\":0.016,\"type\":\"Surprise\"},{\"confid\":0.015,\"type\":\"Boredom\"},{\"confid\":0.014,\"type\":\"Grievance\"},{\"confid\":0.011,\"type\":\"Trust\"},{\"confid\":0.011,\"type\":\"Fatigue\"},{\"confid\":0.007,\"type\":\"Calm\"},{\"confid\":0.006,\"type\":\"Interest\"},{\"confid\":0.004,\"type\":\"Admiration\"},{\"confid\":0.004,\"type\":\"Fear\"},{\"confid\":0.004,\"type\":\"Cowardice\"},{\"confid\":0.003,\"type\":\"Boastfulness\"},{\"confid\":0.003,\"type\":\"Anticipation\"},{\"confid\":0.003,\"type\":\"Puzzlement\"},{\"confid\":0.003,\"type\":\"Pride\"},{\"confid\":0.002,\"type\":\"Passiveness\"},{\"confid\":0.002,\"type\":\"Embarrassed\"},{\"confid\":0.002,\"type\":\"Pessimism\"},{\"confid\":0.001,\"type\":\"Tension\"},{\"confid\":0.001,\"type\":\"Neutral\"},{\"confid\":0.001,\"type\":\"Disgust\"},{\"confid\":0.001,\"type\":\"Suspicion\"},{\"confid\":0.001,\"type\":\"Bravery\"},{\"confid\":0.001,\"type\":\"Awe\"},{\"confid\":0.001,\"type\":\"Angry\"},{\"confid\":0.001,\"type\":\"Deceptiveness\"},{\"confid\":0.0,\"type\":\"Contempt\"},{\"confid\":0.0,\"type\":\"Aggressiveness\"},{\"confid\":0.0,\"type\":\"Distraction\"},{\"confid\":0.0,\"type\":\"Conflict\"},{\"confid\":0.0,\"type\":\"Disapproval\"},{\"confid\":0.0,\"type\":\"Sadness\"},{\"confid\":0.0,\"type\":\"Uneasiness\"},{\"confid\":0.0,\"type\":\"Love\"},{\"confid\":0.0,\"type\":\"Insincerity\"},{\"confid\":0.0,\"type\":\"Hate\"},{\"confid\":0.0,\"type\":\"Envy\"},{\"confid\":0.0,\"type\":\"Defiance\"},{\"confid\":0.0,\"type\":\"Neglect\"},{\"confid\":0.0,\"type\":\"Gratitude\"},{\"confid\":0.0,\"type\":\"Insult\"},{\"confid\":0.0,\"type\":\"Remorse\"},{\"confid\":0.0,\"type\":\"Shame\"}]", "[{\"confid\":0.291,\"type\":\"Harmony\"},{\"confid\":0.251,\"type\":\"Sincerity\"},{\"confid\":0.185,\"type\":\"Joy\"},{\"confid\":0.117,\"type\":\"Vitality\"},{\"confid\":0.061,\"type\":\"Calm\"},{\"confid\":0.055,\"type\":\"Optimism\"},{\"confid\":0.014,\"type\":\"Grievance\"},{\"confid\":0.01,\"type\":\"Serenity\"},{\"confid\":0.009,\"type\":\"Neutral\"},{\"confid\":0.002,\"type\":\"Acceptance\"},{\"confid\":0.001,\"type\":\"Desire\"},{\"confid\":0.001,\"type\":\"Anticipation\"},{\"confid\":0.001,\"type\":\"Fear\"},{\"confid\":0.001,\"type\":\"Depression\"},{\"confid\":0.001,\"type\":\"Boredom\"},{\"confid\":0.0,\"type\":\"Fatigue\"},{\"confid\":0.0,\"type\":\"Trust\"},{\"confid\":0.0,\"type\":\"Apprehension\"},{\"confid\":0.0,\"type\":\"Annoyance\"},{\"confid\":0.0,\"type\":\"Contempt\"},{\"confid\":0.0,\"type\":\"Embarrassed\"},{\"confid\":0.0,\"type\":\"Submission\"},{\"confid\":0.0,\"type\":\"Interest\"},{\"confid\":0.0,\"type\":\"Surprise\"},{\"confid\":0.0,\"type\":\"Pessimism\"},{\"confid\":0.0,\"type\":\"Boastfulness\"},{\"confid\":0.0,\"type\":\"Pride\"},{\"confid\":0.0,\"type\":\"Distraction\"},{\"confid\":0.0,\"type\":\"Tension\"},{\"confid\":0.0,\"type\":\"Admiration\"},{\"confid\":0.0,\"type\":\"Cowardice\"},{\"confid\":0.0,\"type\":\"Insincerity\"},{\"confid\":0.0,\"type\":\"Puzzlement\"},{\"confid\":0.0,\"type\":\"Bravery\"},{\"confid\":0.0,\"type\":\"Deceptiveness\"},{\"confid\":0.0,\"type\":\"Love\"},{\"confid\":0.0,\"type\":\"Envy\"},{\"confid\":0.0,\"type\":\"Passiveness\"},{\"confid\":0.0,\"type\":\"Suspicion\"},{\"confid\":0.0,\"type\":\"Disgust\"},{\"confid\":0.0,\"type\":\"Awe\"},{\"confid\":0.0,\"type\":\"Conflict\"},{\"confid\":0.0,\"type\":\"Neglect\"},{\"confid\":0.0,\"type\":\"Defiance\"},{\"confid\":0.0,\"type\":\"Angry\"},{\"confid\":0.0,\"type\":\"Disapproval\"},{\"confid\":0.0,\"type\":\"Sadness\"},{\"confid\":0.0,\"type\":\"Uneasiness\"},{\"confid\":0.0,\"type\":\"Gratitude\"},{\"confid\":0.0,\"type\":\"Hate\"},{\"confid\":0.0,\"type\":\"Aggressiveness\"},{\"confid\":0.0,\"type\":\"Remorse\"},{\"confid\":0.0,\"type\":\"Insult\"},{\"confid\":0.0,\"type\":\"Shame\"}]", "[{\"confid\":0.181,\"type\":\"Annoyance\"},{\"confid\":0.139,\"type\":\"Angry\"},{\"confid\":0.113,\"type\":\"Vitality\"},{\"confid\":0.107,\"type\":\"Optimism\"},{\"confid\":0.064,\"type\":\"Apprehension\"},{\"confid\":0.048,\"type\":\"Contempt\"},{\"confid\":0.044,\"type\":\"Anticipation\"},{\"confid\":0.044,\"type\":\"Puzzlement\"},{\"confid\":0.041,\"type\":\"Cowardice\"},{\"confid\":0.038,\"type\":\"Submission\"},{\"confid\":0.033,\"type\":\"Joy\"},{\"confid\":0.014,\"type\":\"Aggressiveness\"},{\"confid\":0.014,\"type\":\"Tension\"},{\"confid\":0.012,\"type\":\"Remorse\"},{\"confid\":0.01,\"type\":\"Grievance\"},{\"confid\":0.009,\"type\":\"Admiration\"},{\"confid\":0.008,\"type\":\"Surprise\"},{\"confid\":0.007,\"type\":\"Harmony\"},{\"confid\":0.007,\"type\":\"Sadness\"},{\"confid\":0.007,\"type\":\"Desire\"},{\"confid\":0.007,\"type\":\"Conflict\"},{\"confid\":0.007,\"type\":\"Fatigue\"},{\"confid\":0.004,\"type\":\"Hate\"},{\"confid\":0.004,\"type\":\"Acceptance\"},{\"confid\":0.004,\"type\":\"Envy\"},{\"confid\":0.003,\"type\":\"Fear\"},{\"confid\":0.003,\"type\":\"Disgust\"},{\"confid\":0.003,\"type\":\"Pessimism\"},{\"confid\":0.003,\"type\":\"Insult\"},{\"confid\":0.003,\"type\":\"Disapproval\"},{\"confid\":0.003,\"type\":\"Uneasiness\"},{\"confid\":0.003,\"type\":\"Embarrassed\"},{\"confid\":0.002,\"type\":\"Defiance\"},{\"confid\":0.002,\"type\":\"Passiveness\"},{\"confid\":0.001,\"type\":\"Gratitude\"},{\"confid\":0.001,\"type\":\"Deceptiveness\"},{\"confid\":0.001,\"type\":\"Sincerity\"},{\"confid\":0.001,\"type\":\"Serenity\"},{\"confid\":0.001,\"type\":\"Suspicion\"},{\"confid\":0.001,\"type\":\"Boastfulness\"},{\"confid\":0.001,\"type\":\"Pride\"},{\"confid\":0.001,\"type\":\"Trust\"},{\"confid\":0.001,\"type\":\"Calm\"},{\"confid\":0.001,\"type\":\"Boredom\"},{\"confid\":0.0,\"type\":\"Interest\"},{\"confid\":0.0,\"type\":\"Love\"},{\"confid\":0.0,\"type\":\"Shame\"},{\"confid\":0.0,\"type\":\"Depression\"},{\"confid\":0.0,\"type\":\"Neglect\"},{\"confid\":0.0,\"type\":\"Insincerity\"},{\"confid\":0.0,\"type\":\"Bravery\"},{\"confid\":0.0,\"type\":\"Distraction\"},{\"confid\":0.0,\"type\":\"Awe\"},{\"confid\":0.0,\"type\":\"Neutral\"}]", "fail", "[{\"confid\":0.504,\"type\":\"Vitality\"},{\"confid\":0.427,\"type\":\"Joy\"},{\"confid\":0.06,\"type\":\"Optimism\"},{\"confid\":0.004,\"type\":\"Disgust\"},{\"confid\":0.002,\"type\":\"Harmony\"},{\"confid\":0.001,\"type\":\"Apprehension\"},{\"confid\":0.001,\"type\":\"Angry\"},{\"confid\":0.0,\"type\":\"Deceptiveness\"},{\"confid\":0.0,\"type\":\"Aggressiveness\"},{\"confid\":0.0,\"type\":\"Annoyance\"},{\"confid\":0.0,\"type\":\"Sincerity\"},{\"confid\":0.0,\"type\":\"Contempt\"},{\"confid\":0.0,\"type\":\"Pride\"},{\"confid\":0.0,\"type\":\"Surprise\"},{\"confid\":0.0,\"type\":\"Fear\"},{\"confid\":0.0,\"type\":\"Conflict\"},{\"confid\":0.0,\"type\":\"Interest\"},{\"confid\":0.0,\"type\":\"Hate\"},{\"confid\":0.0,\"type\":\"Disapproval\"},{\"confid\":0.0,\"type\":\"Admiration\"},{\"confid\":0.0,\"type\":\"Anticipation\"},{\"confid\":0.0,\"type\":\"Embarrassed\"},{\"confid\":0.0,\"type\":\"Love\"},{\"confid\":0.0,\"type\":\"Desire\"},{\"confid\":0.0,\"type\":\"Grievance\"},{\"confid\":0.0,\"type\":\"Cowardice\"},{\"confid\":0.0,\"type\":\"Submission\"},{\"confid\":0.0,\"type\":\"Acceptance\"},{\"confid\":0.0,\"type\":\"Trust\"},{\"confid\":0.0,\"type\":\"Depression\"},{\"confid\":0.0,\"type\":\"Bravery\"},{\"confid\":0.0,\"type\":\"Passiveness\"},{\"confid\":0.0,\"type\":\"Boredom\"},{\"confid\":0.0,\"type\":\"Calm\"},{\"confid\":0.0,\"type\":\"Boastfulness\"},{\"confid\":0.0,\"type\":\"Insult\"},{\"confid\":0.0,\"type\":\"Tension\"},{\"confid\":0.0,\"type\":\"Serenity\"},{\"confid\":0.0,\"type\":\"Awe\"},{\"confid\":0.0,\"type\":\"Sadness\"},{\"confid\":0.0,\"type\":\"Insincerity\"},{\"confid\":0.0,\"type\":\"Pessimism\"},{\"confid\":0.0,\"type\":\"Envy\"},{\"confid\":0.0,\"type\":\"Puzzlement\"},{\"confid\":0.0,\"type\":\"Suspicion\"},{\"confid\":0.0,\"type\":\"Fatigue\"},{\"confid\":0.0,\"type\":\"Defiance\"},{\"confid\":0.0,\"type\":\"Gratitude\"},{\"confid\":0.0,\"type\":\"Neglect\"},{\"confid\":0.0,\"type\":\"Distraction\"},{\"confid\":0.0,\"type\":\"Remorse\"},{\"confid\":0.0,\"type\":\"Uneasiness\"},{\"confid\":0.0,\"type\":\"Neutral\"},{\"confid\":0.0,\"type\":\"Shame\"}]" };
        String msg = new PingAnTool().getEmotionMessage( "D:\\项目\\场景识别\\后端\\SchoolGuider\\src\\main\\webapp\\upload\\cry.jpg" );
       System.out.println( msg );
   }
}