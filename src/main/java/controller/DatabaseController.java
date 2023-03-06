package controller;

import model.Armas;
import model.Jugadores;
import model.Mapas;
import model.Partidas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DatabaseController {
    private Connection connection;
    EntityManagerFactory entityManagerFactory;
    PartidasController partidasController;
    JugadoresController jugadoresController;
    ArmasController armasController;
    MapasController mapasController;
    Scanner sc = new Scanner(System.in);

    public DatabaseController(Connection connection) {
        this.connection = connection;
    }

    public DatabaseController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
        this.partidasController = new PartidasController(connection, entityManagerFactory);
        this.jugadoresController = new JugadoresController(connection, entityManagerFactory);
        this.armasController = new ArmasController(connection, entityManagerFactory);
        this.mapasController = new MapasController(connection, entityManagerFactory);
    }

    /**
     * Método que permite crear las tablas a partir de un archivo "schema.sql".
     * Si no existe el archivo o hay un error en el proceso, se muestra un mensaje de error.
     */
    public void crearTablas() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/schema.sql"))) {
            PreparedStatement pr = connection.prepareStatement(br.lines().collect(Collectors.joining(" \n")));
            pr.execute();
            System.out.println("Se han creado correctamente");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Comprueba el fichero schema.sql: " + e.getMessage());
        }
    }

    public void insertarDatos() throws IOException {
        try {
            List<Partidas> partidas = partidasController.readArticlesFile("src/main/resources/Partidas.csv");
            List<Jugadores> jugadores = jugadoresController.readArticlesFile("src/main/resources/Jugador.csv");
            List<Armas> armas = armasController.readArticlesFile("src/main/resources/Armas.csv");
            List<Mapas> mapas = mapasController.readArticlesFile("src/main/resources/Mapas.csv");

            for (Armas arma : armas) {
                armasController.addArmas(arma);
            }

            for (Mapas mapa : mapas) {
                mapasController.addMapas(mapa);
            }

            for (Jugadores jugador : jugadores) {
                jugadoresController.addPartidas(jugador);
            }

            for (Partidas partida : partidas) {
                partidasController.addPartidas(partida);
            }
            System.out.println("Se han insertado correctamente");

        }catch (Exception e) {
            System.out.println("Comprueba que esten creadas las tablas: " + e.getMessage());
        }
    }

    public void listarUnaTabla(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Escribe un numero para listar la tabla para poder listarla: 1.Jugadores, 2.Mapas, 3.Armas, 4.Partidas");
        int eleccion = sc.nextInt();

        switch (eleccion){
            case 1:
                System.out.println("\nTabla: Jugadores");
                jugadoresController.listArticles();
                break;
            case 2:
                System.out.println("\nTabla: Mapas");
                mapasController.listArticles();
                break;
            case 3:
                System.out.println("\nTabla: Armas");
                armasController.listArticles();
                break;
            case 4:
                System.out.println("\nTabla: Partidas");
                partidasController.listArticles();
                break;
        }

    }

    public void listarTablas(){
        System.out.println("Tabla: Armas");
        armasController.listArticles();

        System.out.println("\nTabla: Mapas");
        mapasController.listArticles();


        System.out.println("\nTabla: Jugadores");
        jugadoresController.listArticles();


        System.out.println("\nTabla: Partidas");
        partidasController.listArticles();
    }

    public void modificarRegistro(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("¿Que idarma quieres eliminar?");
        int idarma = sc.nextInt();

        Armas arma = entityManager.find(Armas.class, idarma);

        System.out.println("¿Que nuevo nombre quieres poner?");
        String nombre = sc.nextLine();

        arma.setName(nombre);

        // y así sucesivamente para todos los campos que desees modificar
        entityManager.merge(arma);
        entityManager.getTransaction().commit();



    }

    public void eliminarRegistro(){
        EntityManager em = entityManagerFactory.createEntityManager();

        System.out.println("¿Que idarma quieres eliminar?");
        int idarma = sc.nextInt();
        /*
        // Modificar un registro concreto
        Armas arma = em.find(Armas.class, idarma);
        if (arma != null) {
            arma.setPropiedad("nuevoValor");
            em.getTransaction().begin();
            em.flush();
            em.getTransaction().commit();
        }

         */

        // Eliminar un registro concreto
        Armas armaAEliminar = em.find(Armas.class, idarma);
        if (armaAEliminar != null) {
            em.getTransaction().begin();
            em.remove(armaAEliminar);
            em.flush();
            em.getTransaction().commit();
        }

        em.close();

    }

    public void eliminarTablas() {
        try{
            partidasController.deleteTable();
            jugadoresController.deleteTable();
            armasController.deleteTable();
            mapasController.deleteTable();
            System.out.println("Tablas eliminadas correctamente");

        } catch (Exception e){
            System.out.println("Comprueba que las tablas esten creadas antes de eliminarlas. "+ e.getMessage());
        }

    }

}
