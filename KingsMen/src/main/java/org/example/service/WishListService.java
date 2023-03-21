package org.example.service;

import java.util.List;
import javax.transaction.Transactional;
import org.example.model.Product;
import org.example.model.User;
import org.example.model.WishList;
import org.example.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WishListService {


        private final WishListRepository wishListRepository;
    
        public WishListService(WishListRepository wishListRepository) {
            this.wishListRepository = wishListRepository;
        }
      
        //Create Wishlist
        public void createWishlist(WishList wishList) {
            wishListRepository.save(wishList);
        }
    
        //ReadWishlist
        public List<WishList> readWishList(Integer userId) {
            return wishListRepository.findAllByUserId(userId);
        }
   
        // delete from wishlist
        public void deleteFromWishList(Integer userId, Long productId) {
            wishListRepository.deleteByUserIdAndProductId(userId, productId);
        }



    }
