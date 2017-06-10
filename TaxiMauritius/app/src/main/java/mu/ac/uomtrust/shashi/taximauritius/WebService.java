package mu.ac.uomtrust.shashi.taximauritius;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class WebService {

    private static final String SERVER_URL = "http://192.168.100.4/api/";
    public static final String API_ACCOUNT = SERVER_URL + "account/";
    public static final String API_CLIENT = SERVER_URL + "client/";

    public static final String API_CREATE_ACCOUNT = API_ACCOUNT +"createAccount";
    public static final String API_CREATE_CAR_DETAILS = API_ACCOUNT +"createCarDetails";
}
