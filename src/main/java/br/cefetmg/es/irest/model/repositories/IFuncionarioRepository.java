package br.cefetmg.es.irest.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.cefetmg.es.irest.model.entity.Funcionario;
import br.cefetmg.es.irest.model.entity.Usuario;

public interface IFuncionarioRepository extends
		JpaRepository<Funcionario, Integer> {

	@Query("select u from Funcionario f "
			+ "right join f.usuario u "
			+ "where u.id = :id "
			+ "AND f.id is not null")
	public Usuario buscarDependenciaUsuario(@Param("id") Integer idUsuario);

	public Funcionario getFuncionarioByUsuario(Usuario usuario);

}
