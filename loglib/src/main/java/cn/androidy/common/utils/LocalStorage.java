/**
 * *************************************************************************
 * Copyright (c) 2013 cocos2d-x.org
 * <p/>
 * http://www.cocos2d-x.org
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * **************************************************************************
 */

package cn.androidy.common.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

public class LocalStorage {

    private static final String TAG = LocalStorage.class.getSimpleName();

    private static final String DATABASE_NAME = "LocalCache";
    private static final String TABLE_NAME = "data";
    private static final int DATABASE_VERSION = 1;

    private DBOpenHelper mDatabaseOpenHelper = null;
    private SQLiteDatabase mDatabase = null;

    private static LocalStorage instance;

    private LocalStorage(Context mContext) {
        init(mContext);
    }

    public static LocalStorage getIntance(Context mContext) {
        if (null == instance) {
            instance = new LocalStorage(mContext);
        }
        return instance;
    }

    /**
     * Constructor
     *
     * @param mContext
     *            The Context within which to work, used to create the DB
     * @return
     */
    private void init(Context mContext) {
        mDatabaseOpenHelper = new DBOpenHelper(mContext);
        mDatabase = mDatabaseOpenHelper.getWritableDatabase();
    }

    public void destory() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public void setItem(String key, String value) {
        try {
            String sql = "replace into " + TABLE_NAME + "(key,value)values(?,?)";
            mDatabase.execSQL(sql, new Object[]{key, value});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置当前环境标志
     */
    public void setEnvTag(String tag) {
        setItem("env", tag);
        if (TextUtils.isEmpty(tag)) {
            setItem("env_content", "");
        }
    }

    public String getEnv() {
        return getItem("env");
    }

    public void setEnvTaContent(String content) {
        setItem("env_content", content);
    }

    public String getEnvContent() {
        return getItem("env_content");
    }

    // // added by cloudbox
    public void setByteItem(String key, byte[] value) {
        try {
            String sql = "replace into " + TABLE_NAME + "(key,value)values(?,?)";
            mDatabase.execSQL(sql, new Object[]{key, value});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getItem(String key) {
        String ret = null;
        try {
            String sql = "select value from " + TABLE_NAME + " where key=?";
            Cursor c = mDatabase.rawQuery(sql, new String[]{key});
            while (c.moveToNext()) {
                // only return the first value
                if (ret != null) {
                    Log.e(TAG, "The key contains more than one value.");
                    break;
                }
                ret = c.getString(c.getColumnIndex("value"));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret == null ? "" : ret;
    }

    // // added by cloudbox
    public byte[] getByteItem(String key) {
        byte[] ret = null;
        try {
            String sql = "select value from " + TABLE_NAME + " where key=?";
            Cursor c = mDatabase.rawQuery(sql, new String[]{key});
            while (c.moveToNext()) {
                // only return the first value
                if (ret != null) {
                    Log.e(TAG, "The key contains more than one value.");
                    break;
                }
                ret = c.getBlob(c.getColumnIndex("value"));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void removeItem(String key) {
        try {
            String sql = "delete from " + TABLE_NAME + " where key=?";
            mDatabase.execSQL(sql, new Object[]{key});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clean() {
        try {
            String sql = "delete from " + TABLE_NAME + ";";
            mDatabase.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This creates/opens the database.
     */
    private class DBOpenHelper extends SQLiteOpenHelper {

        DBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(key TEXT PRIMARY KEY,value TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            // db.execSQL("DROP TABLE IF EXISTS " + VIRTUAL_TABLE);
            // onCreate(db);
        }

        @Override
        public synchronized void close() {
            // TODO Auto-generated method stub
            super.close();
        }
    }
}
