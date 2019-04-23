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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText nombreP,razaP,edadP,descripcionP;
    Button insertarP,eliminarP,actualizarP,consultarP;
    ListView listaP;
    private DatabaseReference manejadorBD;
    List<Perro> datosperro;
    ListView listaperro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreP = findViewById(R.id.txtNombrePerro);
        razaP = findViewById(R.id.txtRazaPerro);
        edadP = findViewById(R.id.txtEdadPerro);
        descripcionP = findViewById(R.id.txtDescripcionPerro);
        insertarP = findViewById(R.id.btnInsertarPerro);
        eliminarP = findViewById(R.id.btnEliminarPerro);
        actualizarP = findViewById(R.id.btnActualizarPerro);
        consultarP = findViewById(R.id.btnConsultarPerro);
        listaP = findViewById(R.id.ListaPerros);
        manejadorBD = FirebaseDatabase.getInstance().getReference();

        insertarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarPerro();
            }
        });

        eliminarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPerro();
            }
        });

        actualizarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPerro();
            }
        });

        consultarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerenListview();
            }
        });

    }

    private  void insertarPerro(){
        final Perro perro = new Perro(nombreP.getText().toString(),razaP.getText().toString(),edadP.getText().toString(),descripcionP.getText().toString());
        manejadorBD.child("Perros").child(perro.nombre).setValue(perro)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Inserato", Toast.LENGTH_SHORT).show();
                        nombreP.setText("");razaP.setText("");edadP.setText("");descripcionP.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private  void eliminarPerro(){
        final EditText nombreperro = new EditText(this);
        nombreperro.setHint("nombre del perro");
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage("Nombre del perro:").setView(nombreperro).setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarPerro2(nombreperro.getText().toString());
            }
        }).setNegativeButton("Cancelar", null).show();
    }


    private void consultaPerro(String i){
        manejadorBD.child("perros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datosperro = new ArrayList<>();
                for(final DataSnapshot snap : dataSnapshot.getChildren()){
                    manejadorBD.child("perros").child(snap.getKey()).addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Perro perro = dataSnapshot.getValue(Perro.class);

                                    if(perro!=null){
                                        datosperro.add(perro);
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
        if (datosperro.size()==0) return;
        String nombres[] = new String[datosperro.size()];

        for(int i = 0; i<nombres.length; i++){
            Perro perro = datosperro.get(i);
            nombres[i] = perro.nombre;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
        listaP.setAdapter(adapter);
    }

    /*

     */




    private void eliminarPerro2(String NombreP){
        manejadorBD.child("perros").child(NombreP).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
                        nombreP.setText("");razaP.setText("");edadP.setText("");descripcionP.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarPerro(){
         Perro perro = new Perro(nombreP.getText().toString(),razaP.getText().toString(),edadP.getText().toString(),descripcionP.getText().toString());

        manejadorBD.child("perros").child(nombreP.getText().toString()).setValue(nombreP)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "actualizado", Toast.LENGTH_SHORT).show();
                        nombreP.setText("");razaP.setText("");edadP.setText("");descripcionP.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
