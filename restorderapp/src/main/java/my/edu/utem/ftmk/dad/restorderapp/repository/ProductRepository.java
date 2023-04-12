package my.edu.utem.ftmk.dad.restorderapp.repository;

import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.edu.utem.ftmk.dad.restorderapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	//this method find product which name contains the specified string
	List<Product> findAllByNameContains(String name);

	//this method find product within the range of first and second parameter
	List<Product> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(double min,double max);
	
	//this method return list product which price at least the specified price
	List<Product> findAllByPriceGreaterThanEqual(double price);
	
	//this method return list of product with price does not exceed the specified value
	List<Product> findAllByPriceLessThanEqual(double price);
	
	//this method return list of product with the selected type
	List<Product> findAllByProductType_ProductTypeId(int productTypeId);
	
	//this method return product with matching name,price range and product type
	//currently not used due to inflexibility
	List<Product> findAllByNameContainsAndPriceGreaterThanEqualAndPriceLessThanEqualAndProductType_ProductTypeId(String name, double min, double max, int productType);
 
	
	//the following method uses parameterized query to search matching products
	@Query(value = "SELECT p FROM Product p WHERE (:name is null or p.name LIKE CONCAT('%' ,:name, '%') )"
			+ " and (:min is null or p.price >= :min)" 
			+ " and (:max is null or p.price <= :max)" 
			+ " and (:type is null or p.productType.productTypeId = :type)")
	List<Product> searchProduct(@Param("name") String name,@Param("min") Double min, @Param("max") Double max, @Param("type") Integer type);
	
}
