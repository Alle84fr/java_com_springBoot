package br.com.afr8799.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/tasks")

public class TaskController {
    
    @Autowired
    private ITaskRepository taskRespository;
    
    @PostMapping("/")
    public TaskModel create(@RequestBody TaskModel taskModel) {

        var task = this.taskRespository.save(taskModel);
        return task;

    }
}
