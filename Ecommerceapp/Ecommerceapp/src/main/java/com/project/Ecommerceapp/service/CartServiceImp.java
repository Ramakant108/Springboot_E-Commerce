package com.project.Ecommerceapp.service;

import com.project.Ecommerceapp.categoryDTO.CartDTO;
import com.project.Ecommerceapp.categoryDTO.ProductDTO;
import com.project.Ecommerceapp.exaption.APIIExeption;
import com.project.Ecommerceapp.exaption.DataNotPresentExeption;
import com.project.Ecommerceapp.exaption.ResourceNotFoundException;
import com.project.Ecommerceapp.helpingpakage.AuthUtils;
import com.project.Ecommerceapp.model.CartItems;
import com.project.Ecommerceapp.model.Carts;
import com.project.Ecommerceapp.model.Product;
import com.project.Ecommerceapp.repostory.CartItemsRepository;
import com.project.Ecommerceapp.repostory.CartRepository;
import com.project.Ecommerceapp.repostory.ProductRepostory;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImp implements CartService{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepostory productRepostory;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CartDTO addproduct(Long productId, Integer quantity) {
        Carts cart=getcart();

        Product product=productRepostory.findById(productId).orElseThrow(()->new DataNotPresentExeption("product not available at"));
        CartItems cartItem=cartItemsRepository.findByProductIdAndCartId(product.getProductId(),cart.getCartId());
        if(cartItem!=null){
             throw new APIIExeption("product is alredy present is cart");
        }
        if(product.getQuantity()==0){
            throw new APIIExeption("product is not available at");
        }
        if(product.getQuantity()<quantity){
            throw new APIIExeption("Product quantity is not more than your requirement");
        }

        CartItems usercartItem=new CartItems();
        cart.setTotalprice(cart.getTotalprice()+(product.getSpeciaprice()*quantity));
        usercartItem.setCart(cart);
        usercartItem.setProductprice(product.getSpeciaprice());
        usercartItem.setQuantity(quantity);
        usercartItem.setProduct(product);
        usercartItem.setDiscount(product.getDiscount());

        cartItemsRepository.save(usercartItem);
        cartRepository.save(cart);

        CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);
        List<CartItems> cartItems=cartItemsRepository.findByCartId(cart.getCartId());

        List<ProductDTO> productDTOList=cartItems.stream().map(item->{
            ProductDTO map=modelMapper.map(item.getProduct(),ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        }).toList();
//        List<Product> productlist=cartItems.stream().map(item->{
//            }).toList();
        cartDTO.setProducts(productDTOList);
        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCart() {
        List<Carts> carts=cartRepository.findAll();
        if(carts==null){
            throw new APIIExeption("NO cart present");
        }
        List<CartDTO> cartDTOS=carts.stream().map(cart->{
            CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);

            List<ProductDTO> productDTOList=cart.getProducts().stream().map(p->modelMapper.map(p.getProduct(),ProductDTO.class)).toList();
            cartDTO.setProducts(productDTOList);
            return cartDTO;
        }
        ).toList();


        return cartDTOS;
    }

    @Override
    public CartDTO getUsercart(String emailId, Long cartId) {
        Carts cart=cartRepository.findByEmailAndCartId(emailId,cartId);
        if(cart==null){
            throw new APIIExeption("Cart not not present");
        }
        CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);
        List<ProductDTO> productDTOList=cart.getProducts().stream().map(p->modelMapper.map(p.getProduct(),ProductDTO.class)).toList();
        cartDTO.setProducts(productDTOList);

        return cartDTO;
    }



    @Transactional
    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {

        String emailId = authUtils.loggedInEmail();
        Carts userCart = cartRepository.findByEmail(emailId);
        Long cartId  = userCart.getCartId();

        Carts cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepostory.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (product.getQuantity() == 0) {
            throw new APIIExeption( "Product is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new APIIExeption("product quantity is no more");
        }

        CartItems cartItem = cartItemsRepository.findByProductIdAndCartId( productId,cartId);

        if (cartItem == null) {
            throw new APIIExeption("Product not available in the cart!!!");
        }

        cartItem.setProductprice(product.getSpeciaprice());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setDiscount(product.getDiscount());
        cart.setTotalprice(cart.getTotalprice() + (cartItem.getProductprice() * quantity));
        cartRepository.save(cart);
        CartItems updatedItem = cartItemsRepository.save(cartItem);
        if(updatedItem.getQuantity() == 0){
            cartItemsRepository.deleteById(updatedItem.getCartItemId());
        }


        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItems> cartItems = cart.getProducts();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
            prd.setQuantity(item.getQuantity());
            return prd;
        });


        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Carts cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItems cartItem = cartItemsRepository.findByProductIdAndCartId( productId,cartId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        cart.setTotalprice(cart.getTotalprice() -
                (cartItem.getProductprice() * cartItem.getQuantity()));

        cartItemsRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
    }

    private Carts getcart(){
        Carts cart=cartRepository.findByEmail(authUtils.loggedInEmail());
        if(cart!=null){
            return cart;
        }
        Carts usercart=new Carts();
        usercart.setTotalprice(0.0);
        usercart.setUser(authUtils.loggedInUser());
        return cartRepository.save(usercart);
    }
}
