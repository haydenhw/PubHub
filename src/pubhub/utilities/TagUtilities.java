package pubhub.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagUtilities {
	public static List<String> parseCommaSeparatedTags(String tags) {
		List<String> tagList = Arrays.asList(tags.split("\\s*,\\s*"));
		return tagList.stream().map(t -> t.trim()).filter(t -> !t.isEmpty()).collect(Collectors.toList());
	}
}
