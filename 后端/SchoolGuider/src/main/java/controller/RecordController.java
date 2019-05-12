package controller;

import bean.DBTool;
import bean.RecordDao;
import bean.Scenery_CN_Bean;
import bean.Scenery_EN_Bean;
import bean.Scenery_JA_Bean;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping( "/record" )
public class RecordController {
    @RequestMapping( value = "/get_recordCN" ,produces = "application/json; charset=utf-8")
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


    @RequestMapping( value = "/updateRecordCN" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public void update_recordCN(Scenery_CN_Bean scenery_CN_Bean) {
        System.out.println( "begin to update record\n" );
        DBTool.INSTANCE.update(scenery_CN_Bean);
    }
    @RequestMapping( "/updateRecordEN" )
    @ResponseBody
    public void update_recordEN(Scenery_EN_Bean scenery_EN_Bean) {
        System.out.println( "begin to update record\n" );
        DBTool.INSTANCE.update(scenery_EN_Bean);
    }
    @RequestMapping( "/updateRecordJA" )
    @ResponseBody
    public void update_recordJA(Scenery_JA_Bean scenery_JA_Bean) {
        System.out.println( "begin to update record\n" );
        DBTool.INSTANCE.update(scenery_JA_Bean);
    }
}
