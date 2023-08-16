package br.com.fiap;

import br.com.fiap.domain.entity.Area;
import br.com.fiap.domain.entity.Documento;
import br.com.fiap.domain.entity.TipoDocumento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager manager = factory.createEntityManager();

        // Area area = addArea(manager);
        // TipoDocumento tipo = addTipoDocumento(manager);

        Documento documento = new Documento();

        documento.setNumero("231543543");
        documento.setValidade(LocalDate.now().plusYears(5));
        documento.setTipo(findById(2L, manager));

        manager.getTransaction().begin();
        manager.persist(documento);
        manager.getTransaction().commit();

        System.out.println(documento);

        manager.close();
        factory.close();
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