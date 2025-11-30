package entidades;

public class Passageiro extends Pessoa {

    private Ticket ticket;

    public Passageiro(String nome, String cpf) {
        super(nome, cpf);
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
