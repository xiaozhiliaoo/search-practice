package org.apache.lucene.demo;

/**
 * @author lili
 * @date 2021/8/28 14:18
 */

import lombok.SneakyThrows;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.StringReader;

public class MyAnalyzer1 extends Analyzer {

    private Version matchVersion;


    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        return new TokenStreamComponents(new WhitespaceTokenizer());
    }

    @SneakyThrows
    public static void main(String[] args) {
        // text to tokenize
        final String text = "This is a demo of the TokenStream API";

        MyAnalyzer2 analyzer = new MyAnalyzer2();
        TokenStream stream = analyzer.tokenStream("field", new StringReader(text));

        // get the CharTermAttribute from the TokenStream
        CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);

        try {
            stream.reset();

            // print all tokens until stream is exhausted
            while (stream.incrementToken()) {
                System.out.println(termAtt.toString());
            }

            stream.end();
        } finally {
            stream.close();
        }
    }
}
