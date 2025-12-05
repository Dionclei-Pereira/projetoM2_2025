package entidades;

import enums.StatusMissao;
import enums.StatusOperacional;

import java.time.Instant;

public final class Maquinista extends Funcionario {

    private Trem trem;

    public Maquinista(String nome, String cpf) {
        super(nome, cpf);
    }

    private void realizarViagem(Missao missao) {
        missao.embarcarPassageiros();
        this.trem.setStatusOperacional(StatusOperacional.EM_VIAGEM);
        missao.setStatusMissao(StatusMissao.EM_CURSO);
        System.out.println("Missão: " + missao.getIdMissao() + " iniciada pelo maquinista: " + this.getNome());
    }

    private void finalizarViagem(Missao missao) {
        missao.finalizarMissao();
        this.trem.setStatusOperacional(StatusOperacional.PARADA);
    }

    public void verificarViagem(Missao missao, Instant instant) {
        if (this.trem == null || missao == null) {
            return;
        }

        if (missao.getTrem() != this.getTrem()) {
            System.out.println("Esta missão não pertence ao trem deste maquinista.");
            return;
        }

        StatusMissao status = missao.getStatusMissao();

        switch (status) {
            case AGENDADA:
                if (!instant.isBefore(missao.getDataPartida())) {
                    this.realizarViagem(missao);
                }
                break;

            case EM_CURSO:
                if (!instant.isBefore(missao.getDataChegada())) {
                    this.finalizarViagem(missao);
                    System.out.println("Missão: " + missao.getIdMissao() + " Finalizada pelo maquinista: " + this.getNome());
                } else {
                    System.out.println("Missão: " + missao.getIdMissao() + " Segue em viagem");
                }
                break;
        }
    }

    public void setTrem(Trem trem) {
        this.trem = trem;
    }

    public Trem getTrem() {
        return this.trem;
    }

    @Override
    public void exibirInfo() {
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Tipo: Maquinista");
    }
}
