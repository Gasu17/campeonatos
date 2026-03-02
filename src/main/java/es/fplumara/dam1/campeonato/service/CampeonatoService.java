package es.fplumara.dam1.campeonato.service;

import es.fplumara.dam1.campeonato.exception.DuplicadoException;
import es.fplumara.dam1.campeonato.exception.NoEncontradoException;
import es.fplumara.dam1.campeonato.exception.OperacionNoPermitidaException;
import es.fplumara.dam1.campeonato.model.Deportista;
import es.fplumara.dam1.campeonato.model.LineaRanking;
import es.fplumara.dam1.campeonato.model.Resultado;
import es.fplumara.dam1.campeonato.repository.DeportistaRepository;
import es.fplumara.dam1.campeonato.repository.ResultadoRepository;

import java.util.*;
import java.util.stream.Collectors;


public class CampeonatoService {

    private DeportistaRepository deportistaRepository;
    private ResultadoRepository resultadoRepository;

    public CampeonatoService(DeportistaRepository deportistaRepository, ResultadoRepository resultadoRepository) {
        this.deportistaRepository = deportistaRepository;
        this.resultadoRepository = resultadoRepository;
    }


    public void registrarDeportista(Deportista d) {
//        si d es null o id/nombre/pais es null/vacío → IllegalArgumentException
//        si ya existe un deportista con ese id → DuplicadoException
//        si todo ok → guarda
        Optional<Deportista> existe = deportistaRepository.findById(d.getId().toString());

        if (d == null || d.getId().isEmpty() || d.getNombre().isBlank() || d.getPais().isEmpty() || d.getPais().isBlank()) {
            throw new IllegalArgumentException("Algun campo es nulo");
        }
        if (existe.isPresent()) {
            throw new DuplicadoException("Deportista duplicado");
        }

        deportistaRepository.save(d);
    }

    public void registrarResultado(Resultado r) {
//        si r es null o id/idPrueba/idDeportista es null/vacío → IllegalArgumentException
//        si tipoPrueba es null → IllegalArgumentException
//        si posicion <= 0 → IllegalArgumentException
//        si ya existe un resultado con ese id → DuplicadoException
//        si el deportista no existe → NoEncontradoException
//        regla del campeonato: un deportista solo puede tener 1 resultado por prueba
//        si resultadoRepo.existsByPruebaYDeportista(idPrueba, idDeportista) → OperacionNoPermitidaException
//        si todo ok → guarda

        if (r == null || r.getId() == null || r.getIdPrueba().isEmpty() || r.getIdPrueba().isBlank() || r.getIdDeportista().isBlank() || r.getIdDeportista().isEmpty() || r.getTipoPrueba() == null) {
            throw new IllegalArgumentException("no pueden habe campos nulos");
        } else if (r.getPosicion() <= 0) {
            throw new IllegalArgumentException("La poscicion no puede ser 0 o menor a 0");
        }

        Optional<Resultado> existeR = resultadoRepository.findById(r.getId());
        Optional<Deportista> existeD = deportistaRepository.findById(r.getId());
        if (existeR.isPresent()) {
            throw new DuplicadoException("Resultado duplicado");

        } else if (existeD.isEmpty()) {
            throw new NoEncontradoException("Deportista no encontrado");
        }

        if (resultadoRepository.existsByPruebaYDeportista(r.getIdPrueba(), r.getIdDeportista())) {
            throw new OperacionNoPermitidaException("El deportista ya tiene registro ene sta prueba");
        }

        resultadoRepository.save(r);
    }

    public List<LineaRanking> ranking() {

        Map<String, Integer> puntosPorDeportista = resultadoRepository.listAll().stream()
                .collect(Collectors.groupingBy(Resultado::getIdDeportista, Collectors.summingInt(Resultado::getPuntos)));

        return puntosPorDeportista.entrySet().stream()
                .map(e -> new LineaRanking(
                        deportistaRepository.findById(e.getKey()).get(),
                        e.getValue()
                ))
                .sorted(Comparator.comparingInt(LineaRanking::getPuntos).reversed())
                .toList();


    }

    public List<Resultado> resultadosDePais(String pais) {
//        obtiene primero los deportistas de ese país con deportistaRepo.findByPais(pais)
//        y devuelve los resultados cuyos idDeportista estén en esa lista (filtrado/transformación de colecciones)

        List <Deportista> deportistasIds = deportistaRepository.findByPais(pais);
        deportistasIds.stream()
                .map(Deportista :: getId)
                .toList();

        return resultadoRepository.listAll().stream()
                .filter(r -> deportistasIds.contains(r.getIdDeportista()))
                .toList();




    }

    public Set<String> paisesParticipantes() {
       return deportistaRepository.listAll().stream()
               .map(Deportista :: getPais)
               .collect(Collectors.toSet());
    }
}
