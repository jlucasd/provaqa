package sc.senai.br.prova_java.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import sc.senai.br.prova_java.data.UnidadeRepository;
import sc.senai.br.prova_java.model.Unidade;
import sc.senai.br.prova_java.service.UnidadeBusiness;

@Model
public class UnidadeController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private UnidadeBusiness unidadeBusiness;

    @Inject
    private UnidadeRepository unidadeRepository;

    @Produces
    @Named
    private Unidade unidade = new Unidade();

    @PostConstruct
    public void initNewMember() {
        unidade = new Unidade();
    }

    public void salvar() throws Exception {
        try {
            unidadeBusiness.salvar(unidade);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo!", "Salvo com sucesso");
            facesContext.addMessage(null, m);
            initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Erro ao salvar.");
            facesContext.addMessage(null, m);
        }
    }
    
    public void excluir(Unidade unidade) throws Exception {
        try {
            unidadeBusiness.excluir(unidade);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido!", "Removido com sucesso");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Erro ao remover.");
            facesContext.addMessage(null, m);
        }
    }
    
    private String getRootErrorMessage(Exception e) {
        String errorMessage = "Erro ao salvar. Verificar o log.";
        if (e == null) {
            return errorMessage;
        }

        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }

    public void carregarUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public List<Unidade> getAllUnidades() {
        return unidadeRepository.findAllOrderedByName();
    }
    
    public void ativarDesativarUnidade(Unidade unidade) throws Exception {
        unidadeBusiness.salvar(unidade);
    }

}
