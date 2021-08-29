package org.apache.lucene.demo;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

/**
 * @author lili
 * @date 2021/8/28 14:13
 */
public class PartOfSpeechTaggingFilter extends TokenFilter {

    PartOfSpeechAttribute posAtt = addAttribute(PartOfSpeechAttribute.class);
    CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    protected PartOfSpeechTaggingFilter(TokenStream input) {
        super(input);
    }


    @Override
    public boolean incrementToken() throws IOException {
        if (!input.incrementToken()) {
            return false;
        }
        posAtt.setPartOfSpeech(determinePOS(termAtt.buffer(), 0, termAtt.length()));
        return true;
    }

    // determine the part of speech for the given term
    protected PartOfSpeechAttribute.PartOfSpeech determinePOS(char[] term, int offset, int length) {
        // naive implementation that tags every uppercased word as noun
        if (length > 0 && Character.isUpperCase(term[0])) {
            return PartOfSpeechAttribute.PartOfSpeech.Noun;
        }
        return PartOfSpeechAttribute.PartOfSpeech.Unknown;
    }
}
