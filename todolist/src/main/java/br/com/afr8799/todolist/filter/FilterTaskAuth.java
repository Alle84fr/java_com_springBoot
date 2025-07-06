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
public class FilterTaskAuth extends OncePerRequestFilter{


    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

            // antes de qualquer coisa fazer verificação de rotas
            var servletPath = request.getServletPath();

            //se for igual "/tasks - faz toda opera já escrita
            if(servletPath.equals("/tasks")) {

                var autoriza = request.getHeader("Authorization");
                    
                var authEcoded = autoriza.substring("Basic".length()).trim();


                byte[] autoDecode = Base64.getDecoder().decode(authEcoded);
                

                var convetStr = new String(autoDecode);
                    

                String[] credencials = convetStr.split(":");
                String username = credencials[0];
                String password = credencials[1];

                var usuario = this.userRepository.findByUsername(username);

                if(usuario == null) {
                        response.sendError(401);
                } else {
                    var passwVerify = BCrypt.verifyer().verify(password.toCharArray(), usuario.getPassword());

                    if(passwVerify.verified) {

                        filterChain.doFilter(request, response);
                    } else {
                        response.sendError(401);
                    }
                }

                    var senha = this.userRepository.findByUsername(password);

            } else {
                filterChain.doFilter(request, response);
            }
        }
}

// post - localhost:8080/users/ - body - json - 200
// {
//      "username": "Ale",
//      "name": "ALessa",
//      "password": "abacate"
// }

// post - localhost:8080/tasks/ - Auth - Basic Auth(type) - Ale (username) - abacate  (password) - 200
// {
//     "idUsuario": "24134a11-a4b4-4494-9b60-3c119cacd398",
//     "descricao": "aprendendo java co rocketseat",
//     "titulo": "Cadastro tarefa",
//     "inicio": "2025-06-30T23:18:00",
//     "fim": "2025-07-01T00:18:00",
//     "prioridade": "média"
// }
// // post - localhost:8080/tasks/ - Auth - Basic Auth(type) - Ale (username) - senhaErrada  (password) - 401
//aqui dá erro, ao colicar senha - aba - dá 200