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
        while(opcao != 6){
            showMenu();
            opcao = input.nextInt();


            switch (opcao){
                case 1: addUser(users, input);
                    break;

                case 2:
                    removeUser(users, input);
                    break;

            }

        }
        }


    private static void showMenu (){
        System.out.println("1 - Inserir usuario: ");
        System.out.println("2- Remover usuario:");
        System.out.println("3- Deletar usuario");
        System.out.println("4- Atualizar usuario: ");
    }

    private static void addUser (List<User> list, Scanner input){

        System.out.println("Insira as seguintes informaçoes: ");


        System.out.println("Name: ");
        input.nextLine();
        String name = input.nextLine();


        System.out.println("Email: ");
        String email = input.nextLine();

        System.out.println("Data de nascimento (dd/MM/yyyy): ");
        String dateInput = input.nextLine();
        LocalDate birthday = LocalDate.parse( dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("Password: ");
        String password = input.nextLine();


        System.out.println("Confirm password ");
        String passwordConfirm = input.nextLine();


        Predicate<String> passwordMatches = confirm -> passwordConfirm.equals(password);

       if (!passwordMatches.test(passwordConfirm)){
           throw new IllegalArgumentException("Senhas não coincidem");
       }


        User user = new User(name, email, password, birthday);
        System.out.println(user);
        list.add(user);

    }

    private static void removeUser (List<User> users, Scanner input){

        if(users.isEmpty()){
            throw new NoSuchElementException("List is empty");
        }

        System.out.println("Insira o nome do user a ser removido: ");
        input.nextLine();
        String nome = input.nextLine();

        boolean exists = users.stream().
                anyMatch(byName(nome));
        if (!exists){
            throw new NoSuchElementException("User inexistente");
        }

        users.removeIf(byName(nome));
    }

     private static Predicate<User> byName (String name){
        return user -> user.getName().equals(name);
     }
}
