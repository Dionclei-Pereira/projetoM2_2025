package entidades;

public final class Maquinista extends Funcionario{

    private Trem trem;

    public Maquinista(String nome, String cpf) {
        super(nome, cpf);
        this.trem = trem;
    }

    public void realizarViagem() {

    }

}
