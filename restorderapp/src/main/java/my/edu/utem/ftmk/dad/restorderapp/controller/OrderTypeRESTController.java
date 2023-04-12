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

import my.edu.utem.ftmk.dad.restorderapp.model.OrderType;
import my.edu.utem.ftmk.dad.restorderapp.repository.OrderTypeRepository;


/*
 * This class contain the web services functionality
 * to manage ordertypes related resources 
 */
@RestController
@RequestMapping("/api/ordertypes")
public class OrderTypeRESTController {
	
	@Autowired
	private OrderTypeRepository orderTypeRepository;
	
	@GetMapping 
	public List<OrderType> getOrderTypes(){ 
		//this method return all the order types exist in the database
		return orderTypeRepository.findAll();
	}
	
	@GetMapping("{orderTypeId}")
	public OrderType getOrderType(@PathVariable long orderTypeId) {
		//this method return a specific order type based on the request path variable
		OrderType orderType = orderTypeRepository.findById(orderTypeId).get();
		return orderType;
	}
	
	@PostMapping
	public OrderType inserOrderType(@RequestBody OrderType orderType) {
		//this method receive and save orderType details in request body to database 
		return orderTypeRepository.save(orderType);
	}
	
	@PutMapping
	public OrderType updateOrderType(@RequestBody OrderType orderType) {
		//this method receive and update orderType details in request body to database
		return orderTypeRepository.save(orderType);
	}
	
	
	@DeleteMapping("{orderTypeId}")
	public ResponseEntity<HttpStatus> deleteOrderType(@PathVariable long orderTypeId){
		//this method delete specific order type based on the specified id in request path variable
		orderTypeRepository.deleteById(orderTypeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
