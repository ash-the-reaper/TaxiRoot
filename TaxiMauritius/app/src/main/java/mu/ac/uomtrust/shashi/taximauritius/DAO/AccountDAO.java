package mu.ac.uomtrust.shashi.taximauritius.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import mu.ac.uomtrust.shashi.taximauritius.DTO.AccountDTO;
import mu.ac.uomtrust.shashi.taximauritius.Database;
import mu.ac.uomtrust.shashi.taximauritius.Enums.Gender;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserRole;
import mu.ac.uomtrust.shashi.taximauritius.Enums.UserStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class AccountDAO {

    private final String TABLE_NAME = "account";
    private final Database dbHelper;

    public AccountDAO(final Context context) {
        dbHelper = Database.getInstance(context);
    }

    public AccountDTO getAccountById(final int accountId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM account  ");
        sql.append(" WHERE account_id = " + accountId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        AccountDTO dto = new AccountDTO();

        while (!res.isAfterLast()) {

            dto.setAccountId(res.getInt(res.getColumnIndex("account_id")));
            dto.setEmail(res.getString(res.getColumnIndex("email")));
            dto.setAddress(res.getString(res.getColumnIndex("address")));
            dto.setProfilePicture(res.getBlob(res.getColumnIndex("profile_picture")));
            dto.setFirstName(res.getString(res.getColumnIndex("first_name")));
            dto.setLastName(res.getString(res.getColumnIndex("last_name")));
            dto.setFacebookUserId(res.getString(res.getColumnIndex("facebook_user_id")));
            dto.setRole(res.getInt(res.getColumnIndex("role")) != 0? UserRole.USER:UserRole.TAXI_DRIVER);
            dto.setGender(res.getInt(res.getColumnIndex("gender")) != 0? Gender.FEMALE:Gender.MALE);
            dto.setUserStatus(res.getInt(res.getColumnIndex("status")) == 0? UserStatus.ACTIVE:UserStatus.DESACTIVE);
            dto.setDateCreated(new Date(res.getLong(res.getColumnIndex("date_created"))));

            res.moveToNext();
        }

        res.close();

        return dto;
    }

    private ContentValues setContentValues(AccountDTO accountDTO){
        ContentValues values = new ContentValues();

        values.put("account_id", accountDTO.getAccountId());
        values.put("email", accountDTO.getEmail());
        values.put("address", accountDTO.getAddress());
        values.put("profile_picture", accountDTO.getProfilePicture());
        values.put("gender", accountDTO.getGender().ordinal());
        values.put("first_name", accountDTO.getFirstName());
        values.put("last_name", accountDTO.getLastName());
        values.put("facebook_user_id", accountDTO.getFacebookUserId());
        values.put("role", accountDTO.getRole().ordinal());
        values.put("status", accountDTO.getUserStatus().ordinal());
        values.put("date_created", accountDTO.getDateCreated().getTime());

        return values;

    }

    public long saveAccount(AccountDTO accountDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = setContentValues(accountDTO);

        return db.insert(TABLE_NAME, null, contentValues);
    }

    public long updateAccountIdFromWS(int accountId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        AccountDTO accountDTO = getAccountById(-1);
        accountDTO.setAccountId(accountId);

        ContentValues contentValues = setContentValues(accountDTO);

        return db.update(TABLE_NAME, contentValues, " account_id = \"" + -1+ "\"" ,null);
    }
}
