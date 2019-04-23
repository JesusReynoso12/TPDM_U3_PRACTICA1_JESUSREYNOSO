package reynosojesus.ittepic.edu.tpdm_u3_practica1_jesusreynoso;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    EditText nombreG,razaG,edadG,descripcionG;
    Button insertarG,eliminarG,actualizarG,consultarG;
    ListView listaG;
    private DatabaseReference manejadorBD;
    List<Gato> datosgato;
    ListView listagato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nombreG = findViewById(R.id.txtNombreGato);
        razaG = findViewById(R.id.txtRazaGato);
        edadG = findViewById(R.id.txtEdadGato);
        descripcionG = findViewById(R.id.txtDescripcionGato);
        insertarG = findViewById(R.id.btnInsertarGato);
        eliminarG = findViewById(R.id.btnEliminarGato);
        actualizarG = findViewById(R.id.btnActualizarGato);
        consultarG = findViewById(R.id.btnConsultarGato);
        listaG = findViewById(R.id.ListaGato);

        insertarG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarGato();
            }
        });

        eliminarG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarGato();
            }
        });

        actualizarG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarGato();
            }
        });

        consultarG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerenListview();
            }
        });
    }

    private  void insertarGato(){
        final Gato gato = new Gato(nombreG.getText().toString(),razaG.getText().toString(),edadG.getText().toString(),descripcionG.getText().toString());
        manejadorBD.child("Gatos").child(gato.nombre).setValue(gato)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Main2Activity.this, "Inserato", Toast.LENGTH_SHORT).show();
                        nombreG.setText("");razaG.setText("");edadG.setText("");descripcionG.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Main2Activity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private  void eliminarGato(){
        final EditText nombregato = new EditText(this);
        nombregato.setHint("nombre del gato");
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage("Nombre del gato:").setView(nombregato).setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarGato2(nombregato.getText().toString());
            }
        }).setNegativeButton("Cancelar", null).show();
    }


    private void consultaGato(String i){
        manejadorBD.child("gatos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datosgato = new ArrayList<>();
                for(final DataSnapshot snap : dataSnapshot.getChildren()){
                    manejadorBD.child("gatos").child(snap.getKey()).addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Gato gato = dataSnapshot.getValue(Gato.class);

                                    if(gato!=null){
                                        datosgato.add(gato);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            }
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ponerenListview(){
        if (datosgato.size()==0) return;
        String nombres[] = new String[datosgato.size()];

        for(int i = 0; i<nombres.length; i++){
            Gato gato = datosgato.get(i);
            nombres[i] = gato.nombre;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
        listaG.setAdapter(adapter);
    }

    /*

     */




    private void eliminarGato2(String NombreP){
        manejadorBD.child("gatos").child(NombreP).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Main2Activity.this, "Eliminado", Toast.LENGTH_SHORT).show();
                        nombreG.setText("");razaG.setText("");edadG.setText("");descripcionG.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Main2Activity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarGato(){
        Gato gato = new Gato(nombreG.getText().toString(),razaG.getText().toString(),edadG.getText().toString(),descripcionG.getText().toString());

        manejadorBD.child("gatos").child(nombreG.getText().toString()).setValue(nombreG)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Main2Activity.this, "actualizado", Toast.LENGTH_SHORT).show();
                        nombreG.setText("");razaG.setText("");edadG.setText("");descripcionG.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Main2Activity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
