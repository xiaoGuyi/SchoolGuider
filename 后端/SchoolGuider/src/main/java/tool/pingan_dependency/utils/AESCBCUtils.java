package tool.pingan_dependency.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class AESCBCUtils {

	public static final Logger logger = LoggerFactory.getLogger(AESCBCUtils.class);
	public static final String KEY_ALGORITHM = "AES";
	// 加密模式为ECB，填充模式为NoPadding
	public static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";
	// 字符集
	public static final String ENCODING = "UTF-8";
	// 向量
	public static final String IV_SEED = "1234567812345678";
	
	// 加密模式为CBC，填充模式为NoPadding
	public static final String CIPHER_ALGORITHM_CBC = "AES/CBC/NoPadding";
	// 加密模式为ECB，填充模式为PKCS5Padding
	public static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

	/**
	 * AES加密算法
	 * 
	 * @param str密文
	 * @param key密key
	 * @return
	 */
	public static String encrypt(String str, String key) {
		try {
			if (str == null) {
				logger.error("AES加密出错:Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (key.length() != 16) {
				logger.error("AES加密出错:Key长度不是16位");
				return null;
			}
			byte[] raw = key.getBytes(ENCODING);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] srawt = str.getBytes(ENCODING);
			int len = srawt.length;
			/* 计算补空格后的长度 */
			while (len % 16 != 0)
				len++;
			byte[] sraw = new byte[len];
			/* 在最后空格 */
			for (int i = 0; i < len; ++i) {
				if (i < srawt.length) {
					sraw[i] = srawt[i];
				} else {
					sraw[i] = 32;
				}
			}
			byte[] encrypted = cipher.doFinal(sraw);
			return formatString(new String(Base64.encodeBase64(encrypted), "UTF-8"));
		} catch (Exception ex) {
			logger.error("AES加密出错：" + ex.toString());
			return null;
		}
	}

	/**
	 * AES解密算法
	 * 
	 * @param str密文
	 * @param key密key
	 * @return
	 */
	public static String decrypt(String str, String key) {
		try {
			// 判断Key是否正确
			if (key == null) {
				logger.error("AES解密出错:Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (key.length() != 16) {
				logger.error("AES解密出错：Key长度不是16位");
				return null;
			}
			byte[] raw = key.getBytes(ENCODING);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] bytes = Base64.decodeBase64(str.getBytes("UTF-8"));
			bytes = cipher.doFinal(bytes);
			return new String(bytes, ENCODING);
		} catch (Exception ex) {
			logger.error("AES解密出错：" + ex.toString());
			return null;
		}
	}

	private static String formatString(String sourceStr) {
		if (sourceStr == null) {
			return null;
		}
		return sourceStr.replaceAll("\\r", "").replaceAll("\\n", "");
	}
	
	/** 
	 * byte数组转化为16进制字符串 
	 * @param bytes 
	 * @return 
	 */  
	public static String byteToHexString(byte[] bytes) {    
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < bytes.length; i++) {
	    	String strHex=Integer.toHexString(bytes[i]);
	        if(strHex.length() > 3){
	        	sb.append(strHex.substring(6));
	        } else {
	        	if(strHex.length() < 2){
	        		sb.append("0" + strHex);
	            } else {
	            	sb.append(strHex);
	            }
	        }
	    }
	    return  sb.toString();       
	}

	/** 
     * 随机生成秘钥 
     */  
    public static String getAesKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256  
            SecretKey sk = kg.generateKey();
            return byteToHexString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            return "defaultaes";
        }
    }
    /**
	 * AES加密算法,加密模式为ECB，填充模式为PKCS5Padding
	 * 
	 * @return
	 */
	public static String encrypt_ecb(String str, String key) {
		try {
			if (key == null) {
				logger.warn("AES with ECB加密出错:Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (key.length() != 16) {
				logger.warn("AES with ECB加密出错:Key长度不是16位");
				return null;
			}
			byte[] raw = key.getBytes(ENCODING);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] srawt = str.getBytes(ENCODING);
			int len = srawt.length;
			/* 计算补空格后的长度 */
			while (len % 16 != 0)
				len++;
			byte[] sraw = new byte[len];
			/* 在最后空格 */
			for (int i = 0; i < len; ++i) {
				if (i < srawt.length) {
					sraw[i] = srawt[i];
				} else {
					sraw[i] = 32;
				}
			}
			byte[] encrypted = cipher.doFinal(sraw);
			return formatString(new String(Base64.encodeBase64(encrypted), "UTF-8"));
		} catch (Exception ex) {
			logger.error("AES with ECB加密出错：", ex);
			return null;
		}
	}

	/**
	 * AES解密算法,加密模式为ECB，填充模式为PKCS5Padding
	 * 
	 * @return
	 */
	public static String decrypt_ecb(String str, String key) {
		try {
			// 判断Key是否正确
			if (key == null) {
				logger.warn("AES with ECB解密出错:Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (key.length() != 16) {
				logger.warn("AES with ECB解密出错：Key长度不是16位");
				return null;
			}
			byte[] raw = key.getBytes(ENCODING);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] bytes = Base64.decodeBase64(str.getBytes("UTF-8"));
			bytes = cipher.doFinal(bytes);
			return new String(bytes, ENCODING);
		} catch (Exception ex) {
			logger.error("AES with ECB解密出错：", ex);
			return null;
		}
	}
    public static void main(String[] args) {
		String aes_key = "abcdefghabcdefgh";
		String source = "abcdefghabcdef12";
		// 加密
		String encrypt_str = encrypt(source, aes_key);
		System.out.println(encrypt_str);
		// 解密
		String decrypt_str = decrypt(encrypt_str, aes_key);
		System.out.println(decrypt_str);
	}

}