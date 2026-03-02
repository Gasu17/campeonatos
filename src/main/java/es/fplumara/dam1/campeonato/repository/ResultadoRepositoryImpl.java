package es.fplumara.dam1.campeonato.repository;

import es.fplumara.dam1.campeonato.model.Resultado;

import java.util.*;

public class ResultadoRepositoryImpl implements ResultadoRepository {
    private Map<String, Resultado> datosResultados = new HashMap<>();

    @Override
    public void save(Resultado r) {
        datosResultados.put(UUID.randomUUID().toString(), r);
    }

    @Override
    public Optional<Resultado> findById(String id) {
        return Optional.of(datosResultados.get(id));
    }

    @Override
    public List<Resultado> listAll() {
        return datosResultados.values().stream().toList();
    }

    @Override
    public boolean existsByPruebaYDeportista(String idPrueba, String idDeportista) {
        boolean existe = false;
        for (Resultado r : datosResultados.values()) {
            if (r.getIdPrueba().equalsIgnoreCase(idPrueba) && r.getIdDeportista().equalsIgnoreCase(idDeportista)) {
                existe = true;
            }


        }

        return existe;
    }
}
