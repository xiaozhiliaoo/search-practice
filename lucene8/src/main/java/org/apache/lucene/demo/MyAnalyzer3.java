package org.apache.lucene.demo;

import lombok.SneakyThrows;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.StringReader;

/**
 * @author lili
 * @date 2021/8/28 13:55
 */
public class MyAnalyzer3 extends Analyzer {

    private Version matchVersion;

    public MyAnalyzer3(Version matchVersion) {
        this.matchVersion = matchVersion;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final Tokenizer source = new WhitespaceTokenizer();
        TokenStream result = new LengthFilter(source, 3, Integer.MAX_VALUE);
        result = new PartOfSpeechTaggingFilter(result);
        return new TokenStreamComponents(source, result);
    }

    @SneakyThrows
    public static void main(String[] args) {
        final String text = "This is a demo of the TokenStream API";
        Version version = Version.LATEST;
        MyAnalyzer3 analyzer = new MyAnalyzer3(version);
        TokenStream stream = analyzer.tokenStream("field", new StringReader(text));

        CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);
        PartOfSpeechAttribute posAtt = stream.addAttribute(PartOfSpeechAttribute.class);

        try {
            stream.reset();

            // print all tokens until stream is exhausted
            while (stream.incrementToken()) {
                System.out.println(termAtt.toString() + ":" + posAtt.getPartOfSpeech());
            }
            stream.end();
        } finally {
            stream.close();
        }
    }
}

