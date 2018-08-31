package br.com.cinq.spring.matcher;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.cinq.spring.data.entity.City;

/**
 * Matcher that checks if list of cities matches the name.
 * @author Andre Brito Fonseca
 *
 */
public class CityMatcher {
	
	public static TypeSafeMatcher<List<City>> hasCityWithName(final String name) {
		
		return new TypeSafeMatcher<List<City>>() {

			@Override
			public void describeTo(final Description description) {
				description.appendText("there should be a city with name " ).appendText(name);
				
			}

			@Override
			protected boolean matchesSafely(final List<City> cities) {
				if (cities == null) {
					return false;
				}
				
				return cities.stream()
						.map(x -> x.getName())
						.anyMatch(x -> x.contains(name));
			}
		};
		
	}

}
