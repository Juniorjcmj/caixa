/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mar.mil.br.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Desenvolvedor
 */
@Entity
@Table(name = "sugestao")
public class Sugestao implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer tickt;
    
    private String sugestao;
    private String observacao;
    @Temporal(TemporalType.DATE)
    private Date dataSugestao; 
    @Temporal(TemporalType.DATE)
    private Date dataObservacao;
    private String modificador;
    private String ip;
    private String assunto;
    private String situacao;

    public Sugestao() {
        super();
                
    }   

    public Sugestao(Long id, String sugestao, String observacao, Date dataSugestao, Date dataObservacao, String modificador, String ip, String assunto, String situacao) {
        this.id = id;
        this.sugestao = sugestao;
        this.tickt = tickt;
        this.observacao = observacao;
        this.dataSugestao = dataSugestao;
        this.dataObservacao = dataObservacao;
        this.modificador = modificador;
        this.ip = ip;
        this.assunto = assunto;
        this.situacao = situacao;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSugestao() {
        return sugestao;
    }

    public void setSugestao(String sugestao) {
        this.sugestao = sugestao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataSugestao() {
        return dataSugestao;
    }

    public void setDataSugestao(Date dataSugestao) {
        this.dataSugestao = dataSugestao;
    }

    public Date getDataObservacao() {
        return dataObservacao;
    }

    public void setDataObservacao(Date dataObservacao) {
        this.dataObservacao = dataObservacao;
    }

    public String getModificador() {
        return modificador;
    }

    public void setModificador(String modificador) {
        this.modificador = modificador;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "Sugestao{" + "id=" + id + ", sugestao=" + sugestao + ", observacao=" + observacao + ", dataSugestao=" + dataSugestao + ", dataObservacao=" + dataObservacao + ", modificador=" + modificador + ", ip=" + ip + ", assunto=" + assunto + ", situacao=" + situacao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.sugestao);
        hash = 13 * hash + Objects.hashCode(this.observacao);
        hash = 13 * hash + Objects.hashCode(this.dataSugestao);
        hash = 13 * hash + Objects.hashCode(this.dataObservacao);
        hash = 13 * hash + Objects.hashCode(this.modificador);
        hash = 13 * hash + Objects.hashCode(this.ip);
        hash = 13 * hash + Objects.hashCode(this.assunto);
        hash = 13 * hash + Objects.hashCode(this.situacao);
        return hash;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sugestao other = (Sugestao) obj;
        if (!Objects.equals(this.sugestao, other.sugestao)) {
            return false;
        }
        if (!Objects.equals(this.observacao, other.observacao)) {
            return false;
        }
        if (!Objects.equals(this.modificador, other.modificador)) {
            return false;
        }
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (!Objects.equals(this.assunto, other.assunto)) {
            return false;
        }
        if (!Objects.equals(this.situacao, other.situacao)) {
            return false;
        }
        if (!Objects.equals(this.dataSugestao, other.dataSugestao)) {
            return false;
        }
        if (!Objects.equals(this.dataObservacao, other.dataObservacao)) {
            return false;
        }
        return true;
    }

    public Integer getTickt() {
        return tickt;
    }

    public void setTickt(Integer tickt) {
        this.tickt = tickt;
    }

        
    
    
}
