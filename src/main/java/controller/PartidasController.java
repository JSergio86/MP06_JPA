package controller;

import model.Partidas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PartidasController {
  private Connection connection;
  private EntityManagerFactory entityManagerFactory;


  public PartidasController(Connection connection) {
    this.connection = connection;
  }

  public PartidasController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * @param partidasFile Aquest String correspon amb l'arxiu on s'emmagatzemen les
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
  public List<Partidas> readArticlesFile(String partidasFile) throws IOException {
    int idpartida;
    int idjugador;
    int idmapa;
    String type;
    String result;

    BufferedReader br = new BufferedReader(new FileReader(partidasFile));
    String linea = "";
    List<Partidas> partidaList = new ArrayList<>();

    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      idpartida = Integer.parseInt(str.nextToken());
      idjugador = Integer.parseInt(str.nextToken());
      idmapa = Integer.parseInt(str.nextToken());
      type = str.nextToken();
      result = str.nextToken();

      partidaList.add(new Partidas(idpartida, idjugador, idmapa, type, result));

    }
    br.close();

    return partidaList;
  }


  /* Method to CREATE a Partidas in the database */
  public void addPartidas(Partidas partidas) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(partidas);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to READ all Articles */
  public void listArticles() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Partidas> result = em.createQuery("from Partidas", Partidas.class)
        .getResultList();
    for (Partidas partidas : result) {
      System.out.println(partidas.toString());
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
