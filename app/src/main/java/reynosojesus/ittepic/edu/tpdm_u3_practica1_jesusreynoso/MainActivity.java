package reynosojesus.ittepic.edu.tpdm_u3_practica1_jesusreynoso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    EditText nombreP,razaP,edadP,descripcionP;
    Button insertarP,eliminarP,actualizarP,consultarP;
    ListView listaP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
