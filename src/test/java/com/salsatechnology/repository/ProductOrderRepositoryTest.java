package com.salsatechnology.repository;

import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.model.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductOrderRepositoryTest {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testFindByUserName() {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setUserName("testUser");
        productOrder.setProductType(ProductType.BEACH_CHAIR);
        productOrder.setTimeHour(1);
        productOrder.setProductValue(50L);
        productOrder.setProductTotal(50L);
        productOrder.setUserAmount(5L);
        testEntityManager.persistAndFlush(productOrder);

        List<ProductOrder> orders = productOrderRepository.findByUserName("testUser");
        assertThat(orders).hasSize(1);
    }

    @Test
    public void testFindByUserName_WhenNoOrdersExist() {
        List<ProductOrder> orders = productOrderRepository.findByUserName("nonExistentUser");
        assertThat(orders).isEmpty();
    }

    @Test
    public void testExistsByUserName() {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setUserName("testUser2");
        productOrder.setProductType(ProductType.SURFBOARD);
        productOrder.setTimeHour(2);
        productOrder.setProductValue(100L);
        productOrder.setProductTotal(200L);
        productOrder.setUserAmount(20L);
        testEntityManager.persistAndFlush(productOrder);

        boolean exists = productOrderRepository.existsByUserName("testUser2");
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByUserName_WhenUserDoesNotExist() {
        boolean exists = productOrderRepository.existsByUserName("nonExistentUser");
        assertThat(exists).isFalse();
    }
}