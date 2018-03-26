/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mar.mil.br.controller.relatorio;


import java.io.FileNotFoundException;
import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import mar.mil.br.util.UtilException;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author sistemas
 */
@ManagedBean
@RequestScoped
public class RelatorioBean {
    
    private StreamedContent arquivoRetorno;
    private int tipoRelatorio;

    public StreamedContent getArquivoretorno() throws JRException, FileNotFoundException, UtilException {
        FacesContext context = FacesContext.getCurrentInstance();
        String nomeRelatorioJasper = "RelatorioDetail";
        String nomeRelatorioSaida = "Relatorio Detalhado";
        RelatorioUtil relatorioUtil = new RelatorioUtil();
        HashMap parametrosRelatorio = new HashMap();
        try{
            this.arquivoRetorno = relatorioUtil.gerarRelatorio(parametrosRelatorio, 
                                                               nomeRelatorioJasper, 
                                                               nomeRelatorioSaida, 
                                                               this.tipoRelatorio);
        }catch(UtilException e ){
            context.addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
        return arquivoRetorno;
    }

    public void setArquivoretorno(StreamedContent arquivoretorno) {
        this.arquivoRetorno = arquivoretorno;
    }

    public int getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(int tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }
    
    
}
