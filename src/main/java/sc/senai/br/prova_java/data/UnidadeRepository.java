package sc.senai.br.prova_java.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import sc.senai.br.prova_java.model.Unidade;

@ApplicationScoped
public class UnidadeRepository {

    @Inject
    private EntityManager em;

    public Unidade findById(Long id) {
        return em.find(Unidade.class, id);
    }

    public List<Unidade> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Unidade> criteria = cb.createQuery(Unidade.class);
        Root<Unidade> unidade = criteria.from(Unidade.class);
        criteria.select(unidade).orderBy(cb.asc(unidade.get("nome")));
        return em.createQuery(criteria).getResultList();
    }

    public List<Unidade> findAllAtivasOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Unidade> criteria = cb.createQuery(Unidade.class);
        Root<Unidade> unidade = criteria.from(Unidade.class);
        criteria.select(unidade).where(cb.equal(unidade.get("ativa"), true)).orderBy(cb.asc(unidade.get("nome")));
        return em.createQuery(criteria).getResultList();
    }

}
