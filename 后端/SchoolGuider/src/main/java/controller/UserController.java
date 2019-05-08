package controller;

import bean.*;
import com.google.gson.Gson;
import kotlin.jvm.Synchronized;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping( "/user" )
public class UserController {
    @RequestMapping( "/isNewUser" )
    @ResponseBody
    public String isNewUser(ServletRequest request ) {
        if( UserDao.isExist( request.getParameter("openId")) ) {
            return "1";
        }
        return "0";
    }
    @RequestMapping( "/addNewUser" )
    @ResponseBody
    public String addNewUser(User user) {
        System.out.println( user );
        if( UserDao.isExist( user.getOpenId() ) ) {
            return "1";
        }
        DBTool.INSTANCE.save( user );
        return "0";
    }
    @RequestMapping( "/getUserRecords" )
    @ResponseBody
    public String getUserRecords( ServletRequest request ) {
        String openId = request.getParameter( "openId" );
        List res = UserRecordsDao.getByOpenId( openId );
        return new Gson().toJson( res );
    }
    @RequestMapping( "/getRecord" )
    @ResponseBody
    public String getRecord( ServletRequest request ) {
        String recordId = request.getParameter( "recordId" );
        UserRecords record = UserRecordsDao.getById( recordId );
        return new Gson().toJson( record );
    }
    @RequestMapping( "/getRecords" )
    @ResponseBody
    public String getRecords() {
        List<UserRecords> records = UserRecordsDao.get();
        List res = new ArrayList();
        for( UserRecords records1: records ) {
            UserRecordsView view = new UserRecordsView( records1 );
            User user = UserDao.getByOpenId( records1.getOpenId() );
            view.setNickName( user.getNickName() );
            view.setAvatarUrl( user.getAvatarUrl() );
            res.add( view );
        }
        return new Gson().toJson( res );
    }
    @RequestMapping( "/getInfo" )
    @ResponseBody
    public String getInfo( ServletRequest request ) {
        String openId = request.getParameter( "openId" );
        User user = UserDao.getByOpenId( openId );
        return new Gson().toJson( user );
    }
}
