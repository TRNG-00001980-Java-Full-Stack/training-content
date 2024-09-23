package com.project.revshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.revshop.model.Car;
import com.project.revshop.repository.CarRepository;

@Service
public class CarService {
	
	@Autowired
	private CarRepository repo;
	
	public void insertCar(Car car) {
		repo.save(car);
	}
	
	public List<Car> getAllCars(){
		return repo.findAll();
	}
	
	public Car getCarById(Long id) {
		return repo.findById(id).get();
	}
	
	public void deleteCar(Long id) {
		repo.deleteById(id);
	}

}
