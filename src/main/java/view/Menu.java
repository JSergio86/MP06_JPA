package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    private int option;

    public Menu() {
        super();
    }

    public int mainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.println(" \nMENU PRINCIPAL \n");
            System.out.println("1. Crear tablas");
            System.out.println("2. Rellenar tablas");
            System.out.println("3. Borrar tablas");
            System.out.println("4. Listar una columna");
            System.out.println("5. Listar tabla");
            System.out.println("6. Listar texto/num concreto");
            System.out.println("7. Listar condicion");
            System.out.println("8. Modificar registro");
            System.out.println("9. Eliminar un registro");
            System.out.println("10. Eliminar un conjunto de registros");
            System.out.println("11. Eliminar una tabla");
            System.out.println("12. Sortir");
            System.out.println("Escoge una opcion: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no valido");
                e.printStackTrace();

            }

        } while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6 && option != 7
                && option != 8 && option != 9 && option != 10 && option != 11 && option != 12);

        return option;
    }






}