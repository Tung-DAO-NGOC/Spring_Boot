package tung.daongoc.shoppingcart.controller;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.extern.slf4j.Slf4j;
import tung.daongoc.shoppingcart.model.product.ProductModel;
import tung.daongoc.shoppingcart.service.CartService;
import tung.daongoc.shoppingcart.service.IService;


@Controller
@Slf4j
@RequestMapping("/")
public class ProductController {
    private static final String CARTSIZE = "cartSize";
    private static final String ACTIVE = "active";
    private static final String SUCCESS = "success";

    @Autowired
    private IService<ProductModel> productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = {"", "index"})
    public String getIndex(Model model, HttpSession httpSession) {
        model.addAttribute(CARTSIZE, cartService.countItemInCart(httpSession));
        model.addAttribute(ACTIVE, "index");
        return "index";
    }

    @GetMapping(value = {"list"})
    public String getList(Model model,
            @RequestParam(required = false, value = SUCCESS) String sucesss,
            HttpSession httpSession)
            throws EmptyResultDataAccessException {
        model.addAttribute(CARTSIZE, cartService.countItemInCart(httpSession));
        model.addAttribute("productList", productService.getAll());
        model.addAttribute(SUCCESS, sucesss);
        model.addAttribute(ACTIVE, "list");
        return "list";
    }

    @PostMapping("/buy_{id}")
    public String postBuy(@PathVariable(value = "id") Long id, HttpSession httpSession) {
        cartService.addToCart(id, httpSession);
        return "redirect:/list";
    }

    @GetMapping("add")
    public String getAdd(Model model, HttpSession httpSession) {
        model.addAttribute(CARTSIZE, cartService.countItemInCart(httpSession));
        model.addAttribute(ACTIVE, "add");
        model.addAttribute("product", new ProductModel());
        return "add";
    }

    @SuppressWarnings({"java:S2259", "java:S4449"})
    @PostMapping(value = {"add"}, consumes = {"multipart/form-data"})
    public String postAdd(@Valid @ModelAttribute(value = "product") ProductModel product,
            BindingResult br, RedirectAttributes redirAttribute, Model model) throws IOException {
        Locale locale = LocaleContextHolder.getLocale();
        product.fromMPFToBloat();
        product.fromBloatToBase64();


        if (product.getImageMPF().isEmpty()) {
            br.addError(new FieldError("product", "imageMPF",
                    messageSource.getMessage("photo_required", null, "Photo required", locale)));
        }

        if (br.hasErrors()) {
            if (br.getFieldError("manufacturer") != null) {
                model.addAttribute("manuError",
                        br.getFieldError("manufacturer").getCode().equals("Size") ? "" : "NB");
            }
            return "add";
        }

        productService.add(product);
        redirAttribute.addAttribute(SUCCESS, "yes");
        return "redirect:/list";
    }

    @GetMapping("checkout")
    public String getCheckout(Model model, HttpSession httpSession) {
        model.addAttribute(CARTSIZE, cartService.countItemInCart(httpSession));
        log.info(cartService.getCart(httpSession).toString());
        model.addAttribute("cart", cartService.getCart(httpSession));
        model.addAttribute(ACTIVE, "checkout");
        return "checkout";
    }

}
