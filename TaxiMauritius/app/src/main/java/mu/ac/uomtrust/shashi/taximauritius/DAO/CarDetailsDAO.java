package mu.ac.uomtrust.shashi.taximauritius.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;
import mu.ac.uomtrust.shashi.taximauritius.Database;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class CarDetailsDAO {
    private final String TABLE_NAME = "car_details";
    private final Database dbHelper;

    public CarDetailsDAO(final Context context) {
        dbHelper = Database.getInstance(context);
    }

    public CarDetailsDTO getCarDetailsByAccountID(int accountId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE account_id = " + accountId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        CarDetailsDTO dto = new CarDetailsDTO();

        while (!res.isAfterLast()) {

            dto.setCarId(res.getInt(res.getColumnIndex("car_id")));
            dto.setAccountId(res.getInt(res.getColumnIndex("account_id")));
            dto.setMake(res.getString(res.getColumnIndex("make")));
            dto.setYear(res.getInt(res.getColumnIndex("year")));
            dto.setNumOfPassenger(res.getInt(res.getColumnIndex("num_of_passenger")));
            dto.setPlateNum(res.getString(res.getColumnIndex("plate_num")));

            dto.setPicture1(res.getBlob(res.getColumnIndex("picture1")));
            dto.setPicture2(res.getBlob(res.getColumnIndex("picture2")));
            dto.setPicture3(res.getBlob(res.getColumnIndex("picture3")));
            dto.setPicture4(res.getBlob(res.getColumnIndex("picture4")));

            res.moveToNext();
        }

        res.close();

        return dto;
    }

    public CarDetailsDTO getCarDetailsByCarID(int carId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE car_id = " + carId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        CarDetailsDTO dto = new CarDetailsDTO();

        while (!res.isAfterLast()) {

            dto.setCarId(res.getInt(res.getColumnIndex("car_id")));
            dto.setAccountId(res.getInt(res.getColumnIndex("account_id")));
            dto.setMake(res.getString(res.getColumnIndex("make")));
            dto.setYear(res.getInt(res.getColumnIndex("year")));
            dto.setNumOfPassenger(res.getInt(res.getColumnIndex("num_of_passenger")));
            dto.setPlateNum(res.getString(res.getColumnIndex("plate_num")));

            dto.setPicture1(res.getBlob(res.getColumnIndex("picture1")));
            dto.setPicture2(res.getBlob(res.getColumnIndex("picture2")));
            dto.setPicture3(res.getBlob(res.getColumnIndex("picture3")));
            dto.setPicture4(res.getBlob(res.getColumnIndex("picture4")));

            res.moveToNext();
        }

        res.close();

        return dto;
    }

    private ContentValues setContentValues(CarDetailsDTO carDetailsDTO){
        ContentValues values = new ContentValues();

        values.put("car_id", carDetailsDTO.getCarId());
        values.put("account_id", carDetailsDTO.getAccountId());
        values.put("make", carDetailsDTO.getMake());
        values.put("year", carDetailsDTO.getYear());
        values.put("num_of_passenger", carDetailsDTO.getNumOfPassenger());
        values.put("picture1", carDetailsDTO.getPicture1());
        values.put("picture2", carDetailsDTO.getPicture2());
        values.put("picture3", carDetailsDTO.getPicture3());
        values.put("picture4", carDetailsDTO.getPicture4());
        values.put("plate_num", carDetailsDTO.getPlateNum());

        return values;

    }

    public long saveCarDetails(CarDetailsDTO carDetailsDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = setContentValues(carDetailsDTO);

        return db.insert(TABLE_NAME, null, contentValues);
    }

    public long updateCarDetailsIdFromWS(int carId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        CarDetailsDTO carDetailsDTO = getCarDetailsByCarID(-1);
        carDetailsDTO.setCarId(carId);

        ContentValues contentValues = setContentValues(carDetailsDTO);

        return db.update(TABLE_NAME, contentValues, " car_id = \"" + -1 + "\"" ,null);
    }
}
