import entidades.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());

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
        System.out.println("2 - Estações e Missões");
        System.out.println("0 - Sair");
        System.out.println("|----------------------|");

        byte opcao = sc.nextByte();

        switch (opcao) {
            case 1:
                menuPessoas(sc);
                break;
            case 2:
                menuEstacoesVagoes(sc);
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

    private static void menuEstacoesVagoes(Scanner sc) {
        while (true) {
            System.out.println("|---------------------|");
            System.out.println("1 - Criar Estação");
            System.out.println("2 - Criar Missão");
            System.out.println("3 - Emitir Ticket");
            System.out.println("4 - Mostrar Estações");
            System.out.println("5 - Mostrar Missões");
            System.out.println("0 - Voltar");
            System.out.println("|---------------------|");

            byte opcao = sc.nextByte();
            switch (opcao) {
                case 1:
                    criarEstacao(sc);
                    break;
                case 2:
                    criarMissao(sc);
                    break;
                case 3:
                    criarTicket(sc);
                    break;
                case 4:
                    mostrarEstacoes(sc);
                    break;
                case 5:
                    menuMissoes(sc);
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

    private static void criarTicket(Scanner sc) {
        sc.nextLine();
        Passageiro passageiro = (Passageiro) selecionarPessoa(sc, "Selecione o usuario que ira comprar o Ticket", Passageiro.class);

        if (passageiro == null) {
            sc.nextLine();
            return;
        }

        if (passageiro.getTicket() != null) {
            System.out.println("Passageiro já tem um Ticket!");
            sc.nextLine();
            return;
        }

        Missao missao = selecionarMissao(sc);

        if (missao == null) {
            sc.nextLine();
        }

        missao.getRota().getEstacaoOrigem()
                .getBilheteria().emitirTicket(passageiro, missao);
        System.out.println("Ticket emitido com sucesso!");
        sc.nextLine();
    }

    private static void menuMissoes(Scanner sc) {
        System.out.println("|---------------------|");
        System.out.println("1 - Ver todas missões");
        System.out.println("2 - Buscar por estação");
        System.out.println("|---------------------|");
        int  opcao = sc.nextInt();

        if (opcao == 1) {
            for (Estacao e : Estacao.estacoes) {
                for (Missao m : e.getMissoes()) {
                    m.exibirInfo();
                    System.out.println();
                }
            }
        } else if (opcao == 2) {
            Estacao estacao = selecionarEstacao(sc, "Selecione uma estação");

            for (Missao m : estacao.getMissoes()) {
                m.exibirInfo();
                System.out.println();
            }
        } else {
            System.out.println("Opção inválida!");
        }
    }

    private static void criarMissao(Scanner sc) {
        sc.nextLine();


        Estacao origem = selecionarEstacao (sc, "Escolha a estação de inicio");
        Estacao destino = selecionarEstacao (sc, "Escolha a estação de destino");

        if (origem == null || destino == null) {
            sc.nextLine();
            return;
        }

        if (origem.equals(destino)) {
            System.out.println("A estação de inicio não pode ser a mesma de destino");
            sc.nextLine();
            sc.nextLine();
            return;
        }

        System.out.println("Digite a data da missão no formato ano-mês-dia");
        String partidaString = sc.next();

        Instant partida;

        try {
            partida = LocalDate.parse(partidaString)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant();
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido!");
            return;
        }

        System.out.println("Digite tempo de duração da viagem em dias");
        int dias = sc.nextInt();
        Instant chegada = Instant.now()
                .atOffset(ZoneOffset.UTC).plusDays(dias).toInstant();

        Missao missao = new Missao(partida, chegada, origem, destino);
        origem.adicionarMissao(missao);

        System.out.println("Missão criada com sucesso");
        sc.nextLine();
    }

    private static Estacao selecionarEstacao(Scanner sc, String message) {
        int i = 0;
        System.out.println();
        for (Estacao e : Estacao.estacoes) {
            i++;
            System.out.println("[" + i + "]: " + e.getNome());
        }

        System.out.println(message);

        int index = sc.nextInt();
        int quantidadeEstacoes = Estacao.estacoes.size();

        if (index > quantidadeEstacoes) {
            System.out.println("Estação inválida!");
            return null;
        }

        return Estacao.estacoes.get(index - 1);
    }

    private static Pessoa selecionarPessoa(Scanner sc, String message, Class<? extends Pessoa> tipo) {
        int i = 0;

        List<Pessoa> pessoas = new ArrayList<>();
        for (Pessoa p : Pessoa.pessoas) {
            if (tipo.isInstance(p)) {
                pessoas.add(p);
            }
        }

        for (Pessoa p : pessoas) {
            i++;
            System.out.println("[" + i + "]: " + p.getNome());
        }

        System.out.println(message);

        int index = sc.nextInt();
        if (index > pessoas.size()) {
            System.out.println("Pessoa inválida");
            sc.nextLine();
            return null;
        }

        Pessoa pessoa = pessoas.get(index - 1);

        if (tipo.isInstance(pessoa)) {
            return pessoa;
        } else {
            System.out.println("Pessoa inválida");
            sc.nextLine();
            return null;
        }
    }

    private static Missao selecionarMissao(Scanner sc) {
        List<Missao> missoes = new ArrayList<>();
        for (Estacao e : Estacao.estacoes) {
            for (Missao m : e.getMissoes()) {
                missoes.add(m);
            }
        }

        int i = 0;
        for (Missao m : missoes) {
            i++;
            System.out.print("[" + i + "]: ");
            m.exibirInfo();
            System.out.println();
        }

        System.out.println("Selecione a missão");

        int index = sc.nextInt();

        if (index > missoes.size()) {
            System.out.println("Missão inválida");
            sc.nextLine();
            return null;
        }

        Missao missao = missoes.get(index - 1);

        return missao;
    }

    private static void mostrarEstacoes(Scanner sc) {
        System.out.println();
        for (Estacao e : Estacao.estacoes) {
            e.exibirInfo();
            System.out.println();
        }

        sc.nextLine();
        sc.nextLine();
    }

    private static void criarEstacao(Scanner sc) {
        sc.nextLine();
        System.out.println("Digite o nome da estação");
        String  nome = sc.nextLine();

        Estacao estacao = new Estacao(nome);
        estacao.criarBilheteria();
        Estacao.estacoes.add(estacao);
        System.out.println("Estação e Bilheteria criada com sucesso");
        sc.nextLine();
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
                    mostarPessoas(sc);
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

    private static void mostarPessoas(Scanner sc) {
        System.out.println();
        for (Pessoa p : Pessoa.pessoas) {
            String tipo = "";

            if (p instanceof Funcionario) tipo = "Funcionario";
            if (p instanceof Maquinista) tipo = "Maquinista";
            if (p instanceof Passageiro) tipo = "Passageiro";

            System.out.println("Nome: " + p.getNome());
            System.out.println("CPF: " + p.getCpf());
            if (p instanceof Passageiro) {
                if (((Passageiro) p).getTicket() == null) {

                } else {
                    System.out.println("Ticket [Id da Missão]: " + ((Passageiro) p).getTicket().toString());
                }
            }
            System.out.println("Tipo: " + tipo);
            System.out.println();
        }
        sc.nextLine();
        sc.nextLine();
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