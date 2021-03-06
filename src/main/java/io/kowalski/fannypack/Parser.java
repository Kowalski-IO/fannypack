package io.kowalski.fannypack;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Parser {

    private static final Pattern MARKER_PATTERN = Pattern.compile("^\\s*--\\s*name\\s*:\\s*(.+)");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("^\\s*--\\s*(.+)");

    private static final Pattern BLOCK_COMMENT_START = Pattern.compile("([/][*])");
    private static final Pattern BLOCK_COMMENT_END = Pattern.compile("([*][/])");

    private static final String EMPTY_STRING = "";

    static Map<String, String> parseFile(final String filename) {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            Map<String, String> queryMap = new HashMap<>();

            Tallier tallier = new Tallier();

            stream.forEach(l -> {
                tallier.increment();

                ParsedLine pl = parseLine(l);
                switch (pl.getType()) {
                    case QUERY_PART:
                        if (tallier.isInBlockComment()) {
                            break;
                        } else if (tallier.getLastMarker() == null) {
                            throw new ParseException(filename, tallier.getLineNum(), "Expected Name Marker, Comment or Blank Line.", pl.getLine());
                        } else if (queryMap.containsKey(tallier.getLastMarker())) {
                            String current = queryMap.get(tallier.getLastMarker());
                            queryMap.put(tallier.getLastMarker(), current.concat(" ").concat(pl.getLine()));
                        } else {
                            queryMap.put(tallier.getLastMarker(), pl.getLine());
                        }
                        break;
                    case MARKER:
                        if (tallier.getLastLine() != null && tallier.getLastLine().getType().equals(LineType.MARKER)) {
                            throw new ParseException(filename, tallier.getLineNum(), "Expected Query Part, Comment, or Blank Line.", pl.getLine());
                        }
                        tallier.setLastMarker(pl.getLine());
                        break;
                    case BLOCK_COMMENT_START:
                        tallier.setInBlockComment(true);
                        break;
                    case BLOCK_COMMENT_END:
                        tallier.setInBlockComment(false);
                        break;
                    case BLANK:
                    case COMMENT:
                    default:
                        break;
                }

                tallier.setLastLine(pl);
            });

            return queryMap;
        } catch (IOException e) {
            throw new ParseException("Unable to read query file", e);
        }
    }

    private static ParsedLine parseLine(final String line) {
        if (line.trim().equals("")) {
            return new ParsedLine(LineType.BLANK, "");
        } else if (MARKER_PATTERN.matcher(line).matches()) {
            return new ParsedLine(LineType.MARKER, line.substring(line.indexOf(":") + 1).trim());
        } else if (COMMENT_PATTERN.matcher(line).matches()) {
            return new ParsedLine(LineType.COMMENT, COMMENT_PATTERN.matcher(line).replaceAll(EMPTY_STRING).trim());
        } else if (BLOCK_COMMENT_START.matcher(line).find()) {
            return new ParsedLine(LineType.BLOCK_COMMENT_START, BLOCK_COMMENT_START.matcher(line).replaceAll(EMPTY_STRING).trim());
        } else if (BLOCK_COMMENT_END.matcher(line).find()) {
            return new ParsedLine(LineType.BLOCK_COMMENT_END, BLOCK_COMMENT_END.matcher(line).replaceAll(EMPTY_STRING).trim());
        }

        return new ParsedLine(LineType.QUERY_PART, line.trim());
    }

}
