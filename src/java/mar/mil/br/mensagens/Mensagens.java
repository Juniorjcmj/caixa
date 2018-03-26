/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mar.mil.br.mensagens;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Desenvolvedor
 */
public class Mensagens {

    private final String SUCESSO = "Operação realizada com sucesso";
    private final String ERRO = "Ops! Ocorreu um erro inesperado";

    public void mensagemSucesso(ActionEvent event) {
        FacesContext instance = FacesContext.getCurrentInstance();
        instance.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        SUCESSO,
                        SUCESSO)
        );
    }

    public void mensagemErro(ActionEvent event) {
        FacesContext instance = FacesContext.getCurrentInstance();
        instance.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ERRO,
                        ERRO)
        );
    }
}
