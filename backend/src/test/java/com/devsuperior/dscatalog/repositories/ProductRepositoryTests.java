package com.devsuperior.dscatalog.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25;
	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
	
	@Test
	public void saveShouldUpdateProductWhenIdExists() {
		
		Product product = Factory.createProduct();
		
		Product result = repository.save(product);
		
		Assertions.assertEquals(product, result);
		Assertions.assertSame(product.getId(), result.getId());
		Assertions.assertEquals(product.toString(), result.toString());
	}
	
	@Test
	public void findAllShouldReturnAllProductsWhenDatabaseIsPopulated() {
		
		List<Product> listOfResults = repository.findAll();
		
		Assertions.assertFalse(listOfResults.isEmpty());
		Assertions.assertEquals(countTotalProducts, listOfResults.size());
	}
	
	@Test
	public void findAllShouldReturnAEmptyListWhenDatabaseIsEmpty() {
		
		repository.deleteAll();
		List<Product> listOfResults = repository.findAll();
		
		Assertions.assertTrue(listOfResults.isEmpty());
		Assertions.assertEquals(0L, listOfResults.size());
	}
	
	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
		
		Optional<Product> result = repository.findById(existingId);
		
		Assertions.assertTrue(result.isPresent());;
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
		
		Optional<Product> result = repository.findById(nonExistingId);
		
		Assertions.assertTrue(result.isEmpty());;
	}
	

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		repository.deleteById(existingId);

		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}

}
