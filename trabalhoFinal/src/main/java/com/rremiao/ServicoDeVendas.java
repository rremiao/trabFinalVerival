package com.rremiao;

import java.util.List;

public class ServicoDeVendas {
    private RegraImposto regraImposto;
    private Produtos produtos;
    private Estoque estoque;
    private FactoryValidacao factoryValidacao;

    public ServicoDeVendas(Produtos produtos, Estoque estoque, RegraImposto regraImposto, FactoryValidacao factoryValidacao) {
        this.produtos = produtos;
        this.estoque = estoque;
        this.regraImposto = regraImposto;
        this.factoryValidacao = factoryValidacao;
    }

    public void valida(List<ItemVenda> itens) {
        factoryValidacao.getRegraValidacao().valida(produtos, estoque, itens);
    }

    public Integer calculaSubtotal(List<ItemVenda> itens) {
        return (int) (itens.stream().mapToDouble(it -> it.getValorVendido()).sum());
    }

    public Integer calculaImpostos(List<ItemVenda> itens) {
        return (int) regraImposto.calcular(itens);
    }

    public Integer calculaPrecoFinal(List<ItemVenda> itens) {
        return calculaSubtotal(itens) + calculaImpostos(itens);
    }

    public Integer[] todosValores(List<ItemVenda> itens) {
        Integer[] valores = new Integer[3];
        valores[0] = calculaSubtotal(itens);
        valores[1] = calculaImpostos(itens);
        valores[2] = valores[0] + valores[1]; //FALTAVA ULTIMA POSICAO DO ARRAY
        return valores;
    }

    public void setRegraImposto(RegraImposto aux){ //CRIEI O SETREGRA P FORÇAR O TIPO DE IMPOSTO
        this.regraImposto = aux;
    }
}
