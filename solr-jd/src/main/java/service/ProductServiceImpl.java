package service;

import org.apache.commons.lang3.StringUtils;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.Products;
import po.ResultModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Service
public class ProductServiceImpl implements ProductService {

	// 依赖注入HttpSolrServer
	@Autowired
	private HttpSolrServer server;

	@Override
	public ResultModel getProducts(String queryString, String catalogName,
								   String price, String sort, Integer page) throws Exception {
		// 创建SolrQuery对象
		SolrQuery query = new SolrQuery();
		// 输入关键字
		if (StringUtils.isNotEmpty(queryString)) {
			query.setQuery(queryString);
		} else {
			// 为空全部查
			query.setQuery("*:*");
		}

		// 输入商品分类过滤条件
		if (StringUtils.isNotEmpty(catalogName)) {
			query.addFilterQuery("product_catalog_name:" + catalogName);
		}

		// 输入价格区间过滤条件
		// price的值：0-9 10-19
		if (StringUtils.isNotEmpty(price)) {
			String[] ss = price.split("-");
			if (ss.length == 2) {
				query.addFilterQuery("product_price:[" + ss[0] + " TO " + ss[1] + "]");
			}
		}

		// 设置排序  界面传过来1
		if ("1".equals(sort)) {
			query.addSortField("product_price", SolrQuery.ORDER.desc);
		} else {
			query.addSortField("product_price", SolrQuery.ORDER.asc);
		}

		// 设置分页信息
		if (page == null) {
			page = 1;
		}
		query.setStart((page - 1) * 20);
		query.setRows(20);

		// 设置默认域
		query.set("df", "product_keywords");

		// 设置高亮信息
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<font style=\"color:red\" >");
		query.setHighlightSimplePost("</font>");

		QueryResponse response = server.query(query);
		// 查询出的结果
		SolrDocumentList results = response.getResults();
		// 记录总数
		long count = results.getNumFound();

		List<Products> products = new ArrayList<>();
		Products prod;

		// 获取高亮信息
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		// 封装页面所需要的信息
		for (SolrDocument doc : results) {
			prod = new Products();

			// 商品ID
			prod.setPid(doc.get("id").toString());
			// 商品名称高亮
			List<String> list = highlighting.get(doc.get("id")).get("product_name");
			// 商品名称
			if (list != null)
				prod.setName(list.get(0));
			else {
				prod.setName(doc.get("product_name").toString());
			}

			// 商品价格
			prod.setPrice(Float.parseFloat(doc.get("product_price").toString()));
			// 商品图片地址
			prod.setPicture(doc.get("product_picture").toString());

			products.add(prod);
		}

		// 封装ResultModel对象
		ResultModel rm = new ResultModel();
		rm.setProductList(products);
		rm.setCurPage(page);
		rm.setRecordCount(count);

		int pageCount = (int) (count / 20);

		if (count % 20 > 0) {
			pageCount++;
		}
		// 设置总页数
		rm.setPageCount(pageCount);

		return rm;
	}
}
