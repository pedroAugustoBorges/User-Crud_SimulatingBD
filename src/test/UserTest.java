package test;

import entites.User;
import services.UserServices;

import java.io.Console;
import java.util.*;
import java.util.function.Predicate;

public class UserTest {

    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            return;
        }

        List<User> users = new ArrayList<>();
        Set<Integer> validOptions = Set.of(1, 2, 3, 4, 5);
        UserServices userServices = new UserServices();

        int opcao = 0;
        while (opcao != 5) {
            opcao = getValidOption(console, validOptions);

            switch (opcao) {
                case 1:
                    User user = createUser(console, userServices);
                    addToList(users, user);
                    break;

                case 2:
                    readUsers(users);
                    break;

                case 3:
                    updateUser(users, console, userServices);
                    break;

                case 4:
                    deleteUser(users, console);
                    break;

                case 5:
                    break;
            }

        }
    }

    private static int getValidOption(Console console, Set<Integer> validOptions) {
        int opcao = -1;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.println("Escolha a opcão:");
                showMenu();
                String input = console.readLine();
                opcao = Integer.parseInt(input);
                System.out.println();
                if (validOptions.contains(opcao)) {
                    valid = true;
                } else {
                    System.out.println("Opção invalida! Insira corretamente");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, insira um numero");
            }
        }

        return opcao;
    }

    private static void showMenu() {
        System.out.println("1 - Criar usuario: ");
        System.out.println("2 - Imprimir usuario:");
        System.out.println("3 - Atualizar usuario: ");
        System.out.println("4 - Deletar usuario");
        System.out.println("5 - Encerrar programa");
    }

    private static User createUser(Console console, UserServices userServices) {

        System.out.println("Insira as seguintes informaçoes: ");

        System.out.println("Name: ");
        String name = console.readLine().trim();

        System.out.println("Email: ");
        String email = console.readLine().trim();

        System.out.println("Data de nascimento (dd/MM/yyyy): ");
        String dateInput = console.readLine().trim();

        System.out.println("Password: ");
        char[] passwordArray = console.readPassword();
        String password = new String(passwordArray).trim();

        System.out.println("Confirm password: ");
        char[] passwordConfirmArray = console.readPassword();
        String passwordConfirm = new String(passwordConfirmArray).trim();

        try {
            return userServices.createUser(name, email, password, dateInput, passwordConfirm);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void readUsers(List<User> users) {

        if (users.isEmpty()) {
            throw new NoSuchElementException("Lista vazia. Nenhum usuario encontrado");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void deleteUser(List<User> users, Console console) {

        if (users.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }


        System.out.println("Insira o nome do user a ser removido: ");
        String nome = console.readLine().trim();

        boolean exists = users.stream().anyMatch(byName(nome));
        if (!exists) {
            throw new NoSuchElementException("User inexistente");
        }


        users.removeIf(byName(nome));

        System.out.println("Usuario" + nome + "removido com sucesso!");
    }

    private static void updateUser(List<User> users, Console console, UserServices userServices) {

        if (users.isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        System.out.println("Insira o nome do user a ser atualizado: ");
        String nome = console.readLine().trim();
        System.out.flush();


        Optional<User> userFound = users
                .stream()
                .filter(byName(nome))
                .findFirst();


        if (userFound.isEmpty()) {
            throw new NoSuchElementException("Usuario ou nome não correspondente");
        }
        System.out.println("Usuario existente!");

        User user = createUser(console, userServices);



        //pegando o usuario com base no index para o set
        int indexOf = users.indexOf(userFound.get());

        //seta o usuario
        users.set(indexOf, user);
        System.out.println("Usuario atualizado com sucesso!");
        System.out.println(users);
    }

    private static void addToList(List<User> users, User user) {
        if (user != null) {
            users.add(user);
        }
    }

    private static Predicate<User> byName(String name) {
        return user -> user.getName().equals(name);
    }
}
