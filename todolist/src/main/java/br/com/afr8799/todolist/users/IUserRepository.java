package br.com.afr8799.todolist.users;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


//public interface IUserREpository extends JpaRepository<UserModel, UUID> {

public interface IUserRepository extends JpaRepository<UserModel, UUID> {

    // criar um método colocando findBy e o que se quer + tipo
    UserModel findByUsername(String username);

}

// ir para user controller