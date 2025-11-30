package entidades;

import java.util.Random;

public class Ticket {


    private int idTicket;
    private Missao missao;

    public Ticket() { }

    public Ticket(int idTicket, Missao missao) {
        this.idTicket = idTicket;
        this.missao = missao;
    }

}
