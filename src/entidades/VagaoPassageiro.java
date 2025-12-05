package entidades;

import java.util.ArrayList;
import java.util.List;

public class VagaoPassageiro extends Vagao {

    private final int capacidade = 10;
    private List<Passageiro> passageiros = new ArrayList<>();

    public VagaoPassageiro() {
        super();
    }

    public boolean temEspaco() {
        return passageiros.size() < capacidade;
    }

    public void adicionarPassageiro(Passageiro p) {
        passageiros.add(p);
    }

    public void limparVagao() {
        this.passageiros.clear();
    }

}
