package dev.utano.formatter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterTest {


	@Test
	public void keyValueTest() {
		Formatter formatter = DefaultFormatter.getFormatter();
		String textToFormat1 = "Hello {name}. This is test {test_number} of {test_amount}!";
		String expectedResult = "Hello Formatter. This is test 1 of 2!";

		PlaceHolder[] placeHolders = new PlaceHolder[]{
				new PlaceHolder("name", "Formatter"),
				new PlaceHolder("test_number", 1),
				new PlaceHolder("test_amount", 2),
		};

		assertEquals(expectedResult, formatter.format(textToFormat1, placeHolders));
	}

	@Test
	public void quickTest() {
		Formatter formatter = DefaultFormatter.getFormatter();
		String textToFormat1 = "Hello %. This is test % of %!";
		String expectedResult = "Hello Formatter. This is test 2 of 2!";

		Object[] objects = {"Formatter", 2, 2};

		assertEquals(expectedResult, formatter.format(textToFormat1, objects));
	}

}