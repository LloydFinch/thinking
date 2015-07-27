package com.example.android.common.logger;

public class LogCategory implements LogItem {
    private String tag;

    public LogCategory(String tag) {
        super();
        this.tag = tag;
    }

    @Override
    public String getLogTag() {
        return tag;
    }

    @Override
    public ItemType getLogItemType() {
        return ItemType.CATEGORY;
    }
}
