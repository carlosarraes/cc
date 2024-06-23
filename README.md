# Clean Code e Padrões de Projeto

Este projeto tem como objetivo demonstrar a aplicação de princípios de Clean Code e Padrões de Projeto em uma aplicação Java que simula a gestão de funcionários de uma empresa.

Repositório pode ser acessado [clicando aqui](https://github.com/carlosarraes/cc).

PS.: O pdf corta um pouco os code blocks, para melhor visualização, recomendo ver o readme no github.

## 1) Estrutura

```java
enum Setor {
  DESENVOLVIMENTO,
  DEVOPS,
  BANCO_DE_DADOS,
}

enum Cargo {
  ESTAGIARIO,
  JUNIOR,
  PLENO,
  SENIOR,
}


@Data
public class Funcionario {
  private String nome;
  private List<String> telefones = new ArrayList<>();
  private String endereco;
  private double salario;
  private Setor setor;
  private Cargo cargo;

  public Funcionario(String nome, String telefone, String endereco, double salario, Setor setor, Cargo cargo) {
    this.nome = nome;
    this.telefones.add(telefone);
    this.endereco = endereco;
    this.salario = salario;
    this.setor = setor;
    this.cargo = cargo;
  }

  public void reajustarSalario(double porcentagem) {
    this.salario += this.salario * porcentagem / 100;
  }
}

@Data
@EqualsAndHashCode(callSuper = true)
class FuncionarioTerceirizado extends Funcionario {
    private String empresaContratada;
    private int tempoPrevistoMeses;

    public FuncionarioTerceirizado(String nome, String telefone, String endereco, double salario, Setor setor, Cargo cargo,
                                   String empresaContratada, int tempoPrevistoDePermanencia) {
        super(nome, telefone, endereco, salario, setor, cargo);
        this.empresaContratada = empresaContratada;
        this.tempoPrevistoMeses = tempoPrevistoMeses;
    }

    @Override
    public void reajustarSalario(double porcentagem) {
        System.out.println("Funcionários terceirizados não recebem reajuste de salário.");
    }
}
```

- Cada classe tem uma única responsabilidade. `Funcionario` gerencia os dados básicos dos funcionários, enquanto `FuncionarioTerceirizado` adiciona responsabilidades específicas para funcionários terceirizados.
- As classes estão abertas para extensão, mas fechadas para modificação. `FuncionarioTerceirizado` estende `Funcionario` para adicionar funcionalidade sem modificar a classe base.
- `FuncionarioTerceirizado` pode substituir Funcionario onde quer que Funcionario seja esperado, respeitando a interface pública da classe base, embora o método reajustarSalario seja sobrescrito para adicionar comportamento específico.
- Os atributos e métodos estão bem segregados entre as classes.
- Não temos dependências injetadas nas classes. Em projetos maiores, poderíamos usar interfaces e injeção de dependência para seguir este princípio.

## 2) Patterns

### 2.1) Criacional - Singleton -> [commit](https://github.com/carlosarraes/cc/commit/dddeada72e325b07788dc5ab2e98370bbd2f1cf3)

```java
public class FuncionarioSingleton {
  private static FuncionarioSingleton instancia;
  private List<Funcionario> funcionarios;

  private FuncionarioSingleton() {
    funcionarios = new ArrayList<>();
  }

  public static synchronized FuncionarioSingleton getInstancia() {
    if (instancia == null) {
      instancia = new FuncionarioSingleton();
    }
    return instancia;
  }

  public void adicionarFun
    cionario(Funcionario funcionario) {
    funcionarios.add(funcionario);
  }

  public List<Funcionario> getFuncionarios() {
    return funcionarios;
  }
}
```

Seu objetivo principal é garantir que uma classe tenha apenas uma única instância e fornecer um ponto de acesso global a essa instância.

#### Exemplo:

```java
public class Main {
    public static void main(String[] args) {
        FuncionarioSingleton singleton = FuncionarioSingleton.getInstancia();

        Funcionario funcionario1 = new Funcionario("Carlos", "12345-6789", "Rua A, 123", 3000.0, Setor.DESENVOLVIMENTO, Cargo.JUNIOR);
        Funcionario funcionario2 = new Funcionario("Ana", "98765-4321", "Rua B, 456", 4000.0, Setor.DEVOPS, Cargo.PLENO);

        singleton.adicionarFuncionario(funcionario1);
        singleton.adicionarFuncionario(funcionario2);

        for (Funcionario funcionario : singleton.getFuncionarios()) {
            System.out.println("Nome: " + funcionario.getNome() + ", Salário: " + funcionario.getSalario());
        }
    }
}
```

### 2.2) Criacional - Factory -> [commit](https://github.com/carlosarraes/cc/commit/3c6e2e3f165eccd0b27bda3dde5e17a60fc2fcee)

```java
public class FuncionarioFactory {
    public static Funcionario criarFuncionario(String nome, String telefone, String endereco, double salario, Setor setor, Cargo cargo) {
        return new Funcionario(nome, telefone, endereco, salario, setor, cargo);
    }

    public static FuncionarioTerceirizado criarFuncionarioTerceirizado(String nome, String telefone, String endereco, double salario, Setor setor, Cargo cargo, String empresaContratada, int tempoPrevistoMeses) {
        return new FuncionarioTerceirizado(nome, telefone, endereco, salario, setor, cargo, empresaContratada, tempoPrevistoMeses);
    }
}
```

Fornece uma interface para criar objetos em uma superclasse, permitindo que as subclasses alterem o tipo de objetos que serão criados. Isso permite que a classe delegue a responsabilidade de criação para subclasses específicas.

#### Exemplo:

```java
public class Main {
    public static void main(String[] args) {
        Funcionario funcionario = FuncionarioFactory.criarFuncionario("Carlos", "12345-6789", "Rua A, 123", 3000.0, Setor.DESENVOLVIMENTO, Cargo.JUNIOR);

        FuncionarioTerceirizado terceirizado = FuncionarioFactory.criarFuncionarioTerceirizado("Ana", "98765-4321", "Rua B, 456", 4000.0, Setor.DEVOPS, Cargo.PLENO, "Empresa XYZ", 12);

        System.out.println(funcionario.getNome());
        System.out.println(terceirizado.getNome());
    }
}

```

### 2.3) Estrutural - Adapter -> [commit](https://github.com/carlosarraes/cc/commit/da428ab34d7f019f44b20b9ae4f50bc915418e98)

```java
public interface SistemaDeSalarios {
    void pagarSalario(Funcionario funcionario);
}

public class SistemaExternoDePagamentos {
    public void realizarPagamento(String nome, double salario) {
        System.out.println("Pagamento de " + salario + " realizado para " + nome);
    }
}

public class SistemaDeSalariosAdapter implements SistemaDeSalarios {
    private SistemaExternoDePagamentos sistemaExterno;

    public SistemaDeSalariosAdapter(SistemaExternoDePagamentos sistemaExterno) {
        this.sistemaExterno = sistemaExterno;
    }

    @Override
    public void pagarSalario(Funcionario funcionario) {
        sistemaExterno.realizarPagamento(funcionario.getNome(), funcionario.getSalario());
    }
}
```

Permite que classes com interfaces incompatíveis trabalhem juntas, convertendo a interface de uma classe em outra interface que o cliente espera.

#### Exemplo:

```java
public class Main {
    public static void main(String[] args) {
        Funcionario funcionario = new Funcionario("Carlos", "12345-6789", "Rua A, 123", 3000.0, Setor.DESENVOLVIMENTO, Cargo.JUNIOR);
        FuncionarioTerceirizado terceirizado = new FuncionarioTerceirizado("Ana", "98765-4321", "Rua B, 456", 4000.0, Setor.DEVOPS, Cargo.PLENO, "Empresa XYZ", 12);

        SistemaExternoDePagamentos sistemaExterno = new SistemaExternoDePagamentos();
        SistemaDeSalarios sistemaDeSalarios = new SistemaDeSalariosAdapter(sistemaExterno);

        sistemaDeSalarios.pagarSalario(funcionario);
        sistemaDeSalarios.pagarSalario(terceirizado);
    }
}

```

### 2.4) Comportamental - Strategy -> [commit](https://github.com/carlosarraes/cc/commit/98cb687a1f3478c31536b4af6f1c2fe3eb60617f)

```java
public interface ReajusteSalarial {
    void reajustar(Funcionario funcionario);
}

public class ReajustePorDesempenho implements ReajusteSalarial {
    @Override
    public void reajustar(Funcionario funcionario) {
        funcionario.setSalario(funcionario.getSalario() * 1.10); // 10% de aumento
    }
}

public class ReajustePorInflacao implements ReajusteSalarial {
    @Override
    public void reajustar(Funcionario funcionario) {
        funcionario.setSalario(funcionario.getSalario() * 1.05); // 5% de aumento
    }
}

public class GerenciadorDeReajuste {
    private ReajusteSalarial estrategia;

    public GerenciadorDeReajuste(ReajusteSalarial estrategia) {
        this.estrategia = estrategia;
    }

    public void executarReajuste(Funcionario funcionario) {
        estrategia.reajustar(funcionario);
    }

    public void setEstrategia(ReajusteSalarial estrategia) {
        this.estrategia = estrategia;
    }
}
```

Permite definir uma família de algoritmos, encapsular cada um deles e torná-los intercambiáveis. O Strategy permite que o algoritmo varie independentemente dos clientes que o utilizam.Permite definir uma família de algoritmos, encapsular cada um deles e torná-los intercambiáveis. O Strategy permite que o algoritmo varie independentemente dos clientes que o utilizam.

#### Exemplo:

```java
public class Main {
    public static void main(String[] args) {
        Funcionario funcionario = new Funcionario("Carlos", "12345-6789", "Rua A, 123", 3000.0, Setor.DESENVOLVIMENTO, Cargo.JUNIOR);

        GerenciadorDeReajuste gerenciador = new GerenciadorDeReajuste(new ReajustePorDesempenho());
        gerenciador.executarReajuste(funcionario);
        System.out.println("Salário após reajuste por desempenho: " + funcionario.getSalario());

        gerenciador.setEstrategia(new ReajustePorInflacao());
        gerenciador.executarReajuste(funcionario);
        System.out.println("Salário após reajuste por inflação: " + funcionario.getSalario());
    }
}
```
