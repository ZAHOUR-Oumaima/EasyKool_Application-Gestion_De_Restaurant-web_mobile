package com.ehtp.ehtpeasykool.Databse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.ehtp.ehtpeasykool.Model.*;

import java.util.ArrayList;
import java.util.List;

public class Sqldatabase extends SQLiteOpenHelper {
    final static String db_name="FoodData.db";

    public Sqldatabase(Context context) {
        super(context, db_name, null, 1);
    }

    public List<Order> getBreakfastCarts(){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlselect={"articleid","articlename","quantity","points","date"};
        String sqlTable="breakfastorders";
        qb.setTables(sqlTable);

        Cursor c =qb.query(db,sqlselect,null,null,null,null,null);

        final  List<Order> result =new ArrayList<>();
        if (c.moveToFirst()){

            do{
                result.add(new Order(c.getString(c.getColumnIndex("articleid")),
                        c.getString(c.getColumnIndex("articlename")),
                        c.getString(c.getColumnIndex("quantity")),
                        c.getString(c.getColumnIndex("points")),
                        c.getString(c.getColumnIndex("date"))
                        ));

            }while (c.moveToNext());
        }
        return result;

    }
    public List<Order> getDinnerCarts(){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlselect={"articleid","articlename","quantity","points","date"};
        String sqlTable="dinnerorders";
        qb.setTables(sqlTable);

        Cursor c =qb.query(db,sqlselect,null,null,null,null,null);

        final  List<Order> result =new ArrayList<>();
        if (c.moveToFirst()){

            do{
                result.add(new Order(c.getString(c.getColumnIndex("articleid")),
                        c.getString(c.getColumnIndex("articlename")),
                        c.getString(c.getColumnIndex("quantity")),
                        c.getString(c.getColumnIndex("points")),
                        c.getString(c.getColumnIndex("date"))
                ));

            }while (c.moveToNext());
        }
        return result;

    }
    public List<Order> getLunchCarts(){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlselect={"articleid","articlename","quantity","points","date"};
        String sqlTable="lunchorders";
        qb.setTables(sqlTable);

        Cursor c =qb.query(db,sqlselect,null,null,null,null,null);

        final  List<Order> result =new ArrayList<>();
        if (c.moveToFirst()){

            do{
                result.add(new Order(c.getString(c.getColumnIndex("articleid")),
                        c.getString(c.getColumnIndex("articlename")),
                        c.getString(c.getColumnIndex("quantity")),
                        c.getString(c.getColumnIndex("points")),
                        c.getString(c.getColumnIndex("date"))
                ));

            }while (c.moveToNext());
        }
        return result;

    }

    public void addToBreakfastCart(Order order){
        SQLiteDatabase db=getWritableDatabase();
        String query=String.format("INSERT INTO breakfastorders (articleid,articlename,quantity,points,date) VALUES ('%s','%s','%s','%s','%s');",
                order.getArticleid(),order.getArticlename(),order.getQuantity(),order.getPoints(),order.getDate()
        );
        db.execSQL(query);
    }

    public void addToLunchCart(Order order){
        SQLiteDatabase db=getWritableDatabase();
        String query=String.format("INSERT INTO lunchorders (articleid,articlename,quantity,points,date) VALUES ('%s','%s','%s','%s','%s');",
                order.getArticleid(),order.getArticlename(),order.getQuantity(),order.getPoints(),order.getDate()
        );
        db.execSQL(query);
    }
    public void addToDinnerCart(Order order){
        SQLiteDatabase db=getWritableDatabase();
        String query=String.format("INSERT INTO dinnerorders (articleid,articlename,quantity,points,date) VALUES ('%s','%s','%s','%s','%s');",
                order.getArticleid(),order.getArticlename(),order.getQuantity(),order.getPoints(),order.getDate()
        );
        db.execSQL(query);
    }
    public void cleanBreakfastCart(){
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM breakfastorders");
        db.execSQL(query);
    }
    public void cleanLunchCart(){
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM lunchorders");
        db.execSQL(query);
    }
    public void cleanDinnerCart(){
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM dinnerorders");
        db.execSQL(query);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS breakfastorders (id INTERGER primary key,articleid TEXT,articlename TEXT,quantity TEXT,points TEXT,date TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS lunchorders (id INTERGER primary key,articleid TEXT,articlename TEXT,quantity TEXT,points TEXT,date TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS dinnerorders (id INTERGER primary key,articleid TEXT,articlename TEXT,quantity TEXT,points TEXT,date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS breakfastorders");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS lunchorders");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS dinnerorders");
        onCreate(sqLiteDatabase);
    }
}
