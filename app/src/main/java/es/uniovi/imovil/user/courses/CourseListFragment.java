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

    public static CourseListFragment newInstance() {
        CourseListFragment fragment = new CourseListFragment();
        return fragment;
    }
    public CourseListFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.course_list_fragment,
                container, false);

        // Configurar la lista
        ListView lvItems = (ListView) rootView.findViewById(R.id.list_view_courses);
        String [] courses = getResources().getStringArray(R.array.courses);
        String [] teachers = getResources().getStringArray(R.array.teachers);
        String [] descriptions = getResources().getStringArray(R.array.course_details);
        mAdapter = new CourseAdapter(getContext(), createCourseList(courses, teachers, descriptions));
        lvItems.setAdapter(mAdapter);
        // Importante, hay que decirle a la lista quien implementa el manejador del evento.
        lvItems.setOnItemClickListener(this);

        return rootView;
    }

    private List<Course> createCourseList(String[] names, String[] teachers, String[] descriptions) {

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
        mAdapter.addCourse(course);
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

}
