package com.cadastroapi_spb3.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PessoaRecordDto(
        @NotBlank(message = "O nome não deve estar em branco") @Pattern(regexp = "\\d{10}", message = "O nome deve ter no mínimo 10 caracteres") String cfg_Nome,
        @NotBlank(message = "O telefone não deve estar em branco") @Pattern(regexp = "\\d{10,11}", message = "O telefone deve ter 10 ou 11 dígitos") String cfg_Telefone,
        @NotBlank(message = "O email não deve estar em branco") @Email(message = "Email deve ser válido") String cfg_Email) {
}