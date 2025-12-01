package entidades;

import enums.StatusOperacional;

import java.util.ArrayList;
import java.util.List;

public class Trem {

    public static List<Trem> trens = new ArrayList<>();

    private int numeroFrota;
    private List<Vagao> vagoes = new ArrayList<>();
    private Maquinista maquinista;
    private StatusOperacional statusOperacional;
    private double velocidadeAtual;
    private double quilometragemTotalMetros;
    private Missao missaoAtual;

    public Trem(int numeroFrota) {
        this.numeroFrota = numeroFrota;
        this.statusOperacional = StatusOperacional.PARADA;
    }

    public List<Vagao> getVagoes() {
        return this.vagoes;
    }

    public Missao getMissaoAtual() {
        return this.missaoAtual;
    }

    public void setMissaoAtual(Missao missao) {
        this.missaoAtual = missao;

        if (missao != null) {
            missao.setTrem(this);
        }
    }

    public void engatarVagao(Vagao vagao) {
        if (this.statusOperacional != StatusOperacional.EM_VIAGEM) {
            if (vagao.getTrem() == null) {
                this.vagoes.add(vagao);
                vagao.setTrem(this);
            } else {
                System.out.println("Este vagão já esta em um trem");
            }
        } else {
            System.out.println("O trem está em viagem!");
        }
    }

    public void desengatarVagao(Vagao vagao) {
        if (this.statusOperacional != StatusOperacional.EM_VIAGEM) {
            if (vagao.getTrem() == this) {
                this.vagoes.remove(vagao);
                vagao.setTrem(null);
            } else {
                System.out.println("O vagão não pertence a esse trem");
            }
        } else {
            System.out.println("O trem está em viagem!");
        }
    }

    public int getNumeroFrota() {
        return this.numeroFrota;
    }

    public Maquinista getMaquinista() {
        return this.maquinista;
    }

    public void setMaquinista(Maquinista maquinista) {
        this.maquinista = maquinista;
        maquinista.setTrem(this);
    }
}
