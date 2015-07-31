package cn.androidy.logger.data;

/**
 * @author mwp 2015-7-28 15:41:51
 */
public class LogCategory implements ILog {
    private String tag;

    public LogCategory(String tag) {
        super();
        this.tag = tag;
    }

    @Override
    public String getLogTag() {
        return tag;
    }
}
