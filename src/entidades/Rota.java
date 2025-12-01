package entidades;

import java.util.UUID;

public class Rota {

    private String idRota;
    private Estacao estacaoOrigem;
    private Estacao estacaoDestino;

    public Rota(Estacao estacaoOrigem, Estacao estacaoDestino) {
        this.estacaoOrigem = estacaoOrigem;
        this.estacaoDestino = estacaoDestino;
    }

    public Estacao getEstacaoOrigem() {
        return this.estacaoOrigem;
    }

    public Estacao getEstacaoDestino() {
        return this.estacaoDestino;
    }

    @Override
    public String toString() {
        return "[ " + estacaoOrigem.getNome() + " -> " + estacaoDestino.getNome() + " ]";
    }
}
