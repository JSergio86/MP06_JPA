import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import controller.ArmasController;
import controller.JugadoresController;
import controller.PartidasController;
import database.ConnectionFactory;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class Main {
  static SessionFactory sessionFactoryObj;

  private static SessionFactory buildSessionFactory() {
    try {
      StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
          .configure("hibernate.cfg.xml").build();
      Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
      return metadata.getSessionFactoryBuilder().build();

    } catch (HibernateException he) {
      System.out.println("Session Factory creation failure");
      throw he;
    }
  }

  public static EntityManagerFactory createEntityManagerFactory(){
    EntityManagerFactory emf;
    try {
      emf = Persistence. createEntityManagerFactory("JPAMagazines");
    } catch (Throwable ex) {
      System.err.println("Failed to create EntityManagerFactory object."+ ex);
      throw new ExceptionInInitializerError(ex);
    }
    return emf;
  }

  public static void main(String[] args) {
    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    Connection c = connectionFactory.connect();

    EntityManagerFactory entityManagerFactory = createEntityManagerFactory();

    PartidasController partidasController = new PartidasController(c, entityManagerFactory);
    JugadoresController jugadoresController = new JugadoresController(c, entityManagerFactory);
    ArmasController armasController = new ArmasController(c, entityManagerFactory);



    Menu menu = new Menu();
    int opcio;
    opcio = menu.mainMenu();

    switch (opcio) {



      case 1:
        System.out.println("1!!");
        try {
          List<Partidas> partidas = partidasController.readArticlesFile("src/main/resources/Partidas.csv");
          List<Jugadores> jugadores = jugadoresController.readArticlesFile("src/main/resources/Jugador.csv");
          List<Armas> armas = armasController.readArticlesFile("src/main/resources/Armas.csv");


          System.out.println("Armas\n");
          for(Armas arma: armas){
            armasController.addArmas(arma);

          }
          armasController.listArticles();


          System.out.println("\nJugadores\n");
          for(Jugadores jugador: jugadores){
            jugadoresController.addPartidas(jugador);

          }
          jugadoresController.listArticles();


          System.out.println("\nPartidas\n");
          for(Partidas partida: partidas){
            partidasController.addPartidas(partida);

          }
          partidasController.listArticles();

        } catch (NumberFormatException | IOException e) {

          e.printStackTrace();
        }
        break;

      case 2:
        System.out.println("hola");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createNativeQuery("DROP TABLE Armas CASCADE");
        query.executeUpdate();

        em.getTransaction().commit();
        em.close();
        break;

      default:
        System.out.println("Adeu!!");
        System.exit(1);
        break;


    }
  }
}

