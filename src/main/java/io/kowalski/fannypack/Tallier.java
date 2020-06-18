package io.kowalski.fannypack;

import lombok.Getter;
import lombok.Setter;

@Getter
class Tallier {

    private int lineNum;

    @Setter
    private boolean inBlockComment;

    @Setter
    private String lastMarker;

    @Setter
    private ParsedLine lastLine;

    void increment() {
        lineNum++;
    }

}
