package controller;

import model.Armas;
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

public class ArmasController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;

    private JugadoresController jugadoresController = new JugadoresController(connection);

    public ArmasController(Connection connection) {
        this.connection = connection;
    }

    public ArmasController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Permite leer los archivos csv y crear un objeto con la información del archivo
     * @param armasFile Pasar la ruta del archivo csv
     * @return
     * @throws IOException
     */
    public List<Armas> readArticlesFile(String armasFile) throws IOException {
        int idarma;
        String name;
        String type;

        BufferedReader br = new BufferedReader(new FileReader(armasFile));
        String linea = "";
        List<Armas> armasList = new ArrayList<>();
        List<Jugadores> jugadoresList = jugadoresController.readArticlesFile("src/main/resources/Jugador.csv");


        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            idarma = Integer.parseInt(str.nextToken());
            int idjugador = Integer.parseInt(str.nextToken());
            name = str.nextToken();
            type = str.nextToken();

            armasList.add(new Armas(idarma,jugadoresList.get(idjugador - 1), name, type));

        }
        br.close();

        return armasList;
    }


    /**
     * Metodo para añadir la información en la tabla
     * @param armas
     */
    public void addArmas(Armas armas) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(armas);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Metodo para listar toda la tabla y imprimirlo por pantalla
     */
    public void listArticles() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Armas> result = em.createQuery("from Armas", Armas.class).getResultList();
        for (Armas armas : result) {
            System.out.println(armas.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    public void buscarArmasPorTipo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Tipo del arma que quieres buscar");
        String tipo = sc.next();
        List<Armas> lista= listajugadoresRank(tipo);
        for (Armas arma : lista) {
            System.out.println(arma.toString());
        }
    }

    public List<Armas> listajugadoresRank(String tipo) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Armas> query = em.createQuery("SELECT j FROM Armas j WHERE j.type LIKE :tipo", Armas.class);
        query.setParameter("tipo", "%" + tipo + "%");
        return query.getResultList();
    }
    public void buscarPorIDMayorQue(){
        Scanner sc = new Scanner(System.in);
        System.out.println("ID mayor que: ");
        int id = sc.nextInt();
        List<Armas> lista= listaJugadorIDs(id);
        for (Armas arma : lista) {
            System.out.println(arma.toString());
        }
    }

    public List<Armas> listaJugadorIDs(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Armas> query = em.createQuery("SELECT j FROM Armas j WHERE j.idarma > :id", Armas.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
    public void buscarPorId(){
        Scanner sc = new Scanner(System.in);
        System.out.println("ID del arma que quieres buscar");
        int id = sc.nextInt();
        Armas lista= listaId(id);
        System.out.println(lista.toString());
    }

    public Armas listaId(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.find(Armas.class, id);
    }

    public void modificarRegistro() {
        Scanner sc = new Scanner(System.in);
        EntityManager em = entityManagerFactory.createEntityManager();
        listArticles();
        System.out.println("\n¿Que idarma quieres modificar?");
        int id = sc.nextInt();

        Armas arma = em.find(Armas.class, id);

        if (arma != null) {
            System.out.println("Escribe la columna que quiere modificar");
            String columna = sc.next();

            sc.nextLine();

            System.out.println("Escribe el nuevo valor");
            String valor = sc.nextLine();

            switch (columna){
                case "name":
                    arma.setName(valor);
                    break;

                case "type":
                    arma.setType(valor);
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

    public void eliminarRegistro() {
        EntityManager em = entityManagerFactory.createEntityManager();
        Scanner sc = new Scanner(System.in);
        listArticles();
        System.out.println("\n¿Que idjugador quieres eliminar?");
        int idarma = sc.nextInt();

        // Eliminar un registro concreto
        Armas armaAEliminar = em.find(Armas.class, idarma);
        if (armaAEliminar != null) {
            em.getTransaction().begin();
            em.remove(armaAEliminar);
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

        Query query = em.createNativeQuery("DROP TABLE Armas CASCADE");
        query.executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
}
