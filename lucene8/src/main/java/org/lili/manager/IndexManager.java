/*
package org.lili.manager;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.jupiter.api.Test;
import org.lili.dao.BookDao;
import org.lili.dao.BookDaoImpl;
import org.lili.po.Book;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class IndexManager {

	@Test
	public void createIndex() throws Exception {
		// 采集数据
		BookDao dao = new BookDaoImpl();
		List<Book> list = dao.queryBooks();

		// 将采集到的数据封装到Document对象中
		List<Document> docList = new ArrayList<Document>();
		Document document;
		for (Book book : list) {
			document = new Document();
			// store:如果是yes，则说明存储到文档域中  与索引域没什么关系   store只管文档域
			// 图书ID
			// 不分词、索引、存储 StringField
			Field id = new StringField("id", book.getId().toString(), Store.YES);
			// 图书名称
			// 分词、索引、存储 TextField
			Field name = new TextField("name", book.getName(), Store.YES);
			// 图书价格
			// 分词、索引、存储 但是是数字类型，所以使用FloatField
			Field price = new StringField("price", book.getPrice().toString(), Store.YES);
			// 图书图片地址
			// 不分词、不索引、存储 StoredField
			Field pic = new StoredField("pic", book.getPic());
			// 图书描述
			// 分词、索引、不存储 TextField
			Field description = new TextField("description", book.getDescription(), Store.NO);

			// 设置boost值
			if (book.getId() == 4)
				description.setBoost(100f);

			// 将field域设置到Document对象中
			document.add(id);
			document.add(name);
			document.add(price);
			document.add(pic);
			document.add(description);
			docList.add(document);
		}

		// 创建分词器，标准分词器
		 Analyzer analyzer = new StandardAnalyzer();
		// 使用ikanalyzer
		//Analyzer analyzer = new IKAnalyzer();

		// 创建IndexWriter
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_8_9_0, analyzer);
		// 指定索引库的地址  D:\LuceneSpace\index
		File indexFile = new File("D:\\LuceneSpace\\index");
		Directory directory = FSDirectory.open(indexFile);
		IndexWriter writer = new IndexWriter(directory, cfg);
		// 通过IndexWriter对象将Document写入到索引库中
		for (Document doc : docList) {
			writer.addDocument(doc);
		}
		// 关闭writer
		writer.close();
	}

	*/
/*@Test
	public void deleteIndex() throws Exception {
		// 创建分词器，标准分词器
		Analyzer analyzer = new StandardAnalyzer();

		// 创建IndexWriter
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);
		Directory directory = FSDirectory
				.open(new File("E:\\11-index\\hm19\\"));
		// 创建IndexWriter
		IndexWriter writer = new IndexWriter(directory, cfg);

		// Terms
		// writer.deleteDocuments(new Term("id", "1"));

		// 删除全部（慎用）
		writer.deleteAll();

		writer.close();
	}

	@Test
	public void updateIndex() throws Exception {
		// 创建分词器，标准分词器
		Analyzer analyzer = new StandardAnalyzer();

		// 创建IndexWriter
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);

		Directory directory = FSDirectory
				.open(new File("E:\\11-index\\hm19\\"));
		// 创建IndexWriter
		IndexWriter writer = new IndexWriter(directory, cfg);

		// 第一个参数：指定查询条件
		// 第二个参数：修改之后的对象
		// 修改时如果根据查询条件，可以查询出结果，则将以前的删掉，然后覆盖新的Document对象，如果没有查询出结果，则新增一个Document
		// 修改流程即：先查询，再删除，在添加
		Document doc = new Document();
		doc.add(new TextField("name", "lisi", Store.YES));
		writer.updateDocument(new Term("name", "zhangsan"), doc);

		writer.close();
	}*//*

}
*/
