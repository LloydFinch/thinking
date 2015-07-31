package cn.androidy.logger.data;

public class LogEntity implements ILog {
    private String tag;
    private String msg;

    public LogEntity(String tag, String msg) {
        super();
        this.tag = tag;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String getLogTag() {
        return tag;
    }
}
