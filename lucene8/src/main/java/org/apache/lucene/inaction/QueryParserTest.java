package org.apache.lucene.inaction;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.TermQuery;
import org.junit.jupiter.api.Test;

/**
 * @author lili
 * @date 2021/11/14 14:34
 */
public class QueryParserTest {
    @Test
    void test() {
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("field", "kooolll"));
        TermQuery termQuery = new TermQuery(new Term("title", "alibaba"));
        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(fuzzyQuery, BooleanClause.Occur.MUST)
                .add(termQuery, BooleanClause.Occur.MUST).build();
        System.out.println(booleanQuery.toString());
    }
}
