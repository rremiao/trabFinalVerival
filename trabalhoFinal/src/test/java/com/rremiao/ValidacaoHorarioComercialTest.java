package com.rremiao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;

import com.rremiao.SistVendasException.Causa;

import org.junit.jupiter.api.Test;

public class ValidacaoHorarioComercialTest {
    @Test
    public void validaTresProdutosExistentes() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",1500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,15));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,1000));
        itens.add(new ItemVenda(2,30,1,2000));
        itens.add(new ItemVenda(3,50,1,1500));

        RegraValidacao regra = new ValidacaoHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }

    @Test
    public void validaProdutosNaoExistentes() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",1500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(11,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(33,3));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(55,3));

        List<ItemVenda> itens = new ArrayList<>(3);

        try{

            itens.add(new ItemVenda(1,10,2,3344));
            itens.add(new ItemVenda(2,30,3,334));
            itens.add(new ItemVenda(3,55,1,34));
    
        }
        catch(SistVendasException e){
            Causa actualException = e.getCausa();

            e.setCausa(Causa.PRODUTO_NAO_CADASTRADO_ESTOQUE);

            Causa expectedException = e.getCausa();

            Assertions.assertEquals(expectedException, actualException);
        }
    }

    @Test
    public void validaProdutoMaisQueEstoque() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",1500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(3);
        try{

            itens.add(new ItemVenda(1,10,300,3344));
            itens.add(new ItemVenda(2,30,309,334));
            itens.add(new ItemVenda(3,55,111,34));
    
        }
        catch(SistVendasException e){
            Causa actualException = e.getCausa();

            e.setCausa(Causa.QUANTIDADE_INSUFICIENTE);

            Causa expectedException = e.getCausa();

            Assertions.assertEquals(expectedException, actualException);
        }
    }

    @Test
    public void validaProdutoLimiteEstoque() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",1500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(0);

        RegraValidacao regra = new ValidacaoHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }

    @Test
    public void validaHorarioLimiteMinimo() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",1500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,1000));
        itens.add(new ItemVenda(2,30,3,2000));
        itens.add(new ItemVenda(3,50,10,1500));

        FactoryValidacao factory = mock(FactoryValidacao.class);
        factory.setLocalTime(LocalTime.parse("08:00:00"));

        RegraValidacao regra = new ValidacaoHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }

    @Test
    public void validaHorarioLimiteMaximo() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",1500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,1000));
        itens.add(new ItemVenda(2,30,3,2000));
        itens.add(new ItemVenda(3,50,2,1500));

        FactoryValidacao factory = mock(FactoryValidacao.class);
        factory.setLocalTime(LocalTime.parse("18:00:00"));

        RegraValidacao regra = new ValidacaoHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }



    @Test
    public void validaDentroHorarioLimite() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",1500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,1000));
        itens.add(new ItemVenda(2,30,3,2000));
        itens.add(new ItemVenda(3,50,3,1500));

        FactoryValidacao factory = mock(FactoryValidacao.class);
        factory.setLocalTime(LocalTime.parse("08:00:00"));

        RegraValidacao regra = new ValidacaoHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }

    @Test
    public void regraImpostosComprasGrandes() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Bicicleta",50000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Relógio",5000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Calça",5000.0));
        when(produtos.recupera(20)).thenReturn(new Produto(20,"Carro",550000.0));
        when(produtos.recupera(40)).thenReturn(new Produto(40,"Moto",15000.0));
        when(produtos.recupera(60)).thenReturn(new Produto(60,"Atari",2000.0));
        

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,15));
        when(estoque.recupera(20)).thenReturn(new ItemEstoque(20,5));
        when(estoque.recupera(40)).thenReturn(new ItemEstoque(40,3));
        when(estoque.recupera(60)).thenReturn(new ItemEstoque(60,15));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,50000));
        itens.add(new ItemVenda(2,30,3,5000));
        itens.add(new ItemVenda(3,50,1,5000));
        itens.add(new ItemVenda(4,20,2,550000));
        itens.add(new ItemVenda(5,40,1,15000));
        itens.add(new ItemVenda(6,60,10,2000));

        RegraValidacao regra = new ValidacaoHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }
}
