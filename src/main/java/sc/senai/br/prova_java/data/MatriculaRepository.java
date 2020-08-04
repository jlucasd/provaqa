package sc.senai.br.prova_java.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import sc.senai.br.prova_java.model.Matricula;

@ApplicationScoped
public class MatriculaRepository {

    @Inject
    private EntityManager em;

    public Matricula findById(Long id) {
        return em.find(Matricula.class, id);
    }

    public List<Matricula> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Matricula> criteria = cb.createQuery(Matricula.class);
        Root<Matricula> matricula = criteria.from(Matricula.class);
        criteria.select(matricula).orderBy(cb.asc(matricula.get("aluno").get("nome")));
        return em.createQuery(criteria).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Matricula> findMatriculasEmAndamento() {
        return em.createQuery("SELECT m FROM Matricula m WHERE CURRENT_DATE BETWEEN m.curso.inicio AND m.curso.termino").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Matricula> findMatriculasConcluidas() {
        return em.createQuery("SELECT m FROM Matricula m WHERE m.situacao = 'Concluída' ORDER BY m.aluno.nome").getResultList();
    }
    
    public Long getnumeroMatriculasByIdCurso(Long idCurso) {
        return (Long) em.createQuery("SELECT COUNT(*) FROM Matricula m WHERE m.curso.id = :idCurso").setParameter("idCurso", idCurso).getSingleResult();
    }
    
    @SuppressWarnings("unchecked")
    public List<Matricula> matriculasByidAlunoAndIdCurso(Long idAluno, Long idCurso) {
        return em.createQuery("SELECT m FROM Matricula m WHERE m.aluno.id = :idAluno AND m.curso.id = :idCurso")
                 .setParameter("idAluno", idAluno)
                 .setParameter("idCurso", idCurso)
                 .getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Matricula> matriculasByAluno(Long idAluno) {
        return em.createQuery("SELECT m FROM Matricula m WHERE m.aluno.id = :idAluno")
                 .setParameter("idAluno", idAluno)
                 .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Matricula> matriculasByCurso(Long idCurso) {
        return em.createQuery("SELECT m FROM Matricula m WHERE m.curso.id = :idCurso")
                .setParameter("idCurso", idCurso)
                .getResultList();
    }

}
