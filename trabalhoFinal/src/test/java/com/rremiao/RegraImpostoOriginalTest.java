package com.rremiao;

import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RegraImpostoOriginalTest {
    private RegraImposto ri = null;

    @BeforeEach
    public void setUp() {
        ri = new RegraImpostoOriginal();
    }

    @Test
    public void isVazio(){
        List<ItemVenda> itens = new ArrayList<>(0);
        double response = ri.calcular(itens);
        Assertions.assertEquals(0, response);

    }

    @Test
    public void validaImposto() {
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
        
        int aux = (((100/100) * 10) + ((200/100) * 10) + ((300/100) * 10));
        double response = ri.calcular(itens);
        Assertions.assertEquals(aux, response);
    }
}