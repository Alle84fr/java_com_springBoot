package br.com.afr8799.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    // id da pk - private UUID id;
    @Id
    @GeneratedValue(generator = "UUID")

    // criar a caracteristica, o objeto, o que terá nesta tarefa
    private UUID idUsuario;
    private UUID id;
    // limitar quantidade de caracteres na descrição, eu que quis - apenas 200 caracteres
    @Column(length = 200)
    private String descricao;

    // limitar quantidade de caracteres no titulo - apenas 50 caracteres
    @Column(length = 50)
    private String titulo;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private String prioridade;

    @CreationTimestamp
    // mostrar quando tarefa foi criada
    private LocalDateTime createdAt;
}
