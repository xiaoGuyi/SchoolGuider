package controller;

import bean.DBTool;
import bean.RecordDao;
import bean.Scenery_CN_Bean;
import bean.Scenery_EN_Bean;
import bean.Scenery_JA_Bean;
import bean.ScenicBean;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping( "/record" )
public class RecordController {
    @RequestMapping( "/get_record" )
    @ResponseBody
    public String get_record() {
        System.out.println( "begin to get record\n" );
        DBTool.INSTANCE.init();
        List<ScenicBean> list = RecordDao.getRecord();
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }

    @RequestMapping( value = "/get_recordCN" , produces = "application/json; charset=utf-8")
    @ResponseBody
    public String get_recordCN(String search) {
        System.out.println( "begin to get record\n" );
        DBTool.INSTANCE.init();
        List<Scenery_CN_Bean> list = RecordDao.getRecordCN(search);
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }

    @RequestMapping( "/get_recordEN" )
    @ResponseBody
    public String get_recordEN(String search) {
        System.out.println( "begin to get record\n" );
        DBTool.INSTANCE.init();
        List<Scenery_EN_Bean> list = RecordDao.getRecordEN(search);
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }

    @RequestMapping( "/get_recordJA" )
    @ResponseBody
    public String get_recordJA(String search) {
        System.out.println( "begin to get record\n" );
        DBTool.INSTANCE.init();
        List<Scenery_JA_Bean> list = RecordDao.getRecordJA(search);
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }
}
