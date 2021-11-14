1. Yêu cầu

-  Tạo một danh sách sản phẩm trên database H2
-  Danh sách sản phẩm: tên, nhà sản xuất, giá, ảnh
-  Có trang thêm sản phẩm, đăng được ảnh, tương tác lên database H2
-  Tùy chỉnh ngôn ngữ: Anh Việt
-  Có nút thêm vào giỏ hàng và chuyển sang thanh toán. Lưu trữ dữ liệu trên session

2. Bước làm

-  B1: Tạo layout
-  B2: Tùy chỉnh ngôn ngữ: Anh Việt

   -  B2.1: Tạo cookie chứa lựa chọn ngôn ngữ: [LanguageConfig.java](./src/main/java/tung/daongoc/shoppingcart/config/LanguageConfig.java)
   -  B2.2: Tạo model chứa các trường ngôn ngữ: [LocalizeString.java](./src/main/java/tung/daongoc/shoppingcart/model/localization/LocalizeString.java)
   -  B3.3: Tạo phần xử lý khi có yêu cầu: [LocalizedMessageSource.java](./src/main/java/tung/daongoc/shoppingcart/localization/LocalizedMessageSource.java)

-  B3: tạo cơ sở dữ liệu

Entity: id, name, manufacturer, price, image
Model: id, name, manufacturer, price, (image: byte, MPF,, Base64)
Cart: id, name, manufacturer, price, imageBase64, discount
CartHash: name, manufacturer, price, imageBase64 count

CartInfo: HashMap<ID, CartHash>

ý tưởng: lưu thông tin về danh sách từng sản phẩm khi chọn theo list, đến khi ra checkout thì chuyển thành hashmap
