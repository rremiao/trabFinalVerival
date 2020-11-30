package com.rremiao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.rremiao.SistVendasException.Causa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ValidacaoForaHorarioComercialTest {

    @Test
    public void validaocaoTresProdutosForaHorarioComercial() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Bicicleta",200.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Relógio",333.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Calça",222.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,15));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,200));
        itens.add(new ItemVenda(2,30,3,333));
        itens.add(new ItemVenda(3,50,1,222));
    
        RegraValidacao regra = new ValidacaoForaHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }

    @Test
    public void validaocaoProdutosNaoCadastradosForaHorarioComercial() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Bicicleta",3333.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Relógio",333.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Calça",33.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,15));

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
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,1));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(3);

        try{

            itens.add(new ItemVenda(1,10,50,50000));
            itens.add(new ItemVenda(2,30,50,5000));
            itens.add(new ItemVenda(3,50,50,5000));
    
        }
        catch(SistVendasException e){
            Causa actualException = e.getCausa();

            e.setCausa(Causa.QUANTIDADE_INSUFICIENTE);

            Causa expectedException = e.getCausa();

            Assertions.assertEquals(expectedException, actualException);
        }
    }

    @Test
    public void validaLimiteEstoque() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",10.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",20.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",15.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,10));
        itens.add(new ItemVenda(2,30,3,20));
        itens.add(new ItemVenda(3,50,1,15));

        RegraValidacao regra = new ValidacaoForaHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }

    @Test
    public void isVazio(){
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Bicicleta",50000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Relógio",5000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Calça",5000.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,15));

        List<ItemVenda> itens = new ArrayList<>(0);

        RegraValidacao regra = new ValidacaoForaHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));

    }


    @Test
    public void validaValorMaiorQue5K() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",5000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",5000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",5500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(3);

        try{
            itens.add(new ItemVenda(1,10,5,10000));
            itens.add(new ItemVenda(2,30,3,20000));
            itens.add(new ItemVenda(3,50,10,15000));
        }
        catch(SistVendasException e){
            Causa actualException = e.getCausa();

            e.setCausa(Causa.VENDA_COM_ITEM_MUITO_CARO);

            Causa expectedException = e.getCausa();

            Assertions.assertEquals(expectedException, actualException);
        }
    }
    @Test
    public void validaAntesHorarioLimiteMinimo() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",10.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",20.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",15.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,10));
        itens.add(new ItemVenda(2,30,3,20));
        itens.add(new ItemVenda(3,50,3,15));

        FactoryValidacao factory = mock(FactoryValidacao.class);
        factory.setLocalTime(LocalTime.parse("06:00:00"));

        RegraValidacao regra = new ValidacaoForaHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }

    @Test
    public void validaAlemHorarioLimiteMaximo() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10,"Prod10",10.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30,"Prod30",20.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50,"Prod15",15.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10,5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30,3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50,10));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1,10,2,100));
        itens.add(new ItemVenda(2,30,3,200));
        itens.add(new ItemVenda(3,50,10,300));

        FactoryValidacao factory =  new FactoryValidacao(null);
        factory.setLocalTime(LocalTime.parse("22:00:00"));

        RegraValidacao regra = new ValidacaoForaHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos,estoque,itens));
    }
    
}
