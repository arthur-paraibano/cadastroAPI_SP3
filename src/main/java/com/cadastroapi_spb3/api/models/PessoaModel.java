package com.cadastroapi_spb3.api.models;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cfg_pessoa")
public class PessoaModel extends RepresentationModel<PessoaModel> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ID;
	private String cfg_Nome;
	private String cfg_Telefone;
	private String cfg_Email;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getCfg_Nome() {
		return cfg_Nome;
	}

	public void setCfg_Nome(String cfg_Nome) {
		this.cfg_Nome = cfg_Nome;
	}

	public String getCfg_Telefone() {
		return cfg_Telefone;
	}

	public void setCfg_Telefone(String cfg_Telefone) {
		this.cfg_Telefone = cfg_Telefone;
	}

	public String getCfg_Email() {
		return cfg_Email;
	}

	public void setCfg_Email(String cfg_Email) {
		this.cfg_Email = cfg_Email;
	}

}
