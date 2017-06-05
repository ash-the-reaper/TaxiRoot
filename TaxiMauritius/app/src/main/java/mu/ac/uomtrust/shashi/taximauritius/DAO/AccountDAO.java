package mu.ac.uomtrust.shashi.taximauritius.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.Database;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class AccountDAO {

    private final String TABLE_NAME = "account";
    private final Database dbHelper;

    public AccountDAO(final Context context) {
        dbHelper = Database.getInstance(context);
    }

    public Cursor getAccountById(final int id) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM account  ");
        sql.append(" WHERE id = " + id);

        dbHelper.open();
        Cursor mCursor = dbHelper.executeQuery(sql.toString(), null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    private void setContentValues(AccountDTO accountDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", accountDTO.getId());
        values.put("email", accountDTO.getEmail());
        values.put("address", accountDTO.getAddress());
        values.put("profile_picture", accountDTO.getProfilePicture());
        values.put("first_name", accountDTO.getFirstName());
        values.put("last_name", accountDTO.getLastName());
        values.put("facebook_user_id", accountDTO.getFacebookUserId());
        values.put("role", accountDTO.getRole().ordinal());
        values.put("status", accountDTO.getUserStatus().ordinal());
        values.put("date_created", accountDTO.getDateCreated().getTime());

    }

    public long saveAccount(AccountDTO accountDTO){

        return accountDTO.getId();
    }
}
