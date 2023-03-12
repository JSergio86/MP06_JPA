package controller;

import model.Armas;
import model.Mapas;
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
   * Permite leer los archivos csv y crear un objeto con la información del archivo
   * @param mapasFile Pasar la ruta del archivo csv
   * @return
   * @throws IOException
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

  /**
   * Metodo para añadir la información en la tabla
   * @param mapas
   */
  /* Method to CREATE a Partidas in the database */
  public void addMapas(Mapas mapas) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(mapas);
    em.getTransaction().commit();
    em.close();
  }
  /**
   * Metodo para listar toda la tabla y imprimirlo por pantalla
   */
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

  public void buscarMapasPorNombre(){
    Scanner sc = new Scanner(System.in);
    System.out.println("Nombre del mapa que quieres buscar");
    String tipo = sc.next();
    List<Mapas> lista= listaMapasNombre(tipo);
    for (Mapas arma : lista) {
      System.out.println(arma.toString());
    }
  }

  public List<Mapas> listaMapasNombre(String tipo) {
    EntityManager em = entityManagerFactory.createEntityManager();
    TypedQuery<Mapas> query = em.createQuery("SELECT j FROM Mapas j WHERE j.name LIKE :tipo", Mapas.class);
    query.setParameter("tipo", "%" + tipo + "%");
    return query.getResultList();
  }
  public void buscarPorWinsMayorQue(){
    Scanner sc = new Scanner(System.in);
    System.out.println("Wins mayor que: ");
    int id = sc.nextInt();
    List<Mapas> lista= listaWins(id);
    for (Mapas mapa : lista) {
      System.out.println(mapa.toString());
    }
  }

  public List<Mapas> listaWins(int id) {
    EntityManager em = entityManagerFactory.createEntityManager();
    TypedQuery<Mapas> query = em.createQuery("SELECT j FROM Mapas j WHERE j.wins > :id", Mapas.class);
    query.setParameter("id", id);
    return query.getResultList();
  }
  public void buscarPorId(){
    Scanner sc = new Scanner(System.in);
    System.out.println("ID del mapa que quieres buscar");
    int id = sc.nextInt();
    Mapas lista= listaId(id);
    System.out.println(lista.toString());
  }

  public Mapas listaId(int id) {
    EntityManager em = entityManagerFactory.createEntityManager();
    return em.find(Mapas.class, id);
  }

  /* Method to UPDATE activity for an Article */
  public void modificarRegistro() {
    Scanner sc = new Scanner(System.in);
    EntityManager em = entityManagerFactory.createEntityManager();
    listArticles();
    System.out.println("\n¿Qué idmapa quieres modificar?");
    int id = sc.nextInt();

    Mapas mapa = em.find(Mapas.class, id);

    if (mapa != null) {
      System.out.println("Escribe la columna que quieres modificar:");
      String columna = sc.next();

      sc.nextLine();

      System.out.println("Escribe el nuevo valor:");
      String valor = sc.nextLine();

      switch (columna) {
        case "name":
          mapa.setName(valor);
          break;

        case "porcentaje_win":
          mapa.setPorcentaje_win(valor);
          break;

        case "wins":
          mapa.setWins(Integer.parseInt(valor));
          break;

        case "losses":
          mapa.setLosses(Integer.parseInt(valor));
          break;

        case "kd":
          mapa.setKd(Float.parseFloat(valor));
          break;

        case "adr":
          mapa.setAdr(Float.parseFloat(valor));
          break;

        case "acs":
          mapa.setAcs(Float.parseFloat(valor));
          break;

        default:
          System.out.println("La columna no es válida.");
          em.close();
          return;
      }

      em.getTransaction().begin();
      em.flush();
      em.getTransaction().commit();
      System.out.println("El registro ha sido modificado correctamente.");
      listArticles();
      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    } else {
      System.out.println("No se encontró el mapa con id " + id);
    }

    em.close();
  }

  /* Method to DELETE an Article from the records */
  public void eliminarRegistro() {
    EntityManager em = entityManagerFactory.createEntityManager();
    Scanner sc = new Scanner(System.in);
    listArticles();
    System.out.println("\n¿Que idmapa quieres eliminar?");
    int idmapa = sc.nextInt();

    // Eliminar un registro concreto
    Mapas mapaAEliminar = em.find(Mapas.class, idmapa);
    if (mapaAEliminar != null) {
      em.getTransaction().begin();
      em.remove(mapaAEliminar);
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

    Query query = em.createNativeQuery("DROP TABLE Mapas CASCADE");
    query.executeUpdate();

    em.getTransaction().commit();
    em.close();
  }


}
