package com.rremiao;

import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RegraImpostoComprasGrandesTest {
    private RegraImposto ri = null;

    @BeforeEach
    public void setUp() {
        ri = new RegraImpostoComprasGrandes();
    }

    @Test
    public void isVazio(){
        List<ItemVenda> itens = new ArrayList<>(0);
        double response = ri.calcular(itens);
        Assertions.assertEquals(0, response);

    }

    @Test
    public void validaImpostoMenosQueTresItens() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",100.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",200.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));

        List<ItemVenda> itens = new ArrayList<>(2);
        itens.add(new ItemVenda(1,10,2,100));
        itens.add(new ItemVenda(2,30,3,200));

        double response = ri.calcular(itens);
        int calc = (((100/100) * 10) + ((200/100) * 10));
        Assertions.assertEquals(calc, response);
    }

    @Test
    public void validaImpostoTresItens() {
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

        double response = ri.calcular(itens);
        int calc = (((100/100) * 10) + ((200/100) * 10) + ((300/100) * 10));
        Assertions.assertEquals(calc, response);
        
    }

    @Test
    public void validaImpostoMaisQueTresItens() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",100.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",200.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",300.0));
        when(produtos.recupera(60)).thenReturn(new Produto(60,"Prod22",400.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,15));
        when(estoque.recupera(60)).thenReturn(new ItemEstoque(60,20));

        List<ItemVenda> itens = new ArrayList<>(4);
        itens.add(new ItemVenda(1,10,2,100));
        itens.add(new ItemVenda(2,30,3,200));
        itens.add(new ItemVenda(3,50,1,300));
        itens.add(new ItemVenda(4,60,5,400));
        double response = ri.calcular(itens);
        int calc = (((100/100) * 5) + ((200/100) * 10) + ((300/100) * 10) + ((400/100) * 10));
        
        Assertions.assertEquals(calc, response);
    }

}
