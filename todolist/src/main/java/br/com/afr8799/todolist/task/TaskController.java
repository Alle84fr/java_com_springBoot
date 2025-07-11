package br.com.afr8799.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.afr8799.todolist.filter.FilterTaskAuth;
import br.com.afr8799.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;


//operações das tarefas

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final FilterTaskAuth filterTaskAuth;
    
    @Autowired
    private ITaskRepository taskRespository;

    TaskController(FilterTaskAuth filterTaskAuth) {
        this.filterTaskAuth = filterTaskAuth;
    }
    
    @PostMapping("/")


    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        //iduser erro pesquisando
        System.out.println("ID do usuário na requisição (create): " + request.getAttribute("idUsuario"));

        //lida com criação, listagem e atualizaçoes de tarefas

        // recupera o idUsuário setado pelo FilterTaskAuth
        var idUser = request.getAttribute("idUsuario");


        UUID userId = (UUID) idUser;
        // é feito um cast explícito para UUID:((UUId) iduser), e se iduser for null retornará erro
        // seta o objeto taskmodel e também busca tarefas específicas (this.taskRespository.findByIdUsuario((UUID) idUser)))
        taskModel.setIdUsuario((UUID) idUser);

        var currentDate = LocalDateTime.now();

        if(currentDate.isAfter(taskModel.getInicio()) || currentDate.isAfter(taskModel.getFim())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inválida, favor por data futura, pós data de abertura");
        }


        if(taskModel.getInicio().isAfter(taskModel.getFim())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("hora inválida, data de iníicio deve ser antes da de término");
        }


        var task = this.taskRespository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    
    }


    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {

        //verificando iduser erro
        System.out.println("ID do usuário na requisição (list): " + request.getAttribute("idUsuario"));

        var idUser = request.getAttribute("idUsuario");
        var tasks = this.taskRespository.findByIdUsuario((UUID) idUser);
        return tasks;

    }


    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        //TODO: process PUT request
        
        //verificando erro iduser
        System.out.println("ID do usuário na requisição (update): " + request.getAttribute("idUsuario"));

        var idUser = request.getAttribute("idUsuario");
       
        System.out.println("idUser " + idUser);
       
        var task1 = this.taskRespository.findById(id).orElse(null);

        //Utils do próprio projeto
        Utils.copyNonNullProperties(taskModel, task1);


        return this.taskRespository.save(task1);
        
   
    }

}



