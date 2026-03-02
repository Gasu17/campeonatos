package es.fplumara.dam1.campeonato.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class Participante {
    private UUID id;
    private String nombre;
    private String pais;
}
