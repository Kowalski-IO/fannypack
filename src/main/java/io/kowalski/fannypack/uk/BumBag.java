package io.kowalski.fannypack.uk;

import io.kowalski.fannypack.FannyPack;
import io.kowalski.fannypack.ParseException;

import java.util.Map;

public class BumBag {

    /**
     * Fill your Bum Bag! God Save The Queen!
     * @param filenames sql files to parse and include in your bum bag.
     * @return a map of queries bound to name marker immediately preceding it.
     * @throws ParseException on the first error found while parsing.
     */
    public static Map<String, String> fill(String... filenames) throws ParseException {
        return FannyPack.fill(filenames);
    }

}
