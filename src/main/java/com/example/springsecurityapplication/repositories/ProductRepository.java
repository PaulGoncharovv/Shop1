package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Integer> {
    //Поиск всех продуктов по наименованию и не зависит от РеГиСтРа
    List<Product> findByTitleContainingIgnoreCase(String name);
    //ЭТО ПОИСК ПО НАИМЕНОВАНИЮ И ФИЛЬТРАЦИЯ ПО ДИАПАЗОНУ ЦЕН
@Query(value="select * from product where (lower(title)LIKE %?1%) or (lower(title)LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >=?2 and price <= ?3)", nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(String title, float ot, float Do);
    //ЭТО это сортировка по возрастанию цен
@Query(value="select * from product where(lower(title)LIKE %?1%) or (lower(title)LIKE '?1%') OR (lower(title) LIKE '%?1') and(price >=?2 and price <= ?3) order by price)",  nativeQuery = true)
    List<Product>  findByTitleOrderByPriceAsc(String title, float ot, float Do);

//Сортировка по убыванию
    @Query(value="select * from product where(lower(title)LIKE %?1%) or (lower(title)LIKE '?1%') OR (lower(title) LIKE '%?1') and(price >=?2 and price <= ?3) order by price desc )",  nativeQuery = true)
    List<Product>  findByTitleOrderByPriceDesc(String title, float ot, float Do);
//Поиск по нименованию и фильтрация по диапазону цены, а так же фильрация по категори, сортировка по возрастанию цены
    @Query(value="select * from product where category_id = ?4 and(lower(title)LIKE %?1%) or (lower(title)LIKE '?1%') OR (lower(title) LIKE '%?1') and(price >=?2 and price <= ?3) order by price asc )",  nativeQuery = true)
    List<Product>  findByTitleAndCategoryOrderByPriceAsc(String title, float ot, float Do, int category);
//Поиск по нименованию и фильтрация по диапазону цены, сортировка по убыванию цен + фильтр по категории
    @Query(value="select * from product where category_id = ?4 and(lower(title)LIKE %?1%) or (lower(title)LIKE '?1%') OR (lower(title) LIKE '%?1') and(price >=?2 and price <= ?3) order by price desc )",  nativeQuery = true)
    List<Product>  findByTitleAndCategoryOrderByPriceDesc(String title, float ot, float Do, int category);
}
