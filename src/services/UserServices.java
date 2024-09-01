package services;

import entites.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserServices {


    public User createUser(String nome, String email, String password, String ld, String passwordConfirm) {

        if(!EmailValidatorService.isValidEmail(email)){
            throw new IllegalArgumentException("Email Invalido!");
        }

        LocalDate birthday = null;

        try{

            birthday = LocalDate.parse(ld, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Formato de data incorreto. Insira em dd/MM/yyyy");
        }

        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("Senhas não coincidem");
        }

        return new User(nome, email, password, birthday);
    }
}
