package demo.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.springboot.model.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long>{
	
	@Query("select t from Telefone t where t.pessoa.id = ?1 ")
	public List<Telefone> getTelefones(Long pessoaid);

}
