package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Vagao {

    private int idVagao;

    private Trem trem;

    public Vagao() {
        this.idVagao = UUID.randomUUID().hashCode();
    }

    public void setTrem(Trem trem) {
        this.trem = trem;
    }

    public Trem getTrem() {
        return this.trem;
    }
}
