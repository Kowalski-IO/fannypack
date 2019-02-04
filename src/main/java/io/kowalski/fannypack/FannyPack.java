package io.kowalski.fannypack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FannyPack {

    /**
     * Fill your Fanny Pack!
     *
     * @param filenames sql files to parse and include in your fanny pack.
     * @return a map of queries bound to name marker immediately preceding it.
     * @throws ParseException on the first error found while parsing.
     */
    public static Map<String, String> fill(String... filenames) throws ParseException {
        return Arrays.stream(filenames)
                .map(Parser::parseFile)
                .filter(Objects::nonNull)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
