package org.example.productstore.integration;

import org.example.productstore.entity.ProductEntity;
import org.example.productstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFindById() {
        ProductEntity product = ProductEntity.builder()
                .name("Laptop")
                .price(1200.0)
                .description("Gaming Laptop")
                .creationDate(LocalDate.now())
                .build();

        ProductEntity saved = productRepository.save(product);

        assertThat(saved.getId()).isGreaterThan(0);

        Optional<ProductEntity> found = productRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Laptop");
    }

    @Test
    void testFindAll() {
        ProductEntity product1 = ProductEntity.builder()
                .name("Phone")
                .price(699.99)
                .description("Smartphone")
                .creationDate(LocalDate.now())
                .build();

        ProductEntity product2 = ProductEntity.builder()
                .name("Tablet")
                .price(399.99)
                .description("Android Tablet")
                .creationDate(LocalDate.now())
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSizeGreaterThanOrEqualTo(2)
                .extracting(ProductEntity::getName)
                .contains("Phone", "Tablet");
    }

    @Test
    void testDelete() {
        ProductEntity product = ProductEntity.builder()
                .name("Monitor")
                .price(199.99)
                .description("LED Monitor")
                .creationDate(LocalDate.now())
                .build();

        ProductEntity saved = productRepository.save(product);

        productRepository.deleteById(saved.getId());

        Optional<ProductEntity> deleted = productRepository.findById(saved.getId());
        assertThat(deleted).isNotPresent();
    }
}
