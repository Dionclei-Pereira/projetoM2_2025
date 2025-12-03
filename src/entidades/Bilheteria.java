package entidades;

import enums.StatusMissao;

public class Bilheteria {

    private int ticketCount;
    private Estacao estacao;

    public Bilheteria(Estacao estacao) {
        this.estacao = estacao;
    }

    public Ticket emitirTicket(Passageiro passageiro, Missao missao) {
        if (missao.getStatusMissao() != StatusMissao.AGENDADA) {
            System.out.println("Missão está em andamento ou já foi concluida!");
            return null;
        }

        this.ticketCount++;
        Ticket ticket = new Ticket(missao, passageiro);
        missao.addTicket(ticket);
        return ticket;
    }

    public int getTicketCount() {
        return this.ticketCount;
    }
}
