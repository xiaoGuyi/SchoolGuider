package tool.pingan_dependency.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 解密返回密码明文密码
 */
public class SecurityUtils {

	protected static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String KEY_ALGORITHM = "RSA";
    private static final long PWD_ATIVE_TIME = 60000L; // 密码有效时间60秒
    private static final long PWD_ATIVE_TIME_TWO = 60000 * 5L; // 密码有效时间60*5秒---基金新手补贴用

    // //加密公钥(修改成对应环境的加密公钥)
    // private static final String pub =
    // "30819F300D06092A864886F70D010101050003818D00308189028181008E5328628A0B061237AD991C84C64EE2DD8B335641AB6C002901E850C5D2F6BF60FF9B21A82AC1BB58A3BF8379624FC9E0989D6C96A51D8A57303B4D759E95448EFCFE503FD3F1B69CB6D41D1D8DA0FA502547BD5FC3D8D14ABF70962E5BE35A94F2B2067977E8D96D3D6F0875316D9B6C7EF3CBBCFF40B3F70B4D788D6C2DF70203010001";
    // //解密私钥
    // private static final String pri =
    // "30820275020100300D06092A864886F70D01010105000482025F3082025B020100028181008E5328628A0B061237AD991C84C64EE2DD8B335641AB6C002901E850C5D2F6BF60FF9B21A82AC1BB58A3BF8379624FC9E0989D6C96A51D8A57303B4D759E95448EFCFE503FD3F1B69CB6D41D1D8DA0FA502547BD5FC3D8D14ABF70962E5BE35A94F2B2067977E8D96D3D6F0875316D9B6C7EF3CBBCFF40B3F70B4D788D6C2DF702030100010281801FCE69BA0BCB86E1CF5B965A0F3A1A2B0D03ACC75C09A92B27FEEB2211F3A17D077A57FADF901E9775A9B284F93D8543425418EA498840DFC7B859A867DEB6D0B132234D53655D59E4841CEB73523610E1F042229E44886C52B2333FC75756387CA1137264D83336F635CD881657FCBF20878DE9200AE9BCAA53F083649A8DF1024100F85949C6C230DB1BDBADCC16AA743EDEE976B563E973DA85BD6DC7B7A84B5AAF670AE34B0B3A491459C70F4514B1D6B0EA58643881A6E1658505D66E224D9E6502410092B5AA44C910AB6F6873626F63EEE27479930EE407AF234D1214E72A5689647D676B6CA5F60B24A9425CF6C00EDE8D634D5A0307DA8B7C396F2DDCB61451972B02403478F11C9CB523E66BF0F9B92444B16FF9D60A175521145FC3996964459BBE4A673426B819643442A78166998AF8D44AC708EC0C0060D928D1055DBC216552FD024059B1FFBBB6421ECE2A4A0130A1247807461D06C99B7914581669F93B055552BEE8EFE0A946D8613DAA7933D3104438A531B18A90CEA8E0F62C6719BDA88689A9024019DF0AFDA53C8A61C9066260CC9A6FD31DCDEB83A6678ACF3D74E0CDF0CDC210EF11C3BA1DEE8DDA0673B6AB29A654E2DB1B56A97AB32A8CCAE66E26CC08D553";
    private static final String ERROR_CHECK = "CHECK_TIME_STAMP_ERROR";// 时间戳校验异常
    private static final char[] bcdLookup = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final int KEY_SIZE = 1024;
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = KEY_SIZE / 8 - 11;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = KEY_SIZE / 8;

    /**
     * 解密并校验时间戳
     *
     * @param srcPwdForHexStr
     * @param prKForHexStr
     * @return
     * @throws Exception
     */
    public static String decryptAndCheck(String srcPwdForHexStr, String prKForHexStr) throws Exception {
        RSAPrivateKey prK = getPrivateKey(prKForHexStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, prK);
        String srcPwd = new String(cipher.doFinal(hexStrToBytes(srcPwdForHexStr)), "utf-8");
        return checkPwdActiveTime(srcPwd);
    }

    /**
     * 解密
     *
     * @param srcPwdForHexStr
     * @param prKForHexStr
     * @return
     * @throws Exception
     */
    public static String decode(String srcPwdForHexStr, String prKForHexStr) throws Exception {
        RSAPrivateKey prK = getPrivateKey(prKForHexStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, prK);
        String srcPwd = new String(cipher.doFinal(hexStrToBytes(srcPwdForHexStr)), "utf-8");
        return srcPwd;
    }
    
    
    /**
     * BASE64格式的解密
     * @param srcPwdBase64Str
     * @param prKBase64Str
     * @return
     * @throws Exception
     */
    public static String decodeByBase64(String srcPwdBase64Str, String prKForHexStr) throws Exception {
        RSAPrivateKey prK = getPrivateKey(prKForHexStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, prK);
        String srcPwd = new String(cipher.doFinal(Base64Utils.decode(srcPwdBase64Str)), "utf-8");
        return srcPwd;
    }
    
	public static String encode(String str, String s_publicKey) {
		if (str == null || s_publicKey == null) {
        	return null;
        }
		try {             
			PublicKey publicKey = getPublicKey(s_publicKey);
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = cipher.doFinal(str.getBytes("UTF-8"));
            return formatString(new String(Base64.encodeBase64(enBytes), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}  
        return null;
	}
	
	private static String formatString(String sourceStr) {
		if (sourceStr == null) {
        	return null;
        }
		return sourceStr.replaceAll("\\r", "").replaceAll("\\n", "");
	}

    /**
     * @param @param  srcPwdForHexStr
     * @param @param  prKForHexStr
     * @param @return
     * @param @throws Exception 设定文件
     * @return String 返回类型
     * @throws
     * @Title: decryptAndCheck2
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public static String decryptAndCheckTwo(String srcPwdForHexStr, String prKForHexStr) throws Exception {
        RSAPrivateKey prK = getPrivateKey(prKForHexStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, prK);
        String srcPwd = new String(cipher.doFinal(hexStrToBytes(srcPwdForHexStr)), "utf-8");
        /*
		 * if(srcPwd.indexOf("$CurTime=") > 0){ return
		 * srcPwd.substring(0,srcPwd.indexOf("$CurTime=")); }
		 */
        return checkPwdActiveTimeTwo(srcPwd);
    }

    private static String checkPwdActiveTimeTwo(String srcPwdStr) {
        try {
            if (StringUtil.isNotEmpty(srcPwdStr)) {
                long lTime = Long.parseLong(srcPwdStr.substring(srcPwdStr.indexOf("$CurTime=") + 9));
                long curTime = System.currentTimeMillis();
                if ((curTime - lTime) > PWD_ATIVE_TIME_TWO) {
                    return ERROR_CHECK;
                } else {
                    return srcPwdStr.substring(0, srcPwdStr.indexOf("$CurTime="));
                }
            }
        } catch (Exception e) {
        	logger.error("ex=",e);
        	
        }
        return ERROR_CHECK;
    }

    public static byte[] encrypt(byte[] text, RSAPublicKey pubRSA) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubRSA);
        return cipher.doFinal(text);
    }

    /**
     * 校验时间戳
     *
     * @param srcPwdStr
     * @return
     */
    private static String checkPwdActiveTime(String srcPwdStr) {
        try {
            if (StringUtil.isNotEmpty(srcPwdStr)) {
                long lTime = Long.parseLong(srcPwdStr.substring(srcPwdStr.indexOf("$CurTime=") + 9));
                long curTime = System.currentTimeMillis();
                if ((curTime - lTime) > PWD_ATIVE_TIME) {
                    return ERROR_CHECK;
                } else {
                    return srcPwdStr.substring(0, srcPwdStr.indexOf("$CurTime="));
                }
            }
        } catch (Exception e) {
        	logger.error("ex=",e);
        }
        return ERROR_CHECK;
    }

    private static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = hexStrToBytes(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }
    
    private static RSAPrivateKey getPrivateKeyByBase64(String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    /**
     * Transform the specified byte into a Hex String form.
     */
    public static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);
        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }
        return s.toString();
    }

    /**
     * Transform the specified Hex String into a byte array.
     */
    public static final byte[] hexStrToBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    // 生成16进制公私钥字符串
    private static Map<String, String> getGenKeyPair() throws Exception {
        Map<String, String> keyMap = new HashMap<String, String>();
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        SecureRandom random = new SecureRandom();
        keygen.initialize(1024, random);
        // 取得密钥对
        KeyPair kp = keygen.generateKeyPair();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
        String privateKeyString = bytesToHexStr(privateKey.getEncoded());

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        String publicKeyString = bytesToHexStr(publicKey.getEncoded());
        keyMap.put(PUBLIC_KEY, publicKeyString);
        keyMap.put(PRIVATE_KEY, privateKeyString);
        return keyMap;
    }

    public static void main(String[] args) throws Exception {
        String pub = "30819F300D06092A864886F70D010101050003818D00308189028181008E5328628A0B061237AD991C84C64EE2DD8B335641AB6C002901E850C5D2F6BF60FF9B21A82AC1BB58A3BF8379624FC9E0989D6C96A51D8A57303B4D759E95448EFCFE503FD3F1B69CB6D41D1D8DA0FA502547BD5FC3D8D14ABF70962E5BE35A94F2B2067977E8D96D3D6F0875316D9B6C7EF3CBBCFF40B3F70B4D788D6C2DF70203010001";
        String pri = "30820275020100300D06092A864886F70D01010105000482025F3082025B020100028181008E5328628A0B061237AD991C84C64EE2DD8B335641AB6C002901E850C5D2F6BF60FF9B21A82AC1BB58A3BF8379624FC9E0989D6C96A51D8A57303B4D759E95448EFCFE503FD3F1B69CB6D41D1D8DA0FA502547BD5FC3D8D14ABF70962E5BE35A94F2B2067977E8D96D3D6F0875316D9B6C7EF3CBBCFF40B3F70B4D788D6C2DF702030100010281801FCE69BA0BCB86E1CF5B965A0F3A1A2B0D03ACC75C09A92B27FEEB2211F3A17D077A57FADF901E9775A9B284F93D8543425418EA498840DFC7B859A867DEB6D0B132234D53655D59E4841CEB73523610E1F042229E44886C52B2333FC75756387CA1137264D83336F635CD881657FCBF20878DE9200AE9BCAA53F083649A8DF1024100F85949C6C230DB1BDBADCC16AA743EDEE976B563E973DA85BD6DC7B7A84B5AAF670AE34B0B3A491459C70F4514B1D6B0EA58643881A6E1658505D66E224D9E6502410092B5AA44C910AB6F6873626F63EEE27479930EE407AF234D1214E72A5689647D676B6CA5F60B24A9425CF6C00EDE8D634D5A0307DA8B7C396F2DDCB61451972B02403478F11C9CB523E66BF0F9B92444B16FF9D60A175521145FC3996964459BBE4A673426B819643442A78166998AF8D44AC708EC0C0060D928D1055DBC216552FD024059B1FFBBB6421ECE2A4A0130A1247807461D06C99B7914581669F93B055552BEE8EFE0A946D8613DAA7933D3104438A531B18A90CEA8E0F62C6719BDA88689A9024019DF0AFDA53C8A61C9066260CC9A6FD31DCDEB83A6678ACF3D74E0CDF0CDC210EF11C3BA1DEE8DDA0673B6AB29A654E2DB1B56A97AB32A8CCAE66E26CC08D553";
        
         String cc="5084858F74BEA4862C22D6C524669CBADB465E0E04A3046C8B2266D1F893F056B959817A394A2C7E4D42B565DF0E2877C9E538F1358C499C19AD2F563BAAA0DE07A1FF5DB0DC28C0A419CAEFF1070328B58B75948742C5DE3F0347628752C309CC54399950C9D1E0D01F57E7DA2C951819253E04CFABE0F59D3D83F55F6C9465";
       
        //CurTime="1558269481503";
        RSAPrivateKey privateKey = getPrivateKey(pri);
        String CurTime=""+(System.currentTimeMillis());
        
        //String info = "Linsk123456$CurTime="+CurTime; // 密码
        //  String info1 = "123456$CurTime="+CurTime;
          String info1 = "111111$CurTime=1539195135138";
          String infosbank="365A824E7EFC9307E100C6B1A38CBA5FEA969705BABED3DB77460244A9C046CEE3E5EA0F94E3DC0E0444D3880C1F6C54BEBEA1703F16F7AC26413090CCC61A39F8DE2666045F1E9CE14687E2A64ADFCD0342781BA33E586DF6799438E3AF9AE7FEEF9BD8CD5EFCBAA7BB1D5219D0320C5069A9E3C9EFAE749C184C4F22060D9B";
        // +
        // 时间戳
        // (修改成对应的密码,
        // 时间戳不用修改)
        // 加密
          //hash处理
        String key1="40001";//银行编号 10011-泸州
        String info = "qq123456"; // 密码
        key1=MD5Utils.getSignWithMD5(key1);
        info=HMACSHA1.HmacSHA1EncrypttoString(info, key1);
        info= info + "$CurTime=1546315200000";
         //RSA加密
        byte[] bytes = encryptLongStr(info.getBytes("utf-8"), pub);
        String infos = bytesToHexStr(bytes);
        byte[] bytes1 = encryptLongStr(info1.getBytes("utf-8"), pub);
        String infos1 = bytesToHexStr(bytes1);
       /* byte[] bytesbank = encryptLongStr(info.getBytes("utf-8"), bankpub);
        String infosbank1 = bytesToHexStr(bytesbank);*/
        byte[] bytes2 = encryptLongStr(infosbank.getBytes("utf-8"), pub);
        String infos2 = bytesToHexStr(bytes2);
        System.out.println("加密(hash处理+RSA加密)获得密文(登陆用的密码密文):" + infos);
        System.out.println("加密获得密文(交易用的密码密文):" + infos1);
        System.out.println("加密获得密文(交易用的密码密文):" + infos2);
        
        //infos="8B905320678952E9533D9AD27C0C98567AF29F82F5EA1BD31B5E760072E239BAA9CFD9A1E3E19E78BAF055390A5BFD28ED7E325A22401759709E7AD53FC8552218735E04B5290213E5909F7DF223E4084129A4DD045355BA8053B7D5E65381310DE08282E98AA0B582A051C6095D26523502C1763BF917E542133D6CFB325C87";
        
        //String test = decrypt(infos, pri);
        //test=decryptAndCheck(infos, pri);
         //System.out.println("解密获得密码明文:" + test);
         /* String sign = sign(infos.getBytes("utf-8"), pri);
        System.out.println(sign);
        System.out.println(verify(infos.getBytes("utf-8"), pub, sign));*/
    }

    public static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = hexStrToBytes(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    /**
     * @param @param  srcPwdForHexStr
     * @param @param  prKForHexStr
     * @param @throws Exception 设定文件
     * @return String 返回类型
     * @throws
     * @Title: RSA分段解密
     * @author EX-YANJIANGBO001
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public static String decrypt(String srcPwdForHexStr, String prKForHexStr) throws Exception {
        RSAPrivateKey prK = getPrivateKey(prKForHexStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, prK);

        byte[] encryptedData = hexStrToBytes(srcPwdForHexStr);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();

        String srcPwd = new String(decryptedData, "utf-8");
		/*
		 * if(srcPwd.indexOf("$CurTime=") > 0){ return
		 * srcPwd.substring(0,srcPwd.indexOf("$CurTime=")); }
		 */
        return srcPwd;
    }

    /**
     * 分段加密
     *
     * @param text
     * @return
     * @throws Exception
     * @author EX-YANJIANGBO001
     */
    public static byte[] encryptLongStr(byte[] text, String publickKey) throws Exception {
        RSAPublicKey pubRSA = getPublicKey(publickKey);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubRSA);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = text.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(text, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(text, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = hexStrToBytes(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = hexStrToBytes(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }
}
