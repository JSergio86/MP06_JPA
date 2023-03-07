package controller;

import model.Armas;
import model.Jugadores;
import model.Mapas;
import model.Partidas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
        System.out.println("Elige la tabla que desea eliminar un conjunto de registros. 1.Jugadores, 2.Mapas, 3.Armas, 4.Partidas");
        int opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                EntityManager em = entityManagerFactory.createEntityManager();
                System.out.println("¿Que idjugador quieres modificar?");
                int id = sc.nextInt();

                Jugadores jugador = em.find(Jugadores.class, id);

                    if (jugador != null) {
                        System.out.println("Escribe la columna que quiere modificar");
                        String columna = sc.nextLine();

                        switch (columna){
                            case "wins":
                                System.out.println("Escribe el nuevo valor");
                                String valor = sc.nextLine();
                                jugador.setWins(Integer.parseInt(valor));
                                break;

                            case "rank":
                                System.out.println("Escribe el nuevo valor");
                                String nuevoRank = sc.nextLine();
                                jugador.setRank(nuevoRank);
                                break;
                            case "kills":
                                System.out.println("Escribe el nuevo valor");
                                int nuevoKills = Integer.parseInt(sc.nextLine());
                                jugador.setKills(nuevoKills);
                                break;
                            case "deaths":
                                System.out.println("Escribe el nuevo valor");
                                int nuevoDeaths = Integer.parseInt(sc.nextLine());
                                jugador.setDeaths(nuevoDeaths);
                                break;
                            case "assists":
                                System.out.println("Escribe el nuevo valor");
                                int nuevoAssists = Integer.parseInt(sc.nextLine());
                                jugador.setAssists(nuevoAssists);
                                break;
                            case "scoreround":
                                System.out.println("Escribe el nuevo valor");
                                float nuevoScoreRound = Float.parseFloat(sc.nextLine());
                                jugador.setScoreround(nuevoScoreRound);
                                break;
                            case "kad":
                                System.out.println("Escribe el nuevo valor");
                                float nuevoKad = Float.parseFloat(sc.nextLine());
                                jugador.setKad(nuevoKad);
                                break;
                            case "killsround":
                                System.out.println("Escribe el nuevo valor");
                                float nuevoKillsRound = Float.parseFloat(sc.nextLine());
                                jugador.setKillsround(nuevoKillsRound);
                                break;
                            case "plants":
                                System.out.println("Escribe el nuevo valor");
                                int nuevoPlants = Integer.parseInt(sc.nextLine());
                                jugador.setPlants(nuevoPlants);
                                break;
                            case "firstbloods":
                                System.out.println("Escribe el nuevo valor");
                                int nuevoFirstbloods = Integer.parseInt(sc.nextLine());
                                jugador.setFirstbloods(nuevoFirstbloods);
                                break;
                            case "clutches":
                                System.out.println("Escribe el nuevo valor");
                                int nuevoClutches = Integer.parseInt(sc.nextLine());
                                jugador.setClutches(nuevoClutches);
                                break;
                            case "flawless":
                                System.out.println("Escribe el nuevo valor");
                                int nuevoFlawless = Integer.parseInt(sc.nextLine());
                                jugador.setFlawless(nuevoFlawless);
                                break;
                            case "aces":
                                System.out.println("Escribe el nuevo valor");
                                int nuevoAces = Integer.parseInt(sc.nextLine());
                                jugador.setAces(nuevoAces);
                                break;

                        }
                        em.getTransaction().begin();
                        em.flush();
                        em.getTransaction().commit();
                    }



                em.close();
                break;
            case 3:
                EntityManager emArma = entityManagerFactory.createEntityManager();
                System.out.println("¿Que idarma quieres modificar?");
                int idarma = sc.nextInt();

                System.out.println("Escribe el nuevo nombre del arma");
                String nombre = sc.nextLine();

                System.out.println("Escribe el nuevo tipo del arma");
                String tipo = sc.nextLine();

                // Modificar un registro concreto
                Armas arma = emArma.find(Armas.class, idarma);
                if (arma != null) {
                    arma.setName(nombre);
                    arma.setType(tipo);
                    emArma.getTransaction().begin();
                    emArma.flush();
                    emArma.getTransaction().commit();
                }

                emArma.close();
                break;
        }
        EntityManager em = entityManagerFactory.createEntityManager();
        System.out.println("¿Que idarma quieres modificar?");
        int idarma = sc.nextInt();

        System.out.println("Escribe el nuevo nombre del arma");
        String nombre = sc.nextLine();

        System.out.println("Escribe el nuevo tipo del arma");
        String tipo = sc.nextLine();

        // Modificar un registro concreto
        Armas arma = em.find(Armas.class, idarma);
        if (arma != null) {
            arma.setName(nombre);
            arma.setType(tipo);
            em.getTransaction().begin();
            em.flush();
            em.getTransaction().commit();
        }

        em.close();

    }

    public void eliminarRegistro(){
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            System.out.println("¿De que tabla quieres eliminar un registro? 1.Jugadores, 2.Mapas, 3.Armas, 4.Partidas");
            int tabla = sc.nextInt();

            switch (tabla){
                case 1:
                    System.out.println("¿Que idjugador quieres eliminar?");
                    int idjugador = sc.nextInt();

                    // Eliminar un registro concreto
                    Jugadores jugadorAEliminar = em.find(Jugadores.class, idjugador);
                    if (jugadorAEliminar != null) {
                        em.getTransaction().begin();
                        em.remove(jugadorAEliminar);
                        em.flush();
                        em.getTransaction().commit();
                    }
                    break;

                case 2:
                    System.out.println("¿Que idmapa quieres eliminar?");
                    int idmapa = sc.nextInt();

                    // Eliminar un registro concreto
                    Mapas mapaAEliminar = em.find(Mapas.class, idmapa);
                    if (mapaAEliminar != null) {
                        em.getTransaction().begin();
                        em.remove(mapaAEliminar);
                        em.flush();
                        em.getTransaction().commit();
                    }
                    break;

                case 3:
                    System.out.println("¿Que idarma quieres eliminar?");
                    int idarma = sc.nextInt();

                    // Eliminar un registro concreto
                    Armas armaAEliminar = em.find(Armas.class, idarma);
                    if (armaAEliminar != null) {
                        em.getTransaction().begin();
                        em.remove(armaAEliminar);
                        em.flush();
                        em.getTransaction().commit();
                    }
                    break;

                case 4:
                    System.out.println("¿Que idpartida quieres eliminar?");
                    int idpartida = sc.nextInt();

                    // Eliminar un registro concreto
                    Partidas partidaAEliminar = em.find(Partidas.class, idpartida);
                    if (partidaAEliminar != null) {
                        em.getTransaction().begin();
                        em.remove(partidaAEliminar);
                        em.flush();
                        em.getTransaction().commit();
                    }
                    break;
            }

            em.close();
        } catch (Exception e){
            System.out.println("No se pudo eliminar: "+ e);
        }

    }

    public void eliminarConjunto(){
        System.out.println("Elige la tabla que desea eliminar un conjunto de registros. 1.Jugadores, 2.Mapas, 3.Armas, 4.Partidas");
        int opcion = sc.nextInt();

        EntityManager em = entityManagerFactory.createEntityManager();
        System.out.println("Pon la columna donde desea eliminar el conjunto de registros ");
        switch (opcion){
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

        String columna = sc.next();


        System.out.println("Indica el operador que desea utilizar. Por ejemplo > <");
        String operador = sc.next();

        System.out.println("Indica el valor que desea eliminar");
        int valor = sc.nextInt();

        switch (opcion){
                case 1:
                    try {
                        // Obtener los registros a eliminar
                        Query query = em.createQuery("SELECT a FROM Jugadores a WHERE a." + columna + " " + operador + " :valor");
                        query.setParameter("valor", valor);
                        List<Jugadores> jugadoresAEliminar = query.getResultList();

                        // Eliminar los registros
                        em.getTransaction().begin();
                        for (Jugadores jugadores : jugadoresAEliminar) {
                            em.remove(jugadores);
                        }
                        em.flush();
                        em.getTransaction().commit();
                        System.out.println("Se ha eliminado correctamente");
                        System.out.println("\nTabla: Jugadores");
                        jugadoresController.listArticles();
                        Thread.sleep(1000);
                    }catch (Exception e){
                        System.out.println("Error, comprueba el operador y el valor que esten correctamente: "+e);
                    }
                    break;

                case 2:
                    try {
                    // Obtener los registros a eliminar
                    Query queryMapas = em.createQuery("SELECT a FROM Mapas a WHERE a." + columna + " " + operador + " :valor");
                    queryMapas.setParameter("valor", valor);
                    List<Mapas> mapasAEliminar = queryMapas.getResultList();

                    // Eliminar los registros
                    em.getTransaction().begin();
                    for (Mapas mapas : mapasAEliminar) {
                        em.remove(mapas);
                    }
                    em.flush();
                    em.getTransaction().commit();
                    System.out.println("Se ha eliminado correctamente");
                    System.out.println("\nTabla: Mapas");
                    mapasController.listArticles();
                    Thread.sleep(1000);
                    }catch (Exception e){
                        System.out.println("Error, comprueba el operador y el valor que esten correctamente: "+e);
                    }
                    break;

                case 3:
                    try {
                    // Obtener los registros a eliminar
                    Query queryArmas = em.createQuery("SELECT a FROM Armas a WHERE a." + columna + " " + operador + " :valor");
                    queryArmas.setParameter("valor", valor);
                    List<Armas> armasAEliminar = queryArmas.getResultList();

                    // Eliminar los registros
                    em.getTransaction().begin();
                    for (Armas armas : armasAEliminar) {
                        em.remove(armas);
                    }
                    em.flush();
                    em.getTransaction().commit();
                    System.out.println("Se ha eliminado correctamente");
                    System.out.println("\nTabla: Armas");
                    armasController.listArticles();
                    Thread.sleep(1000);
                    }catch (Exception e){
                        System.out.println("Error, comprueba el operador y el valor que esten correctamente: "+e);
                    }
                    break;

                case 4:
                    try {
                    // Obtener los registros a eliminar
                    Query queryPartidas = em.createQuery("SELECT a FROM Partidas a WHERE a." + columna + " " + operador + " :valor");
                    queryPartidas.setParameter("valor", valor);
                    List<Partidas> partidasAEliminar = queryPartidas.getResultList();

                    // Eliminar los registros
                    em.getTransaction().begin();
                    for (Partidas partidas : partidasAEliminar) {
                        em.remove(partidas);
                    }
                    em.flush();
                    em.getTransaction().commit();
                    System.out.println("Se ha eliminado correctamente");
                    System.out.println("\nTabla: Partidas");
                    partidasController.listArticles();
                    Thread.sleep(1000);
                    }catch (Exception e){
                        System.out.println("Error, comprueba el operador y el valor que esten correctamente: "+e);
                    }
                    break;

        }

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
