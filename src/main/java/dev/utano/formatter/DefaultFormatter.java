package dev.utano.formatter;

public class DefaultFormatter {
	private static final Formatter FORMATTER = new Formatter(new FormatterSettings() {
	});

	/**
	 * Retrieves the formatter object used for string formatting.
	 *
	 * @return The formatter object.
	 */
	public static Formatter getFormatter() {
		return FORMATTER;
	}

	/**
	 * Formats the given text using the default formatter and the specified placeholders.
	 *
	 * @param text         the input text to format.
	 * @param placeHolders An array of placeholders to replace in the text.
	 * @return the formatted text.
	 */
	public static String format(String text, Object... placeHolders) {
		return FORMATTER.format(text, placeHolders);
	}

	/**
	 * Formats the given text using the default formatter and the specified placeholders.
	 *
	 * @param text         the input text to format.
	 * @param placeHolders An array of placeholders to replace in the text.
	 * @return the formatted text.
	 */
	public static String format(String text, PlaceHolder... placeHolders) {
		return FORMATTER.format(text, placeHolders);
	}

}