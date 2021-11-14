package tung.daongoc.shoppingcart.model.product;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set", toBuilder = true)
public class ProductSession implements Serializable {
    private Long id;
    private String name;
    private String manufacturer;
    private Long price;
    private String imageBase64;
    private Long discount;
}
