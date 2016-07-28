package com.mrsnottypants.util.parser;

import static org.junit.Assert.*;
import org.junit.Test;

import com.mrsnottypants.test.Exceptions;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by Eric on 7/27/2016.
 */
public class StateBasedCharSequenceParserTest {

    // String parser
    // 1. Strings start with either a single or double quote
    // 2. Double quote legal in singled quoted string, and visa-versa
    // 3. Within a string, backslash escapes following character
    private enum StringParserState implements ParserState {

        // not inside a string - this is where we start
        //
        START {
            @Override
            public Optional<ParserState> accept(Iterator<Character> input, List<String> parsed) {

                // ignore characters until we see either a single or double quote
                while (input.hasNext()) {

                    // double quote starts a string
                    Character next = input.next();
                    if (DOUBLE_QUOTE.equals(next)) {
                        return Optional.of(IN_DOUBLE_QUOTE);
                    }

                    // single quote starts a string
                    if (SINGLE_QUOTE.equals(next)) {
                        return Optional.of(IN_SINGLE_QUOTE);
                    }
                }

                // ran out of input
                return Optional.empty();
            }
        },

        // saw a double quote - in string until we see another
        //
        IN_DOUBLE_QUOTE {
            @Override
            public Optional<ParserState> accept(Iterator<Character> input, List<String> parsed) {
                return acceptString(input, parsed, DOUBLE_QUOTE);
            }
        },

        // saw single quote - in string until we see another
        //
        IN_SINGLE_QUOTE {
            @Override
            public Optional<ParserState> accept(Iterator<Character> input, List<String> parsed) {
                return acceptString(input, parsed, SINGLE_QUOTE);
            }
        }
        ;

        private static final Character DOUBLE_QUOTE = Character.valueOf('"');
        private static final Character SINGLE_QUOTE = Character.valueOf('\'');
        private static final Character BACK_SLASH = Character.valueOf('\\');

        /**
         * Common logic for single and double quoted strings.
         * All that changes is the character that ends the string
         * @param input being parsed
         * @param parsed list of parsed values
         * @param stringEnder the character that ends the string
         * @return state to transition too, or empty for no transition
         */
        private static Optional<ParserState> acceptString(Iterator<Character> input, List<String> parsed,
                                                          Character stringEnder) {
            // accumulate characters until we see an ending double quote
            StringBuilder builder = new StringBuilder();
            while (input.hasNext()) {

                // does this character end the string?
                Character next = input.next();
                if (stringEnder.equals(next)) {
                    parsed.add(builder.toString());
                    return Optional.of(START);
                }

                // add the next character to the current string (this method also handles backslash)
                builder.append(trueNextCharacter(next, input));
            }

            // ran out of input
            return Optional.empty();
        }

        /**
         * If next is a backslash, returns the next input character.
         * Returns next otherwise
         * @param next returned unless it is a backslash
         * @param input if next is a backslash, next input character is returned
         * @return next character
         */
        private static Character trueNextCharacter(Character next, Iterator<Character> input) {

            // if it's a backslash, it is escaping the next character
            if (BACK_SLASH.equals(next) && input.hasNext()) {
                return input.next();
            }

            // otherwise, take it as-is
            return next;
        }
    }

    private static final class StringParserFactory implements ParserStateFactory {

        @Override
        public ParserState getStartState() { return StringParserState.START; }

        @Override
        public Optional<String> illegalEndState(ParserState state) {

            // should end back in the start state, indicating we are not within a string
            if (StringParserState.START.equals(state)) {
                return Optional.empty();
            }

            // input ran out while we were in a string
            return Optional.of("Reached end of input while within a string");
        }
    }

    @Test
    public void testStringParserSimple() {

        // input has both type of strings, neither has a special character in it
        String input = "A 'dog' barks and \"bites\"";

        List<String> output = StateBasedCharSequenceParser.parse(new StringParserFactory(), input);

        assertEquals(2, output.size());
        assertEquals("dog", output.get(0));
        assertEquals("bites", output.get(1));
    }

    @Test
    public void testStringParserWithQuote() {

        // input has both type of strings, each contains the other type of quote
        String input = "A \"dog's\" bite is 'truly \"bad\"'";

        List<String> output = StateBasedCharSequenceParser.parse(new StringParserFactory(), input);

        assertEquals(2, output.size());
        assertEquals("dog's", output.get(0));
        assertEquals("truly \"bad\"", output.get(1));
    }

    @Test
    public void testStringParserEscaped() {

        // input has both type of strings, each has backslashes
        String input = "A 'dog\\'s' bite is \"n\\o\\ \\f\\un\"";

        List<String> output = StateBasedCharSequenceParser.parse(new StringParserFactory(), input);

        assertEquals(2, output.size());
        assertEquals("dog's", output.get(0));
        assertEquals("no fun", output.get(1));
    }

    @Test
    public void testStringParserNoStrings() {

        // input has no strings
        String input = "A dog";

        List<String> output = StateBasedCharSequenceParser.parse(new StringParserFactory(), input);
        assertEquals(0, output.size());
    }

    @Test
    public void testStringParserTrailingQuote() {

        // input forgets to close its final quote
        String input = "A 'dog' barks and \"bites";

        // we didn't complete final string
        assertTrue(Exceptions.isIllegalState(StateBasedCharSequenceParser::parse, new StringParserFactory(), input));
    }

    @Test
    public void testStringParserTrailingEscaped() {

        // input ends with a backslash
        String input = "A 'dog\\'s' bite is \"n\\o\\ \\f\\";

        // we didn't complete the final string
        assertTrue(Exceptions.isIllegalState(StateBasedCharSequenceParser::parse, new StringParserFactory(), input));
    }
}