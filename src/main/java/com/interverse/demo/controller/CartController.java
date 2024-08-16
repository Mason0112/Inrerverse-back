package com.interverse.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.CartDTO;
import com.interverse.demo.dto.CartResponseDTO;
import com.interverse.demo.model.Cart;
import com.interverse.demo.service.CartService;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDto) {
        try {
            Cart savedCart = cartService.addOrUpdateCart(cartDto);
            CartResponseDTO responseDTO = cartService.convertToDTO(savedCart);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to add item to cart: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserCart(@PathVariable Integer userId) {
        try {
            List<Cart> userCart = cartService.getCartItemsByUser(userId);
            List<CartResponseDTO> responseDTOs = userCart.stream()
                .map(cartService::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(responseDTOs);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to get user cart: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody CartDTO cartDto) {
        try {
            Cart updatedCart = cartService.updateCartItemQuantity(cartDto);
            CartResponseDTO responseDTO = cartService.convertToDTO(updatedCart);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to update cart item: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCartItem(@RequestParam Integer userId, @RequestParam Integer productId) {
        try {
            cartService.deleteCartItem(userId, productId);
            return ResponseEntity.ok("Cart item deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to delete cart item: " + e.getMessage());
        }
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearUserCart(@PathVariable Integer userId) {
        try {
            cartService.clearUserCart(userId);
            return ResponseEntity.ok("User cart cleared successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to clear user cart: " + e.getMessage());
        }
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<?> getCartItemCount(@PathVariable Integer userId) {
        try {
            int count = cartService.getCartItemCount(userId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to get cart item count: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCartItems() {
        try {
            List<Cart> allItems = cartService.getAllCartItems();
            List<CartResponseDTO> responseDTOs = allItems.stream()
                .map(cartService::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(responseDTOs);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to get all cart items: " + e.getMessage());
        }
    }
}
