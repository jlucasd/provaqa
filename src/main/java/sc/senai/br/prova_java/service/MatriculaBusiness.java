package sc.senai.br.prova_java.service;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import sc.senai.br.prova_java.model.Matricula;

@Stateless
public class MatriculaBusiness {

	@Inject
    private Logger log;

    @Inject
    private EntityManager em;
    
    @Inject
    private Event<Matricula> memberEventSrc;
    
    public void salvar(Matricula matricula) throws Exception {
        log.info("Salvando a matricula de " + matricula.getAluno().getNome());

        if (matricula.getSituacao() == null) {
            matricula.setSituacao("Em Andamento");
        }



        if(true){
            em.merge(matricula);
            memberEventSrc.fire(matricula);
        }
        
    }
    
    public void excluir(Matricula matricula) throws Exception {
        log.info("Excluindo a matricula de " + matricula.getAluno().getNome());
        em.remove(em.find(Matricula.class, matricula.getId()));
        memberEventSrc.fire(matricula);
    }
    

    public void aprovarMatricula(Matricula matricula) {
        matricula.setSituacao("Concluída");
        matricula.setConfirmacao(new Date());
        em.merge(matricula);
        memberEventSrc.fire(matricula);
    }
}
