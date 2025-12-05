package entidades;

import java.util.UUID;

public class Ticket {

    private String idTicket;
    private String idMissao;
    private Passageiro passageiro;

    public Ticket(Missao missao, Passageiro passageiro) {
        this.idTicket = UUID.randomUUID().toString();
        this.idMissao = missao.getIdMissao();
        this.passageiro = passageiro;
        passageiro.setTicket(this);
    }

    public Passageiro getPassageiro() {
        return this.passageiro;
    }

    @Override
    public String toString() {
        return this.idMissao;
    }
}
