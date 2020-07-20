package com.example.starwars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RecebeActivity extends AppCompatActivity {
    private TextView tvclima, tvnomepla,tvterritorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recebe);
        tvclima = findViewById(R.id.txtvClima);
        tvnomepla = findViewById(R.id.txtvNomePla);
        tvterritorio = findViewById(R.id.txtvterritorio);
        Bundle bundle = getIntent().getExtras(); // resgatar o que vai passar para outra view
        String resultadoString = bundle.getString("resultado");
        String resultadoString2 = bundle.getString("resultado2");
        String resultadoString3 = bundle.getString("resultado3");
        tvnomepla.setText(resultadoString);
        tvclima.setText(resultadoString2);
        tvterritorio.setText(resultadoString3);
    }
    public void abrirSite(View view)
    {
        Uri uri = Uri.parse("https://m.folha.uol.com.br/asmais/2015/10/1697728-dez-lugares-que-todo-fa-de-star-wars-adoraria-conhecer.shtml");
        Intent it = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(it);

    }


}