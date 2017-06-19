package mu.ac.uomtrust.shashi.taximauritius;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class WebService {

    private static final String SERVER_URL = "http://192.168.100.5/api/";
    //private static final String SERVER_URL = "http://192.168.20.59/api/";
    public static final String API_ACCOUNT = SERVER_URL + "account/";
    public static final String API_CLIENT = SERVER_URL + "client/";
    public static final String API_REQUEST = SERVER_URL + "request/";

    public static final String API_CREATE_ACCOUNT = API_ACCOUNT +"createAccount";
    public static final String API_CREATE_CAR_DETAILS = API_ACCOUNT +"createCarDetails";
    public static final String API_CHECK_ACCOUNT_VIA_EMAIL = API_ACCOUNT +"checkAccountViaEmail";

    public static final String API_CREATE_UPDATE_REQUEST = API_REQUEST +"createUpdateRequest";
    public static final String API_DELETE_REQUEST = API_REQUEST +"deleteRequest";

}
