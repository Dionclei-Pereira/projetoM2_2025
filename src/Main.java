import entidades.Funcionario;
import entidades.Maquinista;
import entidades.Passageiro;
import entidades.Pessoa;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean continuar = true;
        while (continuar) {
            continuar = imprimirTela(sc);
        }

        System.out.println();
        System.out.println("Desligando sistema...");
        sc.close();
    }

    private static boolean imprimirTela(Scanner sc) {
        System.out.println("|----------------------|");
        System.out.println("1 - Pessoas");
        System.out.println("0 - Sair");
        System.out.println("|----------------------|");

        byte opcao = sc.nextByte();

        switch (opcao) {
            case 1:
                menuPessoas(sc);
                break;
            case 0:
                return false;
            default:
                System.out.println();
                System.out.println("Opção inválida!");
                System.out.println();
                break;
        }

        return true;
    }

    private static void menuPessoas(Scanner sc) {
        while (true) {
            System.out.println("|---------------------|");
            System.out.println("1 - Criar funcionario");
            System.out.println("2 - Criar passageiro");
            System.out.println("3 - Exibir todas pessoas");
            System.out.println("0 - Voltar");
            System.out.println("|---------------------|");

            byte opcao = sc.nextByte();

            switch (opcao) {
                case 1:
                    criarFuncionario(sc);
                    break;
                case 2:
                    criarPassageiro(sc);
                    break;
                case 3:
                    mostarPessoas();
                    break;
                case 0:
                    return;
                default:
                    System.out.println();
                    System.out.println("Opção inválida!");
                    System.out.println();
                    break;
            }
        }
    }

    private static void mostarPessoas() {
        System.out.println();
        for (Pessoa p : Pessoa.pessoas) {
            String tipo = "";

            if (p instanceof Funcionario) tipo = "Funcionario";
            if (p instanceof Maquinista) tipo = "Maquinista";
            if (p instanceof Passageiro) tipo = "Passageiro";

            System.out.println("Nome: " + p.getNome());
            System.out.println("CPF: " + p.getCpf());
            System.out.println("Tipo: " + tipo);
            System.out.println();
        }
    }

    private static void criarPassageiro(Scanner sc) {
        sc.nextLine();
        System.out.println("Digite o nome do passageiro: ");
        String nome = sc.nextLine();

        System.out.println("Digite o cpf do passageiro: ");
        String cpf = sc.nextLine();

        Passageiro passageiro = new Passageiro(nome, cpf);

        Pessoa.pessoas.add(passageiro);
        System.out.println("Passageiro criado com sucesso");
        sc.nextLine();
    }

    private static void criarFuncionario(Scanner sc) {
        sc.nextLine();

        System.out.println("Digite o nome do funcionario: ");
        String nome = sc.nextLine();

        System.out.println("Digite o cpf do funcionário: ");
        String cpf = sc.nextLine();

        System.out.println("O funcionário é um maquinista? (S/N)");
        String ehMaquinista = sc.nextLine();
        boolean maquinista;

        if (ehMaquinista.toUpperCase().equals("S")) {
            maquinista = true;
        } else if (ehMaquinista.toUpperCase().equals("N")) {
            maquinista = false;
        } else {
            maquinista = false;
            System.out.println("Código não identificado, seguindo como funcionário genérico!");
        }

        if (maquinista) {
            Maquinista Maquinista = new Maquinista(nome, cpf);
            Pessoa.pessoas.add(Maquinista);
            System.out.println("Maquinista criado com sucesso");
            sc.nextLine();
            return;
        }

        Funcionario funcionario = new Funcionario(nome, cpf);
        Pessoa.pessoas.add(funcionario);
        System.out.println("Funcionário criado com sucesso");
        sc.nextLine();
    }
}