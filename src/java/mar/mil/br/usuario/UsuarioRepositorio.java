package mar.mil.br.usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;



@Stateless
public class UsuarioRepositorio  {
	@PersistenceContext
	private EntityManager manager;

	Usuario usuario = new Usuario();
	

	public void salvar(Usuario usuario) {
		if (usuario.getId()== null) {
			this.manager.persist(usuario);
		}
		this.manager.merge(usuario);
	}

	public void atualizar(Usuario usuario) {
		if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) { 
			Usuario usuarioPermissao = this.carregar(usuario.getId()); 
			usuario.setPermissao(usuarioPermissao.getPermissao()); 
			this.manager.detach(usuarioPermissao);
		}
		this.manager.merge(usuario);
	}

	public void excluir(Usuario usuario) {
		Usuario usuarioDB = manager.find(Usuario.class, usuario.getId());// precisa desatachar o objeto para que a exlusão ocorra
		this.manager.remove(usuarioDB);
	}

	public Usuario carregar(Long id) {
		return (Usuario) this.manager.find(Usuario.class, id);
	}

	public List<Usuario> listar() {
		TypedQuery<Usuario> query = this.manager.createQuery("select x from Usuario x", Usuario.class);
		return query.getResultList();
	}
	
}
