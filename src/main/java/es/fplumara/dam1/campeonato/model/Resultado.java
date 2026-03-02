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
public class Resultado implements Puntuable{
    private UUID id;
    private String idPrueba;
    private TipoPrueba tipoPrueba;
    private String idDeportista;
    private int posicion;

    @Override
    public int getPuntos() {
        return 0;
    }
}
