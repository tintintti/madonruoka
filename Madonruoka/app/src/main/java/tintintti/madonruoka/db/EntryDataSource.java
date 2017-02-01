package tintintti.madonruoka.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import tintintti.madonruoka.data.Entry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for adding, removing and getting database info.
 */
public class EntryDataSource {
    private SQLiteDatabase db;
    private FeedingInfoDbHelper dbHelper;
    private String[] allColumns = {FeedingInfoDbHelper.COLUMN_ID,
            FeedingInfoDbHelper.COLUMN_DATE, FeedingInfoDbHelper.COLUMN_FOODITEM,
            FeedingInfoDbHelper.COLUMN_AMOUNT, FeedingInfoDbHelper.COLUMN_ATE,
            FeedingInfoDbHelper.COLUMN_EXTRA, FeedingInfoDbHelper.COLUMN_UNIT_OF_MEASURE};

    public EntryDataSource(Context context) {
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
     * @return          Entry-object that was added to the database
     */
    public Entry createInfo(String date, String food, double amount, boolean ate, String extra, String unitOfMeasure) {

        ContentValues values = new ContentValues();
        values.put(FeedingInfoDbHelper.COLUMN_DATE, date);
        values.put(FeedingInfoDbHelper.COLUMN_FOODITEM, food);
        values.put(FeedingInfoDbHelper.COLUMN_AMOUNT, amount);
        values.put(FeedingInfoDbHelper.COLUMN_ATE, ate);
        values.put(FeedingInfoDbHelper.COLUMN_EXTRA, extra);
        values.put(FeedingInfoDbHelper.COLUMN_UNIT_OF_MEASURE, unitOfMeasure);

        long insertId = db.insert(FeedingInfoDbHelper.TABLE_NAME, null, values);



        Cursor cursor = db.query(FeedingInfoDbHelper.TABLE_NAME, allColumns,
                FeedingInfoDbHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        Entry newEntry = cursorToInfo(cursor);
        cursor.close();

        return newEntry;
    }

    /**
     * Removes given entry from the database.
     * @param entry Entry to be added to database
     */
    public void deleteInfo(Entry entry) {
        long id = entry.getId();
        System.out.println("Entry deleted with id: " + id);
        db.delete(FeedingInfoDbHelper.TABLE_NAME, FeedingInfoDbHelper.COLUMN_ID + " = " + id, null);
    }

    /**
     *
     * @return a list of the entries in the database
     */
    public List<Entry> getAllInfo() {
        List<Entry> allEntries = new ArrayList<>();

        Cursor cursor = db.query(FeedingInfoDbHelper.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Entry entry = cursorToInfo(cursor);
            allEntries.add(entry);
            cursor.moveToNext();
        }

        cursor.close();

        return allEntries;
    }

    private Entry cursorToInfo(Cursor cursor) {
        Entry entry = new Entry();

        entry.setId(cursor.getLong(0));
        entry.setDate(cursor.getString(1));
        entry.setFoodItem(cursor.getString(2));
        entry.setAmount(cursor.getDouble(3));

        int i = cursor.getInt(4);
        if (i == 0) {
            entry.setAte(false);
        } else {
            entry.setAte(true);
        }

        entry.setExtra(cursor.getString(5));
        entry.setUnitOfMeasure(cursor.getString(6));

        return entry;
    }
}
