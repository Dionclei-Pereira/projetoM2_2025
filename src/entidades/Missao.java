package entidades;

import enums.StatusMissao;
import enums.StatusOperacional;
import uteis.Formatacao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Missao {

    private String idMissao;
    private Instant dataPartida;
    private Instant dataChegada;
    private StatusMissao statusMissao;
    private Trem trem;
    private Rota rota;
    private List<Ticket> tickets = new ArrayList<>();

    public Missao(Instant dataPartida, Instant dataChegada, Estacao estacaoOrigem, Estacao estacaoDestino) {
        this.idMissao = UUID.randomUUID().toString();
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.statusMissao = StatusMissao.AGENDADA;

        Rota rota = new Rota(estacaoOrigem, estacaoDestino);
        this.rota = rota;
    }

    public void finalizarMissao() {
        this.statusMissao = StatusMissao.CONCLUIDA;
        this.trem.setStatusOperacional(StatusOperacional.PARADA);

        for (Vagao v : this.trem.getVagoes()) {
            if (v instanceof VagaoPassageiro) {
                ((VagaoPassageiro) v).limparVagao();
            }
        }

        this.trem = null;
    }


    public void embarcarPassageiros() {
        for (Ticket ticket : this.tickets) {

            Passageiro p = ticket.getPassageiro();
            boolean embarcou = false;

            for (Vagao v : this.trem.getVagoes()) {

                if (v instanceof VagaoPassageiro) {
                    VagaoPassageiro vp = (VagaoPassageiro) v;

                    if (vp.temEspaco()) {
                        vp.adicionarPassageiro(p);
                        embarcou = true;
                        break;
                    }
                }
            }

            if (!embarcou) {
                p.setTicket(null);
                System.out.println("Sem vagões disponíveis para o passageiro: " + p.getNome());
            }
        }
    }


    public Rota getRota() {
        return this.rota;
    }

    public String getIdMissao() {
        return this.idMissao;
    }

    public Trem getTrem() {
        return this.trem;
    }

    public void setTrem(Trem trem) {
        this.trem = trem;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void exibirInfo() {
        System.out.println("ID Missão: " + this.idMissao);
        System.out.println("Partida: " + Formatacao.formatarInstant(this.dataPartida));
        System.out.println("Chegada: " + Formatacao.formatarInstant(this.dataChegada));
        System.out.println("Status: " + this.statusMissao);
        System.out.println("Rota: " + this.rota.toString());
    }

    public StatusMissao getStatusMissao() {
        return this.statusMissao;
    }

    public void setStatusMissao(StatusMissao statusMissao) {
        this.statusMissao = statusMissao;
    }

    public Instant getDataPartida() {
        return  this.dataPartida;
    }

    public Instant getDataChegada() {
        return this.dataChegada;
    }
}
