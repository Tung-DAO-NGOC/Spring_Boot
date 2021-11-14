package tung.daongoc.shoppingcart.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tung.daongoc.shoppingcart.model.product.ProductEntity;

@Repository
public class ProductRepo implements IRepository<ProductEntity> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String TABLENAME = "product";
    private static final RowMapper<ProductEntity> ROWMAPPER =
            (rs, rowNum) -> ProductEntity.builder()
                    .setId(rs.getLong("id"))
                    .setName(rs.getString("name"))
                    .setManufacturer(rs.getString("manufacturer"))
                    .setPrice(rs.getLong("price"))
                    .setImage(rs.getBytes("image"))
                    .build();

    @Override
    public List<ProductEntity> getAll() throws DataAccessException {
        String sql = "SELECT * FROM " + TABLENAME;
        return jdbcTemplate.query(sql, ROWMAPPER);
    }

    @Override
    public ProductEntity getByID(Long id) throws DataAccessException {
        String sql = "SELECT * FROM " + TABLENAME + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, ROWMAPPER, id);
    }

    @Override
    public void add(ProductEntity object) {
        String sql = "INSERT INTO " + TABLENAME
                + " (name, manufacturer, price, image) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, object.getName(), object.getManufacturer(), object.getPrice(),
                object.getImage());
    }

}
