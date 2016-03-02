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

        // Cambiamos el texto del layout con la descripción obtenida.
        ((TextView)findViewById(R.id.view_descripcion_asignatura)).setText(description);
    }
}
