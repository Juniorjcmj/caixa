package mar.mil.br.usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import mar.mil.br.usuario.Grupo;


@Stateless
public class GrupoRepositorio {
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona(Grupo g){
		this.manager.persist(g);
	}
	
	public List<Grupo>buscarTodos(){
		TypedQuery<Grupo> query = this.manager.createQuery("select x from Grupo x" , Grupo.class);
		return query.getResultList();
	}

}
