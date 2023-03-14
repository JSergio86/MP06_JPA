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

    /**
     * Método que permite insertar en las tablas a partir de los archivos csv.
     */
    public void insertarDatos() throws IOException {

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

    }

    /**
     * Este método lee la entrada del usuario para determinar qué tabla debe listarse y luego llama al método correspondiente del controlador correspondiente para listar los registros de esa tabla.
     */

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

    /**
     * Este método simplemente llama al método listArticles() de cada uno de los controladores para listar todos los registros de todas las tablas.
     */

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

    /**
     * Este método lee la entrada del usuario para determinar qué tabla y qué tipo de consulta se debe realizar, y luego llama al método correspondiente del controlador para realizar la consulta y mostrar los resultados.
     */

    public void consultasTablas() {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿En que tabla quieres consultar los registros?. 1.Jugadores, 2.Mapas, 3.Armas, 4.Partidas");
        int opcion = sc.nextInt();

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
        System.out.println();

        switch (opcion){
            case 1:
                System.out.println("Elige una opción para buscar en la tabla jugadores. 1.Por su rango, 2.Por sus wins mayor que, 3.Por su ID");
                int opcionJugadores = sc.nextInt();

                switch (opcionJugadores){
                    case 1:
                        jugadoresController.buscarJugadorPorRank();
                        break;
                    case 2:
                        jugadoresController.buscarJugadorPorWinsMayorQue();
                        break;
                    case 3:
                        jugadoresController.buscarJugadorPorId();
                        break;
                }
                break;

            case 2:
                System.out.println("Elige una opción para buscar en la tabla jugadores. 1.Por su nombre, 2.Por sus wins mayor que, 3.Por su ID");
                int opcionMapas = sc.nextInt();

                switch (opcionMapas){
                    case 1:
                        mapasController.buscarMapasPorNombre();
                        break;
                    case 2:
                        mapasController.buscarPorWinsMayorQue();
                        break;
                    case 3:
                        mapasController.buscarPorId();
                        break;
                }
                break;

            case 3:
                System.out.println("Elige una opción para buscar en la tabla armas. 1.Por su tipo, 2.Por sus ID mayor que, 3.Por su ID");
                int opcionArmas = sc.nextInt();

                switch (opcionArmas){
                    case 1:
                        armasController.buscarArmasPorTipo();
                        break;
                    case 2:
                        armasController.buscarPorIDMayorQue();
                        break;
                    case 3:
                        armasController.buscarPorId();
                        break;
                }

                break;

            case 4:
                System.out.println("Elige una opción para buscar en la tabla armas. 1.Por su tipo, 2.Por sus ID mayor que, 3.Por su ID");
                int opcionPartidas = sc.nextInt();

                switch (opcionPartidas){
                    case 1:
                        partidasController.buscarPorTipo();
                        break;
                    case 2:
                        partidasController.buscarPorIDMayorQue();
                        break;
                    case 3:
                        partidasController.buscarPorId();
                        break;
                }

                break;
        }


    }


    /**
     *  Este método lee la entrada del usuario para determinar qué tabla debe modificarse y luego llama al método correspondiente del controlador para modificar los registros de esa tabla.
     */

    public void modificarRegistro(){
        System.out.println("Elige la tabla que desea eliminar un conjunto de registros. 1.Jugadores, 2.Mapas, 3.Armas, 4.Partidas");
        int opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                jugadoresController.modificarRegistro();
                break;

            case 2:
                mapasController.modificarRegistro();
                break;

            case 3:
                armasController.modificarRegistro();
                break;

            case 4:
                partidasController.modificarRegistro();
                break;
        }

    }

    /**
     * Este método lee la entrada del usuario para determinar qué tabla debe eliminarse y luego llama al método correspondiente del controlador para eliminar un registro de esa tabla.
     */
    public void eliminarRegistro(){
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            System.out.println("¿De que tabla quieres eliminar un registro? 1.Jugadores, 2.Mapas, 3.Armas, 4.Partidas");
            int tabla = sc.nextInt();

            switch (tabla){
                case 1:
                    jugadoresController.eliminarRegistro();
                    break;

                case 2:
                    mapasController.eliminarRegistro();
                    break;

                case 3:
                    armasController.eliminarRegistro();
                    break;

                case 4:
                    partidasController.eliminarRegistro();
                    break;
            }

            em.close();
        } catch (Exception e){
            System.out.println("No se pudo eliminar: "+ e);
        }

    }

    /**
     * Este método lee la entrada del usuario para determinar qué tabla debe eliminarse y luego llama al método correspondiente del controlador para eliminar varios registros de esa tabla.
     */
    public void eliminarConjunto(){
        System.out.println("Elige la tabla que desea eliminar un conjunto de registros. 1.Jugadores, 2.Mapas, 3.Armas, 4.Partidas");
        int opcion = sc.nextInt();

        EntityManager em = entityManagerFactory.createEntityManager();
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
        System.out.println();
        System.out.println("Pon la columna donde desea eliminar el conjunto de registros ");
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

    /**
     * Metodo que permite borrar todas las tablas de la base de datos
     */

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

    public void eliminarUnaTabla() {
        try{
            System.out.println("Elige la tabla que desea eliminar. 1.Jugadores, 2.Mapas, 3.Armas, 4.Partidas");
            int opcion = sc.nextInt();
            switch (opcion){
                case 1:
                    jugadoresController.deleteTable();
                    break;
                case 2:
                    mapasController.deleteTable();
                    break;
                case 3:
                    armasController.deleteTable();
                    break;
                case 4:
                    partidasController.deleteTable();
                    break;
            }
            System.out.println("Tablas eliminadas correctamente");

        } catch (Exception e){
            System.out.println("Comprueba que las tablas esten creadas antes de eliminarlas. "+ e.getMessage());
        }

    }

}
