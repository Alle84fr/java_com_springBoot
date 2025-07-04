package br.com.afr8799.todolist.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping ("/users")


public class UserController {
    
    @Autowired
    private IUserRepository userREpository;

   
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        
        var user = this.userREpository.findByUsername(userModel.getUsername());

       
        if(user != null) {
            return ResponseEntity.status(400).body("BAD_REQUEST - Usuário já cadastrado");
            
        }

        var passwordHash = BCrypt.withDefaults()
        .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHash);

        var userCreated =  this.userREpository.save(userModel);
        return ResponseEntity.status(200).body(userCreated);
    }
}

// ao dar send - com o filtertaskAuth feito - aula 16 - dá 200 e chegou no filtro 
// lembrando que é :
// post -> localhost:8080/users/
// body - json
// {
//     "username": "Ale",
//     "name": "ALessa",
//     "password": "abacate"
// }
//retorno do id = 8952e1a3-2025-4616-9551-069f58d103c7