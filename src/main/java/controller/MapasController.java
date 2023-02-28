package controller;

import model.Mapas;
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

public class MapasController {
  private Connection connection;
  private EntityManagerFactory entityManagerFactory;


  public MapasController(Connection connection) {
    this.connection = connection;
  }

  public MapasController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * @param mapasFile Aquest String correspon amb l'arxiu on s'emmagatzemen les
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
  public List<Mapas> readArticlesFile(String mapasFile) throws IOException {
    int idmapa;
    String name;
    String porcentaje_win;
    int wins ;
    int losses;
    float kd;
    float adr;
    float acs;


    BufferedReader br = new BufferedReader(new FileReader(mapasFile));
    String linea = "";
    List<Mapas> mapasList = new ArrayList<>();

    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      idmapa = Integer.parseInt(str.nextToken());
      name = str.nextToken();
      porcentaje_win = str.nextToken();
      wins = Integer.parseInt(str.nextToken());
      losses = Integer.parseInt(str.nextToken());
      kd = Float.parseFloat(str.nextToken());
      adr = Float.parseFloat(str.nextToken());
      acs = Float.parseFloat(str.nextToken());

      mapasList.add(new Mapas(idmapa, name, porcentaje_win, wins, losses, kd, adr, acs));
    }

    br.close();

    return mapasList;
  }


  /* Method to CREATE a Partidas in the database */
  public void addMapas(Mapas mapas) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(mapas);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to READ all Articles */
  public void listArticles() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Mapas> result = em.createQuery("from Mapas", Mapas.class)
        .getResultList();
    for (Mapas mapas : result) {
      System.out.println(mapas.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  /* Method to UPDATE activity for an Article */
  public void updateArticle(Integer articleId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Mapas article = (Mapas) em.find(Mapas.class, articleId);
    em.merge(article);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to DELETE an Article from the records */
  public void deleteArticle(Integer articleId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Mapas article = (Mapas) em.find(Mapas.class, articleId);
    em.remove(article);
    em.getTransaction().commit();
    em.close();
  }

  public void deleteTable(){
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();

    Query query = em.createNativeQuery("DROP TABLE Mapas CASCADE");
    query.executeUpdate();

    em.getTransaction().commit();
    em.close();
  }


}
