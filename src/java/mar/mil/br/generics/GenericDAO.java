
package mar.mil.br.generics;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public abstract class GenericDAO<T extends  Serializable>{
    
    private Class<T>aClass;

    public GenericDAO() {
    }

    protected GenericDAO(Class<T>aClass) {
        this.aClass = aClass;
    }
       
    @PersistenceContext
    protected EntityManager manager;
    
    public long cout(){
        
        Query query = manager.createQuery("select count(c) from"+ aClass.getSimpleName() + "c");
     
        long count = (long) query.getSingleResult();
        
        return count;
    }
    
      //consulta com único resultado  através de parametros
    public T findOne(String jpql, Object ... params){
        
        Query query = manager.createQuery(jpql);
        
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i+1, params[i]);
        }
        T entity = (T) query.getSingleResult();
        return entity;
    }
    
    //consulta através de parametros
    public List<T>find(String jpql, Object ... params){
        
        Query query = manager.createQuery(jpql);
        
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i+1, params[i]);
        }
        List<T>entities = query.getResultList();
        return entities;
    }
    
    public List<T>findAll(){
        Query query = manager.createQuery("from  "+ aClass.getSimpleName());
        List<T>entities = query.getResultList();
        return entities;
        
    }
    public T findById(Long id){
        T entity = (T) manager.find(aClass, id);
        
        return entity;
    }
        
    public void save (T entity){
        this.manager.persist(entity);
    }
    
    public void update(T entity){
        this.manager.merge(entity);
    }
    public void flush(T entity){
        this.manager.flush();
    }
    public void delete(Long id){
        this.manager.remove(manager.getReference(aClass, id));
    }
    
}
