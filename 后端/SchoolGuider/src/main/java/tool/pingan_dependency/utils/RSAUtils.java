package tool.pingan_dependency.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class RSAUtils {

	public static final Logger logger 					= LoggerFactory.getLogger(AESCBCUtils.class);

    public static final String KEY_ALGORITHM            = "RSA";

    public static final String RSA_PADDING_KEY          = "RSA/ECB/PKCS1Padding";
    
    public static final String NO_PADDING_ALGORITHM     = "RSA/ECB/NoPadding";

    public static final String SIGNATURE_ALGORITHM_MD5  = "MD5WithRSA";

    public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1WithRSA";
    public static final String SIGNATURE_ALGORITHM_SHA256 = "SHA256withRSA";

    /**
     * 获取RSA公钥
     * @param key 公钥字符串
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * RSA公钥加密
     * @param plainText 待加密数据
     * @param s_publicKey 公钥字符串
     * @return
     */
    public static String encrypt(String plainText, String s_publicKey) {
        if (plainText == null || s_publicKey == null) {
            return null;
        }
        try {
            PublicKey publicKey = getPublicKey(s_publicKey);
            Cipher cipher = Cipher.getInstance(RSA_PADDING_KEY);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
            return formatString(Base64.encodeBase64String(enBytes));
        } catch (Exception e) {
            logger.error("RSA encrypt Exception:" + e);
        }
        return null;
    }
    
    /**
     * RSA公钥加密
     * @param plainText 待加密数据
     * @param flag 密码加密flag为true，账号加密flag为false
     * @param s_publicKey 公钥字符串
     * @return
     */
	public static String encrypt(String plainText ,Boolean flag, String s_publicKey){
		if (StringUtil.isEmpty(plainText)) {
			return null;
		}
		
		Cipher cipher;
		try {
			if(flag){
				cipher = Cipher.getInstance(RSA_PADDING_KEY);
			}else{
				cipher = Cipher.getInstance(NO_PADDING_ALGORITHM);
			}
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(s_publicKey));
			byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
			return formatString(Base64.encodeBase64String(encrypted));
		} catch (Exception e) {
			logger.error("RSA encrypt Exception:", e);
		}
		return null;
	}

    /**
     * 格式化RSA加密字符串,去掉换行和渐近符号
     * @param sourceStr
     * @return
     */
    private static String formatString(String sourceStr) {
        if (sourceStr == null) {
            return null;
        }
        return sourceStr.replaceAll("\\r", "").replaceAll("\\n", "");
    }

    /** 
     * 获取RSA私钥 
     * @param key 密钥字符串（经过base64编码） 
     * @return
     * @throws Exception 
     */
    private static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * RSA私钥解密
     * @param enStr 待解密数据
     * @param s_privateKey 私钥字符串
     * @return
     */
    public static String decrypt(String enStr, String s_privateKey) {
        if (enStr == null || s_privateKey == null) {
            return null;
        }
        try {
            PrivateKey privateKey = getPrivateKey(s_privateKey);
            Cipher cipher = Cipher.getInstance(RSA_PADDING_KEY);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] deBytes = cipher.doFinal(Base64.decodeBase64(enStr));
            return new String(deBytes, "UTF-8");
        } catch (Exception e) {
            logger.error("RSA decrypt Exception:" + e);
        }
        return null;
    }

    /**
     * RSA签名
     * 
     * MD5摘要RSA签名
     * @param content 待签名数据
     * @param privateKey 关联方私钥
     * @param encode 字符集编码
     * @return
     */
    public static String sign(String content, String privateKey, String encode) {
        if (content == null || privateKey == null || encode == null) {
            return null;
        }
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
            signature.initSign(priKey);
            signature.update(content.getBytes(encode));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            logger.error("RSA sign Exception:" + e);
        }
        return null;
    }

    /**
     * RSA签名
     * 
     * SHA1摘要RSA签名
     * @param content 待签名数据
     * @param privateKey 关联方私钥
     * @param encode 字符集编码
     * @return
     */
    public static String signwithsha1(String content, String privateKey, String encode) {
        if (content == null || privateKey == null || encode == null) {
            return null;
        }
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA1);
            signature.initSign(priKey);
            signature.update(content.getBytes(encode));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            logger.error("RSA sign Exception:" + e);
        }
        return null;
    }
    //
    /**
     * RSA签名
     *
     * SHA256 摘要RSA签名
     * @param content 待签名数据
     * @param privateKey 关联方私钥
     * @param encode 字符集编码
     * @return
     */
    public static String signwithsha256(String content, String privateKey, String encode) {
        if (content == null || privateKey == null || encode == null) {
            return null;
        }
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA256);
            signature.initSign(priKey);
            signature.update(content.getBytes(encode));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            logger.error("RSAWithSha256 sign Exception:" + e);
        }
        return null;
    }
    /**
     * RSA签名
     * 
     * MD5摘要RSA签名
     * @param content 待签名数据
     * @param privateKey 关联方私钥
     * @return
     */
    public static String sign(String content, String privateKey) {
        if (content == null || privateKey == null) {
            return null;
        }
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
            signature.initSign(priKey);
            signature.update(content.getBytes("UTF-8"));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            logger.error("RSA sign Exception:" + e);
        }
        return null;
    }

    /**
     * RSA签名
     * 
     * SHA1摘要RSA签名
     * @param content 待签名数据
     * @param privateKey 关联方私钥
     * @return
     */
    public static String signwithsha1(String content, String privateKey) {
        if (content == null || privateKey == null) {
            return null;
        }
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA1);
            signature.initSign(priKey);
            signature.update(content.getBytes("UTF-8"));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            logger.error("RSA sign Exception:" + e);
        }
        return null;
    }

    /**
     * RSA签名验证
     * 
     * MD5摘要RSA签名验证
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 分配给关联方公钥
     * @param encode 字符集编码
     * @return 布尔值
     */
    public static boolean verifySign(String content, String sign, String publicKey, String encode) {
        if (content == null || sign == null || publicKey == null || encode == null) {
            return false;
        }
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            logger.error("RSA verifySign Exception:" + e);
        }
        return false;
    }

    /**
     * RSA签名验证
     * 
     * SHA1摘要RSA签名验证
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 分配给关联方公钥
     * @param encode 字符集编码
     * @return 布尔值
     */
    public static boolean verifySignwithsha1(String content, String sign, String publicKey,
                                             String encode) {
        if (content == null || sign == null || publicKey == null || encode == null) {
            return false;
        }
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA1);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            logger.error("RSA verifySign Exception:" + e);
        }
        return false;
    }



    /**
     * RSA签名验证
     * 
     * MD5摘要RSA签名验证
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 分配给关联方公钥
     * @return 布尔值
     */
    public static boolean verifySign(String content, String sign, String publicKey) {
        if (content == null || sign == null || publicKey == null) {
            return false;
        }
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
            signature.initVerify(pubKey);
            signature.update(content.getBytes("UTF-8"));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            logger.error("RSA verifySign Exception:" + e);
        }
        return false;
    }

    /**
     * RSA签名验证
     * 
     * SHA1摘要RSA签名验证
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 分配给关联方公钥
     * @return 布尔值
     */
    public static boolean verifySignwithsha1(String content, String sign, String publicKey) {
        if (content == null || sign == null || publicKey == null) {
            return false;
        }
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA1);
            signature.initVerify(pubKey);
            signature.update(content.getBytes("UTF-8"));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            logger.error("RSA verifySign Exception:" + e);
        }
        return false;
    }
    /**
     * RSA签名验证
     *
     * SHA1摘要RSA签名验证
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 分配给关联方公钥
     * @return 布尔值
     */
    public static boolean verifySignwithsha256(String content, String sign, String publicKey) {
        if (content == null || sign == null || publicKey == null) {
            return false;
        }
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA256);
            signature.initVerify(pubKey);
            signature.update(content.getBytes("UTF-8"));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            logger.error("RSA verifySign Exception:" + e);
        }
        return false;
    }

    /**
     * RSA签名验证
     *
     * SHA1摘要RSA签名验证
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 分配给关联方公钥
     * @return 布尔值
     */
    public static boolean verifySignwithsha256(String content, String sign, String publicKey,String encode) {
        if (content == null || sign == null || publicKey == null) {
            return false;
        }
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA256);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            logger.error("RSA verifySign Exception:" + e);
        }
        return false;
    }
    /**
     * 主帐户使用RSA部分
     */

    private static final int   KEY_SIZE            = 2048;
    /**
     * RSA最大加密明文大小
     */
    private static final int   MAX_ENCRYPT_BLOCK   = KEY_SIZE / 8 - 11;

    public static char[]       HEX_CHAR            = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F'     };
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
    * <p>
    * 公钥加密
    * </p>
    * 
    * @param data 源数据
    * @param publicKey 公钥(BASE64编码)
    * @return
    * @throws Exception
    */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static String toHexString(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(toHexString(data[i]));
        }
        return sb.toString();
    }

    public static String toHexString(byte b) {
        int tmp = b & 0xFF;
        int high = (tmp & 0xf0) / 16;
        int low = (tmp & 0x0f) % 16;
        return new String(new char[] { HEX_CHAR[high], HEX_CHAR[low] });
    }
    
    public static byte[] hexStringToBytes(String hexString) {   
        if (hexString == null || hexString.equals("")) {   
            return null;   
        }   
        hexString = hexString.toUpperCase();   
        int length = hexString.length() / 2;   
        char[] hexChars = hexString.toCharArray();   
        byte[] d = new byte[length];   
        for (int i = 0; i < length; i++) {   
            int pos = i * 2;   
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
        }   
        return d;   
    }   
    
    private static byte charToByte(char c) {   
        return (byte) "0123456789ABCDEF".indexOf(c);   
    }  

    /** */
    /**
    * <p>
    * 用私钥对信息生成数字签名
    * </p>
    * 
    * @param data 已加密数据
    * @param privateKey 私钥(BASE64编码)
    * 
    * @return
    * @throws Exception
    */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    public static void createKeyPairs() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // create the keys  
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        PublicKey pubKey = pair.getPublic();
        PrivateKey privKey = pair.getPrivate();
        byte[] pk = pubKey.getEncoded();
        byte[] privk = privKey.getEncoded();
        String strpk = new String(Base64.encodeBase64(pk));
        String strprivk = new String(Base64.encodeBase64(privk));

        System.out.println("公钥:" + Arrays.toString(pk));
        System.out.println("私钥:" + Arrays.toString(privk));
        System.out.println("公钥Base64编码:" + strpk);
        System.out.println("私钥Base64编码:" + strprivk);

        X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(strpk.getBytes()));
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(strprivk.getBytes()));

        KeyFactory keyf = KeyFactory.getInstance("RSA", "BC");
        PublicKey pubkey2 = keyf.generatePublic(pubX509);
        PrivateKey privkey2 = keyf.generatePrivate(priPKCS8);

        System.out.println(pubKey.equals(pubkey2));
        System.out.println(privKey.equals(privkey2));
    }
    
}
