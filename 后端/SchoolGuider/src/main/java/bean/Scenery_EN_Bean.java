package bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table( name = "scenery_en", schema = "guider" )
public class Scenery_EN_Bean {
    @Id @GeneratedValue( strategy = GenerationType.AUTO )
    int id;
    @Basic
    String scenicName;
    @Basic
    String introduce;
    @Basic
    String voiceName;
    @Basic
    String imageNameList;
    @Basic
    int cid;

    public Scenery_EN_Bean() {}

    public Scenery_EN_Bean(String introduce, String voiceName, String imageNameList,int cid) {
        this.introduce = introduce;
        this.voiceName = voiceName;
        this.imageNameList = imageNameList;
        this.cid = cid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getintroduce() {
        return introduce;
    }

    public void setintroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getVoiceName() {
        return voiceName;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }

    public String getImageNameList() {
        return imageNameList;
    }

    public void setImageNameList(String imageNameList) {
        this.imageNameList = imageNameList;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scenery_EN_Bean that = (Scenery_EN_Bean) o;
        return id == that.id &&
                Objects.equals(introduce, that.introduce) &&
                Objects.equals(voiceName, that.voiceName) &&
                Objects.equals(imageNameList, that.imageNameList)&&
                Objects.equals(cid, that.cid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, introduce, voiceName, imageNameList,cid);
    }

    @Override
    public String toString() {
        return "Scenery_EN_Bean{" +
                "id=" + id +
                ", scenicName='" + scenicName + '\'' +
                ", introduce='" + introduce + '\'' +
                ", voiceName='" + voiceName + '\'' +
                ", imageNameList='" + imageNameList + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
