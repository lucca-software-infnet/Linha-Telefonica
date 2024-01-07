import java.util.ArrayList;
import java.util.List;

interface Consumo {
    float calcular();
}

abstract class LinhaTelefonica implements Consumo {
    private int qtdMinutosGastos;
    private String numero;
    private Cliente cliente;

    public LinhaTelefonica(int qtdMinutosGastos, String numero, Cliente cliente) {
        setQtdMinutosGastos(qtdMinutosGastos);
        this.numero = numero;
        this.cliente = cliente;
    }

    public int getQtdMinutosGastos() {
        return qtdMinutosGastos;
    }

    public void setQtdMinutosGastos(int qtdMinutosGastos) {
        if (qtdMinutosGastos >= 0) {
            this.qtdMinutosGastos = qtdMinutosGastos;
        } else {
            System.out.println("Digite um número de minutos gastos válido.");
        }
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String toString() {
        return "Número: " + numero + ", Minutos Gastos: " + qtdMinutosGastos;
    }

    public abstract float calcular();
}

class Fixa extends LinhaTelefonica {
    final public static int FRANQUIA = 30;
    int minutosExcedentes = getQtdMinutosGastos() - FRANQUIA ;
  
    public Fixa(int qtdMinutosGastos, String numero, Cliente cliente) {
        super(qtdMinutosGastos, numero, cliente);
    }

  
  public float calcular(int minutosExcedentes){
      if(minutosExcedentes < 0){
        return 0 ;
      }else{
        return minutosExcedentes * 0.05f ;
      }
    }

  
    public float calcular() {
   float valorPadrao = 45 ;
  return valorPadrao + calcular(minutosExcedentes);
}

  
  
    
}

final class Movel extends LinhaTelefonica {
    private boolean planoDeDadosHabilitado;

    public Movel(int qtdMinutosGastos, String numero, Cliente cliente, boolean planoDeDadosHabilitado) {
        super(qtdMinutosGastos, numero, cliente);
        this.planoDeDadosHabilitado = planoDeDadosHabilitado;
    }

    public float calcular() {
        float custoMinutos = getQtdMinutosGastos() * 0.20f;
        if (planoDeDadosHabilitado) {
            return custoMinutos + 40.0f;
        } else {
            return custoMinutos;
        }
    }
}

class Cliente {
    private String nome;
    private String endereco;
    private List<LinhaTelefonica> linhas;

    public Cliente(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.linhas = new ArrayList<>();
    }

    public void adicionarLinha(LinhaTelefonica linha) {
        linhas.add(linha);
    }

    public List<LinhaTelefonica> getLinhas() {
        return linhas;
    }

    public String toString() {
        return "Nome: " + nome + ", Endereço: " + endereco;
    }
}

public class Main {
    public static void main(String[] args) {
        Cliente lucca = new Cliente("Lucca", "Avenida das Comunicações");
        Cliente matheus = new Cliente("Matheus", "Rua Evandro Carlos de Andrade");

        lucca.adicionarLinha(new Fixa(100, "123456789", lucca));
        matheus.adicionarLinha(new Movel(200, "987654321", matheus, true));

        System.out.println("\nDados do Cliente 1:");
        System.out.println(lucca);

        for (LinhaTelefonica linha : lucca.getLinhas()) {
            System.out.println("Detalhes da Linha Fixa: " + linha);
            float custo = linha.calcular() ;
            if (custo < 0.0f) {
                System.out.println("Digite um numero valido.");
            } else {
                System.out.println("Custo: R$" + custo);
            }
        }

        System.out.println("\nDados do Cliente 2:");
        System.out.println(matheus);

        for (LinhaTelefonica linha : matheus.getLinhas()) {
            System.out.println("Detalhes da Linha Móvel: " + linha);
            float custo = linha.calcular();
            if (custo < 0.0f) {
                System.out.println("Digite um numero valido.");
            } else {
                System.out.println("Custo: R$" + custo);
            }
        }
    }
}
