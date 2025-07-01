package br.com.afr8799.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
 
    private UUID idUsuario;
    @Column(length = 200)
    private String descricao;


    @Column(length = 50)
    private String titulo;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private String prioridade;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
