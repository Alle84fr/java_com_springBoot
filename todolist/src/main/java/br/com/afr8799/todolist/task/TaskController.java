package br.com.afr8799.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private ITaskRepository taskRespository;
    
    @PostMapping("/")

    //  parar recuperar o atributo - id http...
    public TaskModel create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        //para verificar o request
        // lembrar que + concatena
        System.out.println("Chegou cno controller " + request.getAttribute("idUsuario"));

        var task = this.taskRespository.save(taskModel);
        return task;

    }
}

// ao dar send - com o filtertaskAuth feito - aula 16 - dá 200 e chegou no filtro e Controller
// lembrando que é :
// post -> localhost:8080/tasks/
// body - json
// {
//     "idUsuario": "8952e1a3-2025-4616-9551-069f58d103c7",
//     "descricao": "aprendendo java co rocketseat",
//     "titulo": "Cadastro tarefa",
//     "inicio": "2025-06-30T23:18:00",
//     "fim": "2025-07-01T00:18:00",
//     "prioridade": "média"
// }