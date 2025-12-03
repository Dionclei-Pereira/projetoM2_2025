package entidades;

public class VagaoCarga extends Vagao {

    private Carga carga;

    public VagaoCarga() {
        super();
    }

    public void carregar(Carga carga) {
        this.carga = carga;
    }

    public void descarregar(Carga carga) {
        this.carga = carga;
    }
}
