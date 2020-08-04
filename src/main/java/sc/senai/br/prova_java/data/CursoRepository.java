package sc.senai.br.prova_java.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import sc.senai.br.prova_java.model.Curso;

@ApplicationScoped
public class CursoRepository {
	
	 @Inject
	    private EntityManager em;

	    public Curso findById(Long id) {
	        return em.find(Curso.class, id);
	    }
	    
	    public Curso findByCpf(String cpf) {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Curso> criteria = cb.createQuery(Curso.class);
	        Root<Curso> curso = criteria.from(Curso.class);
	        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	        // feature in JPA 2.0
	        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
	        criteria.select(curso).where(cb.equal(curso.get("cpf"), cpf));
	        return em.createQuery(criteria).getSingleResult();
	    }

	    public List<Curso> findAllOrderedByName() {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Curso> criteria = cb.createQuery(Curso.class);
	        Root<Curso> curso = criteria.from(Curso.class);
	        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	        // feature in JPA 2.0
	        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
	        criteria.select(curso).orderBy(cb.asc(curso.get("nome")));
	        return em.createQuery(criteria).getResultList();
	    }
	    
	    @SuppressWarnings("unchecked")
	    public List<Curso> findAllEmAndamento() {
            List<Curso> cursos = em.createQuery("SELECT c FROM Curso c WHERE CURRENT_DATE BETWEEN c.inicio AND c.termino ORDER BY c.nome").getResultList();
            return cursos;
        }
	    
}
