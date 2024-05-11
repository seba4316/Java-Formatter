package dev.utano.formatter.injectionattack;

import dev.utano.formatter.Formatter;
import dev.utano.formatter.FormatterSettings;
import dev.utano.formatter.PlaceHolder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InjectionAttackTest {

	@Test
	public void noPropagation() {
		runTest(false);
	}

	@Test
	public void withPropagation() {
		runTest(true);
	}

	private void runTest(final boolean propagation) {
		FormatterSettings formatterSettings = new FormatterSettings() {
			@Override
			public boolean isApplyFormatterToPlaceHolders() {
				return propagation;
			}
		};
		Formatter formatter = new Formatter(formatterSettings);
		String format = "{sender}> {message}";
		String sender = "User";
		String message = "Hello, {api_key}";
		PlaceHolder[] placeHolders = new PlaceHolder[]{
				new PlaceHolder("sender", sender),
				new PlaceHolder("message", message),
				new PlaceHolder("api_key", "MY_SUPER_SECRET_API_KEY")
		};

		String formatted = formatter.format(format, placeHolders);
		String expectedResultNoPropagation = "User> Hello, {api_key}";
		String expectedResultWithPropagation = "User> Hello, MY_SUPER_SECRET_API_KEY";

		if (propagation)
			assertEquals(expectedResultWithPropagation, formatted);
		else
			assertEquals(expectedResultNoPropagation, formatted);
	}

}