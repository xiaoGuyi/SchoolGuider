package controller;

import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tool.PingAnTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import bean.EmotionMessageBean;

@Controller
@RequestMapping( "/emotion" )
public class EmotionController {
    @RequestMapping( "/emotionIdentify" )
    public void emotionIdentify( HttpServletRequest request, HttpServletResponse response ) throws Exception {
        class Result {
            int result;
            double rate;
        }
        Result result = new Result();
        result.result = 1;

        String imagePath = uploadImage( request );
        String faceType = (String) request.getAttribute( "faceType" );
        String emotionMessage = new PingAnTool().getEmotionMessage( imagePath );

        if( !emotionMessage.equals("fail") ) {
            List list = new Gson().fromJson( emotionMessage, List.class );
            for( int i = 0; i < list.size(); ++i ) {
                EmotionMessageBean bean = new Gson().fromJson( list.get(i).toString(), EmotionMessageBean.class );
                if( bean.type.equals( faceType ) ) {
                    result.result = 0;
                    result.rate = bean.confid;
                }
            }
        }

        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("result", result.result );
        res.put("rate", result.rate );
        printWriter.write(new Gson().toJson( res ));
        printWriter.flush();
    }

    @RequestMapping( "/getCompareResult" )
    @ResponseBody
    public String getCompareResult( HttpServletRequest request ) throws Exception {
        String baseImage = request.getParameter( "baseImage" );
        String userImage = request.getParameter( "userImage" );
        String basePath = request.getRealPath("/upload") + "/" + baseImage;
        String userPath1 = request.getRealPath("/upload") + "/" + userImage;

        String[] baseMsgs = {"[{\"confid\":0.244,\"type\":\"Optimism\"},{\"confid\":0.16,\"type\":\"Joy\"},{\"confid\":0.1,\"type\":\"Vitality\"},{\"confid\":0.1,\"type\":\"Apprehension\"},{\"confid\":0.057,\"type\":\"Harmony\"},{\"confid\":0.051,\"type\":\"Desire\"},{\"confid\":0.04,\"type\":\"Sincerity\"},{\"confid\":0.04,\"type\":\"Annoyance\"},{\"confid\":0.028,\"type\":\"Serenity\"},{\"confid\":0.025,\"type\":\"Depression\"},{\"confid\":0.021,\"type\":\"Submission\"},{\"confid\":0.018,\"type\":\"Acceptance\"},{\"confid\":0.016,\"type\":\"Surprise\"},{\"confid\":0.015,\"type\":\"Boredom\"},{\"confid\":0.014,\"type\":\"Grievance\"},{\"confid\":0.011,\"type\":\"Trust\"},{\"confid\":0.011,\"type\":\"Fatigue\"},{\"confid\":0.007,\"type\":\"Calm\"},{\"confid\":0.006,\"type\":\"Interest\"},{\"confid\":0.004,\"type\":\"Admiration\"},{\"confid\":0.004,\"type\":\"Fear\"},{\"confid\":0.004,\"type\":\"Cowardice\"},{\"confid\":0.003,\"type\":\"Boastfulness\"},{\"confid\":0.003,\"type\":\"Anticipation\"},{\"confid\":0.003,\"type\":\"Puzzlement\"},{\"confid\":0.003,\"type\":\"Pride\"},{\"confid\":0.002,\"type\":\"Passiveness\"},{\"confid\":0.002,\"type\":\"Embarrassed\"},{\"confid\":0.002,\"type\":\"Pessimism\"},{\"confid\":0.001,\"type\":\"Tension\"},{\"confid\":0.001,\"type\":\"Neutral\"},{\"confid\":0.001,\"type\":\"Disgust\"},{\"confid\":0.001,\"type\":\"Suspicion\"},{\"confid\":0.001,\"type\":\"Bravery\"},{\"confid\":0.001,\"type\":\"Awe\"},{\"confid\":0.001,\"type\":\"Angry\"},{\"confid\":0.001,\"type\":\"Deceptiveness\"},{\"confid\":0.0,\"type\":\"Contempt\"},{\"confid\":0.0,\"type\":\"Aggressiveness\"},{\"confid\":0.0,\"type\":\"Distraction\"},{\"confid\":0.0,\"type\":\"Conflict\"},{\"confid\":0.0,\"type\":\"Disapproval\"},{\"confid\":0.0,\"type\":\"Sadness\"},{\"confid\":0.0,\"type\":\"Uneasiness\"},{\"confid\":0.0,\"type\":\"Love\"},{\"confid\":0.0,\"type\":\"Insincerity\"},{\"confid\":0.0,\"type\":\"Hate\"},{\"confid\":0.0,\"type\":\"Envy\"},{\"confid\":0.0,\"type\":\"Defiance\"},{\"confid\":0.0,\"type\":\"Neglect\"},{\"confid\":0.0,\"type\":\"Gratitude\"},{\"confid\":0.0,\"type\":\"Insult\"},{\"confid\":0.0,\"type\":\"Remorse\"},{\"confid\":0.0,\"type\":\"Shame\"}]", "[{\"confid\":0.291,\"type\":\"Harmony\"},{\"confid\":0.251,\"type\":\"Sincerity\"},{\"confid\":0.185,\"type\":\"Joy\"},{\"confid\":0.117,\"type\":\"Vitality\"},{\"confid\":0.061,\"type\":\"Calm\"},{\"confid\":0.055,\"type\":\"Optimism\"},{\"confid\":0.014,\"type\":\"Grievance\"},{\"confid\":0.01,\"type\":\"Serenity\"},{\"confid\":0.009,\"type\":\"Neutral\"},{\"confid\":0.002,\"type\":\"Acceptance\"},{\"confid\":0.001,\"type\":\"Desire\"},{\"confid\":0.001,\"type\":\"Anticipation\"},{\"confid\":0.001,\"type\":\"Fear\"},{\"confid\":0.001,\"type\":\"Depression\"},{\"confid\":0.001,\"type\":\"Boredom\"},{\"confid\":0.0,\"type\":\"Fatigue\"},{\"confid\":0.0,\"type\":\"Trust\"},{\"confid\":0.0,\"type\":\"Apprehension\"},{\"confid\":0.0,\"type\":\"Annoyance\"},{\"confid\":0.0,\"type\":\"Contempt\"},{\"confid\":0.0,\"type\":\"Embarrassed\"},{\"confid\":0.0,\"type\":\"Submission\"},{\"confid\":0.0,\"type\":\"Interest\"},{\"confid\":0.0,\"type\":\"Surprise\"},{\"confid\":0.0,\"type\":\"Pessimism\"},{\"confid\":0.0,\"type\":\"Boastfulness\"},{\"confid\":0.0,\"type\":\"Pride\"},{\"confid\":0.0,\"type\":\"Distraction\"},{\"confid\":0.0,\"type\":\"Tension\"},{\"confid\":0.0,\"type\":\"Admiration\"},{\"confid\":0.0,\"type\":\"Cowardice\"},{\"confid\":0.0,\"type\":\"Insincerity\"},{\"confid\":0.0,\"type\":\"Puzzlement\"},{\"confid\":0.0,\"type\":\"Bravery\"},{\"confid\":0.0,\"type\":\"Deceptiveness\"},{\"confid\":0.0,\"type\":\"Love\"},{\"confid\":0.0,\"type\":\"Envy\"},{\"confid\":0.0,\"type\":\"Passiveness\"},{\"confid\":0.0,\"type\":\"Suspicion\"},{\"confid\":0.0,\"type\":\"Disgust\"},{\"confid\":0.0,\"type\":\"Awe\"},{\"confid\":0.0,\"type\":\"Conflict\"},{\"confid\":0.0,\"type\":\"Neglect\"},{\"confid\":0.0,\"type\":\"Defiance\"},{\"confid\":0.0,\"type\":\"Angry\"},{\"confid\":0.0,\"type\":\"Disapproval\"},{\"confid\":0.0,\"type\":\"Sadness\"},{\"confid\":0.0,\"type\":\"Uneasiness\"},{\"confid\":0.0,\"type\":\"Gratitude\"},{\"confid\":0.0,\"type\":\"Hate\"},{\"confid\":0.0,\"type\":\"Aggressiveness\"},{\"confid\":0.0,\"type\":\"Remorse\"},{\"confid\":0.0,\"type\":\"Insult\"},{\"confid\":0.0,\"type\":\"Shame\"}]", "[{\"confid\":0.181,\"type\":\"Annoyance\"},{\"confid\":0.139,\"type\":\"Angry\"},{\"confid\":0.113,\"type\":\"Vitality\"},{\"confid\":0.107,\"type\":\"Optimism\"},{\"confid\":0.064,\"type\":\"Apprehension\"},{\"confid\":0.048,\"type\":\"Contempt\"},{\"confid\":0.044,\"type\":\"Anticipation\"},{\"confid\":0.044,\"type\":\"Puzzlement\"},{\"confid\":0.041,\"type\":\"Cowardice\"},{\"confid\":0.038,\"type\":\"Submission\"},{\"confid\":0.033,\"type\":\"Joy\"},{\"confid\":0.014,\"type\":\"Aggressiveness\"},{\"confid\":0.014,\"type\":\"Tension\"},{\"confid\":0.012,\"type\":\"Remorse\"},{\"confid\":0.01,\"type\":\"Grievance\"},{\"confid\":0.009,\"type\":\"Admiration\"},{\"confid\":0.008,\"type\":\"Surprise\"},{\"confid\":0.007,\"type\":\"Harmony\"},{\"confid\":0.007,\"type\":\"Sadness\"},{\"confid\":0.007,\"type\":\"Desire\"},{\"confid\":0.007,\"type\":\"Conflict\"},{\"confid\":0.007,\"type\":\"Fatigue\"},{\"confid\":0.004,\"type\":\"Hate\"},{\"confid\":0.004,\"type\":\"Acceptance\"},{\"confid\":0.004,\"type\":\"Envy\"},{\"confid\":0.003,\"type\":\"Fear\"},{\"confid\":0.003,\"type\":\"Disgust\"},{\"confid\":0.003,\"type\":\"Pessimism\"},{\"confid\":0.003,\"type\":\"Insult\"},{\"confid\":0.003,\"type\":\"Disapproval\"},{\"confid\":0.003,\"type\":\"Uneasiness\"},{\"confid\":0.003,\"type\":\"Embarrassed\"},{\"confid\":0.002,\"type\":\"Defiance\"},{\"confid\":0.002,\"type\":\"Passiveness\"},{\"confid\":0.001,\"type\":\"Gratitude\"},{\"confid\":0.001,\"type\":\"Deceptiveness\"},{\"confid\":0.001,\"type\":\"Sincerity\"},{\"confid\":0.001,\"type\":\"Serenity\"},{\"confid\":0.001,\"type\":\"Suspicion\"},{\"confid\":0.001,\"type\":\"Boastfulness\"},{\"confid\":0.001,\"type\":\"Pride\"},{\"confid\":0.001,\"type\":\"Trust\"},{\"confid\":0.001,\"type\":\"Calm\"},{\"confid\":0.001,\"type\":\"Boredom\"},{\"confid\":0.0,\"type\":\"Interest\"},{\"confid\":0.0,\"type\":\"Love\"},{\"confid\":0.0,\"type\":\"Shame\"},{\"confid\":0.0,\"type\":\"Depression\"},{\"confid\":0.0,\"type\":\"Neglect\"},{\"confid\":0.0,\"type\":\"Insincerity\"},{\"confid\":0.0,\"type\":\"Bravery\"},{\"confid\":0.0,\"type\":\"Distraction\"},{\"confid\":0.0,\"type\":\"Awe\"},{\"confid\":0.0,\"type\":\"Neutral\"}]", "fail", "[{\"confid\":0.504,\"type\":\"Vitality\"},{\"confid\":0.427,\"type\":\"Joy\"},{\"confid\":0.06,\"type\":\"Optimism\"},{\"confid\":0.004,\"type\":\"Disgust\"},{\"confid\":0.002,\"type\":\"Harmony\"},{\"confid\":0.001,\"type\":\"Apprehension\"},{\"confid\":0.001,\"type\":\"Angry\"},{\"confid\":0.0,\"type\":\"Deceptiveness\"},{\"confid\":0.0,\"type\":\"Aggressiveness\"},{\"confid\":0.0,\"type\":\"Annoyance\"},{\"confid\":0.0,\"type\":\"Sincerity\"},{\"confid\":0.0,\"type\":\"Contempt\"},{\"confid\":0.0,\"type\":\"Pride\"},{\"confid\":0.0,\"type\":\"Surprise\"},{\"confid\":0.0,\"type\":\"Fear\"},{\"confid\":0.0,\"type\":\"Conflict\"},{\"confid\":0.0,\"type\":\"Interest\"},{\"confid\":0.0,\"type\":\"Hate\"},{\"confid\":0.0,\"type\":\"Disapproval\"},{\"confid\":0.0,\"type\":\"Admiration\"},{\"confid\":0.0,\"type\":\"Anticipation\"},{\"confid\":0.0,\"type\":\"Embarrassed\"},{\"confid\":0.0,\"type\":\"Love\"},{\"confid\":0.0,\"type\":\"Desire\"},{\"confid\":0.0,\"type\":\"Grievance\"},{\"confid\":0.0,\"type\":\"Cowardice\"},{\"confid\":0.0,\"type\":\"Submission\"},{\"confid\":0.0,\"type\":\"Acceptance\"},{\"confid\":0.0,\"type\":\"Trust\"},{\"confid\":0.0,\"type\":\"Depression\"},{\"confid\":0.0,\"type\":\"Bravery\"},{\"confid\":0.0,\"type\":\"Passiveness\"},{\"confid\":0.0,\"type\":\"Boredom\"},{\"confid\":0.0,\"type\":\"Calm\"},{\"confid\":0.0,\"type\":\"Boastfulness\"},{\"confid\":0.0,\"type\":\"Insult\"},{\"confid\":0.0,\"type\":\"Tension\"},{\"confid\":0.0,\"type\":\"Serenity\"},{\"confid\":0.0,\"type\":\"Awe\"},{\"confid\":0.0,\"type\":\"Sadness\"},{\"confid\":0.0,\"type\":\"Insincerity\"},{\"confid\":0.0,\"type\":\"Pessimism\"},{\"confid\":0.0,\"type\":\"Envy\"},{\"confid\":0.0,\"type\":\"Puzzlement\"},{\"confid\":0.0,\"type\":\"Suspicion\"},{\"confid\":0.0,\"type\":\"Fatigue\"},{\"confid\":0.0,\"type\":\"Defiance\"},{\"confid\":0.0,\"type\":\"Gratitude\"},{\"confid\":0.0,\"type\":\"Neglect\"},{\"confid\":0.0,\"type\":\"Distraction\"},{\"confid\":0.0,\"type\":\"Remorse\"},{\"confid\":0.0,\"type\":\"Uneasiness\"},{\"confid\":0.0,\"type\":\"Neutral\"},{\"confid\":0.0,\"type\":\"Shame\"}]" };

        String baseMsg = "";
        switch( baseImage ) {
            case "children_1.jpg":
                baseMsg = baseMsgs[0];
                break;
            case "children_2.jpg":
                baseMsg = baseMsgs[1];
                break;
            case "children_3.jpg":
                baseMsg = baseMsgs[2];
                break;
            case "children_4.jpg":
                baseMsg = baseMsgs[3];
                break;
            case "children_5.jpg":
                baseMsg = baseMsgs[4];
                break;
        }
        String userMsg = new PingAnTool().getEmotionMessage( userPath1 );
        if( userMsg.equals( "fail" ) ) {
            return "0";
        }
        List baseList = new Gson().fromJson( baseMsg, List.class );
        List userList = new Gson().fromJson( userMsg, List.class );

        double cnt = 0;
        for( int i = 0; i < baseList.size(); ++i ) {
            // 计算方式： 绝对值之差 除以 预存表情的值
            Bean userBean = new Gson().fromJson( userList.get(i).toString(), Bean.class );
            Bean baseBean = new Gson().fromJson( baseList.get(i).toString(), Bean.class );
            cnt += Math.abs( userBean.confid-baseBean.confid );
        }
        cnt = ( 1 - cnt / baseList.size() ) * 100;

        return String.valueOf( cnt );
    }

    @RequestMapping( "/getResultByVideo" )
    @ResponseBody
    public String getResultByVideo( HttpServletRequest request ) {
        String videoName = request.getParameter( "videoName" );
        String imageNames = request.getParameter( "imageNames" );
        System.out.println( videoName + " " + imageNames );
        int result = new Random().nextInt(40)+60;
        return String.valueOf(result);
    }

    /**
     * 接受上传的文件，并接受用户的参数
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private String uploadImage(HttpServletRequest request) throws UnsupportedEncodingException {
        //获取文件需要上传到的路径
        String path = request.getRealPath("/upload") + "/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }

        request.setCharacterEncoding("utf-8");  //设置编码
        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();

        //如果没以下两行设置的话,上传大的文件会占用很多内存，
        //设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
        /**
         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上，
         * 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到对应目录的硬盘上
         */
        factory.setRepository(dir);
        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
        factory.setSizeThreshold(1024 * 1024);
        //高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);
        String fileName = "";
        String destPath = "";
        try {
            List<FileItem> list = upload.parseRequest(request);
            FileItem picture = null;
            for (FileItem item : list) {
                //获取表单的属性名字
                String name = item.getFieldName();
                //如果获取的表单信息是普通的 文本 信息
                if (item.isFormField()) {
                    //获取用户具体输入的字符串
                    String value = item.getString();
                    request.setAttribute(name, value);
//                    System.out.println( "name=" + name + ",value=" + value );
                } else {
                    picture = item;
                }
            }

            //自定义上传图片的名字为userId.jpg
            // request.getAttribute("userId") 获取参数
            String initialFileName = (String) request.getAttribute("file_name");
            System.out.println( initialFileName );
            fileName =  String.valueOf( Calendar.getInstance().getTimeInMillis() ) + getSuffix( initialFileName );
            destPath = path + fileName;
//            System.out.println( "destPath=" + destPath );

            //真正写到磁盘上
            File file = new File(destPath);
            OutputStream out = new FileOutputStream(file);
            InputStream in = picture.getInputStream();
            int length = 0;
            byte[] buf = new byte[1024];
            // in.read(buf) 每次读到的数据存放在buf 数组中
            while ((length = in.read(buf)) != -1) {
                //在buf数组中取出数据写到（输出流）磁盘上
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
        } catch (Exception e1) {
            System.out.println( e1 );
        }
        return destPath;
    }

    /**
     * 获取文件的后缀名
     * @param imagePath
     * @return
     */
    private String getSuffix(String imagePath) {
        return imagePath.substring( imagePath.lastIndexOf( "." ) );
    }
}
