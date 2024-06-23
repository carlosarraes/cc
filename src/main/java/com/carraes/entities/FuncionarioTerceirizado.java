package com.carraes.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FuncionarioTerceirizado extends Funcionario {
  private String empresaContratada;
  private int tempoPrevistoMeses;

  public FuncionarioTerceirizado(
      String nome,
      String telefone,
      String endereco,
      double salario,
      Setor setor,
      Cargo cargo,
      String empresaContratada,
      int tempoPrevistoMeses) {
    super(nome, telefone, endereco, salario, setor, cargo);
    this.empresaContratada = empresaContratada;
    this.tempoPrevistoMeses = tempoPrevistoMeses;
  }

  @Override
  public void reajustarSalario(double porcentagem) {
    System.out.println("Funcionários terceirizados não recebem reajuste de salário.");
  }
}
