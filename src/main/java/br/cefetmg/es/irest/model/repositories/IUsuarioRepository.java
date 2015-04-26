package br.cefetmg.es.irest.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Funcionario;
import br.cefetmg.es.irest.model.entity.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	public Usuario findByCpf(String cpf);

	public Usuario findByLogin(String login);


//	@Query("select SUM(COUNT(un)) from ( "
//				+ "select COUNT(*) from Cliente c "
//				+ "right join c.usuario u"
//				+ "where u.id= :id "
//				+ "AND (c.id is not null) "
//				+ "Union all "
//				+ "select COUNT(*) from Funcionario f "
//				+ "right join f.usuario us "
//				+ "where us.id= :id and (f.id is not null) "
//				+ ") as un")
	@Query("select c from Cliente c "
			+ "right join c.usuario u "
			+ "where u.id = :id "
			+ "AND c.id is not null")
	public Cliente buscarDependenciaCliente(@Param("id") Integer idUsuario);

	@Query("select f from Funcionario f "
			+ "right join f.usuario u "
			+ "where u.id = :id "
			+ "AND f.id is not null")
	public Funcionario buscarDependenciaFuncionario(@Param("id") Integer idUsuario);

	public Usuario findByLoginAndSenha(String login, String senha);

	public List<Usuario> findByRole(String role);

}
