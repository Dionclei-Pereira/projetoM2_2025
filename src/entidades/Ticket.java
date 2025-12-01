package entidades;

import java.util.Random;
import java.util.UUID;

public class Ticket {

    private String idTicket;
    private Missao missao;

    public Ticket(Missao missao) {
        this.idTicket = UUID.randomUUID().toString();
        this.missao = missao;
    }

    @Override
    public String toString() {
        return this.missao.getIdMissao();
    }
}
