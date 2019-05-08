package tool.pingan_dependency.utils;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

public class MD5Utils {
	public static final String KEY_ALGORITHM = "MD5";
	
	
	public static String getSignWithMD5(String str){
		String signature = null;
		try {
			/*
			 * MD系列可以使用如下算法（字母不区分大小写）
			 * md5	摘要长度128位，16个字节
			 * 以下算法需要安装bouncy castle算法包
			 * md2
			 * md4
			 * ripemd128	摘要长度128位，16个字节
			 * ripemd160	摘要长度160位，20个字节
			 */
			MessageDigest md5Digest = MessageDigest.getInstance("MD5");
			byte[] digByteResult = md5Digest.digest(str.getBytes("UTF-8"));
			signature = new String(Hex.encodeHex(digByteResult));
		} catch (Exception e) {
			System.out.println("MD5加密出错：");
			return null;
		}
		return signature;
	}
	
/*	public static String decodeMD5Sign(String str){
		try {
			MessageDigest md5Digest = MessageDigest.getInstance("MD5");
			byte[] bytes = str.getBytes();
			md5Digest.update(bytes);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	public static void main(String[] args){
		String source = "{\"appId\":\"P34123\",\"openId\":\"790291445328987768\",\"timestamp\":\"1447828425310\"}";
		String signature = "";
		String key = "abcdefgh12345678";
		signature = getSignWithMD5(source);
		System.out.println(signature);
		String encrystr = AESCBCUtils.encrypt_ecb(signature, key);
		System.out.println(encrystr);
		
		String decrypt_str = AESCBCUtils.decrypt_ecb(encrystr, key);
		System.out.println(decrypt_str);
	}
}
