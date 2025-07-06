package br.com.afr8799.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.afr8799.todolist.users.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        var servletPath = request.getServletPath();

       
        if (servletPath.equals("/tasks/")) {

            var autoriza = request.getHeader("Authorization");

            var authEcoded = autoriza.substring("Basic".length()).trim();

            byte[] autoDecode = Base64.getDecoder().decode(authEcoded);

            var convetStr = new String(autoDecode);

            String[] credencials = convetStr.split(":");
            String username = credencials[0];
            String password = credencials[1];

                var usuario = this.userRepository.findByUsername(username);

                if (usuario == null) {
                    response.sendError(401);
                } else {
                    var passwVerify = BCrypt.verifyer().verify(password.toCharArray(), usuario.getPassword());
                    
                    if(passwVerify.verified) {

                        //forças que os atributos -HttpServletRequest request, HttpServletResponse response, FilterChain filterChain- set i id - e no controller recupere
                        //request é o que está vido e response o que está enviando para usuário
                        //request do http
                        //setar o atributo idusuario com o valor do id usuário
                        request.setAttribute("idUsuario", usuario.getId());
                        // ir para takcontroller
                        filterChain.doFilter(request, response);
                    } else {
                        response.sendError(401);
                    }
                }

        } else {
            
            filterChain.doFilter(request, response);
        }
    }
}

// post - localhost:8080/users/ - body - json - 200
// {
//      "username": "Ales",
//      "name": "ALes",
//      "password": "abacates"
// }
// retorno 200
// {
//     "id": "dd2ba891-12ae-4312-87a4-e1d6e1ce102a",
//     "username": "Ales",
//     "name": "ALes",
//     "password": "$2a$12$fxJ9qoMZqJJikx5ue0HTKeeNukl9.fv9qBoCXJqCcPGy3b/kHFpFm",
//     "createdAt": "2025-07-06T12:21:04.984118"
// }


//post - localhost:8080/tasks/
//tirar linha do id
// {
//     "descricao": "aprendendo java co rocketseat",
//     "titulo": "Cadastro tarefa",
//     "inicio": "2025-06-30T23:18:00",
//     "fim": "2025-07-01T00:18:00",
//     "prioridade": "média"
// }
// ir para // auth - basic - username(ale) - password (abacate) e só agora dar Send
// retorno apidog
// {
//     "id": "d40ad8fc-0972-4ff0-87d4-550a6a88cc37",
//     "idUsuario": null,
//     "descricao": "aprendendo java co rocketseat",
//     "titulo": "Cadastro tarefa",
//     "inicio": "2025-06-30T23:18:00",
//     "fim": "2025-07-01T00:18:00",
//     "prioridade": "média",
//     "createdAt": "2025-07-06T12:22:03.754491"
// }
// retorno terminal
// Chegou cno controller dd2ba891-12ae-4312-87a4-e1d6e1ce102a

// id do terminal e do user são iguais e não muda 
// "id": "dd2ba891-12ae-4312-87a4-e1d6e1ce102a",
//        dd2ba891-12ae-4312-87a4-e1d6e1ce102a

// o id do task é diferente, até o momento - 4.18 da aula, aqui o id não foi setado no taskmolde