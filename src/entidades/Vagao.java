package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Vagao {

    private String idVagao;
    private Trem trem;

    public Vagao() {
        this.idVagao = UUID.randomUUID().toString();
    }

    public String getIdVagao() {
        return this.idVagao;
    }

    public Trem getTrem() {
        return this.trem;
    }

    public void setTrem(Trem trem) {
        this.trem = trem;
    }
}
