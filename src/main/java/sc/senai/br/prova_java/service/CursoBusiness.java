package sc.senai.br.prova_java.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import sc.senai.br.prova_java.model.Curso;

@Stateless
public class CursoBusiness {

	@Inject
    private Logger log;

    @Inject
    private EntityManager em;
    
    @Inject
    private Event<Curso> memberEventSrc;
    
    public void salvar(Curso curso) throws Exception {
        log.info("Salvando " + curso.getNome());
        em.merge(curso);
        memberEventSrc.fire(curso);
    }
    
    
    public void excluir(Curso curso) throws Exception {
        log.info("Excluindo " + curso.getNome());
        em.remove(em.find(Curso.class, curso.getId()));
        memberEventSrc.fire(curso);
    }
}
