package com.mrsnottypants.util.parser;

import com.mrsnottypants.util.iterator.CharSequenceIterator;

import java.util.*;

/**
 * General state-based parser that takes a character sequence as input, and produces a list of string values.
 *
 * The caller passes a parser-state factor that provides the states.  The states contain the parsing logic.
 *
 * Created by Eric on 7/27/2016.
 */
public class StateBasedCharSequenceParser {

    /**
     * Parse the given input.
     * @param factory Provides the states
     * @param input To parse
     * @return List of parsed values
     */
    public static List<String> parse(final ParserStateFactory factory, final CharSequence input) {

        // sanity check
        if ((factory == null) || (input == null)) {
            throw new IllegalArgumentException("factory and input cannot be null");
        }

        // create an empty list of parsed values
        List<String> parsed = new ArrayList<>();

        // we'll pass the input to the states as an iterator of characters
        Iterator<Character> iterator = new CharSequenceIterator(input);

        // initial state
        Optional<ParserState> state = Optional.of(factory.getStartState());

        // allow current state to process input
        // if it returns a state, transition to that state
        // if it does not, or we get to the end of the input, we are done
        while (iterator.hasNext()) {
            Optional<ParserState> nextState = state.flatMap(s -> s.accept(iterator, parsed));
            if (nextState.isPresent()) { state = nextState; }
        }

        // if factory returns a non-empty error message for the end state,
        // throw an illegal state exception, using the error message
        state.ifPresent(s -> factory.illegalEndState(s).
                ifPresent(m -> { throw new IllegalStateException(m); })
        );

        // done!
        return parsed;
    }

    // no reason to instantiate this class
    //
    private StateBasedCharSequenceParser() {}
}
