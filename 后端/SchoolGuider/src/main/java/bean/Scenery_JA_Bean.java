package bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table( name = "scenery_ja", schema = "guider" )

public class Scenery_JA_Bean {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    int id;
    @Basic
    String scenicName;
    @Basic
    String introcude;
    @Basic
    String voiceName;
    @Basic
    String imageNameList;

    public Scenery_JA_Bean() {}

    public Scenery_JA_Bean(String introcude, String voiceName, String imageNameList) {
        this.introcude = introcude;
        this.voiceName = voiceName;
        this.imageNameList = imageNameList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntrocude() {
        return introcude;
    }

    public void setIntrocude(String introcude) {
        this.introcude = introcude;
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
        ScenicBean that = (ScenicBean) o;
        return id == that.id &&
                Objects.equals(introcude, that.introcude) &&
                Objects.equals(voiceName, that.voiceName) &&
                Objects.equals(imageNameList, that.imageNameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, introcude, voiceName, imageNameList);
    }

    @Override
    public String toString() {
        return "ScenicBean{" +
                "id=" + id +
                ", scenicName='" + scenicName + '\'' +
                ", introcude='" + introcude + '\'' +
                ", voiceName='" + voiceName + '\'' +
                ", imageNameList='" + imageNameList + '\'' +
                '}';
    }
}

