package mu.ac.uomtrust.shashi.taximauritius.Enums;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
	MALE(0),
	FEMALE(1);

	private int value ;

	Gender(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private static final Map<Integer, Gender> map = new HashMap<>();
	static {
		for (Gender item : values()) {
			map.put(item.getValue(), item);
		}
	}

	public static Gender valueFor(int ref) {
		return map.get(ref);
	}
}