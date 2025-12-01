package entidades;

public class Bilheteria {

    private int ticketCount;
    private Estacao estacao;

    public Bilheteria(Estacao estacao) {
        this.estacao = estacao;
    }

    public Ticket emitirTicket(Passageiro passageiro, Missao missao) {
        this.ticketCount++;
        Ticket ticket = new Ticket(missao, passageiro);
        missao.addTicket(ticket);
        return ticket;
    }

    public int getTicketCount() {
        return this.ticketCount;
    }
}
