package br.com.fiap.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_CHAMADO")
public class Chamado {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CHAMADO")
    @SequenceGenerator(name = "SQ_CHAMADO", sequenceName = "SQ_CHAMADO")
    @Column(name = "ID_CHAMADO")
    private
    Long id;

    @Column(name = "TITULO", nullable = false)
    private
    String titulo;

    @Column(name = "DS_CHAMADO", nullable = false)
    private
    String descricao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ID_AREA",
            referencedColumnName = "ID_AREA",
            foreignKey = @ForeignKey(name = "FK_AREA_CHAMADO"),
            nullable = false
    )
    private Area area;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ID_SOLICITANTE",
            referencedColumnName = "ID_PESSOA",
            foreignKey = @ForeignKey(name = "FK_SOLICITANTE_CHAMADO"),
            nullable = false
    )
    private Pessoa solicitante;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ID_ATENDENTE",
            referencedColumnName = "ID_PESSOA",
            foreignKey = @ForeignKey(name = "FK_ATENDENTE_CHAMADO")
    )
    private Pessoa atendente;

    @Column(name = "DT_ABERTURA", nullable = false)
    private
    LocalDateTime abertura;
    @Column(name = "DT_INICIO")
    private
    LocalDateTime inicio;
    @Column(name = "DT_FECHAMENTO")
    private
    LocalDateTime fechamento;


    public Chamado() {
    }

    public Chamado(Long id, String titulo, String descricao, Area area, Pessoa solicitante, Pessoa atendente, LocalDateTime abertura, LocalDateTime inicio, LocalDateTime fechamento) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setDescricao(descricao);
        this.setArea(area);
        this.setSolicitante(solicitante);
        this.setAtendente(atendente);
        this.setAbertura(abertura);
        this.setInicio(inicio);
        this.setFechamento(fechamento);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Pessoa getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Pessoa solicitante) {
        this.solicitante = solicitante;
    }

    public Pessoa getAtendente() {
        return atendente;
    }

    public void setAtendente(Pessoa atendente) {
        this.atendente = atendente;
    }

    public LocalDateTime getAbertura() {
        return abertura;
    }

    public void setAbertura(LocalDateTime abertura) {
        this.abertura = abertura;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFechamento() {
        return fechamento;
    }

    public void setFechamento(LocalDateTime fechamento) {
        this.fechamento = fechamento;
    }

    @Override
    public String toString() {
        return "Chamado{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", area=" + area +
                ", solicitante=" + solicitante +
                ", atendente=" + atendente +
                ", abertura=" + abertura +
                ", inicio=" + inicio +
                ", fechamento=" + fechamento +
                '}';
    }
}
