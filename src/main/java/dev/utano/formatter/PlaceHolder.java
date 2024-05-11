package dev.utano.formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a PlaceHolder that is used in formatting operations.
 * It has a name and a value that can be of any type.
 */
public class PlaceHolder {

	private final String name;
	private final Object value;

	/**
	 * Constructs a new PlaceHolder with the specified name and value.
	 *
	 * @param name  the name to set for this PlaceHolder
	 * @param value the value to set for this PlaceHolder
	 */
	public PlaceHolder(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Constructs a new PlaceHolder with the specified value. The name gets set to null.
	 *
	 * @param value the value to set for this PlaceHolder
	 */
	public PlaceHolder(Object value) {
		this(null, value);
	}

	/**
	 * Merges given arrays of PlaceHolder objects into one array, eliminating any duplicates.
	 *
	 * @param placeHoldersArray variable number of PlaceHolder arrays to be merged.
	 * @return an array of PlaceHolder objects merged from input arrays.
	 */
	public static PlaceHolder[] merge(PlaceHolder[]... placeHoldersArray) {
		if (placeHoldersArray == null || placeHoldersArray.length == 0)
			return new PlaceHolder[0];
		List<PlaceHolder> tmp = new ArrayList<>();
		for (PlaceHolder[] placeHolders : placeHoldersArray) {
			if (placeHolders == null)
				continue;

			tmpLoop:
			for (PlaceHolder p : placeHolders) {
				if (p == null)
					continue;
				for (PlaceHolder placeHolder : tmp) {
					if (placeHolder == null || !placeHolder.equals(p))
						continue;
					tmp.remove(placeHolder);
					tmp.add(p);
					continue tmpLoop;
				}
				tmp.add(p);
			}
		}
		return tmp.toArray(new PlaceHolder[0]);
	}

	/**
	 * Returns the name of this PlaceHolder.
	 *
	 * @return the name of this PlaceHolder
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the value of this PlaceHolder.
	 *
	 * @return the value of this PlaceHolder
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Compares this PlaceHolder to the specified object.
	 * The result is true if and only if the argument is a PlaceHolder object that has the same name and value as this object.
	 *
	 * @param object the object to compare this PlaceHolder against
	 * @return true if the given object represents a PlaceHolder equivalent to this PlaceHolder, false otherwise
	 */
	@Override
	public boolean equals(Object object) {
		if (super.equals(object))
			return true;
		if (!(object instanceof PlaceHolder))
			return false;

		return equals((PlaceHolder) object);
	}

	/**
	 * Compares this PlaceHolder to another PlaceHolder.
	 * The result is true if and only if the PlaceHolder argument has the same name and value as this PlaceHolder.
	 *
	 * @param placeHolder the PlaceHolder to compare this PlaceHolder against
	 * @return true if the given PlaceHolder has the same name and value as this PlaceHolder, false otherwise
	 */
	public boolean equals(PlaceHolder placeHolder) {
		if (placeHolder == null || placeHolder.getName() == null)
			return false;
		return placeHolder.getName().equalsIgnoreCase(getName()) && placeHolder.getValue().equals(getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, value);
	}

	/**
	 * Compares this PlaceHolder to another PlaceHolder.
	 * The result is true if and only if the PlaceHolder argument has the same name as this PlaceHolder.
	 *
	 * @param placeHolder the PlaceHolder to compare this PlaceHolder against
	 * @return true if the given PlaceHolder has the same name as this PlaceHolder, false otherwise
	 */
	public boolean isSimilar(PlaceHolder placeHolder) {
		if (placeHolder == null || placeHolder.getName() == null)
			return false;
		return placeHolder.getName().equalsIgnoreCase(getName());
	}

}