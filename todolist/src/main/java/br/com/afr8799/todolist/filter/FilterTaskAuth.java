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
        
        //analisando iduser null
        //1° retorno no terminal = path /user path /tasks/ path /taskss/03993f65-3650-44d7-ad58-5e603b1a92dc
        // com iduser null ainda
        System.out.println("path " + servletPath);
       
        // estava servletPath.equals("/tasks/") -> igual a 
        // agora será iniciando com
        //pegará tanto o tasks quanto o id
        if (servletPath.equals("/tasks/")) {
        //if (servletPath.startsWith("/tasks/")) {

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

                        request.setAttribute("idUsuario", usuario.getId());
                        
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

// retorno terminal
// 2025-07-07T11:57:07.633-03:00  INFO 24296 --- [on(2)-127.0.0.1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 0 ms
// path /users/
// path /tasks/
// path /tasks/64a8482b-7a2d-4e95-97f7-2f6d7f3c68e6
// 2025-07-07T11:57:49.082-03:00 ERROR 24296 --- [nio-8080-exec-4] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception

// java.lang.NullPointerException: Cannot invoke "String.substring(int)" because "autoriza" is null

//                           OBS

// COM EQUAL ACEITA
//RETORNO
// path /tasks/e2957e20-d255-448b-bd1c-5b92d9414f6d
// idUser null
