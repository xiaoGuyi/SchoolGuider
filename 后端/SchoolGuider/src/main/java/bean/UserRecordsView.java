package bean;

public class UserRecordsView extends UserRecords {
    private String nickName;
    private String avatarUrl;

    public UserRecordsView() {}
    public UserRecordsView( UserRecords records ) {
        super.setDt( records.getDt() );
        super.setId( records.getId() );
        super.setOpenId( records.getOpenId() );
        super.setScore( records.getScore() );
        super.setScoreImageUrl( records.getScoreImageUrl() );
        super.setUserVideoUrl( records.getUserVideoUrl() );
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
