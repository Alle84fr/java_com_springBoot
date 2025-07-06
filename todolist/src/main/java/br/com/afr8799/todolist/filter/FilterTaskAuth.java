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

