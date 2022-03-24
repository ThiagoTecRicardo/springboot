package demo.springboot.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class Pessoa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message="Nome não pode ser nulo")
	@NotEmpty(message = "Nome não pode ser vazio")
	private String nome;
	
	@NotNull(message="Sobrenome não pode ser nulo")
	@NotEmpty(message = "Sobrenome não pode ser vazio")
	private String sobreNome;
	
	@NotNull(message="E-mail não pode ser nulo")
	@NotEmpty(message = "E-mail não pode ser vazio")
	private String email;
	
	@OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Telefone> telefones;

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

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	

}
