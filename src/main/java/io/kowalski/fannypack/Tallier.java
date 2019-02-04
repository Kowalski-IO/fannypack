package io.kowalski.fannypack;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Tallier {

    private int lineNum;
    private String lastMarker;
    private ParsedLine lastLine;

    void increment() {
        lineNum++;
    }

}
