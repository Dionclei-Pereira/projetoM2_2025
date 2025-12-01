package entidades;

import java.util.UUID;

public class Ticket {

    private String idTicket;
    private Missao missao;
    private Passageiro passageiro;

    public Ticket(Missao missao, Passageiro passageiro) {
        this.idTicket = UUID.randomUUID().toString();
        this.missao = missao;
        this.passageiro = passageiro;
        missao.addTicket(this);
        passageiro.setTicket(this);
    }

    public Passageiro getPassageiro() {
        return this.passageiro;
    }

    @Override
    public String toString() {
        return this.missao.getIdMissao();
    }
}
