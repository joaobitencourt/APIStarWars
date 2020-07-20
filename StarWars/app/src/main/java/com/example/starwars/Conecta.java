package com.example.starwars;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Conecta {
    private static final String LOG_TAG = Conecta.class.getSimpleName();
    private static final String Url = "https://swapi.dev/api/planets/?";
    private static final String QUERY_PARAM = "search"; //parametro para buscar

    public static String procuraInfoPlanetas(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String planetaString = null;
        try {
            // Construção da URI de Busca
            Uri builtURI = Uri.parse(Url).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .build();

            Log.d("url4",builtURI.toString());
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            Log.d("url2",urlConnection.toString());
            urlConnection.setRequestMethod("GET");
            Log.d("url7",urlConnection.toString());
            urlConnection.connect();
            Log.d("url6",urlConnection.toString());

            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            Log.d("url9",inputStream.toString());
            // Cria o buffer para o input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));
            Log.d("url10",reader.toString());
            // Usa o StringBuilder para receber a resposta.
            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Adiciona a linha a string.
                builder.append(linha);
                builder.append("\n");
                Log.d("url12",builder.toString());

            }
            if (builder.length() == 0) {
                // se o stream estiver vazio não faz nada
                return null;
            }
            planetaString = builder.toString();
            Log.d("uraa13",planetaString);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // escreve o Json no log
        Log.d(LOG_TAG, planetaString);
        return planetaString;


}
}



