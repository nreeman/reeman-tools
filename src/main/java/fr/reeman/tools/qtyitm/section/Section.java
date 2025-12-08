package fr.reeman.tools.qtyitm.section;

import lombok.NonNull;

public interface Section {
	
	/**
	 * If the Section is an enum - which is the main way to implement it - this method could be useful to retrieve the enum based on the associated label.
	 * This implementation is case sensitive.
	 * 
	 * @param <T> An enum implementing Section
	 * @param label The label of the searched enum
	 * @param clazz The class of the enum
	 * @return The found element or null if not
	 */
	public static <T extends Enum<T> & Section> T fromLabel(@NonNull final String label, @NonNull final Class<T> clazz) {
		for (T t : clazz.getEnumConstants()) {
			if (label.equals(((Section)t).getLabel())) {
				return t;
			}
		}
		
		return null;
	}
	
	/**
	 * Same as <code>fromLabel</code> but the seach ignore the case of labels.
	 * 
	 * @param <T> An enum implementing Section
	 * @param label The label of the searched enum
	 * @param clazz The class of the enum
	 * @return The found element or null if not
	 */
	public static <T extends Enum<T> & Section> T fromLabelIgnoreCase(@NonNull final String label, @NonNull final Class<T> clazz) {
		for (T t : clazz.getEnumConstants()) {
			if (label.equalsIgnoreCase(((Section)t).getLabel())) {
				return t;
			}
		}
		
		return null;
	}
	
	String getLabel();
}
