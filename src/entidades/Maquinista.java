package entidades;

public final class Maquinista extends Funcionario{

    private Trem trem;

    public Maquinista(String nome, String cpf) {
        super(nome, cpf);
        this.trem = trem;
    }

    public void realizarViagem() {

    }

    public void setTrem(Trem trem) {
        this.trem = trem;
    }

    public Trem getTrem() {
        return this.trem;
    }

}
