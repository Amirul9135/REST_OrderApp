package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.restorderapp.model.OrderType;
import my.edu.utem.ftmk.dad.restorderapp.model.ProductType;

@Controller
public class ProductTypeMenuController {

	private String defaultURI = "http://localhost:8080/orderapp/api/productType";
	/*
	 * This method return a web page consisting of product type list
	 * when user access orderapp/productType/list via browser
	 */
	@GetMapping("/producttype/list")
	public String getProductTypes(Model model) { 
				
		//request the data from the web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ProductType[]> response = restTemplate.getForEntity(defaultURI, ProductType[].class);
				
		//conversion of response from web service to java object array
		ProductType productTypes[] = response.getBody();
		
		//parse array to list
		List<ProductType> productTypeList = Arrays.asList(productTypes);
		
		//attach list to model as attribute
		model.addAttribute("productTypes",productTypeList);
		
		//return the name of template(html) to be used within the template folder 
		return "producttypelist";
	}
	

	/*
	 * this method return a web page with product type info
	 * if id in path variable is <=0 then it will display blank form to register new product type
	 */
	@GetMapping("/producttype/{productTypeId}")
	public String getProductType(@PathVariable Integer productTypeId, Model model) {
		//initialize page title
		String pageTitle = "New Product Type";
		ProductType productType = new ProductType();
		if(productTypeId > 0) {
			String uri = defaultURI + "/" + productTypeId;
			RestTemplate restTemplate = new RestTemplate();
			//fetch an object consisting of the product type information via the web service
			productType = restTemplate.getForObject(uri, ProductType.class);
			
			//since product type id specified means is currently editing 
			pageTitle = "Edit Product Type";
		}
		
		//attach value into template
		model.addAttribute("productType",productType);
		model.addAttribute("pageTitle",pageTitle);
		
		return "producttypeinfo";
		
	}
	
	/*
	 * this method delete product type by id
	 * redirect user to product type list upon confirmation
	 */
	@RequestMapping("/producttype/delete/{productTypeId}")
	public String deleteProductType(@PathVariable Integer productTypeId) {
		String uri = defaultURI + "/{productTypeId}";
		RestTemplate restTemplate = new RestTemplate();
		//send request to web service with the user selected id to delete the product type from database
		restTemplate.delete(uri,Map.of("productTypeId", Integer.toString(productTypeId)));
		return "redirect:/producttype/list";
	}
	
	/*
	 * This method save changes made to ordertype
	 */
	@RequestMapping("/productType/save")
	public String updateProductType(@ModelAttribute ProductType productType) {
		// Create new Rest Template
		RestTemplate restTemplate = new RestTemplate();
		
		//Create request body
		HttpEntity<ProductType> requestEntity = new HttpEntity<ProductType>(productType);
		
		String prodTypeResponse ="";
		if(productType.getProductTypeId() > 0) {
			//if id>0 means existing so update 
			//consume the service in REST controller
			restTemplate.put(defaultURI, requestEntity,ProductType.class);
		}
		else {
			//if id <=0 means no id yet, new product type
			prodTypeResponse = restTemplate.postForObject(defaultURI, requestEntity, String.class);
		}
		
		System.out.println(prodTypeResponse);
		
		//redirect user to product type list
		return "redirect:/producttype/list";
	}
	
}
