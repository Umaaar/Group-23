package com.example.KingsMen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.KingsMen.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}