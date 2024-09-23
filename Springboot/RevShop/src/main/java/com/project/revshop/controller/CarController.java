package com.project.revshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.revshop.model.Car;
import com.project.revshop.service.CarService;

@RestController
@RequestMapping("/api/cars")
public class CarController {
	
	@Autowired
	CarService service;
	
	@PostMapping
	public ResponseEntity<Car> addCar(@RequestBody Car car ){
		service.insertCar(car);
		return new ResponseEntity<>(car, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Car>> getAllCars(){
		return new ResponseEntity<>(service.getAllCars(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable Long id){
		return new ResponseEntity<>(service.getCarById(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car){
		service.insertCar(car);
		return new ResponseEntity<>(car, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCar(@PathVariable Long id){
		service.deleteCar(id);
		return new ResponseEntity<>("deleted Successfully", HttpStatus.OK);
	}
	
	

}
