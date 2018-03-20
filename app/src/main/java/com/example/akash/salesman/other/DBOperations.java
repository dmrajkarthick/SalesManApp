package com.example.akash.salesman.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBOperations {

    //DB Details.
    private static String DB_NAME = "SalesManAppDB";


    private static final String CATEGORY_TABLE_NAME = "categorytable";
    public static final String KEY_CATEGORYNAME="category_name";
    public static final String KEY_DISPLAYNAME="display_name";
    public static final String KEY_CATEGORYIMAGE="image";

    private static final int DATABASE_VERSION = 1;
    private static Context myContext;

    //DB Column Details
    public static final String KEY_ITEMID="item_id";
    public static final String KEY_ITEMIMAGE = "item_image";
    public static final String KEY_ITEMNAME = "item_name";
    public static final String KEY_DISPLAYCONTENT= "display_content";
    public static final String KEY_BOOKMARK="bookmark";
    public static final String KEY_OWNER = "owner";
    public static final String KEY_CONTACT = "contact";


    private DBHelper ourHelper;
    private final Context ourContext;
    private static SQLiteDatabase myDataBase;

    public List<CategoryPageItem> getAllCategories() {

        List<CategoryPageItem> categoryPageItems = new ArrayList<>();
        String[] columns = new String[]{KEY_CATEGORYNAME, KEY_DISPLAYNAME, KEY_CATEGORYIMAGE};

        Cursor c = myDataBase.query(CATEGORY_TABLE_NAME, columns,null,null,null,null,null);

        int iCategoryName = c.getColumnIndex(KEY_CATEGORYNAME);
        int iDisplayName = c.getColumnIndex(KEY_DISPLAYNAME);
        int iCategoryImage = c.getColumnIndex(KEY_CATEGORYIMAGE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            CategoryPageItem item = new CategoryPageItem();
            item.setCategory_name(c.getString(iCategoryName));
            item.setDisplay_name(c.getString(iDisplayName));

            byte[] blob = c.getBlob(iCategoryImage);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            item.setCategoryImage(bitmap);
            categoryPageItems.add(item);
        }

        return categoryPageItems;
    }

    public List<ContentItem> getMainContentPageData(String category_name) {

        List<ContentItem> contentItems = new ArrayList<>();
        String[] columns = new String[]{KEY_ITEMID, KEY_ITEMIMAGE, KEY_ITEMNAME, KEY_DISPLAYCONTENT, KEY_BOOKMARK, KEY_CONTACT, KEY_OWNER};

        Cursor c = myDataBase.query(category_name, columns, null,null,null,null,null);

        int iItemID = c.getColumnIndex(KEY_ITEMID);
        int iItemImage = c.getColumnIndex(KEY_ITEMIMAGE);
        int iItemName = c.getColumnIndex(KEY_ITEMNAME);
        int iDisplayContent = c.getColumnIndex(KEY_DISPLAYCONTENT);
        int iBookMark = c.getColumnIndex(KEY_BOOKMARK);
        int iOwner = c.getColumnIndex(KEY_CONTACT);
        int iContact = c.getColumnIndex(KEY_OWNER);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            ContentItem item = new ContentItem();
            item.setItem_id(c.getInt(iItemID));
            byte[] blob = c.getBlob(iItemImage);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            item.setItem_image(bitmap);
            item.setItem_name(c.getString(iItemName));
            item.setDisplay_content(c.getString(iDisplayContent));
            item.setBookmark(c.getInt(iBookMark));
            item.setOwner(c.getString(iOwner));
            item.setContact(c.getString(iContact));

            contentItems.add(item);
        }
        return contentItems;
    }

    private static class DBHelper extends SQLiteOpenHelper
    {
        String DB_PATH = null;


        private final Context myContext;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, 10);
            this.myContext = context;
            this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
            Log.e("Path 1", DB_PATH);
        }

        //TODO: When we upgrade the app, Add a version file and based on the version number, load new version's DB file.
        public void createDataBase() throws IOException {
            boolean dbExist = checkDataBase();
            if (dbExist) {
                // this part of the code replaces the DB everytime App is opened!
                this.getReadableDatabase();
                try {
                    copyDataBase();
                } catch (IOException e) {
                    throw new Error("Error copying database");
                }
            } else {
                this.getReadableDatabase();
                try {
                    copyDataBase();
                } catch (IOException e) {
                    throw new Error("Error copying database");
                }
            }
        }

        private boolean checkDataBase() {
            SQLiteDatabase checkDB = null;
            try {
                String myPath = DB_PATH + DB_NAME;
                File file = new File(myPath);
                if (file.exists() && !file.isDirectory())
                {
                    checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
                }
            } catch (SQLiteException e) {
            }
            if (checkDB != null) {
                checkDB.close();
            }
            return checkDB != null ? true : false;
        }

        private void copyDataBase() throws IOException {
            InputStream myInput = myContext.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[10];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }

        public void openDataBase() throws SQLException {
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory()) {
                myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            }

        }

        @Override
        public synchronized void close() {
            if (myDataBase != null)
                myDataBase.close();
            super.close();
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion)
                try {
                    copyDataBase();
                } catch (IOException e) {
                    e.printStackTrace();

                }
        }

    }

    public DBOperations(Context c)
    {
        ourContext = c;
    }

    public DBOperations open() throws SQLException
    {
        ourHelper = new DBHelper(ourContext);
        //ourDatabase = ourHelper.getWritableDatabase();
        try {

            ourHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }
        ourHelper.openDataBase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

}
