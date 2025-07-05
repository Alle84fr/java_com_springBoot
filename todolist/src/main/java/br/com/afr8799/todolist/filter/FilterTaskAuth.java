package br.com.afr8799.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                
                var autoriza = request.getHeader("Authorization");
                   
                //antes era user_password
                var authDecoded = autoriza.substring("Basic".length()).trim();

                //decoder
                // este comando gerará um decode, criadno array de bytes- array[]
                byte[] autoDecode = Base64.getDecoder().decode(authDecoded);
            
                //o restorno é assim [B@4bd91d42 - para entendermos o que é, transformar em string
                var convetStr = new String(autoDecode);

                // antes era pass_word
                System.out.println(autoDecode);
                System.out.println(convetStr);
                
                // retorno
                // Authorization
                // Basic QWxlOmFiYWNhdGU=
                // [B@4bd91d42

                // retorno com con string
                // Authorization
                // Basic QWxlOmFiYWNhdGU=
                // [B@6662a5a7
                // Ale:abacate

                //dividir o string em arrays, tendo base o :
                // usa split(), da mesma forma que no .py
                // String[] = converte em array a string
                // credencials = nome da variável
                // credencial[0} pega no índice zero, 1° elemento
                String[] credencials = convetStr.split(":");
                String username = credencials[0];
                String password = credencials[1];

                // dei um sout - um SOUT
                //desceu com esta parte
                System.out.println("Authorization");
                System.out.println(username);
                System.out.println(password);

                //retorno
                // [B@5e1191f4
                // Ale:abacate
                // Authorization
                // Ale
                // abacate

            }
}


