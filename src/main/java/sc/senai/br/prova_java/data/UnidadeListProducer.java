package sc.senai.br.prova_java.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import sc.senai.br.prova_java.model.Unidade;

@RequestScoped
public class UnidadeListProducer {
	
	@Inject
    private UnidadeRepository unidadeRepository;
	
	private List<Unidade> unidades;

	@Produces
    @Named
    public List<Unidade> getUnidades() {
        return unidades;
    }
	
	public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Unidade unidade) {
		retrieveAllUnidadesOrderedByName();
    }

    @PostConstruct
    public void retrieveAllUnidadesOrderedByName() {
        unidades = unidadeRepository.findAllOrderedByName();
    }
	
}
