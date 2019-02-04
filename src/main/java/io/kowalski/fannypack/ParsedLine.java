package io.kowalski.fannypack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class ParsedLine {

    private LineType type;
    private String line;

}
