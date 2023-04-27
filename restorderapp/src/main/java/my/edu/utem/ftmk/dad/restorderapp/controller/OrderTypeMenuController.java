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


/*
 *This controller class contain methods that consume the webservice from OrderTypeRESTController  
 */
@Controller
public class OrderTypeMenuController {
	
	private String defaultURI = "http://localhost:8080/orderapp/api/ordertypes";
	
	@GetMapping("/ordertype/{orderTypeId}")
	public String getOrderType(@PathVariable Integer orderTypeId, Model model) {
		String pageTitle = "New Order Type";
		OrderType orderType = new OrderType();
		if(orderTypeId > 0) {
			String uri = defaultURI + "/" + orderTypeId;
			RestTemplate restTemplate = new RestTemplate();
			orderType = restTemplate.getForObject(uri, OrderType.class);
			
			//since ordertypeId specified means is currently editing 
			pageTitle = "Edit Order Type";
		}
		
		//attach value into template
		model.addAttribute("orderType",orderType);
		model.addAttribute("pageTitle",pageTitle);
		
		return "ordertypeinfo";
		
	}
	
	/*
	 * this method delete an order type by id
	 */
	@RequestMapping("/ordertype/delete/{orderTypeId}")
	public String deleteOrderType(@PathVariable Integer orderTypeId) {
		String uri = defaultURI + "/{orderTypeId}";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri,Map.of("orderTypeId", Integer.toString(orderTypeId)));
		return "redirect:/ordertype/list";
	}
	
	/*
	 * This method save changes made to ordertype
	 */
	@RequestMapping("/ordertype/save")
	public String updateOrderType(@ModelAttribute OrderType orderType) {
		// Create new Rest Template
		RestTemplate restTemplate = new RestTemplate();
		
		//Create request body
		HttpEntity<OrderType> requestEntity = new HttpEntity<OrderType>(orderType);
		
		String orderTypResponse ="";
		if(orderType.getOrderTypeId() > 0) {
			//if id>0 means existing so update 
			//consume the service in REST controller
			restTemplate.put(defaultURI, requestEntity,OrderType.class);
		}
		else {
			//if id <=0 means no id yet, new ordertype
			orderTypResponse = restTemplate.postForObject(defaultURI, requestEntity, String.class);
		}
		
		System.out.println(orderTypResponse);
		
		//redirect user to ordertype/list
		return "redirect:/ordertype/list";
	}
	
	
	/*
	 * This method return a web page consisting of order type list
	 * when user access orderapp/ordertype/list via browser
	 */
	@GetMapping("/ordertype/list")
	public String getOrderTypes(Model model) {
		//uri to access the ordertype web service 
				
		//request the data from the web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OrderType[]> response = restTemplate.getForEntity(defaultURI, OrderType[].class);
				
		//conversion of response from web service to java object array
		OrderType orderTypes[] = response.getBody();
		
		//parse array to list
		List<OrderType> orderTypeList = Arrays.asList(orderTypes);
		
		//attach list to model as attribute
		model.addAttribute("orderTypes",orderTypeList);
		
		//return the name of template(html) to be used within the template folder
		//start from templates, if in folder in template use foldername/filename n so on
		return "ordertypes";
	}
	
	
	
	
	
	
	
	
	
	/* OLD VERSION
	 * This method handle the request to /orderapp/ordertype/list
	 * this method will print the list of ordertype in the console
	 
	@GetMapping("/ordertype/list")
	public ResponseEntity<String> getOrderTypes() {
		
		//uri to access the ordertype web service
		String uri = "http://localhost:8080/orderapp/api/ordertypes";
		
		//request the data from the web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OrderType[]> response = restTemplate.getForEntity(uri, OrderType[].class);
		
		//conversion of response from web service to java object array
		OrderType orderTypes[] = response.getBody();
		
		//Display the data into console
		System.out.println(this.getClass().getSimpleName());
		System.out.println("Total records: " + orderTypes.length + "\n");
		
		for(OrderType orderType:orderTypes) {
			System.out.print(orderType.getOrderTypeId() + "-");
			System.out.print(orderType.getCode() + "-");
			System.out.println(orderType.getName());
		}
		
		//return a string as response message
		String messageString = "Chech out message in the console";
		return new ResponseEntity<>(messageString, HttpStatus.OK);
	}
	*/
}
