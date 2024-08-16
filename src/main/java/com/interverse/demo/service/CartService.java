package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interverse.demo.dto.CartDTO;
import com.interverse.demo.model.Cart;
import com.interverse.demo.model.CartId;
import com.interverse.demo.model.CartRepository;


@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepo;
	
	// 添加或更新購物車項目
    @Transactional
    public Cart addOrUpdateCart(CartDTO cartDTO) {
        Cart existingCart = cartRepo.findByUsersIdAndProductsId(cartDTO.getUsersId(), cartDTO.getProductsId());
        if (existingCart != null) {
            // 如果購物車項目已存在，更新數量
            existingCart.setVol(existingCart.getVol() + cartDTO.getVol());
            return cartRepo.save(existingCart);
        } else {
            // 如果購物車項目不存在，創建新項目
            Cart newCart = new Cart();
            CartId cartId = new CartId(cartDTO.getUsersId(), cartDTO.getProductsId());
            newCart.setCartId(cartId);
            newCart.setVol(cartDTO.getVol());
            return cartRepo.save(newCart);
        }
    }

    // 查詢所有購物車項目
    public List<Cart> getAllCartItems() {
        return cartRepo.findAll();
    }

    // 查詢特定用戶的購物車項目
    public List<Cart> getCartItemsByUser(Integer userId) {
        return cartRepo.findByUsers(userId);
    }

    // 刪除購物車項目
    @Transactional
    public void deleteCartItem(Integer userId, Integer productId) {
        Cart cart = cartRepo.findByUsersIdAndProductsId(userId, productId);
        if (cart != null) {
            cartRepo.delete(cart);
        }
    }

    // 更新購物車項目數量
    @Transactional
    public Cart updateCartItemQuantity(CartDTO cartDTO) {
        Cart cart = cartRepo.findByUsersIdAndProductsId(cartDTO.getUsersId(), cartDTO.getProductsId());
        if (cart != null) {
            cart.setVol(cartDTO.getVol());
            return cartRepo.save(cart);
        }
        throw new RuntimeException("Cart item not found");
    }

    // 清空用戶的購物車
    @Transactional
    public void clearUserCart(Integer userId) {
        List<Cart> userCartItems = cartRepo.findByUsers(userId);
        cartRepo.deleteAll(userCartItems);
    }

    // 獲取購物車項目數量
    public int getCartItemCount(Integer userId) {
        List<Cart> userCartItems = cartRepo.findByUsers(userId);
        return userCartItems.size();
    }

}
