package io.kowalski.fannypack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BumBag {

    final Map<String, String> queryMap;

    /**
     * Fill your Bum Bag!
     *
     * @param filenames sql files to parse and include in your bum bag.
     * @return a map of queries bound to name marker immediately preceding it.
     * @throws ParseException when an enumerated file cannot be read or parsed
     */
    public static BumBag fill(String... filenames) throws ParseException {
        return new BumBag(Arrays.stream(filenames)
                .map(Parser::parseFile)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    /**
     * Retrieve a query from your BumBag.
     *
     * @param name listed in the sql file marker line
     * @return the query if it exists
     */
    public String get(@NonNull String name) {
        return queryMap.get(name);
    }

    /**
     * Indicates if a particular query name exists in your BumBag.
     *
     * @param name listed in the sql file marker line
     * @return if it exists
     */
    public boolean queryExists(@NonNull String name) {
        return queryMap.containsKey(name);
    }

    /**
     * My FannyPack is bigger than your BumBag
     *
     * @return size of your BumBag
     */
    public int size() {
        return queryMap.size();
    }

}
