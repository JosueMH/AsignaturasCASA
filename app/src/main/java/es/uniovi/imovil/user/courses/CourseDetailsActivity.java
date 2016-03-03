package es.uniovi.imovil.user.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CourseDetailsActivity extends AppCompatActivity {

    // Es necesario un campo público y estático para poder pasar el parámetro a través de un Intent.
    public static final String DESCRIPTION =
            "es.uniovi.imovil.user.courses.DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details_activity);

        // Accedemos al Intent que arrancó esta actividad, y de ahí cogemos la descripción.
        Intent intent = getIntent();
        String description = intent.getStringExtra(DESCRIPTION);

        // Una vez tenemos la descripción que nos mandó la actividad de la lista (en realidad, fue el fragmento a través de la actividad).
        // Creamos el fragmento de forma dinámica y lo colgamos en el frameLayout mediante una TRANSACTION.
        // Comprobar si Existe el contenedor del fragmento.
        if (findViewById(R.id.course_details_fragment_container) != null) {
            // Si estamos restaurando desde un estado previo no hacemos nada
            if (savedInstanceState != null) {
                return;
            }
            // Crear el fragmento pasándole el parámetro
            CourseDetailsFragment fragment =
                    CourseDetailsFragment.newInstance(description);
            // Añadir el fragmento al contenedor 'fragment_container'
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.course_details_fragment_container, fragment).commit();
        }
    }
}
