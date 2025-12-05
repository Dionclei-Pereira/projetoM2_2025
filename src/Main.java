import entidades.*;
import enums.StatusMissao;
import enums.StatusOperacional;
import enums.TipoCarga;
import uteis.Formatacao;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    private static Instant instant = Instant.now();

    private static List<Estacao> estacoes = new ArrayList<>();
    private static List<Pessoa> pessoas = new ArrayList<>();
    private static List<Vagao> vagoes = new ArrayList<>();
    private static List<Trem> trens = new ArrayList<>();

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

    private static void passarDia() {
        instant = instant.atZone(ZoneId.systemDefault()).plusDays(1).toInstant();
    }

    private static void atualizarMissoes() {
        List<Missao> missoes = new ArrayList<>();

        for (Estacao estacao : estacoes) {
            for (Missao missao : estacao.getMissoes()) {
                missoes.add(missao);
            }
        }

        // Cancelar missões sem trem
        for (Missao m : missoes) {
            if (m.getTrem() == null && instant.isAfter(m.getDataPartida())) {
                m.setStatusMissao(StatusMissao.CANCELADA);
            }
        }


        for (Trem t : trens) {
            Missao m = getMissaoDoTrem(t);
            for (Pessoa p : pessoas) {
                if (p instanceof Maquinista) {
                    Maquinista maquinista = (Maquinista) p;
                    if (maquinista.getTrem() == t) {
                        maquinista.verificarViagem(m, instant);
                    }
                }
            }
        }
    }

    private static boolean imprimirTela(Scanner sc) {

        atualizarMissoes();

        System.out.println("|----------------------| " + Formatacao.formatarInstant(instant));
        System.out.println("1 - Pessoas");
        System.out.println("2 - Estações, Missões e Tickets");
        System.out.println("3 - Trens e Vagões");
        System.out.println("4 - Passar o dia");
        System.out.println("0 - Sair");
        System.out.println("|----------------------|");

        byte opcao = sc.nextByte();

        switch (opcao) {
            case 1:
                menuPessoas(sc);
                break;
            case 2:
                menuEstacoesMissoes(sc);
                break;
            case 3:
                menuTrensVagoes(sc);
                break;
            case 4:
                passarDia();
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

    private static void menuTrensVagoes(Scanner sc) {
        while (true) {

            System.out.println("|---------------------|");
            System.out.println("1 - Criar Trem");
            System.out.println("2 - Criar Vagão");
            System.out.println("3 - Associar Vagão");
            System.out.println("4 - Associar Missão");
            System.out.println("5 - Associar Maquinista");
            System.out.println("6 - Mostrar Trens e Vagões");
            System.out.println("0 - Voltar");
            System.out.println("|---------------------|");

            byte opcao = sc.nextByte();
            switch (opcao) {
                case 1:
                    criarTrem(sc);
                    break;
                case 2:
                    criarVagao(sc);
                    break;
                case 3:
                    assosiarVagao(sc);
                    break;
                case 4:
                    associarMissao(sc);
                    break;
                case 5:
                    associarMaquinista(sc);
                    break;
                case 6:
                    mostrarTrensVagoes(sc);
                    break;
                case 0:
                    return;
                default:
                    System.out.println();
                    System.out.println("Opção inválida");
                    System.out.println();
                    break;
            }
        }
    }

    private static void mostrarTrensVagoes(Scanner sc) {
        sc.nextLine();
        System.out.println("Trens:");
        System.out.println();
        for (Trem t : trens) {
            String cima = "    ||";
            String meio = "[-----]";
            String baixo = " 0   0 ";
            for (Vagao v : t.getVagoes()) {
                if (v instanceof VagaoPassageiro) {
                    meio += "-[Passageiro]";
                    baixo += "  0        0 ";
                } else {
                    meio += "-[Carga]";
                    baixo += "  0   0 ";
                }
            }

            System.out.println("Frota: " + t.getIdFrota());
            System.out.println("Status: " + t.getStatusOperacional());
            Missao missao = getMissaoDoTrem(t);
            if (missao != null) {
                System.out.print("Missão Atual: ");
                missao.exibirInfo();
            } else {
                System.out.println("Missão Atual: Nenhuma (Disponível)");
            }
            System.out.println(cima);
            System.out.println(meio);
            System.out.println(baixo);
            System.out.println();
        }
        System.out.println("Vagões Avulso: ");
        for (Vagao v : vagoes) {
            if (!isVagaoEmUso(v)) {
                if (v instanceof VagaoPassageiro) {
                    System.out.println("-[oPassageiro]");
                    System.out.println("  0         0");
                } else {
                    System.out.println("-[Carga]");
                    System.out.println("  0   0");
                }
            }
        }
        System.out.println();
        sc.nextLine();
    }

    private static void assosiarVagao(Scanner sc) {
        Vagao vagao = selecionarVagao(sc);
        if (vagao == null) {
            return;
        }

        Trem trem = selecionarTrem(sc);
        if (trem == null) {
            return;
        }

        trem.engatarVagao(vagao);

        System.out.println("Vagão engatado com sucesso!");
    }

    private static void criarTrem(Scanner sc) {

        String frota = UUID.randomUUID().toString();
        Trem trem = new Trem(frota);

        trens.add(trem);
        System.out.println("Trem criado com sucesso!");
        sc.nextLine();
    }

    private static void criarVagao(Scanner sc) {
        sc.nextLine();

        System.out.println("Tipo do vagão? [Carga/Passageiro]");
        String tipo = sc.nextLine().toUpperCase();

        if (tipo.equals("CARGA")) {
            VagaoCarga vagaoCarga = new VagaoCarga();

            System.out.println("Qual tipo de carga? [ALIMENTO. COMBUSTIVEL. MATERIAL. RADIOATIVA]");

            String tipoCargaStr = sc.nextLine();

            try {
                TipoCarga tipoCarga = TipoCarga.valueOf(tipoCargaStr.toUpperCase());

                System.out.println("Digite a quantidade em KG");
                double quantidade = sc.nextDouble();

                Carga carga = new Carga(tipoCarga, quantidade);

                vagaoCarga.carregar(carga);
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo inválido!");
                return;
            }

            vagoes.add(vagaoCarga);
        } else if (tipo.equals("PASSAGEIRO")) {
            System.out.println("Digite a capacidade maxima do vagão");

            int capacidade = sc.nextInt();

            VagaoPassageiro vagaoPassageiro = new VagaoPassageiro(capacidade);
            vagoes.add(vagaoPassageiro);
        } else {
            System.out.println("Tipo inválido!");
            sc.nextLine();
            return;
        }

        System.out.println("Vagão criado com sucesso!");
        sc.nextLine();
    }

    private static void menuEstacoesMissoes(Scanner sc) {
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


        Missao missao = selecionarMissao(sc);
        if (missao == null) {
            sc.nextLine();
            return;
        }

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

        Ticket ticket = missao.getRota().getEstacaoOrigem()
                .getBilheteria().emitirTicket(passageiro, missao);

        if (ticket != null) {
            System.out.println("Ticket emitido com sucesso!");
        }
        sc.nextLine();
    }

    private static void menuMissoes(Scanner sc) {
        System.out.println("|---------------------|");
        System.out.println("1 - Ver todas missões");
        System.out.println("2 - Buscar por estação");
        System.out.println("|---------------------|");
        int opcao = sc.nextInt();

        if (opcao == 1) {
            for (Estacao e : estacoes) {
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


        Estacao origem = selecionarEstacao(sc, "Escolha a estação de inicio");
        Estacao destino = selecionarEstacao(sc, "Escolha a estação de destino");

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
            if (partida.isBefore(Instant.now())) {
                System.out.println("Data inválida!");
                return;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido!");
            return;
        }

        System.out.println("Digite tempo de duração da viagem em dias");
        int dias = sc.nextInt();
        Instant chegada = partida
                .atOffset(ZoneOffset.UTC).plusDays(dias).toInstant();

        Missao missao = new Missao(partida, chegada, origem, destino);
        origem.adicionarMissao(missao);

        System.out.println("Missão criada com sucesso");
        sc.nextLine();
    }

    private static void associarMaquinista(Scanner sc) {
        sc.nextLine();
        Maquinista maquinista = (Maquinista) selecionarPessoa(sc, "Selecione um maquinista", Maquinista.class);
        if (maquinista == null) {
            return;
        }
        if (maquinista.getTrem() != null) {
            System.out.println("Este maquinista já pertence a um trem");
            sc.nextLine();
            return;
        }

        Trem trem = selecionarTrem(sc);
        if (trem == null) {
            return;
        }
        if (trem.getMaquinista() != null) {
            System.out.println("Este trem já tem um maquinista!");
            sc.nextLine();
            return;
        }

        trem.setMaquinista(maquinista);
        System.out.println("Maquinista associado com sucesso!");
    }

    private static void associarMissao(Scanner sc) {
        sc.nextLine();
        Missao missao = selecionarMissao(sc);
        if (missao == null) {
            return;
        }

        if (missao.getTrem() != null) {
            System.out.println("Essa missão já está associada a um trem!");
            sc.nextLine();
            return;
        }

        Trem trem = selecionarTrem(sc);
        if (trem == null) {
            return;
        }

        if (getMissaoDoTrem(trem) != null) {
            System.out.println("Este trem já possui uma missão!");
            sc.nextLine();
            return;
        }

        missao.setTrem(trem);

        System.out.println("Missão associada com sucesso!");
        sc.nextLine();
    }

    private static Trem selecionarTrem(Scanner sc) {

        int i = 0;

        for (Trem t : trens) {
            i++;
            System.out.println("[" + i + "] " + "[--" + t.getIdFrota() + "--]");
        }

        System.out.println("Selecione um trem");
        int index = sc.nextInt();

        if (index > trens.size() || index <= 0) {
            System.out.println("Trem inválido!");
            sc.nextLine();
            return null;
        }

        return trens.get(index - 1);
    }

    private static Vagao selecionarVagao(Scanner sc) {
        List<Vagao> vagoesDisponiveis = new ArrayList<>();
        sc.nextLine();
        for (Vagao v : vagoes) {
            if (!isVagaoEmUso(v)) {
                vagoesDisponiveis.add(v);
            }
            ;
        }

        int i = 0;

        for (Vagao v : vagoesDisponiveis) {
            i++;
            String tipo;

            if (v instanceof VagaoPassageiro) {
                tipo = "Passageiro";
            } else {
                tipo = "Carga";
            }
            System.out.println("[" + i + "] " + "[" + tipo + "]");
        }


        System.out.println("Selecione um vagão");
        int index = sc.nextInt();

        if (index > vagoesDisponiveis.size() || index <= 0) {
            System.out.println("Vagão inválido!");
            sc.nextLine();
            return null;
        }

        return vagoesDisponiveis.get(index - 1);
    }

    private static Estacao selecionarEstacao(Scanner sc, String message) {
        int i = 0;
        System.out.println();
        for (Estacao e : estacoes) {
            i++;
            System.out.println("[" + i + "]: " + e.getNome());
        }

        System.out.println(message);

        int index = sc.nextInt();
        int quantidadeEstacoes = estacoes.size();

        if (index > quantidadeEstacoes ||  index <= 0) {
            System.out.println("Estação inválida!");
            return null;
        }

        return estacoes.get(index - 1);
    }

    private static Pessoa selecionarPessoa(Scanner sc, String message, Class<? extends Pessoa> tipo) {
        int i = 0;

        List<Pessoa> pessoas2 = new ArrayList<>();
        for (Pessoa p : pessoas) {
            if (tipo.isInstance(p)) {
                pessoas2.add(p);
            }
        }

        if (pessoas2.isEmpty()) {
            System.out.println("Não há pessoas disponiveis no sistema.");
            return null;
        }

        for (Pessoa p : pessoas2) {
            i++;
            System.out.println("[" + i + "]: " + p.getNome());
        }

        System.out.println(message);

        int index = sc.nextInt();
        if (index > pessoas2.size() || index <= 0) {
            System.out.println("Pessoa inválida");
            sc.nextLine();
            return null;
        }

        Pessoa pessoa = pessoas2.get(index - 1);

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
        for (Estacao e : estacoes) {
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

        if (index > missoes.size() || index <= 0) {
            System.out.println("Missão inválida");
            sc.nextLine();
            return null;
        }

        Missao missao = missoes.get(index - 1);

        return missao;
    }

    private static void mostrarEstacoes(Scanner sc) {
        System.out.println();
        if (estacoes.isEmpty()) {
            System.out.println("Não há estações no sistema.");
        } else {
            for (Estacao e : estacoes) {
                e.exibirInfo();
                System.out.println();
            }
        }

        sc.nextLine();
        sc.nextLine();
    }

    private static void criarEstacao(Scanner sc) {
        sc.nextLine();
        System.out.println("Digite o nome da estação");
        String nome = sc.nextLine();

        Estacao estacao = new Estacao(nome);
        estacao.criarBilheteria();
        estacoes.add(estacao);
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
        if (pessoas.isEmpty()) {
            System.out.println("Não há pessoas no sistema.");
        } else {
            for (Pessoa p : pessoas) {
                p.exibirInfo();
                System.out.println();
            }
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

        pessoas.add(passageiro);
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
            pessoas.add(Maquinista);
            System.out.println("Maquinista criado com sucesso");
            sc.nextLine();
            return;
        }

        Funcionario funcionario = new Funcionario(nome, cpf);
        pessoas.add(funcionario);
        System.out.println("Funcionário criado com sucesso");
        sc.nextLine();
    }

    private static Missao getMissaoDoTrem(Trem trem) {
        for (Estacao e : estacoes) {
            for (Missao m : e.getMissoes()) {
                if (m.getTrem() == trem &&
                        (m.getStatusMissao() == StatusMissao.AGENDADA || m.getStatusMissao() == StatusMissao.EM_CURSO)) {
                    return m;
                }
            }
        }
        return null;
    }

    private static boolean isVagaoEmUso(Vagao vagao) {
        if (vagao.getTrem() == null) return false;
        return true;
    }

}