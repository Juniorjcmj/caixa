/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mar.mil.br.controller.relatorio;


import java.io.*;
import java.sql.*;
import java.util.HashMap;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.*;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import mar.mil.br.util.UtilException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author sistemas
 */
public class RelatorioUtil {
    public static final int RELATORIO_PDF = 1;
   
    
    
    public StreamedContent gerarRelatorio(HashMap parametrosRelatorio, 
                                          String nomeRelatorioJasper, 
                                          String nomeRelatorioSaida,
                                          int tipoRelatorio)throws UtilException, JRException, FileNotFoundException{
        StreamedContent arquivoRetorno = null;
        try{
            //recuperamos a conexão com banco de dados.Essa conexão é obtida do DataSource JNDI, já configurado
            //utilizado tambem pelo hibernate e springsecurity
            Connection conexao = this.getConexao();
            FacesContext contextoFaces = FacesContext.getCurrentInstance();
            ExternalContext contextoExterno = contextoFaces.getExternalContext();
            ServletContext contextoServlet = (ServletContext) contextoExterno.getContext();
            
            String caminhoRelatorios = contextoServlet.getRealPath("/protected/relatorios");
            String caminhoArquivoJasper = caminhoRelatorios + File.separator + nomeRelatorioJasper
                                                                             +".jasper";
            String caminhoArquivoRelatorio = caminhoRelatorios + File.separator + nomeRelatorioSaida;
            
            JasperReport relatorioJasper =(JasperReport)JRLoader.loadObjectFromFile(caminhoArquivoJasper);
            JasperPrint impressoraJasper = JasperFillManager
                    .fillReport(relatorioJasper, parametrosRelatorio,conexao);
            
            String extensao = null;
            File arquivoGerado = null;
            
            switch(tipoRelatorio){
                case RelatorioUtil.RELATORIO_PDF:
                    JRPdfExporter pdfExportado = new JRPdfExporter();
                    extensao = "pdf";
                    arquivoGerado = new java.io.File(caminhoArquivoRelatorio + "." + extensao);
                    pdfExportado.setExporterInput(new SimpleExporterInput(impressoraJasper));
                    pdfExportado.setExporterOutput(new SimpleOutputStreamExporterOutput(arquivoGerado));
                    pdfExportado.exportReport();
                    arquivoGerado.deleteOnExit();
                    break;
                   
            }
            InputStream conteudoRelatorio = new FileInputStream(arquivoGerado);
            arquivoRetorno = new DefaultStreamedContent(conteudoRelatorio, "application/"
                                                         + extensao, nomeRelatorioSaida + "." + extensao);
            
        }catch(JRException e){
            throw new UtilException("Não foi possível gerar o relatório.", e);
        }catch(FileNotFoundException e){
            throw new UtilException("Arquivo do relatório não encontrado.", e);
        }
        return arquivoRetorno;
    }

    private Connection getConexao()throws UtilException{
        
        try{
            Context initContext = new InitialContext();
           // Context envContext =(Context)initContext.lookup("java:/jboss/");
            DataSource ds = (DataSource)initContext.lookup("java:/jboss/jdbc/PostgresDS");
            return ds.getConnection();
        }catch(NamingException e){
            throw new UtilException("Não foi possivel encontrar o nome da conexão com banco.", e);
        }catch(SQLException  e){
            throw new UtilException("Ocorreu um erro de SQL.", e);
        }
        
        
    }
            
}
