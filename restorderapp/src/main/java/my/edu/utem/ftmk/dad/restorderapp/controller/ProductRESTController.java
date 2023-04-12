package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.restorderapp.model.Product;
import my.edu.utem.ftmk.dad.restorderapp.repository.ProductRepository;

/*
 * This class contain the web services functionality
 * to manage product related resources 
 */
@RestController
@RequestMapping("/api/product")
public class ProductRESTController {
	@Autowired
	private ProductRepository productRepository;
	
	
	//this method return all of product in the database
	@GetMapping
	public List<Product> geAlltProducts() {
		return productRepository.findAll();
	}
	
	//this method return specific product with the provided id
	@GetMapping("{productId}")
	public Product getProduct(@PathVariable long productId) {
		return productRepository.findById(productId).get();
	}
	
	
	//this method return list of products which matches the valid specified value in the url parameters
	//if none are specified all product will be returned
	@GetMapping("/search") 
	public List<Product> searchProduct(@RequestParam(required = false) String name, @RequestParam(required = false) Integer type,
			@RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice){  
		return productRepository.searchProduct(name, minPrice, maxPrice, type); 
	}
	
	
	//this method accept complete product information and save into database
	@PostMapping
	public Product insertNewProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	
	//this method accept product information and save into database
	@PutMapping
	public Product updateProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	
	//this method delete product with id specified in the path variable
	@DeleteMapping("{productId}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long productId){
		productRepository.deleteById(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
