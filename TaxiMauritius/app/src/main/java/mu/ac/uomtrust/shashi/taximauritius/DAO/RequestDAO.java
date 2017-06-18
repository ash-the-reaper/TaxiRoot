package mu.ac.uomtrust.shashi.taximauritius.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mu.ac.uomtrust.shashi.taximauritius.DTO.CarDetailsDTO;
import mu.ac.uomtrust.shashi.taximauritius.DTO.RequestDTO;
import mu.ac.uomtrust.shashi.taximauritius.Database;
import mu.ac.uomtrust.shashi.taximauritius.Enums.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RequestDAO {
    private final String TABLE_NAME = "request";
    private final Database dbHelper;

    public RequestDAO(final Context context) {
        dbHelper = Database.getInstance(context);
    }

    public RequestDTO getRequestByID(int requestId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE request_id = " + requestId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        RequestDTO dto = new RequestDTO();

        while (!res.isAfterLast()) {

            dto.setRequestId(res.getInt(res.getColumnIndex("request_id")));
            dto.setAccountId(res.getInt(res.getColumnIndex("account_id")));
            dto.setDateUpdated(new Date(res.getLong(res.getColumnIndex("date_updated"))));
            dto.setDateCreated(new Date(res.getLong(res.getColumnIndex("date_created"))));
            dto.setEvenDateTime(new Date(res.getLong(res.getColumnIndex("event_date_time"))));
            dto.setPlaceFrom(res.getString(res.getColumnIndex("place_from")));
            dto.setPlaceTo(res.getString(res.getColumnIndex("place_to")));
            dto.setDetails(res.getString(res.getColumnIndex("details")));
            dto.setPrice(res.getInt(res.getColumnIndex("price")));
            dto.setRequestStatus(RequestStatus.valueFor(res.getInt(res.getColumnIndex("request_status"))));

            res.moveToNext();
        }

        res.close();

        return dto;
    }

    public  List<RequestDTO> getRequestByStatus(RequestStatus requestStatus) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE request_status = " + requestStatus.getValue());
        sql.append(" ORDER BY request_id DESC");

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        List<RequestDTO> requestDTOList = new ArrayList<>();

        while (!res.isAfterLast()) {

            RequestDTO dto = new RequestDTO();
            dto.setRequestId(res.getInt(res.getColumnIndex("request_id")));
            dto.setAccountId(res.getInt(res.getColumnIndex("account_id")));
            dto.setDateUpdated(new Date(res.getLong(res.getColumnIndex("date_updated"))));
            dto.setDateCreated(new Date(res.getLong(res.getColumnIndex("date_created"))));
            dto.setEvenDateTime(new Date(res.getLong(res.getColumnIndex("event_date_time"))));
            dto.setPlaceFrom(res.getString(res.getColumnIndex("place_from")));
            dto.setPlaceTo(res.getString(res.getColumnIndex("place_to")));
            dto.setDetails(res.getString(res.getColumnIndex("details")));
            dto.setPrice(res.getInt(res.getColumnIndex("price")));
            dto.setRequestStatus(RequestStatus.valueFor(res.getInt(res.getColumnIndex("request_status"))));

            requestDTOList.add(dto);
            res.moveToNext();
        }

        res.close();

        return requestDTOList;
    }


    private ContentValues setContentValues(RequestDTO requestDTO){
        ContentValues values = new ContentValues();

        values.put("request_id", requestDTO.getRequestId());
        values.put("account_id", requestDTO.getAccountId());
        values.put("date_updated", requestDTO.getDateUpdated().getTime());
        values.put("date_created", requestDTO.getDateCreated().getTime());
        values.put("event_date_time", requestDTO.getEvenDateTime().getTime());
        values.put("place_from", requestDTO.getPlaceFrom());
        values.put("place_to", requestDTO.getPlaceTo());

        if(requestDTO.getPrice() != null)
            values.put("price", requestDTO.getPrice());

        values.put("request_status", requestDTO.getRequestStatus().getValue());
        values.put("details", requestDTO.getDetails());


        return values;

    }

    public long saveRequest(RequestDTO requestDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = setContentValues(requestDTO);

        return db.insert(TABLE_NAME, null, contentValues);
    }

  /*  public long updateCarDetailsIdFromWS(int carId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        CarDetailsDTO carDetailsDTO = getCarDetailsByCarID(-1);
        carDetailsDTO.setCarId(carId);

        ContentValues contentValues = setContentValues(carDetailsDTO);

        return db.update(TABLE_NAME, contentValues, " car_id = \"" + -1 + "\"" ,null);
    }*/
}
