
package br.com.afr8799.todolist.users;

public class UserModel {
    //Classe que irá receber criar o objeto contendo infromações do usuário 

    private String username;
    private String name;
    private String password;

    // método para inserir valor ao user name, que é privado
    public void setUsername(String username) {
        this.username = username;
    
        // this.username está se referindo a linha private String username (ln 7)
        // username é se referindo ao parâmetro String username - dado que virá
    }

    // para recuperar, buscar o dado
    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
