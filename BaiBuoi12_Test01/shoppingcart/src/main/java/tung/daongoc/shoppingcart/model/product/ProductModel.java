package tung.daongoc.shoppingcart.model.product;

import java.io.IOException;
import java.util.Base64;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "set", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {

    private Long id;
    @Size(min = 10, max = 50)
    private String name;

    @NotBlank()
    @Size(max = 30)
    private String manufacturer;

    @NotNull()
    private Long price;

    private byte[] imageByte;
    private MultipartFile imageMPF;
    private String imageBase64;

    public void fromBloatToBase64() {
        if (this.imageByte != null && this.imageByte.length != 0) {
            this.imageBase64 = Base64.getEncoder().encodeToString(this.imageByte);
        }
    }

    public void fromMPFToBloat() throws IOException {
        if (this.imageMPF != null && this.imageMPF.getSize() != 0) {
            this.imageByte = this.imageMPF.getBytes();
        }
    }
}
