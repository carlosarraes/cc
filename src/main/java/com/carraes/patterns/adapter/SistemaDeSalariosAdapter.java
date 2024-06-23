package com.carraes.patterns.adapter;

import com.carraes.entities.Funcionario;

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
