package com.interverse.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interverse.demo.dto.CartDTO;
import com.interverse.demo.dto.CartResponseDTO;
import com.interverse.demo.model.Cart;
import com.interverse.demo.model.CartId;
import com.interverse.demo.model.CartRepository;
import com.interverse.demo.model.Product;
import com.interverse.demo.model.ProductRepository;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	// 添加或更新購物車項目
	@Transactional
	public Cart addOrUpdateCart(CartDTO cartDTO) {
		User user = userRepository.findById(cartDTO.getUsersId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		Product product = productRepository.findById(cartDTO.getProductsId())
				.orElseThrow(() -> new RuntimeException("Product not found"));

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
			newCart.setUsers(user);
			newCart.setProducts(product);
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

	public CartResponseDTO convertToDTO(Cart cart) {
		CartResponseDTO dto = new CartResponseDTO();
		dto.setUserId(cart.getUsers().getId());
		dto.setProductId(cart.getProducts().getId());
		dto.setVol(cart.getVol());
		dto.setProductName(cart.getProducts().getName());
		return dto;
	}
}
