package sc.senai.br.prova_java.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import sc.senai.br.prova_java.data.MatriculaRepository;
import sc.senai.br.prova_java.data.UnidadeRepository;
import sc.senai.br.prova_java.model.Curso;
import sc.senai.br.prova_java.model.Matricula;
import sc.senai.br.prova_java.model.Unidade;
import sc.senai.br.prova_java.service.CursoBusiness;

@Model
public class CursoController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private CursoBusiness cursoBusiness;

    @Inject
    private UnidadeRepository unidadeRepository;
    
    @Inject
    private MatriculaRepository matriculaRepository;

    @Produces
    @Named
    private Curso curso = new Curso();

    @PostConstruct
    public void initNewMember() {
        curso = new Curso();
    }

    public void salvar() throws Exception {
        try {
            if (isCursoValido()) {
                cursoBusiness.salvar(curso);
                FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo!", "Salvo com sucesso");
                facesContext.addMessage(null, m);
                initNewMember();
            }
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Erro ao salvar.");
            facesContext.addMessage(null, m);
        }
    }
    
    private boolean isCursoValido() {
        return isInicioMenorTermino();
    }
    
    private  boolean isInicioMenorTermino() {
        if (curso.getInicio().before(curso.getTermino())) {
            return true;
        }
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!", "Início deve ser menor que o término");
        facesContext.addMessage(null, m);
        return false;
    }

    public void excluir(Curso curso) throws Exception {
        try {
            if (!isCursoUsadoEmMatriculas(curso)) { 
                cursoBusiness.excluir(curso);
                FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido!", "Removido com sucesso");
                facesContext.addMessage(null, m);
            } else {
                FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Não Removido!", "O curso é utilizado em alguma matricula");
                facesContext.addMessage(null, m);
            }
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Erro ao remover.");
            facesContext.addMessage(null, m);
        }
    }
    
    private boolean isCursoUsadoEmMatriculas(Curso curso) {
        List<Matricula> matriculas = matriculaRepository.matriculasByCurso(curso.getId());
        return !matriculas.isEmpty();
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

    public void carregarCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Unidade> getAllUnidades() {
        return unidadeRepository.findAllOrderedByName();
    }

    public List<Unidade> getAllUnidadesAtivas() {
        return unidadeRepository.findAllAtivasOrderedByName();
    }

}
