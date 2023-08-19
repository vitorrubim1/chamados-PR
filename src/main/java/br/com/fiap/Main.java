package br.com.fiap;

import br.com.fiap.domain.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager manager = factory.createEntityManager();

        // addArea(manager);
        // TipoDocumento tipo = addTipoDocumento(manager);
        // addDocumento(manager);
        // addPessoa(manager);
        // aberturaDeChamado(manager);

        manager.close();
        factory.close();
    }

    private static void aberturaDeChamado(EntityManager manager) {
        List<Pessoa> pessoas = manager.createQuery("FROM Pessoa").getResultList();
        Pessoa solicitante = (Pessoa) JOptionPane.showInputDialog(
                null,
                "Solicitante",
                "Selecione",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pessoas.toArray(),
                pessoas.get(0)
        );

        List<Area> areas = manager.createQuery("FROM Area").getResultList();
        Area area = (Area) JOptionPane.showInputDialog(
                null,
                "Área",
                "Selecione",
                JOptionPane.QUESTION_MESSAGE,
                null,
                areas.toArray(),
                areas.get(0)
        );

        String titulo = JOptionPane.showInputDialog("Título do problema: ");
        String descricao = JOptionPane.showInputDialog("Descreva o problema: ");

        Chamado chamado = new Chamado();
        chamado.setAbertura(LocalDateTime.now());
        chamado.setArea(area);
        chamado.setTitulo(titulo);
        chamado.setDescricao(descricao);
        chamado.setSolicitante(solicitante);

        manager.getTransaction().begin();
        manager.persist(chamado);
        manager.getTransaction().commit();
    }

    private static void addPessoa(EntityManager manager) {
        Pessoa pessoa = new Pessoa();

        String nome = JOptionPane.showInputDialog("Digite o nome da pessoa:");
        String nascimento = JOptionPane.showInputDialog("Digite a data de nascimento da pessoa, formato: DD/MM/AAAA:");

        int dia = Integer.parseInt(nascimento.substring(0, 2));
        int mes = Integer.parseInt(nascimento.substring(3, 5));
        int ano = Integer.parseInt(nascimento.substring(6, 10));

        pessoa.setNome(nome);
        pessoa.setNascimento(LocalDate.of(ano, mes, dia));

        manager.getTransaction().begin();
        manager.persist(pessoa);
        manager.getTransaction().commit();
    }

    private static void addDocumento(EntityManager manager) {
        Documento documento = new Documento();

        List<TipoDocumento> tipos = manager.createQuery("FROM TipoDocumento").getResultList();
        TipoDocumento tipoSelecionado = (TipoDocumento) JOptionPane.showInputDialog(
                null,
                "Selecione o tipo de documento",
                "Seleção de tipo de documento",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos.toArray(),
                tipos.get(0)
        );

        documento.setTipo(tipoSelecionado);

        String numero = JOptionPane.showInputDialog("Número do documento: ");
        documento.setNumero(numero);

        String validade = JOptionPane.showInputDialog("Digite a data de válidade, formato: DD/MM/AAAA:");
        int dia = Integer.parseInt(validade.substring(0, 2));
        int mes = Integer.parseInt(validade.substring(3, 5));
        int ano = Integer.parseInt(validade.substring(6, 10));
        documento.setValidade(LocalDate.of(ano, mes, dia));

        List<Pessoa> pessoas = manager.createQuery("FROM Pessoa").getResultList();
        Pessoa pessoaSelecionada = (Pessoa) JOptionPane.showInputDialog(
                null,
                "Selecione a pessoa",
                "Seleção de pessoas",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pessoas.toArray(),
                pessoas.get(0)
        );

        documento.setPessoa(pessoaSelecionada);

        manager.getTransaction().begin();
        manager.persist(documento);
        manager.getTransaction().commit();

        System.out.println(documento);
    }

    static TipoDocumento findById (Long id, EntityManager manager) {
        return manager.find(TipoDocumento.class, id);
    }

    private static TipoDocumento addTipoDocumento(EntityManager manager) {
        TipoDocumento tipo = new TipoDocumento();
        boolean salvou = false;

        String nome = JOptionPane.showInputDialog("Tipo de documento: ");
        tipo.setNome(nome);

        do {
            try {
                manager.getTransaction().begin();
                manager.persist(tipo);
                manager.getTransaction().commit();
                System.out.println(tipo);
                salvou = true;
            } catch (Exception ex) {
                String error = """
                        Erro!
                        Não foi possível salvar o tipo de documento.
                                            
                        """ + ex.getMessage();
                System.err.println(error);
                JOptionPane.showMessageDialog(null, error);
            }
        } while (salvou == false);

        return tipo;
    }

    private static Area addArea(EntityManager manager) {
        Area area = new Area();
        boolean salvou = false;

        do {
            String nome = JOptionPane.showInputDialog("Nome da área:");
            String descricao = JOptionPane.showInputDialog("Descrição da área:");

            area.setNome(nome).setDescricao(descricao);

            try {
                manager.getTransaction().begin();
                manager.persist(area);
                manager.getTransaction().commit();

                System.out.println(area);
                salvou = true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível salvar a área.");
            }
        } while (salvou == false);

        return area;
    }
}