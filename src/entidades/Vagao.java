package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Vagao {

    private String idVagao;

    public Vagao() {
        this.idVagao = UUID.randomUUID().toString();
    }

    public String getIdVagao() {
        return idVagao;
    }
}
