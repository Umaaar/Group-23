package org.example.model;

 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.OneToOne;
 import javax.persistence.Table;
 import java.util.Date;

 @Entity
 @Table(name = "wishlist")
 public class WishList {


     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;

     @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
     @JoinColumn(nullable = false, name = "user_id")
     private User user;

     @ManyToOne()
     @JoinColumn(name = "product_id")
     private Product product;

     public WishList() {
     }


     public WishList(User user, Product product) {
         this.user = user;
         this.product = product;
     }

     public Integer getId() {
         return id;
     }

     public void setId(Integer id) {
         this.id = id;
     }

     public User getUser() {
         return user;
     }

     public void setUser(User user) {
         this.user = user;
     }

     public Product getProduct() {
         return product;
     }

     public void setProduct(Product product) {
         this.product = product;
     }
 }
