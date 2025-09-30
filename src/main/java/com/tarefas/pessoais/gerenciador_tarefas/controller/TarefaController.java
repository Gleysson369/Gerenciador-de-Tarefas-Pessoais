package com.tarefas.pessoais.gerenciador_tarefas.controller;

import com.tarefas.pessoais.gerenciador_tarefas.model.Tarefa;
import com.tarefas.pessoais.gerenciador_tarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/v1/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService service;

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@Valid @RequestBody Tarefa novaTarefa) {
        Tarefa tarefaSalva = service.salvar(novaTarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);
    }
    
    @GetMapping
    public List<Tarefa> listarTodas() {
        return service.listarTodas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) { 
        return service.buscarPorId(id)
            .map(tarefa -> ResponseEntity.ok((Object)tarefa)) 
            .orElse(
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("mensagem", "Tarefa com o ID " + id + " não foi encontrada.")
                )
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
        return service.atualizar(id, tarefaDetalhes)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}