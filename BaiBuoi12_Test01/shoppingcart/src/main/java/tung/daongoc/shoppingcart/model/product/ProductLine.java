package tung.daongoc.shoppingcart.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "set", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductLine {
    private Long id;
    private String name;
    private String manufacturer;
    private Long price;
    private String imageBase64;
    private int count;
    private Long totalDiscount;
}
