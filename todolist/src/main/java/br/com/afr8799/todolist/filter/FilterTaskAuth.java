package br.com.afr8799.todolist.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

// do jakarta.servevlet
// servelet base para os frames

// selecionar nome file -> ctrl . -> add unimplementd methods -> enter ->  add o método direto -> só arruma
//além dos imports, apareceu
    // @Override
    // public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
    //         throws IOException, ServletException {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'doFilter'");
    // }

// arrumar  ServletRequest request, ServletResponse response, FilterChain chain) - todos do filter
// deletar -> throw new UnsupportedOperationException("Unimplemented method 'doFilter'");

// toda classe que será gerenciada pelo spring deve ter coponent (clase mais genérica)
@Component
public class FilterTaskAuth implements Filter{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    
    // executar ação podendo bloquear ou seguir

    // ServletRequest reques - requisição/pedido
    // ServletResponse response - retorno/resposta
    // FilterChain chai - filtro, o que será repassado

    //ATALHO PARA CHAMAR SYSTEM.OUT = SYSOUT - sysout
    System.out.println("Chegou no filtro");
    

    //caminho que dá certo/ seguir
    chain.doFilter(request, response);
    }

   
}

// rodar aplicação

// apidog - new request - post - http://localhost:8080/tasks/ - auth - type = basic Auth - send
    
// tentativa 1 - deu 400 - 
// {
//     "timestamp": "2025-07-04T19:45:27.039+00:00",
//     "status": 400,
//     "error": "Bad Request",
//     "path": "/tasks/"
// }
// no terminal
// 2025-07-04T16:45:27.024-03:00  WARN 14836 --- [nio-8080-exec-2] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: Required request body is missing: public br.com.afr8799.todolist.task.TaskModel br.com.afr8799.todolist.task.TaskController.create(br.com.afr8799.todolist.task.TaskModel)]
// "Required request body is missing" (O corpo da requisição é obrigatório e está faltando).

//           COMO ARRUMEI

// !!! APIDOG - CRIEI POST COM http://localhost:8080/tasks/ , SEM SAIR, MUDEI PARA AUTH E DIZ TODO O MESMO CAMINHHO ESCRITO NA PARTE DE CIMA - RODOU 200 - ACHO QUE DEVE CRIA O CORPO DO TASK 1° E NA MESMA ABA, O AUTH E NO TERMINAL DEU CHEGOU NO FILTER E CONTROLLER!!!!