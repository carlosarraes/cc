package com.carraes.patterns;

import com.carraes.entities.Funcionario;
import java.util.ArrayList;
import java.util.List;

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

  public void adicionarFuncionario(Funcionario funcionario) {
    funcionarios.add(funcionario);
  }

  public List<Funcionario> getFuncionarios() {
    return funcionarios;
  }
}
