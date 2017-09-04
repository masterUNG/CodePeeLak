package com.student.codemobile.myauthendemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sitrach on 17/07/2017.
 */

public class DBAdapter{
    private final SQLiteDatabase mDataBase;

    public DBAdapter(Context _context){
//        String dbPath = CMAssetBundle.getAppPackagePath(_context) + "/Account.db";
        String dbPath = CMAssetBundle.getAppPackagePath(_context) + "/UserPassword.db";
        mDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void insert(UserBean bean){
        //mDataBase.execSQL("");
        ContentValues values = new ContentValues();
        values.put(UserBean.COLUMN_USERNAME, bean.username);
        values.put(UserBean.COLUMN_PASSWORD, bean.password);
        values.put(UserBean.COLUMN_PASSWORD_REM, bean.isPasswordRemembered);
        mDataBase.insert(UserBean.TABLE_NAME, null, values);
    }

    public void update(UserBean bean){
        //mDataBase.execSQL("");
        ContentValues values = new ContentValues();
        values.put(UserBean.COLUMN_USERNAME, bean.username);
        values.put(UserBean.COLUMN_PASSWORD, bean.password);
        values.put(UserBean.COLUMN_PASSWORD_REM, bean.isPasswordRemembered);
        String whereClause = "username = ?";
        String[] whereArgs = new String[]{bean.username};

        mDataBase.update(UserBean.TABLE_NAME, values, whereClause, whereArgs);
    }

    public UserBean query(final String username){
        UserBean result = null;
        //mDataBase.rawQuery("");
        String[] columns = new String[]{UserBean.COLUMN_USERNAME, UserBean.COLUMN_PASSWORD, UserBean.COLUMN_PASSWORD_REM};
        String whereClause = "username = ?";
        String[] whereArgs = new String[]{username};
        Cursor cursor = mDataBase.query(UserBean.TABLE_NAME,columns, whereClause, whereArgs, null, null, "username");
        if (cursor.getCount() >0 ){
            cursor.moveToFirst();
            result = new UserBean();
            result.username = cursor.getString(cursor.getColumnIndex(UserBean.COLUMN_USERNAME));
            result.password = cursor.getString(cursor.getColumnIndex(UserBean.COLUMN_PASSWORD));
            result.isPasswordRemembered = cursor.getInt(cursor.getColumnIndex(UserBean.COLUMN_PASSWORD_REM));
        }
        return result;
    }
}
