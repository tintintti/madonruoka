package tintintti.madonruoka.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import tintintti.madonruoka.data.Info;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for adding, removing and getting database info.
 */
public class InfoDataSource {
    private SQLiteDatabase db;
    private FeedingInfoDbHelper dbHelper;
    private String[] allColumns = {FeedingInfoDbHelper.COLUMN_ID,
            FeedingInfoDbHelper.COLUMN_DATE, FeedingInfoDbHelper.COLUMN_FOODITEM,
            FeedingInfoDbHelper.COLUMN_AMOUNT, FeedingInfoDbHelper.COLUMN_ATE, FeedingInfoDbHelper.COLUMN_EXTRA};

    public InfoDataSource(Context context) {
        dbHelper = new FeedingInfoDbHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Creates a new entry to the database.
     *
     * @param date      date of entry
     * @param food      food given
     * @param amount    amount of food
     * @param ate       was the food eaten
     * @param extra     extra notes
     * @return          Info-object that was added to the database
     */
    public Info createInfo(String date, String food, double amount, boolean ate, String extra) {

        ContentValues values = new ContentValues();
        values.put(FeedingInfoDbHelper.COLUMN_DATE, date);
        values.put(FeedingInfoDbHelper.COLUMN_FOODITEM, food);
        values.put(FeedingInfoDbHelper.COLUMN_AMOUNT, amount);
        values.put(FeedingInfoDbHelper.COLUMN_ATE, ate);
        values.put(FeedingInfoDbHelper.COLUMN_EXTRA, extra);

        long insertId = db.insert(FeedingInfoDbHelper.TABLE_NAME, null, values);



        Cursor cursor = db.query(FeedingInfoDbHelper.TABLE_NAME, allColumns,
                FeedingInfoDbHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        Info newInfo = cursorToInfo(cursor);
        cursor.close();

        return newInfo;
    }

    /**
     * Removes given entry from the database.
     * @param info Info to be added to database
     */
    public void deleteInfo(Info info) {
        long id = info.getId();
        System.out.println("Info deleted with id: " + id);
        db.delete(FeedingInfoDbHelper.TABLE_NAME, FeedingInfoDbHelper.COLUMN_ID + " = " + id, null);
    }

    /**
     *
     * @return a list of the entries in the database
     */
    public List<Info> getAllInfo() {
        List<Info> allInfo = new ArrayList<>();

        Cursor cursor = db.query(FeedingInfoDbHelper.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Info info = cursorToInfo(cursor);
            allInfo.add(info);
            cursor.moveToNext();
        }

        cursor.close();

        return allInfo;
    }

    private Info cursorToInfo(Cursor cursor) {
        Info info = new Info();

        info.setId(cursor.getLong(0));
        info.setDate(cursor.getString(1));
        info.setFoodItem(cursor.getString(2));
        info.setAmount(cursor.getDouble(3));

        int i = cursor.getInt(4);
        if (i == 0) {
            info.setAte(false);
        } else {
            info.setAte(true);
        }

        info.setExtra(cursor.getString(5));

        return info;
    }
}
