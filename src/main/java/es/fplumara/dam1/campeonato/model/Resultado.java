package es.fplumara.dam1.campeonato.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Resultado implements Puntuable {
    private String id;
    private String idPrueba;
    private TipoPrueba tipoPrueba;
    private String idDeportista;
    private int posicion;

    @Override
    public int getPuntos() {


        return switch (posicion) {
            case 1 -> 100;
            case 2 -> 80;
            case 3 -> 60;
            case 4 -> 50;
            case 5 -> 40;
            case 6 -> 30;
            case 7 -> 20;
            case 8 -> 10;
            default -> 0;
        };
    }

}

