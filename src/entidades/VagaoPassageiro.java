package entidades;

import java.util.ArrayList;
import java.util.List;

public class VagaoPassageiro extends Vagao {

    private int capacidade;
    private List<Passageiro> passageiros = new ArrayList<>();

    public VagaoPassageiro(int capacidade) {
        super();
        this.capacidade = capacidade;
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

    public List<Passageiro> getPassageiros() {
        return this.passageiros;
    }
}
