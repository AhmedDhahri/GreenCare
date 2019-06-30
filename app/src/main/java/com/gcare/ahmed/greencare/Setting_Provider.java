package com.gcare.ahmed.greencare;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class Setting_Provider extends ContentProvider{

    SQLiteDatabase db;
    static String Authority = "com.gcare.ahmed.greencare.Setting_Provider";
    static UriMatcher URIMatch = new UriMatcher(UriMatcher.NO_MATCH);
    static Uri Content = Uri.parse("content://" + "com.gcare.ahmed.greencare.Setting_Provider" + "/SETTINGS");
    static String CREATE = "CREATE TABLE SETTINGS(" +
            "ID INTEGER  PRIMARY KEY AUTOINCREMENT," +
            "SSID VARCHAR(32)," +
            "PASS VARCHAR(64)," +
            "SLEEPT INTEGER," +
            "SETTIME INTEGER)";
    static{
        URIMatch.addURI(Authority,"SETTINGS/",1);
    }
    static String qGroupBy = null;
    static String qHaving = null;

    @Override
    public boolean onCreate() {
        dbHelper d = new dbHelper(getContext());
        db = d.getWritableDatabase();
        return (db == null)?false:true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Long RowID = db.insert("SETTINGS",null, values);
        if(RowID >0){
            Uri _uri = ContentUris.withAppendedId(Content,RowID);
            getContext().getContentResolver().notifyChange(_uri,null);
            db.close();
            return _uri;
        }else
            throw new SQLException("Failed to add a record into " + uri);


        }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
        sqb.appendWhere("ID="+uri.getPathSegments().get(1));
        if(sortOrder == null || sortOrder.equals("")){
            sortOrder = "ID";
        }
        Cursor c = sqb.query(db,projection,selection,selectionArgs,qGroupBy,qHaving,sortOrder);
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/vnd.example.SETTINGS";
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        try {
            count = db.delete("SETTINGS", selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri,null);
            return count;
        }finally {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        try {
            count = db.update("SETTINGS", values, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri,null);
            return count;
        }finally {
            throw new IllegalArgumentException("Unknown URI " + uri );
        }

    }
    static class dbHelper extends SQLiteOpenHelper {

        public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, "SETTINGS", null, 1);
        }

        public dbHelper(Context context) {
            super(context, "SETTINGS", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP IF EXISTS SETTINGS");
            onCreate(db);
        }
    }
}
