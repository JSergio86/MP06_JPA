package controller;

import model.Jugadores;
import model.Partidas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class JugadoresController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;


    public JugadoresController(Connection connection) {
        this.connection = connection;
    }

    public JugadoresController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * @param jugadoresFile Aquest String correspon amb l'arxiu on s'emmagatzemen les
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

    public List<Jugadores> readArticlesFile(String jugadoresFile) throws IOException {
        int idjugador;
        String rank;
        int wins;
        int kills;
        int deaths;
        int assists;
        float scoreround;
        float kad;
        float killsround;
        int plants;
        int firstbloods;
        int clutches;
        int flawless;
        int aces;

        BufferedReader br = new BufferedReader(new FileReader(jugadoresFile));
        String linea = "";
        List<Jugadores> jugadoresList = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            idjugador = Integer.parseInt(str.nextToken());
            rank = str.nextToken();
            wins = Integer.parseInt(str.nextToken());
            kills = Integer.parseInt(str.nextToken());
            deaths = Integer.parseInt(str.nextToken());
            assists = Integer.parseInt(str.nextToken());
            scoreround = Float.parseFloat(str.nextToken());
            kad = Float.parseFloat(str.nextToken());
            killsround = Float.parseFloat(str.nextToken());
            plants = Integer.parseInt(str.nextToken());
            firstbloods = Integer.parseInt(str.nextToken());
            clutches = Integer.parseInt(str.nextToken());
            flawless = Integer.parseInt(str.nextToken());
            aces = Integer.parseInt(str.nextToken());

            jugadoresList.add(new Jugadores(idjugador, rank, wins, kills, deaths, assists, scoreround, kad, killsround, plants, firstbloods, clutches, flawless, aces));

        }
        br.close();

        return jugadoresList;
    }


    /* Method to CREATE a Partidas in the database */
    public void addPartidas(Jugadores jugadores) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(jugadores);
        em.getTransaction().commit();
        em.close();
    }

    /* Method to READ all Articles */
    public void listArticles() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Jugadores> result = em.createQuery("from Jugadores", Jugadores.class).getResultList();
        for (Jugadores jugadores : result) {
            System.out.println(jugadores.toString());
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
}
