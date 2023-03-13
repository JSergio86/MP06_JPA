package controller;

import model.Jugadores;
import model.Partidas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class JugadoresController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;


    public JugadoresController(Connection connection) {
        this.connection = connection;
    }

    public JugadoresController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Permite leer los archivos csv y crear un objeto con la información del archivo
     * @param jugadoresFile Pasar la ruta del archivo csv
     * @return
     * @throws IOException
     */

    public List<Jugadores> readArticlesFile(String jugadoresFile) throws IOException {
        int idjugador;
        String rank;
        int wins;
        int kills;
        int deaths;
        int assists;
        float scoreround;
        float kad;
        float killsround;
        int plants;
        int firstbloods;
        int clutches;
        int flawless;
        int aces;

        BufferedReader br = new BufferedReader(new FileReader(jugadoresFile));
        String linea = "";
        List<Jugadores> jugadoresList = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            idjugador = Integer.parseInt(str.nextToken());
            rank = str.nextToken();
            wins = Integer.parseInt(str.nextToken());
            kills = Integer.parseInt(str.nextToken());
            deaths = Integer.parseInt(str.nextToken());
            assists = Integer.parseInt(str.nextToken());
            scoreround = Float.parseFloat(str.nextToken());
            kad = Float.parseFloat(str.nextToken());
            killsround = Float.parseFloat(str.nextToken());
            plants = Integer.parseInt(str.nextToken());
            firstbloods = Integer.parseInt(str.nextToken());
            clutches = Integer.parseInt(str.nextToken());
            flawless = Integer.parseInt(str.nextToken());
            aces = Integer.parseInt(str.nextToken());

            jugadoresList.add(new Jugadores(idjugador, rank, wins, kills, deaths, assists, scoreround, kad, killsround, plants, firstbloods, clutches, flawless, aces));

        }
        br.close();

        return jugadoresList;
    }

    /**
     * Metodo para añadir la información en la tabla
     * @param jugadores
     */
    public void addPartidas(Jugadores jugadores) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(jugadores);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Metodo para listar toda la tabla y imprimirlo por pantalla
     */
    public void listArticles() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Jugadores> result = em.createQuery("from Jugadores", Jugadores.class).getResultList();
        for (Jugadores jugadores : result) {
            System.out.println(jugadores.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    public void buscarJugadorPorRank(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Rango del jugador que quieres buscar");
        String rango = sc.next();
        List<Jugadores> jugadoresList= listajugadoresRank(rango);
        for (Jugadores jugador : jugadoresList) {
            System.out.println(jugador.toString());
        }
    }

    public List<Jugadores> listajugadoresRank(String rank) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Jugadores> query = em.createQuery("SELECT j FROM Jugadores j WHERE j.rank LIKE :rank", Jugadores.class);
        query.setParameter("rank", "%" + rank + "%");
        return query.getResultList();
    }
    public void buscarJugadorPorWinsMayorQue(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Wins mayor que: ");
        int wins = sc.nextInt();
        List<Jugadores> jugadoresList= listaJugadorWins(wins);
        for (Jugadores jugador : jugadoresList) {
            System.out.println(jugador.toString());
        }
    }

    public List<Jugadores> listaJugadorWins(int wins) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Jugadores> query = em.createQuery("SELECT j FROM Jugadores j WHERE j.wins > :wins", Jugadores.class);
        query.setParameter("wins", wins);
        return query.getResultList();
    }
    public void buscarJugadorPorId(){
        Scanner sc = new Scanner(System.in);
        System.out.println("ID del jugador que quieres buscar");
        int id = sc.nextInt();
        Jugadores jugadoresList= listaJugadorId(id);
        System.out.println(jugadoresList.toString());
    }

    public Jugadores listaJugadorId(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.find(Jugadores.class, id);
    }

    /**
     * Metodo para modificar un registro de la base de datos
     */
    public void modificarRegistro() {
        Scanner sc = new Scanner(System.in);
        EntityManager em = entityManagerFactory.createEntityManager();
        listArticles();
        System.out.println("\n¿Que idjugador quieres modificar?");
        int id = sc.nextInt();

        Jugadores jugador = em.find(Jugadores.class, id);

        if (jugador != null) {
            System.out.println("Escribe la columna que quiere modificar");
            String columna = sc.next();

            sc.nextLine();

            System.out.println("Escribe el nuevo valor");
            String valor = sc.nextLine();

            switch (columna){
                case "wins":
                    jugador.setWins(Integer.parseInt(valor));
                    break;

                case "rank":
                    jugador.setRank(valor);
                    break;

                case "kills":
                    jugador.setKills(Integer.parseInt(valor));
                    break;

                case "deaths":
                    jugador.setDeaths(Integer.parseInt(valor));
                    break;

                case "assists":
                    jugador.setAssists(Integer.parseInt(valor));
                    break;

                case "scoreround":
                    jugador.setScoreround(Float.parseFloat(valor));
                    break;

                case "kad":
                    jugador.setKad(Float.parseFloat(valor));
                    break;

                case "killsround":
                    jugador.setKillsround(Float.parseFloat(valor));
                    break;

                case "plants":
                    jugador.setPlants(Integer.parseInt(valor));
                    break;

                case "firstbloods":
                    jugador.setFirstbloods(Integer.parseInt(valor));
                    break;

                case "clutches":
                    jugador.setClutches(Integer.parseInt(valor));
                    break;

                case "flawless":
                    jugador.setFlawless(Integer.parseInt(valor));
                    break;

                case "aces":
                    jugador.setAces(Integer.parseInt(valor));
                    break;

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
        System.out.println("\n¿Que idjugador quieres eliminar?");
        int idjugador = sc.nextInt();

        // Eliminar un registro concreto
        Jugadores jugadorAEliminar = em.find(Jugadores.class, idjugador);
        if (jugadorAEliminar != null) {
            em.getTransaction().begin();
            em.remove(jugadorAEliminar);
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

        Query query = em.createNativeQuery("DROP TABLE Jugadores CASCADE");
        query.executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
}
