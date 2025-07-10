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
    // userModel - recebe os dados do usuário no corpo da requisição json via @RequestBody
        public ResponseEntity create(@RequestBody UserModel userModel) {
        
        // o username é para verificar existência do usuário, usando 
        var user = this.userREpository.findByUsername(userModel.getUsername());

       //verifica se o usuário já existe, se sim 400, se não segue
        if(user != null) {
            return ResponseEntity.status(400).body("BAD_REQUEST - Usuário já cadastrado");
            
        }

        var passwordHash = BCrypt.withDefaults()
        .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHash);

        //o objeeto userModel completo, com senha criptografada é salvo no bd - this.userREpository.save(userModel)
        var userCreated =  this.userREpository.save(userModel);
        return ResponseEntity.status(200).body(userCreated);
    }
}

