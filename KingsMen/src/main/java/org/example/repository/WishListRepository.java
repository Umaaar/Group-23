package org.example.repository;

 import org.springframework.stereotype.Repository;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.example.model.User;
 import org.example.model.WishList;
 import java.util.List;

 @Repository
 public interface WishListRepository extends JpaRepository<WishList, Integer> {

     List<WishList> findAllByUserId(Integer userId);

     void deleteByUserIdAndProductId(Integer userId, Long productId);



 }
