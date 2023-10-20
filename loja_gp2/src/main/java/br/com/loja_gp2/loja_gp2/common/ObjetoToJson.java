package br.com.loja_gp2.loja_gp2.common;

import com.google.gson.Gson;

public class ObjetoToJson {
    
    private static Gson gson = new Gson();

    /**
     * Converte qualquer objeto para o tipo string em formato json.
     * @param obj
     * @return json string
     */
    public static String conversor (Object obj) {
        return gson.toJson(obj);
    }
}
