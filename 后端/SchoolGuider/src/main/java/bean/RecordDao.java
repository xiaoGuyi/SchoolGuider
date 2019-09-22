package bean;

import java.util.List;

public class RecordDao {
    public static List<Scenery_ZH_Bean> getRecordZH(String s) {
        String sql = "from Scenery_ZH_Bean where scenicName like '%" + s + "%'";
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
    public static List<Scenery_ZH_Bean> getRecordZH(String placeholder, String cid) {
        String sql = "from Scenery_ZH_Bean where cid =" + cid;
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_EN_Bean> getRecordEN(String placeholder, String cid) {
        String sql = "from Scenery_EN_Bean where cid =" + cid;
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_JA_Bean> getRecordJA(String placeholder, String cid) {
        String sql = "from Scenery_JA_Bean where cid =" + cid;
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }

}
