package es.fplumara.dam1.campeonato.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LineaRanking {
    private String idDeportista;
    private String nombre;
    private String pais;
    private int puntos;

    public LineaRanking(Deportista deportista, Integer value) {
    }
}
