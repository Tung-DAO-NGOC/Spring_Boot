package tung.daongoc.shoppingcart.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import tung.daongoc.shoppingcart.model.product.ProductEntity;
import tung.daongoc.shoppingcart.model.product.ProductModel;
import tung.daongoc.shoppingcart.repository.IRepository;

@Service
public class ProductService implements IService<ProductModel> {
    @Autowired
    IRepository<ProductEntity> productRepo;

    private ProductModel mapEntToModel(ProductEntity productEntity) {
        ProductModel productModel = ProductModel.builder()
                .setId(productEntity.getId())
                .setName(productEntity.getName())
                .setManufacturer(productEntity.getManufacturer())
                .setPrice(productEntity.getPrice())
                .setImageByte(productEntity.getImage())
                .build();
        productModel.fromBloatToBase64();
        return productModel;
    }

    private ProductEntity mapModelToEnt(ProductModel productModel) throws IOException {
        productModel.fromMPFToBloat();
        return ProductEntity.builder()
                .setId(productModel.getId())
                .setName(productModel.getName())
                .setManufacturer(productModel.getManufacturer())
                .setPrice(productModel.getPrice())
                .setImage(productModel.getImageByte())
                .build();
    }

    @Override
    @SuppressWarnings("java:S1612")
    public List<ProductModel> getAll() throws DataAccessException {
        return productRepo.getAll().stream().map(item -> mapEntToModel(item)).toList();
    }

    @Override
    public ProductModel getByID(Long id) throws DataAccessException {
        return mapEntToModel(productRepo.getByID(id));
    }

    @Override
    public void add(ProductModel object) throws IOException {
        productRepo.add(mapModelToEnt(object));
    }

}
