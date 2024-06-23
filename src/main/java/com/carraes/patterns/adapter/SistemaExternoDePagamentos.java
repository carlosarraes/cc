package com.carraes.patterns.adapter;

public class SistemaExternoDePagamentos {
  public void realizarPagamento(String nome, double salario) {
    System.out.println("Pagamento de " + salario + " realizado para " + nome);
  }
}
