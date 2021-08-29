package org.apache.lucene.demo;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * @author lili
 * @date 2021/8/28 14:05
 */
public class LengthFilter extends FilteringTokenFilter {

    private final int min;
    private final int max;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    public LengthFilter(TokenStream in, int min, int max) {
        super(in);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean accept() {
        final int len = termAtt.length();
        return (len >= min && len <= max);
    }
}
