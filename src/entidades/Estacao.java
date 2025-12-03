package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Estacao {

    private String idEstacao;
    private Bilheteria bilheteria;
    private List<Missao> missoes = new ArrayList();
    private String nome;

    public Estacao(String nome) {
        this.idEstacao = UUID.randomUUID().toString();
        this.nome = nome;
    }

    public void criarBilheteria() {
        if (this.bilheteria == null) {
            this.bilheteria = new Bilheteria(this);
        } else {
            System.out.println("Esta estação já tem uma bilheteria!");
        }
    }

    public void exibirInfo() {
        System.out.println("ID: " + this.idEstacao);
        System.out.println("Nome: " + this.nome);
        System.out.println("Tickets emitidos: " + this.bilheteria.getTicketCount());
    }

    public void adicionarMissao(Missao missao) {
        this.missoes.add(missao);
    }

    public Bilheteria getBilheteria() {
        return this.bilheteria;
    }

    public String getNome() {
        return this.nome;
    }

    public List<Missao> getMissoes() {
        return this.missoes;
    }
}
