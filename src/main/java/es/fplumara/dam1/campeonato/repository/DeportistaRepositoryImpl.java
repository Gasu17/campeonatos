package es.fplumara.dam1.campeonato.repository;

import es.fplumara.dam1.campeonato.model.Deportista;

import java.util.*;

public class DeportistaRepositoryImpl implements DeportistaRepository {

    private Map<String, Deportista> datosDeportistas = new HashMap<>();

    @Override
    public void save(Deportista d) {

        datosDeportistas.put(UUID.randomUUID().toString(), d);


    }

    @Override
    public Optional<Deportista> findById(String id) {

        return Optional.of(datosDeportistas.get(id));
    }

    @Override
    public List<Deportista> findByPais(String pais) {
        List<Deportista> encontrados = new ArrayList<>();
        for (Deportista d : datosDeportistas.values()) {
            if (d.getPais().equalsIgnoreCase(pais)) {
                encontrados.add(d);
            }
        }
        return encontrados;
    }

    @Override
    public List<Deportista> listAll() {
        return datosDeportistas.values().stream().toList();
    }
}
