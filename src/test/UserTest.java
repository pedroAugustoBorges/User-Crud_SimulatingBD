package test;


import entites.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;


public class UserTest {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<User> users = new ArrayList<>();

        System.out.println("Escolha a opção: ");


        int opcao = 0;
        while (opcao != 6) {
            showMenu();
            opcao = input.nextInt();


            switch (opcao) {
                case 1:
                    addToList(users, createUser(input));
                    break;

                case 2:
                    readUsers(users);

                case 3:
                    updateUser(users, input);
                    break;

                case 4:
                    deleteUser(users, input);
                    break;
            }

        }
    }

    private static void showMenu() {
        System.out.println("1 - Criar usuario: ");
        System.out.println("2- Imprimir usuario:");
        System.out.println("4- Atualizar usuario: ");
        System.out.println("3- Deletar usuario");
    }

    private static User createUser(Scanner input) {

        System.out.println("Insira as seguintes informaçoes: ");

        System.out.println("Name: ");
        input.nextLine();
        String name = input.nextLine();


        System.out.println("Email: ");
        String email = input.nextLine();

        System.out.println("Data de nascimento (dd/MM/yyyy): ");
        String dateInput = input.nextLine();
        LocalDate birthday = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("Password: ");
        String password = input.nextLine();


        System.out.println("Confirm password ");
        String passwordConfirm = input.nextLine();

        if (!password.equalsIgnoreCase(passwordConfirm)) {
            throw new IllegalArgumentException("Senhas não coincidem");
        }

        User user = new User(name, email, password, birthday);
        System.out.println(user);

        return user;

    }

    private static void readUsers(List<User> users) {

        if (users.isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        } else {
            users.forEach(System.out::println);
        }

    }

    private static void deleteUser(List<User> users, Scanner input) {

        if (users.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        System.out.println("Insira o nome do user a ser removido: ");
        input.nextLine();
        String nome = input.nextLine();

        boolean exists = users.stream().
                anyMatch(byName(nome));
        if (!exists) {
            throw new NoSuchElementException("User inexistente");
        }

        users.removeIf(byName(nome));
    }

    private static void updateUser(List<User> users, Scanner input) {

        if (users.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        System.out.println("Insira o nome do user a ser atualizado: ");

        input.nextLine();
        String nome = input.nextLine();

        Optional<User> userFound = users
                .stream()
                .filter(byName(nome))
                .findFirst();


        if (userFound.isEmpty()) {
            throw new NoSuchElementException("Usuario ou nome não correspondente");
        }


        System.out.println("Usuario existente!");


        //pegando o usuario com base no index para o set
        int indexOf = users.indexOf(userFound.get());

        User user = createUser(input);

        //seta o usuario
        users.set(indexOf, user);
        System.out.println("Usuario inserido com sucesso!");
        System.out.println(users);

    }

    private static void addToList(List<User> users, User user) {
        users.add(user);
    }

    private static Predicate<User> byName(String name) {
        return user -> user.getName().equals(name);
    }
}
