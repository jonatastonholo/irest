package br.cefetmg.es.irest.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.cefetmg.es.irest.model.dto.ClienteDto;
import br.cefetmg.es.irest.model.entity.Cliente;
import br.cefetmg.es.irest.model.entity.Usuario;

public interface IClienteRepository  extends JpaRepository<Cliente, Integer>{

	@Query("select c.nome, c.endereco, c.telefone, c.nascimento, c.email, u.login, u.cpf, u.role from Cliente c "
			+ "join c.usuario u")
	public List<ClienteDto> findClienteUsuario();

	public Cliente findByNome(String nome);

	@Query("select u from Cliente c "
			+ "right join c.usuario u "
			+ "where u.id = :id "
			+ "AND c.id is not null")
	public Usuario buscarDependenciaUsuario(@Param("id") Integer idUsuario);

	public Cliente getClienteByUsuario(Usuario usuario);

}
