package shashi.uomtrust.ac.mu.enums;

import java.util.HashMap;
import java.util.Map;

public enum RequestStatus {
	REQUEST_PENDING(0),
	REQUEST_CANCEL(1),
	CLIENT_ACCEPTED(2),
	CLIENT_REJECTED(3),
	TAXI_DRIVER_ACCEPTED(4),
	TAXI_DRIVER_REJECTED(5),
	PAID(6);


	private int value ;

	RequestStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private static final Map<Integer, RequestStatus> map = new HashMap<>();
	static {
		for (RequestStatus item : values()) {
			map.put(item.getValue(), item);
		}
	}

	public static RequestStatus valueFor(int ref) {
		return map.get(ref);
	}
}