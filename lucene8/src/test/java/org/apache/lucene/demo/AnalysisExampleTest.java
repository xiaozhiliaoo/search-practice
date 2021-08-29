package org.apache.lucene.demo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

class AnalysisExampleTest {
    @Test
    void analysis() {
        Version matchVersion = Version.LUCENE_8_6_2; // Substitute desired Lucene version for XY
        Analyzer analyzer = new StandardAnalyzer(); // or any other analyzer
        TokenStream ts = analyzer.tokenStream("myfield", new StringReader("some text goes here"));
        // The Analyzer class will construct the Tokenizer, TokenFilter(s), and CharFilter(s),
        //   and pass the resulting Reader to the Tokenizer.
        OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);

        try {
            ts.reset(); // Resets this stream to the beginning. (Required)
            while (ts.incrementToken()) {
                // Use AttributeSource.reflectAsString(boolean)
                // for token stream debugging.
                System.out.println("token: " + ts.reflectAsString(true));

                System.out.println("token start offset: " + offsetAtt.startOffset());
                System.out.println("  token end offset: " + offsetAtt.endOffset());
            }
            ts.end();   // Perform end-of-stream operations, e.g. set the final offset.
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ts.close(); // Release resources associated with this stream.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}