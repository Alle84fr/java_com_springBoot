
package br.com.afr8799.todolist.users;

import lombok.Data;

//define getter e setter juntos
@Data
public class UserModel {
    //Classe que irá receber criar o objeto contendo infromações do usuário 

    private String username;
    private String name;
    private String password;


}
