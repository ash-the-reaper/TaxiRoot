package mu.ac.uomtrust.shashi.taximauritius;

import android.content.Context;
import android.database.Cursor;
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
        createTableCarDetails(db);
        createTableRequest(db);
        createTableManageRequest(db);
    }

    private void createTableAccount(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS account (" +
                " account_id INTEGER PRIMARY KEY NOT NULL, " +
                " email TEXT UNIQUE NOT NULL, " +
                " address TEXT, " +
                " profile_picture BLOB, " +
                " first_name TEXT NOT NULL, " +
                " last_name TEXT NOT NULL, " +
                " facebook_user_id TEXT, " +
                " role INTEGER NOT NULL, " +
                " gender INTEGER NOT NULL, " +
                " status INTEGER NOT NULL, " +
                " date_created NUMERIC DEFAULT NULL ); ";
        db.execSQL(qb);
    }

    private void createTableRequest(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS request (" +
                " request_id INTEGER PRIMARY KEY NOT NULL, " +
                " account_id INTEGER NOT NULL, " +
                " request_status INTEGER, " +
                " price INTEGER, "+
                " place_from TEXT DEFAULT NULL, "+
                " place_to TEXT DEFAULT NULL, "+
                " details TEXT DEFAULT NULL, "+
                " event_date_time NUMERIC DEFAULT NULL, "+
                " date_updated NUMERIC DEFAULT NULL, "+
                " date_created NUMERIC DEFAULT NULL ); ";
        db.execSQL(qb);
    }

    private void createTableManageRequest(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS manage_request (" +
                " manage_request_id INTEGER PRIMARY KEY NOT NULL, " +
                " request_id INTEGER NOT NULL, " +
                " account_id INTEGER  NOT NULL, " +
                " car_id INTEGER NOT NULL, " +
                " request_status INTEGER, " +
                " price INTEGER, "+
                " date_updated NUMERIC DEFAULT NULL, "+
                " date_created NUMERIC DEFAULT NULL ); ";
        db.execSQL(qb);
    }

    private void createTableCarDetails(SQLiteDatabase db){
        String qb = "CREATE TABLE IF NOT EXISTS car_details (" +
                " car_id INTEGER , " +
                " account_id INTEGER NOT NULL, " +
                " make TEXT NOT NULL, " +
                " year INTEGER, " +
                " num_of_passenger INTEGER, "+
                " picture1 BLOB, " +
                " picture2 BLOB, " +
                " picture3 BLOB, " +
                " picture4 BLOB, " +
                " plate_num TEXT PRIMARY KEY NOT NULL); ";
        db.execSQL(qb);
    }

    public Cursor executeQuery(String query, String[] selectionArgs) {
        Cursor mCursor = db.rawQuery(query, selectionArgs);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
