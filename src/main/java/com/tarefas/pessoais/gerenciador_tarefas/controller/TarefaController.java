package com.tarefas.pessoais.gerenciador_tarefas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarefas.pessoais.gerenciador_tarefas.model.Tarefa;
import com.tarefas.pessoais.gerenciador_tarefas.repository.TarefaRepository;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController 
@RequestMapping("/api/tarefas") 
public class TarefaController {

    @Autowired
    private TarefaRepository repository;

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@Valid @RequestBody Tarefa novaTarefa) {
           
        Tarefa tarefaSalva = repository.save(novaTarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);
    }
    
    @GetMapping
    public List<Tarefa> listarTodas() {
        return repository.findAll();
    }
    
    @GetMapping("/{id}")
     public ResponseEntity<Object> buscarPorId(@PathVariable Long id) { 
        return repository.findById(id)
                .map(tarefa -> ResponseEntity.ok((Object)tarefa)) 
                .orElse(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("mensagem", "Tarefa com o ID " + id + " não foi encontrada.")
                    )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
        return repository.findById(id)
            .map(tarefaExistente -> {
                tarefaExistente.setTitulo(tarefaDetalhes.getTitulo());
                tarefaExistente.setDescricao(tarefaDetalhes.getDescricao());
                tarefaExistente.setConcluida(tarefaDetalhes.isConcluida());
                tarefaExistente.setCategoria(tarefaDetalhes.getCategoria());
                
                Tarefa atualizada = repository.save(tarefaExistente);
                return ResponseEntity.ok(atualizada);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        return repository.findById(id)
            .map(tarefa -> {
                repository.delete(tarefa);
                return ResponseEntity.noContent().<Void>build(); 
            })
            .orElse(ResponseEntity.notFound().build());
    }
}