/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mar.mil.br.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mar.mil.br.entity.Sugestao;
import mar.mil.br.repository.SugestaoRepository;
import mar.mil.br.usuario.Usuario;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Junior
 */
@ManagedBean
@ViewScoped
public class SugestaoBean implements Serializable {

    // INJETANDO O METODO REPOSITORIO NA CLASSE
    @EJB
    SugestaoRepository sugestaoRepository;

    // CRIANDO AS VARIAVEIS UTEIS
    private Sugestao sugestao = new Sugestao();
    private List<Sugestao> findAll = new ArrayList<>();

    private String destinoSalvar;

    //ATRIBUTOS CRIADOS PARA CRIAÇÃO DE TICKTS
    Integer tickt;
    private List<Sugestao> findAllpoTickt = new ArrayList<>();

    private String ip;
    //RESPOSTA AUTOMÁTICA AO FAZER UMA NOVA SUGESTÃO E DURANTE A RESPOSTA
    private String analise = "Em Analise";
    private String respondido = "Respondido";
    //pegando usuario logado e adicionando o nome dele a atualização
    String modificador = SecurityContextHolder.getContext().getAuthentication().getName();
    //=============================================================================

    public void novaSituacao() {
        sugestao = new Sugestao();
    }

    public String logar() {
        return "/publico/login.xhtml?faces-redirect=true";
    }
    //ESTE METODO REDIRECIONA PARA PAGINA DE USUARIO

    public String admin() {
        return "/usuario/principal.xhtml";
    }
    // METODO UTILITARIO PARA REDIRECIONAMENTO DA PAGINA PARA HOME

    public String home() {
        return "/home.xhtml?faces-redirect=true";
    }

    //BUSCAR SUGESTÃO POR TICKT
    public List<Sugestao> getBuscarPorTickt() {
        if (this.tickt == null) {
            // CASO O USUÁRIO NÃO DIGITE NENHUM VALOR SERÁ LANÇADA UMA MENSAGEM 
            FacesMessage msg = new FacesMessage("O campo Protocolo não pode ficar em branco");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            //BUSCA A SUGESTÃO ATRAVÉS DO TICKT DIGITADO NO BANCO DE DADOS
            List<Sugestao> sugestaoPorTickt = this.sugestaoRepository.buscarPorTicket(this.tickt);
            // RESULTADO RETORNADO É PASSADO PARA O METODO SUGESTÃOPORTICKT
            findAllpoTickt = sugestaoPorTickt;

            if (findAllpoTickt == null) {
                // CASO NENHUMA SUGESTÃO FOR ENCONTRADA SERÁ LANÇADA A MENSAGEM ABAIXO
                FacesMessage msg = new FacesMessage("Não existe sugestão para este número de protocolo!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        return null;
    }

    //METODO CRIADO PARA ALIMENTAR O DATATABLE 
    public List<Sugestao> getFindAllpoTickt() {

        return findAllpoTickt;
    }

    //GERADOR DE TIKET
    @SuppressWarnings("null")
    public Integer gerarTiket() {
        Random geradorTiket = new Random();
        //GERANDO NUMERAÇÃO ALEATÓRIA PARA O TICKET
        Integer tickt = geradorTiket.nextInt(1000000) + 10000;

        //BUSCA O TICKET GERADO NO BANCO DE DADOS E VERIFICA SE JÁ EXISTE
        boolean sComTicket = this.sugestaoRepository.buscarTicketNoBanco(tickt);
        //VERIFICA SE O sComTickt  É VERDADEIRO CASO SEJA ELE CHAMARA O METODO GERARTICKT NOVAMENTE
        if (sComTicket != true) {
            gerarTiket();
        }

        return tickt;
    }

    
    // METODO PARA  SALVAR UMA SUGESTÃO
    public String save() {
        //VERIFICA SE O CAMPO ASSUNTO E/OU SUGESTÃO ESTÃO EM BRANCO
        if (sugestao.getAssunto() != null && sugestao.getSugestao() != null) {
            //VERIFICA SE A SUGESTÃO JÁ EXISTE E SE RETORNAR VERDADEIRO CHAMA O MÉTODO PARA ATUALIZAR
            if (sugestao.getId() == null) {
                //VERIFICA O NOME DO MODIFICADOR ATRAVÉS  DO SPRING SECURITY E GRAVA NO BANCO DE DADOS
                this.sugestao.setModificador(modificador);

                //CHAMA O METODO GEREAR TICKT QUE RETORNA UM TICKT UNICO
                this.sugestao.setTickt(gerarTiket());
                //PEGA A DATAHORA NO MOMENTO DA CRIAÇÃO DA NOVA SUGESTÃO
                sugestao.setDataSugestao(new Date());
                //ADICIONA UMA INFORMAÇÃO PADRÃO PARA TODAS AS MENSAGENS
                sugestao.setSituacao(analise);

                // pega o ip do usuario no momento da criação da sugestão
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                sugestao.setIp(request.getRemoteAddr());
                //SALVA A NOVA SUGESTÃO NO BANCO DE DADOS
                this.sugestaoRepository.save(sugestao);
                // LANÇA UMA MENSAGEM PARA O USUÁRIO INFORMANDO QUE A GRAVAÇÃO FOI REALIZADA COM SUCESSO E MOSTRA O NUMERO DO TICKT
                FacesMessage msg = new FacesMessage("Sugestão cadastrada com sucesso! seu protocolo é " + this.sugestao.getTickt());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                //SALVA O NOME DO MODIFICADOR
                this.sugestao.setModificador(modificador);
                //SALVA A DATA DE MODIFICAÇÃO
                sugestao.setDataObservacao(new Date());
                //ATUALIZA AS INFORMAÇÕES NO BANCO DE DADOS
                this.sugestaoRepository.update(sugestao);
                //LANÇA UMA MENSAGEM PARA O USUARIO INFORMANDO QUE A ATUALIZAÇÃO OCORREU COM SUCESSO
                FacesMessage msg = new FacesMessage("Observação salva com sucesso!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            this.sugestao = new Sugestao();
            this.findAll = null;
            return null;
        } else {
            //CASO O SUSUARIO TENTE CADASTRAR UMA SUGESTÃO SEM ASSUNTO OU SEM SUGESTÃO SERÁ LANÇADO A MENSAGEM ABAIXO
            FacesMessage msg = new FacesMessage(" A sugestão e/ou Assunto não pode ficar em branco, por favor preencha novamente!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }

        return null;
    }

    //REDIRECIONA PARA PAGINA DE EDIÇÃO
    public String editar() {
        return "addViatura";
    }

    // METODO PARA EXCLUSÃO 
    public String excluir1() {

        sugestaoRepository.excluir(this.sugestao);
        this.findAll = null;
        return null;
    }


    //BUSCA TODAS AS SUGESTÕES NO BANCO DE DADOS
    public List<Sugestao> getFindAll() {
        this.findAll = this.sugestaoRepository.sugestoes();
        return findAll;
    }

    /**
     * Busca as sugestões no banco de dados e organiza comforme a data
     *
     * @return
     */
    public List<Sugestao> getOrdenadas() {
        this.findAll = this.sugestaoRepository.sugestoes();
        return findAll;
    }

    //=============================Consulta de acordo com a OM======================================================
    /**
     * Busca a Om do usuário logado no sistema e retorna a tabela conforme a
     * regra de negócio para aquela OM
     *
     * @return
     */
    public List<Sugestao> getFindAllDefguranca() {

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario OmLogado = this.sugestaoRepository.findOneLogadoOm(login);
        String om = OmLogado.getOm();
        String DivAnf = "DivAnf";
        List<Sugestao> findAllOm = new ArrayList<>();
        if (OmLogado.getOm().equals(DivAnf)) {
            findAllOm = this.sugestaoRepository.sugestoes();
        }
        return findAllOm;
    }

    //==============================================================================================================
    /**
     * Identifica o endereço IP remot
     *
     * @return o
     */
    public String getIP() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //String ip = null;
        ip = request.getHeader("x-forwarded-for");
        if (ip == null) {
            ip = request.getHeader("X_FORWARDED_FOR");
            if (ip == null) {
                ip = request.getRemoteAddr();
            }
        }
        return ip;
    }

    //Este método verifica se o usuário esta logado e caso esteja ele retorna true
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
    //lista de assuntos para escolha   

    public List<String> getAssuntos() {
        List<String> assuntos = new ArrayList<>();
        assuntos.add("Rancho");
        assuntos.add("Conforto");
        assuntos.add("Limpeza");
        assuntos.add("Força de Trabalho");
        assuntos.add("Ativ_Administrativa");
        assuntos.add("Ativ_Esportiva");
        assuntos.add("Cerimônias");
        assuntos.add("Estacionamento");
        assuntos.add("Serviço da OM");
        assuntos.add("Eventos");
        return assuntos;

    }

    public Sugestao getSugestao() {
        return sugestao;
    }

    public String getModificador() {
        return modificador;
    }

    public void setSugestao(Sugestao sugestao) {
        this.sugestao = sugestao;
    }

    public void setFindAll(List<Sugestao> findAll) {
        this.findAll = findAll;
    }

    public void setModificador(String modificador) {
        this.modificador = modificador;
    }

    public String getAnalise() {
        return analise;
    }

    public void setAnalise(String analise) {
        this.analise = analise;
    }

    public String getRespondido() {
        return respondido;
    }

    public void setRespondido(String respondido) {
        this.respondido = respondido;
    }

    public String getDestinoSalvar() {
        return destinoSalvar;
    }

    public void setDestinoSalvar(String destinoSalvar) {
        this.destinoSalvar = destinoSalvar;
    }

    public Integer getTickt() {
        return tickt;
    }

    public void setTickt(Integer tickt) {
        this.tickt = tickt;
    }

}
