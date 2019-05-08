package tool;

import cn.xsshome.taip.util.FileUtil;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;
import tool.baidu_dependency.*;

import java.io.IOException;
import java.util.HashMap;

public class BaiduTool {
//    //获取人种中文说明 yellow: 黄种人 white: 白种人 black:黑种人 arabs: 阿拉伯人
//    private static String getRaceType(FaceV3DetectBean faceBDJSON) {
//        String result = "";
//        if (faceBDJSON.getResult().getFace_list().get(0).getRace().getType().equals("yellow")) {
//            result = "黄种人";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getRace().getType().equals("white")) {
//            result = "白种人";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getRace().getType().equals("black")) {
//            result = "黑种人";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getRace().getType().equals("arabs")) {
//            result = "阿拉伯人";
//        } else {
//            result = "未知";
//        }
//        return result;
//    }
//    //获取脸型中文说明  square: 正方形 triangle:三角形 oval: 椭圆 heart: 心形 round: 圆形
//    private static String getFaceShape(FaceV3DetectBean faceBDJSON) {
//        String result = "";
//        if (faceBDJSON.getResult().getFace_list().get(0).getFace_shape().getType().equals("square")) {
//            result = "正方形";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getFace_shape().getType().equals("triangle")) {
//            result = "三角形";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getFace_shape().getType().equals("oval")) {
//            result = "椭圆";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getFace_shape().getType().equals("heart")) {
//            result = "心形";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getFace_shape().getType().equals("round")) {
//            result = "圆形";
//        }  else {
//            result = "未知";
//        }
//        return result;
//    }
//    //获取是否带眼镜 中文说明 none:无眼镜，common:普通眼镜，sun:墨镜
//    private static String getGlasses(FaceV3DetectBean faceBDJSON) {
//        String result = "";
//        if (faceBDJSON.getResult().getFace_list().get(0).getGlasses().getType().equals("none")) {
//            result = "无眼镜";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getGlasses().getType().equals("common")) {
//            result = "普通眼镜";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getGlasses().getType().equals("sun")) {
//            result = "墨镜";
//        } else {
//            result = "未知";
//        }
//        return result;
//    }
//    //获取性别中文说明 性别 male:男性 female:女性
//    private static String getGender(FaceV3DetectBean faceBDJSON) {
//        String result="";
//        if(faceBDJSON.getResult().getFace_list().get(0).getGender().getType().equals("male")){
//            result="男性";
//        }else if (faceBDJSON.getResult().getFace_list().get(0).getGender().getType().equals("female")) {
//            result="女性";
//        }else{
//            result="未知";
//        }
//        return result;
//    }
//    //获取表情中文说明       表情 none:不笑；smile:微笑；laugh:大笑
//    private static String getExpression(FaceV3DetectBean faceBDJSON) {
//        String result="";
//        if (faceBDJSON.getResult().getFace_list().get(0).getExpression().getType().equals("none")) {
//            result = "不笑";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getExpression().getType().equals("smile")) {
//            result = "微笑";
//        } else if (faceBDJSON.getResult().getFace_list().get(0).getExpression().getType().equals("laugh")) {
//            result = "大笑";
//        } else{
//            result = "未知";
//        }
//        return result;
//    }
//    //组装数据
//    private static BDFaceDetectDO getFaceBdDO(FaceV3DetectBean faceBDJSON, String imagePath) {
//        BDFaceDetectDO bdDO = new BDFaceDetectDO();
//        bdDO.setErrorCode(String.valueOf(faceBDJSON.getError_code()));
//        bdDO.setErrorMsg(faceBDJSON.getError_msg());
//        bdDO.setLogId(String.valueOf(faceBDJSON.getLog_id()));
//        bdDO.setTimestamp(String.valueOf(faceBDJSON.getTimestamp()));
//        bdDO.setCached(faceBDJSON.getCached());
//        bdDO.setFaceNum(faceBDJSON.getResult().getFace_num());
//        bdDO.setFaceToken(faceBDJSON.getResult().getFace_list().get(0).getFace_token());
//        bdDO.setFaceProbability(String.valueOf(faceBDJSON.getResult().getFace_list().get(0).getFace_probability()));
//        bdDO.setAge(faceBDJSON.getResult().getFace_list().get(0).getAge());
//        bdDO.setBeauty(String.valueOf(faceBDJSON.getResult().getFace_list().get(0).getBeauty()));
//        bdDO.setExpressionType(faceBDJSON.getResult().getFace_list().get(0).getExpression().getType());
//        bdDO.setFaceShapeType(faceBDJSON.getResult().getFace_list().get(0).getFace_shape().getType());
//        bdDO.setGender(faceBDJSON.getResult().getFace_list().get(0).getGender().getType());
//        bdDO.setGlassesType(faceBDJSON.getResult().getFace_list().get(0).getGlasses().getType());
//        bdDO.setRaceType(faceBDJSON.getResult().getFace_list().get(0).getRace().getType());
//        bdDO.setImagePath(imagePath);
//        return bdDO;
//    }

//    public static void test( String imagePath ) throws IOException {
//        AipFace aipFace = BDFactory.getAipFace();
//        HashMap<String, String> option = new HashMap<String, String>();
//        option.put("face_field","age,beauty,expression,faceshape,gender,glasses,race,qualities");
//        option.put("max_face_num", "1");
//        JSONObject jsonObject = aipFace.detect(Base64Util.encode(FileUtil.readFileByBytes(imagePath)),"BASE64",option);
//        FaceV3DetectBean faceBDJSON = com.alibaba.fastjson.JSONObject.parseObject(jsonObject.toString(),FaceV3DetectBean.class);
//        if(null!=faceBDJSON.getResult()){
//            BDFaceDetectDO faceBdDO = new BDFaceDetectDO();
//            faceBdDO = getFaceBdDO(faceBDJSON,imagePath);
////            faceBdDO.setOpenId(openId);
////            faceBdDO.setNikeName(nickName);
//            BdFaceResponse bdFaceResponse = new BdFaceResponse();
//            bdFaceResponse.setCode(BDConstant.BD_SUCCESS.getCode().toString());
//            bdFaceResponse.setMsg(BDConstant.BD_SUCCESS.getMsg());
//            bdFaceResponse.setAge(String.valueOf(faceBDJSON.getResult().getFace_list().get(0).getAge()));
//            bdFaceResponse.setBeauty(String.valueOf(faceBDJSON.getResult().getFace_list().get(0).getBeauty()));
//            bdFaceResponse.setExpression(faceBDJSON.getResult().getFace_list().get(0).getExpression().getType());
//            bdFaceResponse.setFaceShape(faceBDJSON.getResult().getFace_list().get(0).getFace_shape().getType());
//            bdFaceResponse.setGender(faceBDJSON.getResult().getFace_list().get(0).getGender().getType());
//            bdFaceResponse.setGlasses(faceBDJSON.getResult().getFace_list().get(0).getGlasses().getType());
//            bdFaceResponse.setRaceType(faceBDJSON.getResult().getFace_list().get(0).getRace().getType());
//            bdFaceResponse.setExpression(getExpression(faceBDJSON));
//            bdFaceResponse.setGender(getGender(faceBDJSON));
//            bdFaceResponse.setGlasses(getGlasses(faceBDJSON));
//            bdFaceResponse.setFaceShape(getFaceShape(faceBDJSON));
//            bdFaceResponse.setRaceType(getRaceType(faceBDJSON));
//            String resultData = JSON.toJSONString(bdFaceResponse);
//        }else{
////            System.out.print("人脸检测百度接口返回为:"+faceBDJSON.getError_code()+"======"+faceBDJSON.getError_msg());
////            BdFaceResponse bdFaceResponse = new BdFaceResponse();
////            bdFaceResponse.setCode(BDConstant.BD_NOFACE.getCode().toString());
////            bdFaceResponse.setMsg(BDConstant.BD_NOFACE.getMsg());
////            String resultData = JSON.toJSONString(bdFaceResponse);
//
//        }
//    }

    public static String getMicroExpression( String imagePath ) throws IOException {
        AipFace aipFace = BDFactory.getAipFace();
        HashMap<String, String> option = new HashMap<String, String>();
        option.put("face_field","age,beauty,expression,faceshape,gender,glasses,race,qualities");
        option.put("max_face_num", "1");
        JSONObject jsonObject = aipFace.detect(Base64Util.encode(FileUtil.readFileByBytes(imagePath)),"BASE64",option);
        FaceV3DetectBean faceBDJSON = com.alibaba.fastjson.JSONObject.parseObject(jsonObject.toString(),FaceV3DetectBean.class);
        if(null!=faceBDJSON.getResult()){
//            BDFaceDetectDO faceBdDO = new BDFaceDetectDO();
//            faceBdDO = getFaceBdDO(faceBDJSON,imagePath);
//            faceBdDO.setOpenId(openId);
//            faceBdDO.setNikeName(nickName);
//            BdFaceResponse bdFaceResponse = new BdFaceResponse();
//            bdFaceResponse.setCode(BDConstant.BD_SUCCESS.getCode().toString());
//            bdFaceResponse.setMsg(BDConstant.BD_SUCCESS.getMsg());
//            bdFaceResponse.setAge(String.valueOf(faceBDJSON.getResult().getFace_list().get(0).getAge()));
//            bdFaceResponse.setBeauty(String.valueOf(faceBDJSON.getResult().getFace_list().get(0).getBeauty()));

//            bdFaceResponse.setExpression(faceBDJSON.getResult().getFace_list().get(0).getExpression().getType());

//            bdFaceResponse.setFaceShape(faceBDJSON.getResult().getFace_list().get(0).getFace_shape().getType());
//            bdFaceResponse.setGender(faceBDJSON.getResult().getFace_list().get(0).getGender().getType());
//            bdFaceResponse.setGlasses(faceBDJSON.getResult().getFace_list().get(0).getGlasses().getType());
//            bdFaceResponse.setRaceType(faceBDJSON.getResult().getFace_list().get(0).getRace().getType());
//            bdFaceResponse.setExpression(getExpression(faceBDJSON));
//            bdFaceResponse.setGender(getGender(faceBDJSON));
//            bdFaceResponse.setGlasses(getGlasses(faceBDJSON));
//            bdFaceResponse.setFaceShape(getFaceShape(faceBDJSON));
//            bdFaceResponse.setRaceType(getRaceType(faceBDJSON));
//            String resultData = JSON.toJSONString(bdFaceResponse);

            return faceBDJSON.getResult().getFace_list().get(0).getExpression().getType();
        }else{
            System.out.print("人脸检测百度接口返回为:"+faceBDJSON.getError_code()+"======"+faceBDJSON.getError_msg());
//            BdFaceResponse bdFaceResponse = new BdFaceResponse();
//            bdFaceResponse.setCode(BDConstant.BD_NOFACE.getCode().toString());
//            bdFaceResponse.setMsg(BDConstant.BD_NOFACE.getMsg());
//            String resultData = JSON.toJSONString(bdFaceResponse);
            return "identify fail";
        }
    }

    public static String getSuffix( String imagePath ) {
        return imagePath.substring( imagePath.lastIndexOf( "." ) );
    }

    public static void main( String[] args ) throws IOException {
//        test( "C:\\Users\\Bule-Zst\\Desktop\\u=2771678511,4110295242&fm=27&gp=0.jpg" );
        String imagePath = "http://tmp/wxb6195675ed238653.o6zAJsw0UIRc0n_KPZQJ6s2wzixc.cWyj12Ce9JgP67e70d981ba5611206e8d38f84ae8ebf.jpg";
        System.out.println( getSuffix( imagePath ) );
    }
}
