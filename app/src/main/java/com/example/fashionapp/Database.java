package com.example.fashionapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "productcart.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "product_list";
    private static final String COLUMN_ID = "product_id";
    private static final String COLUMN_NAME = "product_name";
    private static final String COLUMN_COLOR = "product_color";
    private static final String COLUMN_SIZE = "product_size";
    private static final String COLUMN_QUANTITY = "product_quantity";
    private static final String COLUMN_PRICE = "product_price";
    private static final String COLUMN_IMAGE = "product_image";

    Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_COLOR + " TEXT, " +
                COLUMN_SIZE + " TEXT, " +
                COLUMN_QUANTITY + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_IMAGE + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    void addData(String name, String color, String size, int quantity, int price, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_SIZE, size);
        cv.put(COLUMN_QUANTITY, quantity);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_IMAGE, image);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Lỗi!", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public ArrayList<cartProduct> readALLData(){
        ArrayList<cartProduct> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while (cursor.moveToNext())
        {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String color = cursor.getString(2);
            String size = cursor.getString(3);
            int quantity = cursor.getInt(4);
            int price = cursor.getInt(5);
            String image = cursor.getString(6);
            cartProduct cartproduct = new cartProduct(id, name,color,size,quantity,price,image);

            arrayList.add(cartproduct);
        }
        cursor.close();
        return arrayList;
    }

    void updateData(String row_id, String name, String color, String size, int quantity, int price, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_SIZE, size);
        cv.put(COLUMN_QUANTITY, quantity);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_IMAGE, image);

        long result = db.update(TABLE_NAME, cv, "product_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Lỗi!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "product_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Lỗi!", Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT);
        }
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }
}
