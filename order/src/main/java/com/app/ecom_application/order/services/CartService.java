package com.app.ecom_application.order.services;

import com.app.ecom_application.order.clients.ProductServiceClient;
import com.app.ecom_application.order.clients.UserServiceClient;
import com.app.ecom_application.order.dto.CartItemRequest;
import com.app.ecom_application.order.dto.ProductResponse;
import com.app.ecom_application.order.dto.UserResponse;
import com.app.ecom_application.order.models.CartItem;
import com.app.ecom_application.order.repositories.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    public boolean addToCart(String userId, CartItemRequest request) {
        ProductResponse productResponse = productServiceClient.getProductDetails(request.getProductId());
        if (productResponse == null || productResponse.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        UserResponse userResponse = userServiceClient.getUserDetails(userId);
        if(userResponse == null){
            return false;
        }

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if(cartItem != null){
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }


    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);

    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
