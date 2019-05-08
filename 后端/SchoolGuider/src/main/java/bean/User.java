package bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_fc_wxminiapp_userinfo", schema = "datahacker", catalog = "")
public class User {
    private long id;
    private String openId;
    private String nickName;
    private String avatarUrl;
    private String country;
    private String province;
    private String city;
    private Integer gender;
    private String language;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "openId")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Basic
    @Column(name = "nickName")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    @Basic
    @Column(name = "avatarUrl")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(openId, user.openId) &&
                Objects.equals(nickName, user.nickName) &&
                Objects.equals(avatarUrl, user.avatarUrl) &&
                Objects.equals(country, user.country) &&
                Objects.equals(province, user.province) &&
                Objects.equals(city, user.city) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(language, user.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openId, nickName, avatarUrl, country, province, city, gender, language);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", gender=" + gender +
                ", language='" + language + '\'' +
                '}';
    }
}
