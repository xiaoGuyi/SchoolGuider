package bean;

import java.util.List;

public class RecordDao {
    public static List<Scenery_CN_Bean> getRecordCN(String s) {
        String sql = "from Scenery_CN_Bean where scenicName like '%" + s + "%'";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_EN_Bean> getRecordEN(String s) {
        String sql = "from Scenery_EN_Bean where scenicName like '%" + s + "%'";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_JA_Bean> getRecordJA(String s) {
        String sql = "from Scenery_JA_Bean where scenicName like '%" + s + "%'";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }




}
