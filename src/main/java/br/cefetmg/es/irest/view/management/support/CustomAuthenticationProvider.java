package br.cefetmg.es.irest.view.management.support;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import br.cefetmg.es.irest.model.entity.Usuario;
import br.cefetmg.es.irest.model.repositories.IUsuarioRepository;


@Named
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Inject
	private IUsuarioRepository usuarioRepository;

	public CustomAuthenticationProvider() {
		super();
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		String login = authentication.getName();
		String senha = authentication.getCredentials().toString();

		Usuario usuario = this.usuarioRepository.findByLoginAndSenha(login, senha);

		if (usuario != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

			grantedAuthorities.add(new SimpleGrantedAuthority(usuario.getRole()));

			UserDetails userDetails = new User(login, senha, grantedAuthorities);
			return new UsernamePasswordAuthenticationToken(userDetails, senha, grantedAuthorities);
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
