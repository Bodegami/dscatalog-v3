package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional
	public List<CategoryDTO> findAll() {
		List<Category> list =  categoryRepository.findAll();
		List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		//List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).toList();
		return listDto;
	}
	
}
