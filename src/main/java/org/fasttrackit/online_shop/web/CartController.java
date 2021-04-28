package org.fasttrackit.online_shop.web;

import org.fasttrackit.online_shop.service.CartService;
import org.fasttrackit.online_shop.transfer.cart.AddProductsToCartRequest;
import org.fasttrackit.online_shop.transfer.cart.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
   @PutMapping
    public ResponseEntity<Void> addProductsToCart(@Validated @RequestBody
                                                          AddProductsToCartRequest request){
        cartService.addProductsToCart(request);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
   @GetMapping("/{id}")
   public ResponseEntity<CartResponse> getCart(@PathVariable long id){

       CartResponse cart = cartService.getCart(id);

       return new ResponseEntity<>(cart,HttpStatus.OK);


   }
}
