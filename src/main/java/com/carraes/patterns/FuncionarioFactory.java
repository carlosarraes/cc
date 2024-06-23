package com.carraes.patterns;

import com.carraes.entities.Cargo;
import com.carraes.entities.Funcionario;
import com.carraes.entities.FuncionarioTerceirizado;
import com.carraes.entities.Setor;

// Permite a criação de diferentes tipos de Funcionario com base em parâmetros fornecidos.
public class FuncionarioFactory {
  public static Funcionario criarFuncionario(
      String nome, String telefone, String endereco, double salario, Setor setor, Cargo cargo) {
    return new Funcionario(nome, telefone, endereco, salario, setor, cargo);
  }

  public static FuncionarioTerceirizado criarFuncionarioTerceirizado(
      String nome,
      String telefone,
      String endereco,
      double salario,
      Setor setor,
      Cargo cargo,
      String empresaContratada,
      int tempoPrevistoMeses) {
    return new FuncionarioTerceirizado(
        nome, telefone, endereco, salario, setor, cargo, empresaContratada, tempoPrevistoMeses);
  }
}
