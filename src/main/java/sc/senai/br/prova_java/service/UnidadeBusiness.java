package sc.senai.br.prova_java.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import sc.senai.br.prova_java.model.Unidade;

@Stateless
public class UnidadeBusiness {

	@Inject
    private Logger log;

    @Inject
    private EntityManager em;
    
    @Inject
    private Event<Unidade> memberEventSrc;
    
    public void salvar(Unidade unidade) throws Exception {
        log.info("Salvando " + unidade.getNome());
        em.merge(unidade);
        memberEventSrc.fire(unidade);
    }
    
    
    public void excluir(Unidade unidade) throws Exception {
        log.info("Excluindo " + unidade.getNome());
        em.remove(em.find(Unidade.class, unidade.getId()));
        memberEventSrc.fire(unidade);
    }
}
