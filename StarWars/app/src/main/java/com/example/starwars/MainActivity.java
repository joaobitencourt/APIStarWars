package com.example.starwars;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private TextView tvclima, tvnomepla, tvLoading, tvterritorio;
    private EditText EtNome;
    // private Button btnprocurar;
    String nomeplaneta;
    String climaplaneta, territorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvclima = findViewById(R.id.txtvClima);
        tvnomepla = findViewById(R.id.txtvNomePla);
        tvterritorio = findViewById(R.id.txtvterritorio);
        tvLoading = findViewById(R.id.txtvLoading);
        EtNome = findViewById(R.id.txtDigiteoNome);
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void Receba (View view ){
       /* Intent receba = new Intent(MainActivity.this, RecebeActivity.class);
        //receba.putExtra("resultado", txtResu);
        startActivity(receba);*/
    }



    public void procuraPlanetas(View view) {
        // Recupera a string de busca.
        String queryString = EtNome.getText().toString();
        // esconde o teclado qdo o botão é clicado
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        // Verifica o status da conexão de rede
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        /* Se a rede estiver disponivel e o campo de busca não estiver vazio
         iniciar o Loader CarregaLivros */
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            Log.d("uraa", queryString);
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            tvLoading.setText(R.string.loading);

        }/*else if(nomeplaneta != null){

            tvLoading.setText("");
        }*/
        // atualiza a textview para informar que não há conexão ou termo de busca
        else {
            if (queryString.length() == 0) {
                tvLoading.setText(R.string.no_search_term);
            } else {
                tvLoading.setText(R.string.no_network);
            }
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        Log.d("uraa2", queryString);
        return new BuscaPlanetas(this, queryString);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            JSONObject jsonObject = new JSONObject(data);
            Log.d("uraa3", jsonObject.toString());
            // Obtem o JSONArray dos itens dos Planetas
            JSONArray itemsArray = jsonObject.getJSONArray("results");
            Log.d("Arrayfunfo", itemsArray.toString());
            JSONObject balinha = new JSONObject(itemsArray.get(0).toString());
            Log.d("balinha", balinha.toString());
             nomeplaneta = balinha.getString("name");
             climaplaneta = balinha.getString("climate");
             territorio = balinha.getString("terrain");
            Log.d("balinhaname", nomeplaneta.toString());
            Log.d("balinhaclimate", climaplaneta.toString());
            Intent receba = new Intent(MainActivity.this, RecebeActivity.class);
            receba.putExtra("resultado", nomeplaneta);
            receba.putExtra("resultado2", climaplaneta);
            receba.putExtra("resultado3", territorio);
            startActivity(receba);

        } catch (Exception e) {
            // Se não receber um JSOn valido, informa ao usuário
            tvLoading.setText(R.string.no_results);
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}


