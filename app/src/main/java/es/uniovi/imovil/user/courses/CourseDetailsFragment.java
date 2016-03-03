package es.uniovi.imovil.user.courses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.uniovi.imovil.user.courses.R;

public class CourseDetailsFragment extends Fragment {

    private static final String DESCRIPTION_ARG = "description";
    private View rootView = null;

    public static CourseDetailsFragment newInstance(String desc) {
        CourseDetailsFragment fragment = new CourseDetailsFragment();
        Bundle args = new Bundle();
        args.putString(DESCRIPTION_ARG, desc);
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

        // Luego recogemos los parámetros del Bundle. Con la descripción obtenida, cambiamos el contenido del textView.
        Bundle args = getArguments();
        if (args != null) {
            String desc = args.getString(DESCRIPTION_ARG);
            setDescription(desc);
        }

        return rootView;
    }

    // Ahora necesitamos un método para que la Actividad se comunique con él en caso de pantalla grande.
    public void setDescription(String details){
        ((TextView)rootView.findViewById(R.id.view_descripcion_asignatura)).setText(details);
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
