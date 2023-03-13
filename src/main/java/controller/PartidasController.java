package controller;

import model.Armas;
import model.Partidas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
   * Permite leer los archivos csv y crear un objeto con la información del archivo
   * @param partidasFile Pasar la ruta del archivo csv
   * @return
   * @throws IOException
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

  /**
   * Metodo para añadir la información en la tabla
   * @param partidas
   */
  public void addPartidas(Partidas partidas) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(partidas);
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Metodo para listar toda la tabla y imprimirlo por pantalla
   */
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

  public void buscarPorTipo(){
    Scanner sc = new Scanner(System.in);
    System.out.println("Tipo de partida que quieres buscar");
    String tipo = sc.next();
    List<Partidas> lista= listajugadoresTipo(tipo);
    for (Partidas partida : lista) {
      System.out.println(partida.toString());
    }
  }

  public List<Partidas> listajugadoresTipo(String tipo) {
    EntityManager em = entityManagerFactory.createEntityManager();
    TypedQuery<Partidas> query = em.createQuery("SELECT j FROM Partidas j WHERE j.type LIKE :tipo", Partidas.class);
    query.setParameter("tipo", "%" + tipo + "%");
    return query.getResultList();
  }
  public void buscarPorIDMayorQue(){
    Scanner sc = new Scanner(System.in);
    System.out.println("ID mayor que: ");
    int id = sc.nextInt();
    List<Partidas> lista= listaJugadorIDs(id);
    for (Partidas partida : lista) {
      System.out.println(partida.toString());
    }
  }

  public List<Partidas> listaJugadorIDs(int id) {
    EntityManager em = entityManagerFactory.createEntityManager();
    TypedQuery<Partidas> query = em.createQuery("SELECT j FROM Partidas j WHERE j.idpartida > :id", Partidas.class);
    query.setParameter("id", id);
    return query.getResultList();
  }
  public void buscarPorId(){
    Scanner sc = new Scanner(System.in);
    System.out.println("ID de la partida que quieres buscar");
    int id = sc.nextInt();
    Partidas lista= listaId(id);
    System.out.println(lista.toString());
  }

  public Partidas listaId(int id) {
    EntityManager em = entityManagerFactory.createEntityManager();
    return em.find(Partidas.class, id);
  }

  /**
   * Metodo para modificar un registro de la base de datos
   */
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

  /**
   * Metodo para eliminar un registro de la base de datos
   */
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

  /**
   * Metodo para eliminar una tabla en la base de datos
   */
  public void deleteTable(){
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();

    Query query = em.createNativeQuery("DROP TABLE Partidas CASCADE");
    query.executeUpdate();

    em.getTransaction().commit();
    em.close();
  }


}
