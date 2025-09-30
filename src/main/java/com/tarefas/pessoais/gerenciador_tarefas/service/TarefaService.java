package com.tarefas.pessoais.gerenciador_tarefas.service;

import com.tarefas.pessoais.gerenciador_tarefas.model.Tarefa;
import com.tarefas.pessoais.gerenciador_tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    public List<Tarefa> listarTodas() {
        return repository.findAll();
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Tarefa salvar(Tarefa novaTarefa) {
        return repository.save(novaTarefa);
    }

    public Optional<Tarefa> atualizar(Long id, Tarefa tarefaDetalhes) {
        return repository.findById(id)
            .map(tarefaExistente -> {
                tarefaExistente.setTitulo(tarefaDetalhes.getTitulo());
                tarefaExistente.setDescricao(tarefaDetalhes.getDescricao());
                tarefaExistente.setConcluida(tarefaDetalhes.isConcluida());
                tarefaExistente.setCategoria(tarefaDetalhes.getCategoria());
                
                return repository.save(tarefaExistente);
            });
    }

    public boolean deletar(Long id) {
        return repository.findById(id)
            .map(tarefa -> {
                repository.delete(tarefa);
                return true;
            })
            .orElse(false);
    }
}