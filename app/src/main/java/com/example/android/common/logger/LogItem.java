package com.example.android.common.logger;

/**
 * Created by Rick Meng on 2015/7/27.
 */
public interface LogItem {
    public enum ItemType {
        ENTITY, CATEGORY
    }

    public String getLogTag();

    public ItemType getLogItemType();
}
