package entidades;

import enums.StatusMissao;

import java.time.Instant;

public final class Maquinista extends Funcionario {

    private Trem trem;

    public Maquinista(String nome, String cpf) {
        super(nome, cpf);
    }

    private void realizarViagem() {

        Missao missao = this.trem.getMissaoAtual();

        missao.embarcarPassageiros();

        missao.setStatusMissao(StatusMissao.EM_CURSO);
        System.out.println("Missão: " + missao.getIdMissao() + " iniciada pelo maquinista: " + this.getNome());
    }

    private void finalizarViagem() {
        Missao missao = this.trem.getMissaoAtual();
        missao.finalizarMissao();
    }

    public void verificarViagem(Instant instant) {
        if (this.trem == null) {
            return;
        }

        Missao missao = this.trem.getMissaoAtual();
        if (missao == null) {
            return;
        }

        StatusMissao status = missao.getStatusMissao();

        switch (status) {
            case AGENDADA:
                if (!instant.isBefore(missao.getDataPartida())) {
                    this.realizarViagem();
                }
                break;

            case EM_CURSO:
                if (!instant.isBefore(missao.getDataChegada())) {
                    this.finalizarViagem();
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
