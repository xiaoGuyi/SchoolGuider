package bean;

import kotlin.jvm.Synchronized;
import org.hibernate.Query;

import java.util.List;

public class UserDao {
    public static boolean isExist( String openId ) {
        DBTool.INSTANCE.init();
        String sql = "from User where openId = '" + openId + "'";
        Query query = DBTool.session.createQuery( sql );
        List list = query.list();
        DBTool.INSTANCE.destroy();
        return list.size() != 0;
    }

    public static void main( String[] args ) {
        // test
//        User user = new User();
//        user.setOpenId( "aa" );
//        DBTool.INSTANCE.init();
//        DBTool.session.save( user );
//        DBTool.INSTANCE.destroy();
        System.out.println( UserDao.isExist( "aa" ) );
    }

    @Synchronized
    public static User getByOpenId(String openId) {
        DBTool.INSTANCE.init();
        String sql = "from User where openId = '" + openId + "'";
        Query query = DBTool.session.createQuery( sql );
        List<User> list = query.list();
        DBTool.INSTANCE.destroy();
        return list.get(0);
    }

    public static String getNickNameByOpenId(String openId) {
        DBTool.INSTANCE.init();
        String sql = "from User where openId = '" + openId + "'";
        Query query = DBTool.session.createQuery( sql );
        List<User> list = query.list();
        DBTool.INSTANCE.destroy();
        return list.get(0).getNickName();
    }
}
