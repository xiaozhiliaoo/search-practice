package org.apache.lucene.demo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DemoTest {


    @Test
    void testIndexThenSearch() {
        try {
            //index
            Analyzer analyzer = new StandardAnalyzer();
            Path indexPath = Files.createTempDirectory("tempIndex");
            Directory directory = FSDirectory.open(indexPath);
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter iwriter = new IndexWriter(directory, config);
            Document doc = new Document();
            String text = "This is the text to be indexed.";
            doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
            iwriter.addDocument(doc);
            iwriter.close();

            //search
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            // Parse a simple query that searches for "text":
            QueryParser parser = new QueryParser("fieldname", analyzer);
            Query query = parser.parse("text");
            ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
            assertEquals(1, hits.length);
            // Iterate through the results:
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
            }
            ireader.close();
            directory.close();
            IOUtils.rm(indexPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}