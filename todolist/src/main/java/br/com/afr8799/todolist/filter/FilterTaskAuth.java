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

// este filtro é o  coração da autentificação das requisições para /tasks/
// É chamado a cada requisição

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //verificação do erro iduser
        //sout
        System.out.println("entrou no filtertask");

        var servletPath = request.getServletPath();

        //verificação do erro iduser
        //sout
        System.out.println("path " + servletPath);

        if (servletPath.equals("/tasks/")) {

            // pega valor do cabeçalho Authorization da reuisição html como o basic asdfgh...
            // extrai a parte codificada em base64 das credenciais
            var autoriza = request.getHeader("Authorization");

            // contém as strings das credenciais (username:password) após remover basic da frente
            var authEcoded = autoriza.substring("Basic".length()).trim();

            // authEdode b64 vira um array
            byte[] autoDecode = Base64.getDecoder().decode(authEcoded);

            // transforma a array em strings legível, que é o formato "username:password"
            var convetStr = new String(autoDecode);
            // aqui separa as strings,cria novos índices, para  aparecer separado
            String[] credencials = convetStr.split(":");
            String username = credencials[0];
            String password = credencials[1];

                //armazena o boj UserModel completo retornado do bd com base no username
                var usuario = this.userRepository.findByUsername(username);

                // verifica se é nulo (erro) ou se já existe (continua)
                if (usuario == null) {
                    response.sendError(401);
                } else {
                    //se tiver usuáriousar senha cruptografada para verificar a senha
                    var passwVerify = BCrypt.verifyer().verify(password.toCharArray(), usuario.getPassword());
                    
                    // se autentificação for ok, o usuário.getID() é armazenado como um atributo na requisiçã http 
                    if(passwVerify.verified) {

                        //verificar erro idusername
                        System.out.println("Usuário autenticado: " + usuario.getUsername() + ", ID: " + usuario.getId());

                        //aqui que o idUsuário é setado para poder ser usaado novamente
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
