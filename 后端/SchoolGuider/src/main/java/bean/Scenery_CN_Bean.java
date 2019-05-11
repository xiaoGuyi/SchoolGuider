package bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table( name = "scenery_cn", schema = "guider" )
public class Scenery_CN_Bean {
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

    public Scenery_CN_Bean() {}

    public Scenery_CN_Bean(String introduce, String voiceName, String imageNameList) {
        this.introduce = introduce;
        this.voiceName = voiceName;
        this.imageNameList = imageNameList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        Scenery_CN_Bean that = (Scenery_CN_Bean) o;
        return id == that.id &&
                Objects.equals(introduce, that.introduce) &&
                Objects.equals(voiceName, that.voiceName) &&
                Objects.equals(imageNameList, that.imageNameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, introduce, voiceName, imageNameList);
    }

    @Override
    public String toString() {
        return "Scenery_CN_Bean{" +
                "id=" + id +
                ", scenicName='" + scenicName + '\'' +
                ", introduce='" + introduce + '\'' +
                ", voiceName='" + voiceName + '\'' +
                ", imageNameList='" + imageNameList + '\'' +
                '}';
    }
}
