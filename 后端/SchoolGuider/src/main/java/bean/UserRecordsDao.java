package bean;

import kotlin.jvm.Synchronized;
import org.hibernate.Query;

import java.util.List;

public class UserRecordsDao {
    @Synchronized
    public static List getByOpenId(String openId) {
        DBTool.INSTANCE.init();
        String sql = "from UserRecords where openId = '" + openId + "'";
        Query query = DBTool.session.createQuery( sql );
        List res = query.list();
        DBTool.INSTANCE.destroy();
        return res;
    }

    public static UserRecords getById(String recordId) {
        DBTool.INSTANCE.init();
        String sql = "from UserRecords where id = '" + recordId + "'";
        Query query = DBTool.session.createQuery( sql );
        List<UserRecords> res = query.list();
        DBTool.INSTANCE.destroy();
        return res.get(0);
    }

    public static void main( String[] args ) {
        DBTool.INSTANCE.init();

        User user = new User();
        user.setOpenId( "" );
        DBTool.session.save( user );
        DBTool.INSTANCE.destroy();

        System.out.println( user.getId() );
    }

    public static List<UserRecords> get() {
        DBTool.INSTANCE.init();
        String sql = "from UserRecords";
        Query query = DBTool.session.createQuery( sql ).setMaxResults(5);
        List res = query.list();
        DBTool.INSTANCE.destroy();
        return res;
    }
}
