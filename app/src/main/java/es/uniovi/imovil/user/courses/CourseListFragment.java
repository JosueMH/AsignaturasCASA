package es.uniovi.imovil.user.courses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.imovil.user.courses.R;

public class CourseListFragment extends Fragment implements
        AdapterView.OnItemClickListener {

    // Este va a ser el campo en el que guardaremos la referencia a la actividad contenedora.
    private Callbacks mCallback = null;
    private CourseAdapter mAdapter = null;
    private ArrayList<Course> lista_cursos = null;
    private final String LISTA_CURSOS_PARCELADA = "lista_cursos_parcelada";

    public static CourseListFragment newInstance() {
        CourseListFragment fragment = new CourseListFragment();
        return fragment;
    }
    public CourseListFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflamos el layout del fragmento, ya que al contrario de la actividad, no se hace automáticamente.
        View rootView;
        rootView = inflater.inflate(R.layout.course_list_fragment,
                container, false);
        ListView lvItems = (ListView) rootView.findViewById(R.id.list_view_courses);

        if (savedInstanceState != null) {
        // Obtener la lista del bundle con getParcelableArrayList()
            lista_cursos = savedInstanceState.getParcelableArrayList(LISTA_CURSOS_PARCELADA);
        } else {
        // Inicializar la lista por defecto (desde los recursos)

            // Configurar la lista
            String [] courses = getResources().getStringArray(R.array.courses);
            String [] teachers = getResources().getStringArray(R.array.teachers);
            String [] descriptions = getResources().getStringArray(R.array.course_details);

            // Ahora, en vez de pasarle la lista de Courses al adaptador, lo guardamos en el fragmento, y después se lo pasamos al adaptador.
            lista_cursos = createCourseList(courses, teachers, descriptions);
        }

        mAdapter = new CourseAdapter(getContext(), lista_cursos);
        lvItems.setAdapter(mAdapter);

        // Importante, hay que decirle a la lista quien implementa el manejador del evento.
        lvItems.setOnItemClickListener(this);

        return rootView;
    }

    private ArrayList<Course> createCourseList(String[] names, String[] teachers, String[] descriptions) {

        if (names.length != teachers.length) {
            throw new IllegalStateException();
        }

        ArrayList<Course> courses = new ArrayList<Course>(names.length);
        for (int i = 0; i < names.length; i++) {
            courses.add(new Course(names[i], teachers[i], descriptions[i]));
        }
        return courses;
    }

    public void addCourse(Course course) {
        // Ojo, ahora, no tenemos que añadir el curso a la lista del adaptador porque su lista es una referencia a la lista en el fragmento.
        // Por tanto, solo tenemos que avisarle de que la lista cambió, para que haga cosas.
        lista_cursos.add(course);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
// ...
        Course course = (Course) parent.getItemAtPosition(position);
        mCallback.onCourseSelected(course);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement Callbacks");
        }
    }

    public interface Callbacks {
        public void onCourseSelected(Course course);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LISTA_CURSOS_PARCELADA, lista_cursos);
    }
}
