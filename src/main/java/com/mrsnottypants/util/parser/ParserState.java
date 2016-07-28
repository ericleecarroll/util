package com.mrsnottypants.util.parser;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * A state for use with StateBasedParser.
 *
 * Created by Eric on 7/27/2016.
 */
public interface ParserState {

    /**
     * Process input, adding parsed values to parsed.
     * Return a state to have the parser transition to that state, or empty to stay in the current state.
     * @param input to parse
     * @param parsed list of parsed values
     * @return next state
     */
    Optional<ParserState> accept(Iterator<Character> input, List<String> parsed);
}
