package tintintti.madonruoka.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Creates and upgrades the database, and stores the table and column names for easy access.
 */
public class    FeedingInfoDbHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PETNAME = "petName";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_FOODITEM = "foodItem";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_ATE = "ate";
    public static final String COLUMN_EXTRA = "extra";

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "FeedingInfo.db";

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "("
            + COLUMN_ID       + " integer primary key autoincrement, "
            + COLUMN_PETNAME + " text, "
            + COLUMN_DATE     + " text not null, "
            + COLUMN_FOODITEM + " text, "
            + COLUMN_AMOUNT   + " real, "
            + COLUMN_ATE      + " integer, "
            + COLUMN_EXTRA    + " text);";


    public FeedingInfoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FeedingInfoDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
