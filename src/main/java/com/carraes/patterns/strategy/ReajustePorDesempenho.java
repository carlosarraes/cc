package com.carraes.patterns.strategy;

import com.carraes.entities.Funcionario;

public class ReajustePorDesempenho implements ReajusteSalarial {
  @Override
  public void reajustar(Funcionario funcionario) {
    funcionario.setSalario(funcionario.getSalario() * 1.10);
  }
}
