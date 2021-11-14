package tung.daongoc.shoppingcart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tung.daongoc.shoppingcart.model.cart.Cart;
import tung.daongoc.shoppingcart.model.product.ProductLine;
import tung.daongoc.shoppingcart.model.product.ProductModel;
import tung.daongoc.shoppingcart.model.product.ProductSession;

@Service
public class CartService {
    private static final String SESSIONCART = "CART";
    private static final Long TEMPDISCOUNT = (long) 0;

    private ProductSession mapModelToSession(ProductModel pModel, Long discountRate) {
        return ProductSession.builder()
                .setId(pModel.getId())
                .setName(pModel.getName())
                .setManufacturer(pModel.getManufacturer())
                .setPrice(pModel.getPrice())
                .setImageBase64(pModel.getImageBase64() != null ? pModel.getImageBase64() : null)
                .setDiscount(discountRate * pModel.getPrice())
                .build();
    }

    private ProductLine mapSessionToLine(ProductSession productSession) {
        return ProductLine.builder()
                .setId(productSession.getId())
                .setName(productSession.getName())
                .setManufacturer(productSession.getManufacturer())
                .setPrice(productSession.getPrice())
                .setImageBase64(
                        productSession.getImageBase64() != null ? productSession.getImageBase64()
                                : null)
                .build();
    }

    @Autowired
    IService<ProductModel> productService;

    @SuppressWarnings("unchecked")
    public int countItemInCart(HttpSession httpSession) {
        var sessionCart = httpSession.getAttribute(SESSIONCART);
        List<ProductSession> cart;
        if (sessionCart instanceof List) {
            cart = (ArrayList<ProductSession>) sessionCart;
            return cart.size();
        } else {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public void addToCart(Long id, HttpSession httpSession) {
        var sessionCart = httpSession.getAttribute(SESSIONCART);
        List<ProductSession> cart;
        if (sessionCart instanceof List) {
            cart = (ArrayList<ProductSession>) sessionCart;
        } else {
            cart = new ArrayList<>();
        }
        cart.add(mapModelToSession(productService.getByID(id), TEMPDISCOUNT));
        httpSession.setAttribute(SESSIONCART, cart);
    }

    @SuppressWarnings("unchecked")
    public Cart getCart(HttpSession httpSession) {
        var sessionCartList = httpSession.getAttribute(SESSIONCART);
        List<ProductSession> cartList;
        Map<Long, ProductLine> cartMap = new HashMap<>();
        if (sessionCartList instanceof List) {
            cartList = (ArrayList<ProductSession>) sessionCartList;
        } else {
            cartList = new ArrayList<>();
        }
        if (!cartList.isEmpty()) {
            cartList.stream().forEach(productSession -> {
                ProductLine productLine = cartMap.getOrDefault(
                        productSession.getId(), new ProductLine());
                if (productLine.getCount() == 0) {
                    productLine = mapSessionToLine(productSession);
                    productLine.setCount(1);
                    productLine.setTotalDiscount(productSession.getDiscount());
                } else {
                    productLine.setCount(productLine.getCount() + 1);
                    productLine.setTotalDiscount(
                            productLine.getTotalDiscount() + productSession.getDiscount());
                }
                cartMap.put(productLine.getId(), productLine);
            });
        }
        return new Cart(cartMap.values().stream().toList());
    }
}
