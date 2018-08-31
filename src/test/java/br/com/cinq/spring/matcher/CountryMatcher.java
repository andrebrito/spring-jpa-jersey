package br.com.cinq.spring.matcher;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.cinq.spring.data.entity.Country;

/**
 * Matcher that checks if list of countries matches the name.
 * @author Andre Brito Fonseca
 *
 */
public class CountryMatcher {
	
	public static TypeSafeMatcher<List<Country>> hasCountryWithName(final String name) {
		
		return new TypeSafeMatcher<List<Country>>() {

			@Override
			public void describeTo(final Description description) {
				description.appendText("there should be a country with name " ).appendText(name);
				
			}

			@Override
			protected boolean matchesSafely(final List<Country> countries) {
				if (countries == null) {
					return false;
				}
				
				return countries.stream()
						.map(x -> x.getName())
						.anyMatch(x -> x.contains(name));
			}
		};
		
	}

}
