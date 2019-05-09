package bean;

import java.util.List;

public class RecordDao {
    public static List<ScenicBean> getRecord() {
        String sql = "from ScenicBean";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_CN_Bean> getRecordCN() {
        String sql = "from Scenery_CN_Bean";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_CN_Bean> getRecordCN(String data) {
        String sql = "from Scenery_CN_Bean where scenicname like '%"+data+"%'";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_EN_Bean> getRecordEN() {
        String sql = "from Scenery_EN_Bean";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_EN_Bean> getRecordEN(String data) {
        String sql = "from Scenery_EN_Bean where scenicname like '%"+data+"%'";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_JA_Bean> getRecordJA() {
        String sql = "from Scenery_JA_Bean";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }
    public static List<Scenery_JA_Bean> getRecordJA(String data) {
        String sql = "from Scenery_JA_Bean where scenicname like '%"+data+"%'";
        return DBTool.INSTANCE.getSession().createQuery( sql ).list();
    }


}
