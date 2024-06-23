package com.carraes.entities;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Funcionario {
  private String nome;
  private List<String> telefones = new ArrayList<>();
  private String endereco;
  private double salario;
  private Setor setor;
  private Cargo cargo;

  public Funcionario(
      String nome, String telefone, String endereco, double salario, Setor setor, Cargo cargo) {
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
