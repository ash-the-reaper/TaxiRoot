package mu.ac.uomtrust.shashi.taximauritius.Enums;

import java.util.HashMap;
import java.util.Map;

public enum UserRole {
	TAXI_DRIVER(0),
	USER(1);

	private int value ;

	UserRole(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private static final Map<Integer, UserRole> map = new HashMap<>();
	static {
		for (UserRole item : values()) {
			map.put(item.getValue(), item);
		}
	}

	public static UserRole valueFor(int ref) {
		return map.get(ref);
	}
}