package dev.utano.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods for formatting strings using various placeholders.
 * It allows setting custom formatter settings and applying them to the string formatting.
 */
public class Formatter {
	private FormatterSettings formatterSettings;

	/**
	 * Constructs a new Formatter with the specified {@link FormatterSettings}.
	 *
	 * @param formatterSettings the formatter settings to be used for formatting operations
	 */
	public Formatter(FormatterSettings formatterSettings) {
		this.formatterSettings = formatterSettings;
	}

	/**
	 * Retrieves the formatter settings used for formatting operations.
	 *
	 * @return The FormatterSettings object defining the formatting settings.
	 */
	public FormatterSettings getFormatterSettings() {
		return formatterSettings;
	}

	/**
	 * Sets the formatter settings to be used for formatting operations.
	 *
	 * @param formatterSettings the FormatterSettings object defining the formatting settings
	 */
	public void setFormatterSettings(FormatterSettings formatterSettings) {
		if (formatterSettings == null)
			throw new NullPointerException("No FormatterSettings provided");
		this.formatterSettings = formatterSettings;
	}

	/**
	 * Formats the given text using this instance's formatter and the specified placeholders.
	 *
	 * @param text The input text to format.
	 * @param args The placeholders to replace in the text.
	 * @return The formatted text.
	 */
	public String format(String text, Object... args) {
		return format(text, objectsToPlaceHolders(args).toArray(new PlaceHolder[0]));
	}

	/**
	 * Format the given text replacing placeholders found with their corresponding values.
	 *
	 * <p> This method will parse the text and replace each occurrences of placeholders with their respective
	 * values provided in `placeHolders` parameter. If any placeholders in the text could not be found in
	 * `placeHolders` it will be left as is in the final output. </p>
	 *
	 * <p> Quick replacement with single character delimiters can be made by using the formatter's quick character.
	 * The method will replace the full placeholder (including the start and end characters) with the corresponding
	 * value. </p>
	 *
	 * @param text         The input text containing placeholders.
	 * @param placeHolders The placeholders to replace in the text.
	 * @return A new string with all placeholders replaced by their respective values.
	 */
	public String format(String text, PlaceHolder... placeHolders) {
		// If there's no input or placeholders we return the original input
		if (placeHolders.length == 0 || text == null || text.trim().isEmpty())
			return text;

		StringBuilder formatted = new StringBuilder();
		String startCharacter = formatterSettings.getStartCharacterString();
		String endCharacter = formatterSettings.getEndCharacterString();
		String quickCharacter = formatterSettings.getQuickCharacterString();

		int currentIndex = 0;

		boolean quick = false;

		while (text.contains(startCharacter) || (quick = text.contains(quickCharacter))) {
			String usedStartCharacter = quick ? quickCharacter : startCharacter;
			int usedCharacterIndex = text.indexOf(usedStartCharacter);
			formatted.append(text, 0, usedCharacterIndex); // Append part before placeholder

			if (quick) {
				// Process quick character (single %)
				text = text.substring(usedCharacterIndex + 1); // Skip the quick character
				Object quickValue = placeHolders[currentIndex++].getValue();
				formatted.append(quickValue == null ? "null" : quickValue.toString());
				quick = false; // Reset quick flag
			} else {
				// Process normal placeholders with {name}
				int endCharacterLocation = text.indexOf(endCharacter, usedCharacterIndex);
				if (endCharacterLocation == -1) {
					// If end character not found, append the rest of the text
					formatted.append(text);
					break;
				}

				String placeHolderName = text.substring(usedCharacterIndex + startCharacter.length(), endCharacterLocation);
				String placeHolderValue = getPlaceHolder(placeHolders, placeHolderName);

				// Append placeholder value or the original placeholder if not found
				formatted.append(placeHolderValue != null ? placeHolderValue : usedStartCharacter + placeHolderName + endCharacter);

				text = text.substring(endCharacterLocation + endCharacter.length()); // Move to next part of the text
			}
		}

		// Append any remaining part of the text
		formatted.append(text);

		return formatted.toString();
	}

	/**
	 * Retrieves a placeholder value by matching the key with the array of placeholder objects.
	 *
	 * @param placeHolders the array of placeholder objects
	 * @param key          the key to search for
	 * @return the placeholder value if found, otherwise null
	 */
	private String getPlaceHolder(PlaceHolder[] placeHolders, String key) {
		if (placeHolders == null || key == null)
			return null;
		for (PlaceHolder placeHolder : placeHolders)
			if (placeHolder != null && key.equals(placeHolder.getName()) && placeHolder.getValue() != null) {
				String value = placeHolder.getValue() == null ? "null" : placeHolder.getValue().toString();

				if (!formatterSettings.isApplyFormatterToPlaceHolders())
					return value;
				return format(value, placeHolders);
			}

		return null;
	}

	/**
	 * Converts an array of objects into a list of PlaceHolder objects.
	 *
	 * @param args the objects to be converted into PlaceHolder objects
	 * @return a list of PlaceHolder objects
	 */
	public List<PlaceHolder> objectsToPlaceHolders(Object... args) {
		List<PlaceHolder> placeHolders = new ArrayList<>();
		for (Object object : args) {
			if (object instanceof Object[])
				placeHolders.addAll(objectsToPlaceHolders((Object[]) object));
			else if (object instanceof PlaceHolder)
				placeHolders.add((PlaceHolder) object);
			else
				placeHolders.add(new PlaceHolder(object));
		}
		return placeHolders;
	}

}