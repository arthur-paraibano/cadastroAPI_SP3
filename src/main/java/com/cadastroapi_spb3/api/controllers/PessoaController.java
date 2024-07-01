package com.cadastroapi_spb3.api.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastroapi_spb3.api.dtos.PessoaRecordDto;
import com.cadastroapi_spb3.api.exceptions.CustomValidationException;
import com.cadastroapi_spb3.api.models.PessoaModel;
import com.cadastroapi_spb3.api.repositories.PessoaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cadastroAPI")
public class PessoaController {

	@Autowired
	PessoaRepository productRepository;

	@GetMapping("/all")
	public ResponseEntity<List<PessoaModel>> getAllProducts() {
		List<PessoaModel> pessoasList = productRepository.findAll();
		if (!pessoasList.isEmpty()) {
			for (PessoaModel product : pessoasList) {
				Integer id = product.getID();
				product.add(linkTo(methodOn(PessoaController.class).getOneProduct(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(pessoasList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") Integer id) {
		Optional<PessoaModel> productO = productRepository.findById(id);
		if (productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		productO.get().add(linkTo(methodOn(PessoaController.class).getAllProducts()).withRel("Products List"));
		return ResponseEntity.status(HttpStatus.OK).body(productO.get());
	}

	@PostMapping("/add")
	public ResponseEntity<PessoaModel> saveProduct(@RequestBody @Valid PessoaRecordDto pessoaRecordDto) {
		if (!pessoaRecordDto.cfg_Nome().matches("\\d{10}")) {
			throw new CustomValidationException("O nome deve ter no mínimo 10 caracteres.");
		}
		if (!pessoaRecordDto.cfg_Email().endsWith("gmail.com")) {
			throw new CustomValidationException("Email deve ser um endereço do Gmail.");
		}
		if (!pessoaRecordDto.cfg_Telefone().matches("\\d{10,11}")) {
			throw new CustomValidationException("O telefone deve ter 10 ou 11 dígitos.");
		}

		var pessoaModel = new PessoaModel();

		// Convertendo os campos para maiúsculas
		pessoaRecordDto = new PessoaRecordDto(
				pessoaRecordDto.cfg_Nome().toUpperCase(),
				pessoaRecordDto.cfg_Telefone().toUpperCase(),
				pessoaRecordDto.cfg_Email().toUpperCase());

		BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(pessoaModel));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") Integer id) {
		Optional<PessoaModel> productO = productRepository.findById(id);
		if (productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		productRepository.delete(productO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") Integer id,
			@RequestBody @Valid PessoaRecordDto pessoaRecordDto) {

		Optional<PessoaModel> productO = productRepository.findById(id);
		if (productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		if (!pessoaRecordDto.cfg_Nome().matches("\\d{10}")) {
			throw new CustomValidationException("O nome deve ter no mínimo 10 caracteres.");
		}
		if (!pessoaRecordDto.cfg_Email().endsWith("gmail.com")) {
			throw new CustomValidationException("Email deve ser um endereço do Gmail.");
		}
		if (!pessoaRecordDto.cfg_Telefone().matches("\\d{10,11}")) {
			throw new CustomValidationException("O telefone deve ter 10 ou 11 dígitos.");
		}

		var productModel = productO.get();
		// Convertendo os campos para maiúsculas
		pessoaRecordDto = new PessoaRecordDto(
				pessoaRecordDto.cfg_Nome().toUpperCase(),
				pessoaRecordDto.cfg_Telefone().toUpperCase(),
				pessoaRecordDto.cfg_Email().toUpperCase());
		BeanUtils.copyProperties(pessoaRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
	}
}