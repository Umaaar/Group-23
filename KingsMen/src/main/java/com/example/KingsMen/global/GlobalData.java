package com.example.KingsMen.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.KingsMen.service.ProductService;
import com.example.KingsMen.repository.CategoryRepository;
import com.example.KingsMen.model.Category;
import com.example.KingsMen.model.Product;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

public class GlobalData{
    public static List<Product> cart;


    static{
            cart = new ArrayList<Product>();
    }
}