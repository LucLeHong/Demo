package com.example.demo.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.example.demo.sqlite.Person;
import com.example.demo.sqlite.PersonDAO;
import com.example.demo.sqlite.PersonSQLHelper;

import java.util.ArrayList;
import java.util.List;

public class MyContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.demo.cp.MyContentProvider";
    private static final String PERSON_TABLE = PersonDAO.TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse( "Content://" + AUTHORITY + "/" + PERSON_TABLE);

    public static final int JOIN_TABLE = 1;
    public static final int JOIN_ID = 2;
    public static final UriMatcher sURIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMATCHER.addURI(AUTHORITY, PERSON_TABLE, JOIN_TABLE);
        sURIMATCHER.addURI(AUTHORITY, PERSON_TABLE + "/#", JOIN_ID);
    }

    private PersonSQLHelper personSQLHelper;

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        personSQLHelper = new PersonSQLHelper(getContext());
        return false;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType= sURIMATCHER.match(uri);
        SQLiteDatabase sqDB= personSQLHelper.getWritableDatabase();
        int rowDelete= 0;
        switch (uriType){
            case JOIN_TABLE:
                rowDelete = sqDB.delete(PERSON_TABLE,selection,selectionArgs);
                break;
            case JOIN_ID:
                String id=uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowDelete=sqDB.delete(PERSON_TABLE,
                            PersonDAO.COLUMN_ID + "=" + id, null);
                }else
                    rowDelete=sqDB.delete(PERSON_TABLE,
                            PersonDAO.COLUMN_ID + "=" + id + " AND " + selection,
                            selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("unknown URI: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowDelete;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriTYPE = sURIMATCHER.match(uri); // dùng 1 đối tượng URIMatcher để xác định kiểu Uri.
        SQLiteDatabase db = personSQLHelper.getWritableDatabase(); // tham chiếu đến csdl
        long id = 0;
        if (uriTYPE == JOIN_TABLE) {
            id = db.insert(PERSON_TABLE, null, values);
        } else
            throw new UnsupportedOperationException("Unknow URI" + uri);

        getContext().getContentResolver().notifyChange(uri, null);  //thông báo khi CSDL thay đổi
        return Uri.parse(JOIN_TABLE + "/" + id); // trả về URI của hàng vừa thêm
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder= new SQLiteQueryBuilder();
        queryBuilder.setTables(PERSON_TABLE);

        int uriType = sURIMATCHER.match(uri);
        switch (uriType){
            case JOIN_TABLE:
                break;
            case JOIN_ID:
                queryBuilder.appendWhere(PersonDAO.COLUMN_ID+"="+uri.getLastPathSegment());
            default:
                throw new UnsupportedOperationException("Unknown URI");
        }
        Cursor cursor= queryBuilder.query(personSQLHelper.getReadableDatabase(),projection,
                selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType= sURIMATCHER.match(uri);
        SQLiteDatabase sqLiteDatabase = personSQLHelper.getWritableDatabase();
        int rowUpdate=0;
        switch (uriType){
            case JOIN_TABLE:
                rowUpdate= sqLiteDatabase.update(PERSON_TABLE,values,selection,selectionArgs);
                break;
            case JOIN_ID:
                String id= uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowUpdate = sqLiteDatabase.update(PERSON_TABLE,values,
                            PersonDAO.COLUMN_ID + "=" +id,null);

                } else {
                    rowUpdate = sqLiteDatabase.update(PERSON_TABLE,values,
                            PersonDAO.COLUMN_ID+ "="+id+" AND "+ selection,selectionArgs);
                }
                break;
            default:
                throw new UnsupportedOperationException("unknown uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowUpdate;
    }

}