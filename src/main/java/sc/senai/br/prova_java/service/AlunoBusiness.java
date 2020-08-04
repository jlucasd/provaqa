package sc.senai.br.prova_java.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import sc.senai.br.prova_java.model.Aluno;

@Stateless
public class AlunoBusiness {

	@Inject
    private Logger log;

    @Inject
    private EntityManager em;
    
    @Inject
    private Event<Aluno> memberEventSrc;
    
    public void salvar(Aluno aluno) throws Exception {
        log.info("Salvando " + aluno.getNome());
        em.merge(aluno);
        memberEventSrc.fire(aluno);
    }
    
    
    public void excluir(Aluno aluno) throws Exception {
        log.info("Excluindo " + aluno.getNome());
        em.remove(em.find(Aluno.class, aluno.getId()));
        memberEventSrc.fire(aluno);
    }
}
