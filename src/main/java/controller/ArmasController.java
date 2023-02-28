package controller;

import model.Armas;
import model.Partidas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ArmasController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;


    public ArmasController(Connection connection) {
        this.connection = connection;
    }

    public ArmasController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * @param armasFile Aquest String correspon amb l'arxiu on s'emmagatzemen les
     *                     dades de les isntancies de Revista
     * @return ArrayList d'objectes Revista, amb els seus articles i la
     * informació de l'autor
     * @throws IOException <dt><b>Preconditions:</b>
     *                     <dd>
     *                     filename<>nil </br> llistaRevistes<>nil </br>
     *                     llistaRevistes.getRevista(i).getArticles()== nil</br>
     *                     <dt><b>Postconditions:</b>
     *                     <dd>
     *                     llistaRevistes.getRevistes()<>nil</br>
     *                     llistaRevistes.getRevista(i).getArticles()<>nil</br>
     *                     llistaRevistes.getRevista(i).getArticle(j)<>nil</br>
     *                     llistaRevistes
     *                     .getRevista(i).getArticle(j).getAutor()<>nil</br>
     */
    public List<Armas> readArticlesFile(String armasFile) throws IOException {
        int idarma;
        String name;
        String type;

        BufferedReader br = new BufferedReader(new FileReader(armasFile));
        String linea = "";
        List<Armas> armasList = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            idarma = Integer.parseInt(str.nextToken());
            name = str.nextToken();
            type = str.nextToken();

            armasList.add(new Armas(idarma, name, type));

        }
        br.close();

        return armasList;
    }


    /* Method to CREATE a Partidas in the database */
    public void addArmas(Armas armas) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(armas);
        em.getTransaction().commit();
        em.close();
    }

    /* Method to READ all Articles */
    public void listArticles() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Armas> result = em.createQuery("from Armas", Armas.class).getResultList();
        for (Armas armas : result) {
            System.out.println(armas.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    /* Method to UPDATE activity for an Article */
    public void updateArticle(Integer articleId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Partidas article = (Partidas) em.find(Partidas.class, articleId);
        em.merge(article);
        em.getTransaction().commit();
        em.close();
    }

    /* Method to DELETE an Article from the records */
    public void deleteArticle(Integer articleId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Partidas article = (Partidas) em.find(Partidas.class, articleId);
        em.remove(article);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteTable(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createNativeQuery("DROP TABLE Armas CASCADE");
        query.executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
}
