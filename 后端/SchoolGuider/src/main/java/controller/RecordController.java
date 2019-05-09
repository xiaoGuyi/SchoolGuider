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
        DBTool.INSTANCE.init();
        List<ScenicBean> list = RecordDao.getRecord();
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }
    @RequestMapping( "/get_recordCN/{search}" )
    @ResponseBody
    public String get_recordCN(@PathVariable("search") String data) {
        DBTool.INSTANCE.init();
        List<Scenery_CN_Bean> list = RecordDao.getRecordCN(data);
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }

    @RequestMapping( "/get_recordCN" )
    @ResponseBody
    public String get_recordCN() {
        DBTool.INSTANCE.init();
        List<Scenery_CN_Bean> list = RecordDao.getRecordCN();
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }

    @RequestMapping( "/get_recordEN/{search}" )
    @ResponseBody
    public String get_recordEN(@PathVariable("search") String data) {
        DBTool.INSTANCE.init();
        List<Scenery_EN_Bean> list = RecordDao.getRecordEN(data);
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }

    @RequestMapping( "/get_recordEN" )
    @ResponseBody
    public String get_recordEN() {
        DBTool.INSTANCE.init();
        List<Scenery_EN_Bean> list = RecordDao.getRecordEN();
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }

    @RequestMapping( "/get_recordJA/{search}" )
    @ResponseBody
    public String get_recordJA(@PathVariable("search") String data) {
        DBTool.INSTANCE.init();
        List<Scenery_JA_Bean> list = RecordDao.getRecordJA(data);
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }

    @RequestMapping( "/get_recordJA" )
    @ResponseBody
    public String get_recordJA() {
        DBTool.INSTANCE.init();
        List<Scenery_JA_Bean> list = RecordDao.getRecordJA();
        DBTool.INSTANCE.destroy();
        return new Gson().toJson( list );
    }

}
