package tung.daongoc.shoppingcart.model.cart;

import java.util.List;
import lombok.Data;
import tung.daongoc.shoppingcart.model.product.ProductLine;

@Data
public class Cart {
    private List<ProductLine> listProduct;
    private long rawTotal;
    private long cartDiscount;
    private long taxAmount;
    private long finalTotal;
    private static final Double DISCOUNTOVERALL = 0.01;
    private static final Double VAT = 0.1;

    public Cart(List<ProductLine> listProduct) {
        this.listProduct = listProduct;
        listProduct.stream().forEach(product -> {
            this.rawTotal += product.getPrice() * product.getCount();
            this.cartDiscount += product.getTotalDiscount();
        });
        this.cartDiscount += (long) (this.rawTotal * DISCOUNTOVERALL);
        this.taxAmount = (long) (this.rawTotal * VAT);
        this.finalTotal = this.rawTotal - this.cartDiscount + this.taxAmount;
    }
}
