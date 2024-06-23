package com.carraes.patterns.strategy;

import com.carraes.entities.Funcionario;

public class ReajusteStrategy {
  private ReajusteSalarial estrategia;

  public ReajusteStrategy(ReajusteSalarial estrategia) {
    this.estrategia = estrategia;
  }

  public void executarReajuste(Funcionario funcionario) {
    estrategia.reajustar(funcionario);
  }

  public void setEstrategia(ReajusteSalarial estrategia) {
    this.estrategia = estrategia;
  }
}
