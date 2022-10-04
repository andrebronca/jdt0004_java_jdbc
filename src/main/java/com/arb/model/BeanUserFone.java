package com.arb.model;

import java.util.List;

/**
 * Join da tabela user com telefone
 * 
 * @author andrebronca
 *
 */
public class BeanUserFone {
	private Long id;
	private String nome;
	private String email;
	private String numero;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "BeanUserFone [id=" + id + ", nome=" + nome + ", email=" + email + ", numero=" + numero + "]";
	}

}
