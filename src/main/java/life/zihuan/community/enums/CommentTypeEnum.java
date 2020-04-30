package life.zihuan.community.enums;

/**
 * @author 60561
 */

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private int type;

    CommentTypeEnum(int type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum:CommentTypeEnum.values()){
            if(commentTypeEnum.getType()==type){
                return true;
            }
        }

        return false;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
