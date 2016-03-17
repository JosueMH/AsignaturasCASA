package es.uniovi.imovil.user.courses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.uniovi.imovil.user.courses.R;

public class CourseDetailsFragment extends Fragment {

    private static final String ASIGNATURA_PARCELADA = "ASIGNATURA_PARCELADA";
    private View rootView = null;
    private Course asignatura_actual = null;

    public static CourseDetailsFragment newInstance(Course course) {
        CourseDetailsFragment fragment = new CourseDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ASIGNATURA_PARCELADA, course);
        fragment.setArguments(args);
        return fragment;
    }

    public CourseDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Primero inflamos el layout del fragmento y lo colgamos de un contenedor en el layout de la actividad.
        rootView = inflater.inflate(R.layout.course_details_fragment,
                container, false);
        // Ahora miramos a ver si estamos recuperándonos de una destrucción de la actividad debido a un evento del sistema.
        if (savedInstanceState != null) {
            setCourse((Course)savedInstanceState.getParcelable(ASIGNATURA_PARCELADA));
        }
        // Si no es ese caso, significa que el fragmento es creado dinámicamente por primera vez por la actividad.
        else{
        // Luego recogemos los parámetros del Bundle. Con la descripción obtenida, cambiamos el contenido del textView.
            Bundle args = getArguments();
            if (args != null) {
                setCourse((Course)args.getParcelable(ASIGNATURA_PARCELADA));
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ASIGNATURA_PARCELADA, asignatura_actual);
        //mostramos en el monitor de android la descripcion guardada con la etiqueta de info (i).
        Log.i("on_saved_instance", asignatura_actual.getDetails());
    }

    // Ahora necesitamos un método para que la Actividad se comunique con él en caso de pantalla grande.
    public void setCourse(Course course){
        asignatura_actual = course;
        ((TextView)rootView.findViewById(R.id.view_descripcion_asignatura)).setText(asignatura_actual.getDetails());
    }

    // Ojo al detalle... Para que la descripción pasara a este fragmento, tuvo que recorrer un largo camino:
    /*
        1. Se generó un evento Click en la lista del ListFragment. A partir de ese evento sabíamos qué descripción coger.
        2. Ese evento llamaba a un método de la Actividad 1. Para ello necesitábamos obtener su referencia e implementar una interfaz en la Actividad.
        3. A través de esa llamada al método de la Actividad, le pasábamos la descripción.
        4. A continuación necesitábamos pasar la descripción a la Actividad 2 a través de un Bundle.
        5. Desde la Actividad 2 necesitábamos crear el DetailsFragment mediante un método estático que crease el fragmento y le pasase los argumentos.
        6. En el evento OnCreateView del fragmento recogimos la descripción a través del Bundle y lo pintamos en el TextView.
     */

}
