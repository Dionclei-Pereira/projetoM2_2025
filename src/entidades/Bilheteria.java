package entidades;

public class Bilheteria {

    private int ticketCount;
    private Estacao estacao;

    public Bilheteria(Estacao estacao) {
        this.estacao = estacao;
    }

    public void emitirTicket(Passageiro passageiro, Missao missao) {
        this.ticketCount++;
        Ticket ticker = new Ticket(missao, passageiro);
    }

    public int getTicketCount() {
        return this.ticketCount;
    }
}
