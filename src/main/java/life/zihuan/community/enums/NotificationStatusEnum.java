package life.zihuan.community.enums;

/**
 * @author 60561
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
