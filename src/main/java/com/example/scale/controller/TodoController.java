/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.scale.controller;

/**
 *
 * @author Duminda
 */

import com.example.scale.model.Todo;
import com.example.scale.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:3000") // allow React frontend calls
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        return repository.findById(id)
            .map(todo -> {
                todo.setTitle(updatedTodo.getTitle());
                todo.setCompleted(updatedTodo.isCompleted());
                return repository.save(todo);
            })
            .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
