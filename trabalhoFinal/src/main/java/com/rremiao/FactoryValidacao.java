package com.rremiao;

import java.time.LocalTime;

public class FactoryValidacao {
    private LocalTime agora;

    // Deve receber o horário corrente (agora) como parâmetro
    public FactoryValidacao(LocalTime agora){
        this.agora = agora;
    }

    public void setLocalTime (LocalTime now){ //CRIEI O SETLOCALTIME P FORÇAR TESTE DE REGRA
        this.agora = now;
    }

    public RegraValidacao getRegraValidacao(){
        if (LocalTime.parse("08:00").isAfter(agora) &&
            LocalTime.parse("06:00").isBefore(agora)){
                return new ValidacaoHorarioComercial();
        }else{
            return new ValidacaoForaHorarioComercial();
        }
    } 
}
