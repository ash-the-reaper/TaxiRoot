package mu.ac.uomtrust.shashi.taximauritius;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vgobin on 02-Jun-17.
 */

public class Database extends SQLiteOpenHelper {

    private static final int databaseVersion = 1;
    private static final String databaseName = "TaxiMauritius.db";
    private static Database mInstance = null;
    private SQLiteDatabase db;

    public Database(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    public static Database getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new Database(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public Database open() throws SQLException {
        db = this.getWritableDatabase();
        return this;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTables(SQLiteDatabase db) {
        createTableAccount(db);
        createTableTransaction(db);
        createTableTaxi(db);
    }

    private void createTableAccount(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS account (" +
                " id INTEGER PRIMARY KEY NOT NULL, " +
                " email TEXT UNIQUE NOT NULL, " +
                " password TEXT, " +
                " address TEXT, " +
                " profile_picture BLOB, " +
                " first_name TEXT NOT NULL, " +
                " last_name TEXT NOT NULL, " +
                " facebook_user_id TEXT, " +
                " role INTEGER NOT NULL, " +
                " status INTEGER NOT NULL, " +
                " date_created NUMERIC DEFAULT NULL, " +
                " date_updated NUMERIC DEFAULT NULL ); ";
        db.execSQL(qb);
    }

    private void createTableTransaction(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS cab (" +
                " id INTEGER PRIMARY KEY NOT NULL, " +
                " account_id INTEGER NOT NULL, " +
                " driver_id INTEGER NOT NULL, " +
                " ad_status INTEGER, " +
                " payment_status INTEGER, " +
                " price real, "+
                " from TEXT, "+
                " to TEXT, "+
                " date_created NUMERIC DEFAULT NULL ); ";
        db.execSQL(qb);
    }

    private void createTableTaxi(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS cab (" +
                " id INTEGER PRIMARY KEY NOT NULL, " +
                " account_id INTEGER NOT NULL, " +
                " make TEXT NOT NULL, " +
                " year INTEGER, " +
                " num_of_passenger INTEGER, "+
                " plate_num UNIQUE TEXT, "+
                " comments TEXT ); ";
        db.execSQL(qb);
    }
}
