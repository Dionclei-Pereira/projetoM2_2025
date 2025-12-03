package entidades;

import java.util.UUID;

public class Funcionario extends Pessoa {

    private String matricula;

    public Funcionario(String nome, String cpf) {
        super(nome, cpf);
        this.matricula = UUID.randomUUID().toString();
    }

    public String getMatricula() {
        return this.matricula;
    }

    @Override
    public void exibirInfo() {
        super.exibirInfo();
        System.out.println("Tipo: Funcionário");
    }
}
