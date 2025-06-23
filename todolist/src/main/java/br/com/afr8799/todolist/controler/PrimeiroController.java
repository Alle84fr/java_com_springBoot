//dar run, ir para bronser, por url - localhost:8080/primeira_rota/
// folder anotacoes - aula4.txt possui os comentário sobre partes do cód


package br.com.afr8799.todolist.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/primeira_rota")

public class PrimeiroController {

    @GetMapping("")

    public String primeiraMensagem(){
        return "Saluton Java's Mondo";
    }
}