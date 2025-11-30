package entidades;

public class Bilheteria {

    private static int ticketCount = 0;
    private Estacao estacao;

    public void emitirTicket(Passageiro passageiro, Missao missao) {
        Ticket ticker = new Ticket(ticketCount++, missao);
        passageiro.setTicket(ticker);
    }

}
