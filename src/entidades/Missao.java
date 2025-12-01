package entidades;

import enums.StatusMissao;
import uteis.Formatacao;

import java.time.Instant;
import java.util.UUID;

public class Missao {

    private String idMissao;
    private Instant dataPartida;
    private Instant dataChegada;
    private StatusMissao statusMissao;
    private Rota rota;

    public Missao(Instant dataPartida, Instant dataChegada, Estacao estacaoOrigem, Estacao estacaoDestino) {
        this.idMissao = UUID.randomUUID().toString();
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.statusMissao = StatusMissao.AGENDADA;

        Rota rota = new Rota(estacaoOrigem, estacaoDestino);
        this.rota = rota;
    }

    public Rota getRota() {
        return this.rota;
    }

    public String getIdMissao() {
        return this.idMissao;
    }

    public void exibirInfo() {
        System.out.println("ID: " + this.idMissao);
        System.out.println("Partida: " + Formatacao.formatarInstant(this.dataPartida));
        System.out.println("Chegada: " + Formatacao.formatarInstant(this.dataChegada));
        System.out.println("Status: " + this.statusMissao);
        System.out.println("Rota: " + this.rota.toString());
    }
}
