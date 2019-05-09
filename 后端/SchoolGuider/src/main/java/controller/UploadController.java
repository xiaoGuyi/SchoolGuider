package controller;

import bean.DBTool;
import bean.Scenery_CN_Bean;
import bean.Scenery_EN_Bean;
import bean.Scenery_JA_Bean;
import bean.ScenicBean;
import bean.UserRecords;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tool.BaiduTool;
import tool.PingAnTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping( "/upload" )
public class UploadController {
    @RequestMapping("/uploadFile")
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取文件需要上传到的路径
        String path = request.getRealPath("/upload") + "/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        System.out.println( "path=" + path );

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
                    System.out.println( "name=" + name + ",value=" + value );
                } else {
                    picture = item;
                }
            }

            //自定义上传图片的名字为userId.jpg
            // request.getAttribute("userId") 获取参数
            fileName =  String.valueOf( Calendar.getInstance().getTimeInMillis() ) + getSuffix((String) request.getAttribute("file_name"));
            String destPath = path + fileName;
            System.out.println( "destPath=" + destPath );

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


        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put( "file_name", fileName );
        printWriter.write(new Gson().toJson( res ));
        printWriter.flush();
    }

    @RequestMapping("/uploadRecords")
    @ResponseBody
    public String uploadRecords(ScenicBean scenicBean) {
        System.out.println( "begin to upload record" );
        System.out.println( scenicBean );
        ScenicBean scenicBean1 = (ScenicBean) DBTool.INSTANCE.save( scenicBean );
        return String.valueOf(scenicBean1.getId());
    }

    @RequestMapping("/uploadRecordsCN")

    public String uploadRecordsCN(Scenery_CN_Bean scenery_CN_Bean) {
        System.out.println( "begin to upload record" );
        System.out.println( scenery_CN_Bean );
        Scenery_CN_Bean scenery_CN_Bean1 = (Scenery_CN_Bean) DBTool.INSTANCE.save(scenery_CN_Bean);
        return String.valueOf(scenery_CN_Bean1.getId());
    }

    @RequestMapping("/uploadRecordsEN")

    public String uploadRecordsEN(Scenery_EN_Bean scenery_EN_Bean) {
        System.out.println( "begin to upload record" );
        System.out.println( scenery_EN_Bean );
        Scenery_EN_Bean scenery_EN_Bean1 = (Scenery_EN_Bean) DBTool.INSTANCE.save(scenery_EN_Bean);
        return String.valueOf(scenery_EN_Bean1.getId());
    }

    @RequestMapping("/uploadRecordsJA")

    public String uploadRecordsCN(Scenery_JA_Bean scenery_JA_Bean) {
        System.out.println( "begin to upload record" );
        System.out.println( scenery_JA_Bean );
        Scenery_JA_Bean scenery_JA_Bean1 = (Scenery_JA_Bean) DBTool.INSTANCE.save(scenery_JA_Bean);
        return String.valueOf(scenery_JA_Bean1.getId());
    }

    @RequestMapping("/get_expression")
    public void getExpression(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                    System.out.println( "name=" + name + ",value=" + value );
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
            System.out.println( "destPath=" + destPath );

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


        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("success", true);
        String emotion = new PingAnTool().getEmotionMessage( destPath );
        res.put( "expression", emotion );
        printWriter.write(new Gson().toJson( res ));
        printWriter.flush();
    }

    private String getSuffix(String imagePath) {
        return imagePath.substring( imagePath.lastIndexOf( "." ) );
    }
}
