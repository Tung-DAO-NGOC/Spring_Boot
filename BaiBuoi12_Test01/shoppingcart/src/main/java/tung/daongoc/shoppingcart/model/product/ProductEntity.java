package tung.daongoc.shoppingcart.model.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set", toBuilder = true)
public class ProductEntity {
    private Long id;
    private String name;
    private String manufacturer;
    private Long price;
    private byte[] image;
}
