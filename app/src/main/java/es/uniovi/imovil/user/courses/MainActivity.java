package es.uniovi.imovil.user.courses;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private CourseAdapter mAdapter = null;
	private int mCourseCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Configurar la lista
		ListView lvItems = (ListView) findViewById(R.id.list_view_courses);
		String [] courses = getResources().getStringArray(R.array.courses);
		String [] teachers = getResources().getStringArray(R.array.teachers);
		String [] descriptions = getResources().getStringArray(R.array.course_details);
		mAdapter = new CourseAdapter(this, createCourseList(courses, teachers, descriptions));
		lvItems.setAdapter(mAdapter);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflar el men� y a�adir acciones al action bar si existe
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			    
	    switch (item.getItemId()) {
	        case R.id.action_add_course:
	            addCourse();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void addCourse() {

		String name = String.format(getString(R.string.default_course_format), ++mCourseCount);
		String teacher = String.format(getString(R.string.default_teacher_format), mCourseCount);
		String details = String.format(getString(R.string.default_details_format), mCourseCount);
		Course course = new Course(name, teacher, details);

		mAdapter.addCourse(course);		
	}
}
