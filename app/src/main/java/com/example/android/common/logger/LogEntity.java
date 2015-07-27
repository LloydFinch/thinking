package com.example.android.common.logger;

public class LogEntity implements LogItem {
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

    @Override
    public ItemType getLogItemType() {
        return ItemType.ENTITY;
    }
}
