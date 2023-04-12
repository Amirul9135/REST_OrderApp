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
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.restorderapp.model.ProductType;
import my.edu.utem.ftmk.dad.restorderapp.repository.ProductTypeRepository;

/*
 * This class contain the web services functionality
 * to manage productType related resources 
 */
@RestController
@RequestMapping("/api/productType")
public class ProductTypeRESTController {
	
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@GetMapping
	public List<ProductType> getAllProductTypes(){
		//this method return all record of productTypes
		return productTypeRepository.findAll();
	}
	
	@GetMapping("{productTypeId}")
	public ProductType geProductType(@PathVariable Long productTypeId ) {
		//this method return specific record of productType based on the id provided in request url
		ProductType productType = productTypeRepository.findById(productTypeId).get();
		return productType;
	}
	
	@PostMapping
	public ProductType insertNewProductType(@RequestBody ProductType productType) {
		//this method accept new productType information in the request body and save into database
		return productTypeRepository.save(productType);
	}
	
	@PutMapping
	public ProductType updateProductType(@RequestBody ProductType productType) {
		//this method accept new productType information in the request body and update it into database
		return productTypeRepository.save(productType);
	}
	
	@DeleteMapping("{productTypeId}")
	public ResponseEntity<HttpStatus> deleteProductType(@PathVariable Long productTypeId){
		//this method delete specific record of productType based on the id provided in request url
		productTypeRepository.deleteById(productTypeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
