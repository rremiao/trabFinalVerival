package com.rremiao;

import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;


public class TodosOsValoresTest {
    private ServicoDeVendas sv = null;
    private RegraImpostoOriginal regra = new RegraImpostoOriginal();

    @BeforeEach
    public void setUp() {
        sv = new ServicoDeVendas(null, null, null, null);
    }

    @Test
    public void isVazio(){
        List<ItemVenda> itens = new ArrayList<>(0);
        sv.setRegraImposto(regra);
        Integer response[] = sv.todosValores(itens);
        Integer aux[] = new Integer[3];
        
        aux[0]=0;
        aux[1]=0;
        aux[2]=0;

        Assertions.assertArrayEquals(aux, response);
    }

    @Test
    public void validaPosicaoArray(){
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",100.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",200.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",300.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,15));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,100));
        itens.add(new ItemVenda(2,30,3,200));
        itens.add(new ItemVenda(3,50,1,300));

        Integer response [] = new Integer[3];
        Integer aux[] = new Integer[3];

        sv.setRegraImposto(regra);

        int subtotal = sv.calculaSubtotal(itens);
        int impostos = sv.calculaImpostos(itens);
        int precoFinal = sv.calculaPrecoFinal(itens);

       response = sv.todosValores(itens);

        aux[0] = subtotal;
        aux[1] = impostos;
        aux[2] = precoFinal;

        Assertions.assertArrayEquals(aux, response);

    }
}
