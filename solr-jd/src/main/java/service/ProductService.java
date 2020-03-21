package service;


import po.ResultModel;

/**
 *  
 * <p>
 * Title: ProductService
 * </p>
 *  
 * <p>
 * Description: TODO(这里用一句话描述这个类的作用) 
 * <p>
 * <p>
 * Company: www.itcast.com
 * </p>
 *  @author 传智.关云长   @date 2015-12-28 下午4:48:25    @version 1.0
 */
public interface ProductService {

	public ResultModel getProducts(String queryString, String catalogName,
                                   String price, String sort, Integer page) throws Exception;
}
