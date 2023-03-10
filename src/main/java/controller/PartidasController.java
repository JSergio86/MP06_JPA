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
      idmapa = Integer.parseInt(str.nextToken());
      idjugador = Integer.parseInt(str.nextToken());
      idpartida = Integer.parseInt(str.nextToken());
      type = str.nextToken();
      result = str.nextToken();

      partidaList.add(new Partidas(idpartida,idmapa , idjugador, type, result));

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
  public void modificarRegistro() {
    Scanner sc = new Scanner(System.in);
    EntityManager em = entityManagerFactory.createEntityManager();
    listArticles();
    System.out.println("\n¿Qué idpartida quieres modificar?");
    int idpartida = sc.nextInt();

    Partidas partida = em.find(Partidas.class, idpartida);

    if (partida != null) {
      System.out.println("Escribe la columna que quiere modificar");
      String columna = sc.next();

      sc.nextLine();

      System.out.println("Escribe el nuevo valor");
      String valor = sc.nextLine();

      switch (columna) {
        case "type":
          partida.setType(valor);
          break;
        case "result":
          partida.setResult(valor);
          break;
        default:
          System.out.println("Columna no válida");
          return;
      }

      em.getTransaction().begin();
      em.flush();
      em.getTransaction().commit();
    }
    listArticles();
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    em.close();
  }

  /* Method to DELETE an Article from the records */
  public void eliminarRegistro() {
    EntityManager em = entityManagerFactory.createEntityManager();
    Scanner sc = new Scanner(System.in);
    listArticles();
    System.out.println("\n¿Que idpartida quieres eliminar?");
    int idpartida = sc.nextInt();

    // Eliminar un registro concreto
    Partidas partidaAEliminar = em.find(Partidas.class, idpartida);
    if (partidaAEliminar != null) {
      em.getTransaction().begin();
      em.remove(partidaAEliminar);
      em.flush();
      em.getTransaction().commit();
    }

    listArticles();
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteTable(){
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();

    Query query = em.createNativeQuery("DROP TABLE Partidas CASCADE");
    query.executeUpdate();

    em.getTransaction().commit();
    em.close();
  }


}
