package com.project.revshop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.revshop.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{

}
