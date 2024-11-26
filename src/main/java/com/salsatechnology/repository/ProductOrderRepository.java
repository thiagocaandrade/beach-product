package com.salsatechnology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salsatechnology.model.ProductOrder;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findByUserName(String userName);
    boolean existsByUserName(String userName);

    @Query("SELECT po FROM ProductOrder po WHERE " +
            "(:filterField = 'userName' AND po.userName = :filterValue) OR " +
            "(:filterField = 'productType' AND po.productType = :filterValue)")
    List<ProductOrder> findByFilterField(@Param("filterField") String filterField, @Param("filterValue") String filterValue);
}
