package io.kowalski.fannypack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FannyPack {

    final Map<String, String> queryMap;

    /**
     * Fill your Fanny Pack!
     *
     * @param filenames sql files to parse and include in your fanny pack.
     * @return a map of queries bound to name marker immediately preceding it.
     * @throws ParseException when an enumerated file cannot be read or parsed
     */
    public static FannyPack fill(String... filenames) throws ParseException {
        return new FannyPack(Arrays.stream(filenames)
                .map(Parser::parseFile)
                .filter(Objects::nonNull)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    /**
     * Retrieve a query from your FannyPack.
     * @param name listed in the sql file marker line
     * @return the query if it exists
     */
    public String get(@NonNull String name) {
        return queryMap.get(name);
    }

    /**
     * Indicates if a particular query name exists in your FannyPack.
     * @param name listed in the sql file marker line
     * @return if it exists
     */
    public boolean queryExists(@NonNull String name) {
        return queryMap.containsKey(name);
    }

    /**
     * My FannyPack is bigger than your FannyPack
     * @return size of your FannyPack
     */
    public int size() {
        return queryMap.size();
    }

}
