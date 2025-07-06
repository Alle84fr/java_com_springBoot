package br.com.afr8799.todolist.task;

import java.util.UUID;

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


    public TaskModel create(@RequestBody TaskModel taskModel, HttpServletRequest request) {


        System.out.println("Chegou cno controller ");


        var idUser = request.getAttribute("idUsuario");

        taskModel.setIdUsuario((UUID) idUser);

        var task = this.taskRespository.save(taskModel);
        return task;

    }
}


