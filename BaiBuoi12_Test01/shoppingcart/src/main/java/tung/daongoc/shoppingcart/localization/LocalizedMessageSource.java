package tung.daongoc.shoppingcart.localization;

import java.util.HashMap;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import tung.daongoc.shoppingcart.model.localization.LocalizeString;

public class LocalizedMessageSource implements MessageSource {
        private HashMap<String, LocalizeString> localizeStringMap;

        public LocalizedMessageSource() {
                localizeStringMap = new HashMap<>();
                // NavBar
                localizeStringMap.put("shop_name",
                                new LocalizeString("Clothing Shop", "Cửa hàng thời trang"));
                localizeStringMap.put("home_page", new LocalizeString("Home Page", "Trang chủ"));
                localizeStringMap.put("list_product",
                                new LocalizeString("List Product", "Danh sách sản phẩm"));
                localizeStringMap.put("add_product",
                                new LocalizeString("Add Product", "Thêm sản phẩm"));
                localizeStringMap.put("checkout", new LocalizeString("Checkout", "Thanh toán"));
                localizeStringMap.put("index_quote", new LocalizeString(
                                "Main page of clothing shop", "Trang chủ cửa hàng thời trang"));

                // List Page
                localizeStringMap.put("manufacture",
                                new LocalizeString("Manufacturer", "Thương hiệu"));
                localizeStringMap.put("price", new LocalizeString("Price", "Mức giá"));
                localizeStringMap.put("purchase", new LocalizeString(
                                "Purchase", "Mua hàng"));
                localizeStringMap.put("addyes",
                                new LocalizeString("Successfully add new product!",
                                                "Thêm sản phẩm thành công!"));


                // Add Page
                localizeStringMap.put("submit", new LocalizeString(
                                "Submit", "Thêm sản phẩm"));
                localizeStringMap.put("info", new LocalizeString(
                                "Infomation", "Thông tin sản phẩm"));

                // Checkout
                localizeStringMap.put("checkout_order", new LocalizeString("No.", "STT"));
                localizeStringMap.put("checkout_image", new LocalizeString("Image", "Hình ảnh"));
                localizeStringMap.put("checkout_name",
                                new LocalizeString("Product Name", "Tên sản phẩm"));
                localizeStringMap.put("checkout_count", new LocalizeString("Number", "Số lượng"));
                localizeStringMap.put("checkout_total", new LocalizeString("Total", "Tổng tiền"));
                localizeStringMap.put("checkout_rawtotal",
                                new LocalizeString("All products price", "Tổng tiền sản phẩm"));
                localizeStringMap.put("checkout_discount",
                                new LocalizeString("Discount", "Tiền giảm giá"));
                localizeStringMap.put("checkout_tax", new LocalizeString("VAT Tax", "Thuế VAT"));
                localizeStringMap.put("checkout_finalamount",
                                new LocalizeString("Final amount", "Tổng tiền thanh toán"));

                // Error
                localizeStringMap.put("product_name_blank", new LocalizeString(
                                "Product name must not blank", "Tên sản phẩm không được để trống"));
                localizeStringMap.put("product_name_minmax", new LocalizeString(
                                "Product name must from 10 to 50 characters",
                                "Tên sản phẩm phải từ 10 đến 50 ký tự"));
                localizeStringMap.put("product_manufacturer_blank", new LocalizeString(
                                "Manufacturer name must not blank",
                                "Tên thương hiệu không được để trống"));
                localizeStringMap.put("product_manufacturer_maxsize", new LocalizeString(
                                "Manufacturer name must less than 30 characters",
                                "Tên thương hiệu phải dưới 30 ký tự"));
                localizeStringMap.put("product_price_blank",
                                new LocalizeString("Price must not blank",
                                                "Giá sản phẩm không để trống"));
                localizeStringMap.put("photo_required",
                                new LocalizeString("Photo is required", "Cần có ảnh sản phẩm"));

                // localizeStringMap.put("", new LocalizeString(eng, vie))
        }

        @Override
        public String getMessage(MessageSourceResolvable resolvable, Locale locale)
                        throws NoSuchMessageException {
                return resolvable.getDefaultMessage();
        }

        @Override
        public String getMessage(String textHolder, Object[] arg1, Locale locale)
                        throws NoSuchMessageException {
                LocalizeString localizeString = localizeStringMap.get(textHolder);
                if (localizeString == null)
                        return textHolder;

                switch (locale.getLanguage()) {
                        case "vie":
                                return localizeString.getVie();
                        case "eng":
                                return localizeString.getEng();
                        default:
                                return localizeString.getEng();
                }
        }

        @Override
        public String getMessage(String textHolder, Object[] arg1, String defaultString,
                        Locale locale) {
                LocalizeString localizeString = localizeStringMap.get(textHolder);
                if (localizeString == null)
                        return defaultString;

                switch (locale.getLanguage()) {
                        case "vie":
                                return localizeString.getVie();
                        case "eng":
                                return localizeString.getEng();
                        default:
                                return localizeString.getEng();
                }
        }

}
