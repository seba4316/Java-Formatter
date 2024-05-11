package dev.utano.formatter;

/**
 * This interface defines the settings required for formatting operations.
 * It provides the functionality to get default settings,
 * start and end characters for a placeholder, quick character for quick replacements,
 * and whether the formatting should be case sensitive.
 */
public interface FormatterSettings {

	/**
	 * Provides default settings for the Formatter.
	 *
	 * @return a FormatterSettings object with default settings.
	 */
	static FormatterSettings defaultSettings() {
		return new FormatterSettings() {
		};
	}

	/**
	 * Returns the start character for a placeholder in the format string.
	 *
	 * @return a string representing the start character, default is "{".
	 */
	default String getStartCharacterString() {
		return "{";
	}

	/**
	 * Returns the end character for a placeholder in the format string.
	 *
	 * @return a string representing the end character, default is "}".
	 */
	default String getEndCharacterString() {
		return "}";
	}

	/**
	 * Returns the quick character for quick placeholder replacements in the format string.
	 *
	 * @return a string representing the quick character, default is "%".
	 */
	default String getQuickCharacterString() {
		return "%";
	}

	/**
	 * Determines if the formatting should be case sensitive.
	 *
	 * @return a boolean, true if case sensitive, default is true.
	 */
	default boolean isCaseSensitive() {
		return true;
	}

	/**
	 * Determines if the formatter should also be applied to placeholder values.
	 *
	 * <p> By default, this method returns false and hence the formatter won't be applied to placeholder values.
	 * If you override this method and make it return true, make sure that you can trust all sources of placeholder
	 * values, as this could create a potential security risk. </p>
	 *
	 * <p> Note: Allowing the formatter to process user input can potentially expose your application to
	 * injection attacks. Always ensure to sanitize and validate any user-provided input before
	 * using this feature. Always perform trust and security tests when prompting user input.  </p>
	 *
	 * @return true if the formatter should be applied to placeholder values, false otherwise.
	 */
	default boolean isApplyFormatterToPlaceHolders() {
		return false;
	}

}