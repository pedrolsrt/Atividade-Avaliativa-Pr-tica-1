import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Mecanico {
    private final String nome, cpf, especialidade, telefone;
    private Box box;

    Mecanico(String nome, String cpf, String especialidade, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.especialidade = especialidade;
        this.telefone = telefone;
    }

    public Box getBox() { return box; }
    public String getNome() { return nome; }
    public void setBox(Box box) { this.box = box; }

    @Override public String toString() {
        return nome + " | CPF: " + cpf + " | Especialidade: " + especialidade + " | Telefone: " + telefone;
    }
}

class Servico {
    private final String nome, categoria;
    private final int tempoEstimado;
    private final double valor;

    Servico(String nome, int tempoEstimado, double valor, String categoria) {
        this.nome = nome;
        this.tempoEstimado = tempoEstimado;
        this.valor = valor;
        this.categoria = categoria;
    }

    public String getCategoria() { return categoria; }

    @Override public String toString() {
        return nome + " | Categoria: " + categoria + " | Tempo estimado: " + tempoEstimado
                + " min | Valor: R$ " + String.format("%.2f", valor);
    }
}

class OrdemServico {
    private final int codigo;
    private final String cliente, modelo, placa, data;
    private final double valorEstimado;
    private final Servico servico;
    private String status = "aberta";
    private Box boxUtilizado;

    OrdemServico(int codigo, String cliente, String modelo, String placa, String data,
                 double valorEstimado, Servico servico) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.modelo = modelo;
        this.placa = placa;
        this.data = data;
        this.valorEstimado = valorEstimado;
        this.servico = servico;
    }

    public int getCodigo() { return codigo; }
    public String getStatus() { return status; }
    public Servico getServico() { return servico; }
    public Box getBoxUtilizado() { return boxUtilizado; }
    public void iniciarNoBox(Box box) { boxUtilizado = box; status = "em execução"; }
    public void finalizar() { status = "finalizada"; }

    @Override public String toString() {
        return "Código: " + codigo + " | Cliente: " + cliente + " | Veículo: " + modelo
                + " | Placa: " + placa + " | Data: " + data + " | Status: " + status
                + " | Valor estimado: R$ " + String.format("%.2f", valorEstimado)
                + "\nServiço: " + servico
                + "\nBox utilizado: " + (boxUtilizado == null ? "nenhum" : boxUtilizado.getNumero())
                + " | Mecânico: " + (boxUtilizado == null || boxUtilizado.getMecanico() == null
                    ? "nenhum" : boxUtilizado.getMecanico().getNome());
    }
}

class Box {
    private final int numero, capacidade;
    private final String tipoServico, localizacao;
    private Mecanico mecanico;
    private final List<OrdemServico> ordensAtivas = new ArrayList<>();
    private int totalFinalizadas;

    Box(int numero, String tipoServico, int capacidade, String localizacao) {
        this.numero = numero;
        this.tipoServico = tipoServico;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
    }

    public int getNumero() { return numero; }
    public Mecanico getMecanico() { return mecanico; }
    public List<OrdemServico> getOrdensAtivas() { return ordensAtivas; }
    public int getTotalFinalizadas() { return totalFinalizadas; }

    public boolean associarMecanico(Mecanico novo) {
        if (novo.getBox() != null && novo.getBox() != this) return false;
        if (mecanico != null) mecanico.setBox(null);
        mecanico = novo;
        novo.setBox(this);
        return true;
    }

    public boolean atribuir(OrdemServico ordem) {
        if (mecanico == null || !ordem.getStatus().equals("aberta")
                || ordensAtivas.size() >= capacidade
                || !tipoServico.equalsIgnoreCase(ordem.getServico().getCategoria())) return false;
        ordensAtivas.add(ordem);
        ordem.iniciarNoBox(this);
        return true;
    }

    public boolean finalizar(OrdemServico ordem) {
        if (!ordensAtivas.remove(ordem)) return false;
        ordem.finalizar();
        totalFinalizadas++;
        return true;
    }

    @Override public String toString() {
        return "Box " + numero + " | Tipo: " + tipoServico + " | Capacidade: " + capacidade
                + " | Localização: " + localizacao + " | Mecânico: "
                + (mecanico == null ? "nenhum" : mecanico.getNome());
    }
}

public class Main {
    private static final Scanner entrada = new Scanner(System.in);
    private static final List<Mecanico> mecanicos = new ArrayList<>();
    private static final List<Box> boxes = new ArrayList<>();
    private static final List<OrdemServico> ordens = new ArrayList<>();

    public static void main(String[] args) {
        criarDadosIniciais();
        int opcao;
        do {
            System.out.println("\n1. Cadastrar ordem\n2. Associar mecânico a box\n3. Atribuir ordem a box"
                    + "\n4. Listar ordens de um box\n5. Total finalizado por box"
                    + "\n6. Buscar por status\n7. Detalhes de uma ordem\n8. Finalizar ordem\n0. Sair");
            opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1: cadastrarOrdem(); break;
                case 2: associarMecanico(); break;
                case 3: atribuirOrdem(); break;
                case 4: listarBox(); break;
                case 5: totaisFinalizados(); break;
                case 6: buscarStatus(); break;
                case 7: detalhesOrdem(); break;
                case 8: finalizarOrdem(); break;
                case 0: System.out.println("Encerrado."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void criarDadosIniciais() {
        mecanicos.add(new Mecanico("Ana", "11111111111", "Mecânica", "31999990001"));
        mecanicos.add(new Mecanico("Bruno", "22222222222", "Elétrica", "31999990002"));
        mecanicos.add(new Mecanico("Carlos", "33333333333", "Funilaria", "31999990003"));
        boxes.add(new Box(1, "Mecânica", 2, "Setor A"));
        boxes.add(new Box(2, "Elétrica", 2, "Setor B"));
        boxes.add(new Box(3, "Funilaria", 1, "Setor C"));
    }

    private static String lerTexto(String pergunta) {
        System.out.print(pergunta);
        return entrada.nextLine().trim();
    }

    private static int lerInt(String pergunta) {
        while (true) {
            try { return Integer.parseInt(lerTexto(pergunta)); }
            catch (NumberFormatException e) { System.out.println("Digite um número inteiro."); }
        }
    }

    private static double lerValor(String pergunta) {
        while (true) {
            try {
                double valor = Double.parseDouble(lerTexto(pergunta).replace(',', '.'));
                if (valor >= 0 && Double.isFinite(valor)) return valor;
            } catch (NumberFormatException e) { /* Solicita novamente. */ }
            System.out.println("Digite um valor válido e não negativo.");
        }
    }

    private static OrdemServico encontrarOrdem(int codigo) {
        for (OrdemServico ordem : ordens) if (ordem.getCodigo() == codigo) return ordem;
        return null;
    }

    private static Box encontrarBox(int numero) {
        for (Box box : boxes) if (box.getNumero() == numero) return box;
        return null;
    }

    private static void cadastrarOrdem() {
        int codigo = lerInt("Código: ");
        if (encontrarOrdem(codigo) != null) { System.out.println("Código já cadastrado."); return; }
        String cliente = lerTexto("Cliente: ");
        String modelo = lerTexto("Modelo: ");
        String placa = lerTexto("Placa: ");
        String data = lerTexto("Data: ");
        String nomeServico = lerTexto("Nome do serviço: ");
        int tempo = lerInt("Tempo estimado (minutos): ");
        double valorServico = lerValor("Valor do serviço: ");
        String categoria = lerTexto("Categoria (Mecânica, Elétrica ou Funilaria): ");
        double estimativa = lerValor("Valor estimado da ordem: ");
        if (cliente.isEmpty() || modelo.isEmpty() || placa.isEmpty() || data.isEmpty()
                || nomeServico.isEmpty() || tempo <= 0 || categoria.isEmpty()) {
            System.out.println("Dados obrigatórios inválidos. Ordem não cadastrada."); return;
        }
        ordens.add(new OrdemServico(codigo, cliente, modelo, placa, data, estimativa,
                new Servico(nomeServico, tempo, valorServico, categoria)));
        System.out.println("Ordem cadastrada com status aberta.");
    }

    private static void associarMecanico() {
        for (int i = 0; i < mecanicos.size(); i++)
            System.out.println((i + 1) + ". " + mecanicos.get(i));
        int indice = lerInt("Número do mecânico na lista: ") - 1;
        for (Box box : boxes) System.out.println(box);
        Box box = encontrarBox(lerInt("Número do box: "));
        if (indice < 0 || indice >= mecanicos.size() || box == null) {
            System.out.println("Mecânico ou box inexistente."); return;
        }
        System.out.println(box.associarMecanico(mecanicos.get(indice))
                ? "Associação realizada." : "Esse mecânico já é responsável por outro box.");
    }

    private static void atribuirOrdem() {
        OrdemServico ordem = encontrarOrdem(lerInt("Código da ordem: "));
        Box box = encontrarBox(lerInt("Número do box: "));
        if (ordem == null || box == null) { System.out.println("Ordem ou box inexistente."); return; }
        System.out.println(box.atribuir(ordem) ? "Ordem atribuída e em execução."
                : "Não foi possível atribuir: verifique status, categoria, capacidade e mecânico do box.");
    }

    private static void listarBox() {
        Box box = encontrarBox(lerInt("Número do box: "));
        if (box == null) { System.out.println("Box inexistente."); return; }
        System.out.println(box);
        for (OrdemServico ordem : box.getOrdensAtivas()) System.out.println(ordem + "\n");
        System.out.println("Total de ordens atribuídas atualmente: " + box.getOrdensAtivas().size());
    }

    private static void totaisFinalizados() {
        for (Box box : boxes)
            System.out.println("Box " + box.getNumero() + ": " + box.getTotalFinalizadas() + " finalizada(s)");
    }

    private static void buscarStatus() {
        String status = lerTexto("Status (aberta, em execução, finalizada): ");
        int total = 0;
        for (OrdemServico ordem : ordens) {
            if (ordem.getStatus().equalsIgnoreCase(status)) {
                System.out.println(ordem + "\n");
                total++;
            }
        }
        System.out.println("Total encontrado: " + total);
    }

    private static void detalhesOrdem() {
        OrdemServico ordem = encontrarOrdem(lerInt("Código da ordem: "));
        System.out.println(ordem == null ? "Ordem inexistente." : ordem);
    }

    private static void finalizarOrdem() {
        OrdemServico ordem = encontrarOrdem(lerInt("Código da ordem: "));
        if (ordem == null || ordem.getBoxUtilizado() == null
                || !ordem.getBoxUtilizado().finalizar(ordem))) {
            System.out.println("Ordem inexistente ou não está em execução."); return;
        }
        System.out.println("Ordem finalizada. O box utilizado permanece registrado na ordem.");
    }
}
