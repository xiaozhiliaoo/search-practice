package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import po.ResultModel;
import service.ProductService;


@Controller
public class ProductController {

	// 注入
	@Autowired
	private ProductService service;

	// 返回逻辑视图名称
	@RequestMapping("/list")
	public String list(String queryString, String catalog_name, String price,
			String sort, Integer page, Model model) throws Exception {
		ResultModel rm = service.getProducts(queryString, catalog_name, price, sort, page);

		// 将查询结果放到request域
		model.addAttribute("result", rm);

		// 简单类型的数据回显
		model.addAttribute("queryString", queryString);
		model.addAttribute("catalog_name", catalog_name);
		model.addAttribute("price", price);
		model.addAttribute("sort", sort);
		model.addAttribute("page", page);

		return "product_list";
	}

}
