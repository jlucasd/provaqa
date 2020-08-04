package sc.senai.br.prova_java.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import sc.senai.br.prova_java.model.Aluno;

@ApplicationScoped
public class AlunoRepository {
	
	 @Inject
	    private EntityManager em;

	    public Aluno findById(Long id) {
	        return em.find(Aluno.class, id);
	    }
	    
	    public Aluno findByCpf(String cpf) {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
	        Root<Aluno> aluno = criteria.from(Aluno.class);
	        criteria.select(aluno).where(cb.equal(aluno.get("cpf"), cpf));
	        return em.createQuery(criteria).getSingleResult();
	    }

	    public List<Aluno> findAllOrderedByName() {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
	        Root<Aluno> aluno = criteria.from(Aluno.class);
	        criteria.select(aluno).orderBy(cb.asc(aluno.get("nome")));
	        return em.createQuery(criteria).getResultList();
	    }
	    
	    @SuppressWarnings("unchecked")
        public List<Aluno> findAllFilteredByName(String nome) {
	        if (nome == null) {
	            return findAllOrderedByName();
	        } else {
	            return em.createQuery("SELECT a FROM Aluno a WHERE lower(a.nome) like lower(:nome)").setParameter("nome", "%"+nome+"%").getResultList();
	        }
        }
	    
	    @SuppressWarnings("unchecked")
        public List<Aluno> findAllSemCurso() {
	        List<Aluno> alunos = em.createQuery("SELECT a FROM Aluno a LEFT JOIN a.matriculas m WHERE m.curso IS NULL ORDER BY a.nome").getResultList();
	        return alunos;
        }
	    
	    public Map<Long, String> findAllOrderedByNameMap() {
	    	List<Aluno> alunos = findAllOrderedByName();
	    	Map<Long, String> alunosMap = new HashMap<>();
	    	for (Aluno aluno : alunos) {
				alunosMap.put(aluno.getId(), aluno.getNome());
			}
	    	return alunosMap;
	    }
	    
}
