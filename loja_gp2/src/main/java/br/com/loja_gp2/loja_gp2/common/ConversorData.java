package br.com.loja_gp2.loja_gp2.common;

import java.text.SimpleDateFormat;

public class ConversorData {

    public static String converterDateParaDataHora(java.util.Date date){
        return new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(date);
    }

}
// 03/10/2023 20:59:32
// 2023-10-03T20:59:23