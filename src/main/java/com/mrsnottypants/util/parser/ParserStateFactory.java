package com.mrsnottypants.util.parser;

import java.util.Optional;

/**
 * Created by Eric on 7/27/2016.
 */
public interface ParserStateFactory {

    /**
     * Return state the parser starts in
     * @return start state
     */
    ParserState getStartState();

    /**
     * Return error message if the endState is not a legal state to end in.
     * This message will become an IllegalStateException
     * @param endState state parser ended in
     * @return Error message if illegal end state, or empty if legal end state
     */
    Optional<String> illegalEndState(ParserState endState);
}
