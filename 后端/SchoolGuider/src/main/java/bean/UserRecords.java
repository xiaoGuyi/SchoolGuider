package bean;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "t_fc_wxminiapp_userrecords", schema = "datahacker", catalog = "")
public class UserRecords {
    private long id;
    private String openId;
    private String userVideoUrl;
    private String scoreImageUrl;
    private int score;
    private String dt;

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
    @Column(name = "userVideoUrl")
    public String getUserVideoUrl() {
        return userVideoUrl;
    }

    public void setUserVideoUrl(String userVideoUrl) {
        this.userVideoUrl = userVideoUrl;
    }

    @Basic
    @Column(name = "scoreImageUrl")
    public String getScoreImageUrl() {
        return scoreImageUrl;
    }

    public void setScoreImageUrl(String scoreImageUrl) {
        this.scoreImageUrl = scoreImageUrl;
    }

    @Basic
    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Basic
    @Column(name = "dt")
    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecords that = (UserRecords) o;
        return id == that.id &&
                score == that.score &&
                Objects.equals(openId, that.openId) &&
                Objects.equals(userVideoUrl, that.userVideoUrl) &&
                Objects.equals(scoreImageUrl, that.scoreImageUrl) &&
                Objects.equals(dt, that.dt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openId, userVideoUrl, scoreImageUrl, score, dt);
    }
}
