import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import controller.*;
import database.ConnectionFactory;
import model.Jugadores;
import model.Mapas;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


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

  public static void main(String[] args) throws IOException, InterruptedException {
    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    Connection c = connectionFactory.connect();

    EntityManagerFactory entityManagerFactory = createEntityManagerFactory();

    DatabaseController databaseController = new DatabaseController(c, entityManagerFactory);

    Menu menu = new Menu();
    int opcio;
    opcio = menu.mainMenu();

    while (opcio > 0 && opcio < 13) {
      switch (opcio) {
        case 1:
          databaseController.crearTablas();
          break;

        case 2:
          databaseController.insertarDatos();
          break;

        case 3:
          databaseController.eliminarTablas();
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          break;

        case 4:
          databaseController.listarUnaTabla();
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          break;

        case 5:
          databaseController.listarTablas();
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          break;
        case 6:
          databaseController.consultasTablas();
          break;

        case 7:
          databaseController.modificarRegistro();
          break;

        case 8:
          databaseController.eliminarRegistro();
          break;

        case 9:
          databaseController.eliminarConjunto();
          break;

        case 10:
          databaseController.eliminarUnaTabla();
          break;



        default:
          System.out.println("Adeu!!");
          System.exit(1);
          break;


      }
      opcio = menu.mainMenu();
    }
  }
}

