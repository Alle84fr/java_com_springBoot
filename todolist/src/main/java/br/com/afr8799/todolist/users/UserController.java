package br.com.afr8799.todolist.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

