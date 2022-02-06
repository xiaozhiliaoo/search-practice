package org.apache.lucene.inaction;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.jupiter.api.Test;

/**
 * @author lili
 * @date 2021/11/14 14:58
 */

class A1 extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        return null;
    }
}

public class AnalyzerTest {
    @Test
    void test() {
        Analyzer a = new StandardAnalyzer();
        TokenStream tokenStream = a.tokenStream("content", "The quick brown fox jumped over the lazy dog");
        System.out.println(tokenStream);

    }



    @Test
    void test2() {
    }
}
