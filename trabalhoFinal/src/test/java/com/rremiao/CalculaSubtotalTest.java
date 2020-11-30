package com.rremiao;

import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CalculaSubtotalTest {
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
        int response = sv.calculaImpostos(itens);
        Assertions.assertEquals(0, response);
    }
    @Test
    public void validaCalculoSubtotal(){
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

        int response = sv.calculaSubtotal(itens);
        Assertions.assertEquals(600, response);
    }

}   
