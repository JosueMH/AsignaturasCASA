package es.uniovi.imovil.user.courses;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements CourseListFragment.Callbacks {

	private CourseAdapter mAdapter = null;
	private int mCourseCount;
	private boolean mTwoPanes = false;
	private final String CONTADOR_PARCELADO = "contador_parcelado";
	private final String PREFERENCES = "preferencias_contador";
	private final String CONTADOR_PERSISTENCIA = "contador_resguardo";
	private final int DEFAULT_CONTADOR = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Si existe el segundo fragmento, entonces es que estamos en el layout de dos fragmentos.
		if (findViewById(R.id.course_details_fragment_container) != null)
			mTwoPanes = true;
		else{
			SharedPreferences prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
			mCourseCount = prefs.getInt(CONTADOR_PERSISTENCIA, DEFAULT_CONTADOR);
		}
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
		// Obtenemos el manejador de fragmentos.
		FragmentManager fragmentManager = getSupportFragmentManager();
		// Accedemos al fragmento almacenado en el view fragment del layout de la actividad.
		CourseListFragment fragment = (CourseListFragment)
				fragmentManager.findFragmentById(R.id.course_list_frag);

		//Creamos de forma genérica el curso a añadir.
		String name = String.format(getString(R.string.default_course_format), ++mCourseCount);
		String teacher = String.format(getString(R.string.default_teacher_format), mCourseCount);
		String details = String.format(getString(R.string.default_details_format), mCourseCount);
		Course course = new Course(name, teacher, details);

		// Llamamos al método del fragmento que será quien le diga al adaptador que introduzca un nuevo dato.
		fragment.addCourse(course);
	}


	@Override
	public void onCourseSelected(Course course) {
		// Si estamos en el layout de un fragmento, lanzamos la actividad.
		if (mTwoPanes == false) {
			Intent intent = new Intent(this, CourseDetailsActivity.class);
			intent.putExtra(CourseDetailsActivity.ASIGNATURA_SELECCIONADA_PARCELADA,
					(Parcelable)course);
			startActivity(intent);
			return;
		}

		// Si no, no necesitamos una nueva actividad...el fragmento ya está en esta.
		// Obtenemos el manejador de fragmentos.
		FragmentManager fragmentManager = getSupportFragmentManager();
		// Accedemos al fragmento almacenado en el view fragment del layout de la actividad.
		CourseDetailsFragment fragment = (CourseDetailsFragment)
				fragmentManager.findFragmentById(R.id.course_details_frag);

		// Llamamos al método del fragmento que acabamos de implementar. (Comunicación Actividad -> Fragmento).
		fragment.setCourse(course);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(CONTADOR_PARCELADO, mCourseCount);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mCourseCount = savedInstanceState.getInt(CONTADOR_PARCELADO);
	}


	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = prefs.edit();
		prefsEditor.putInt(CONTADOR_PERSISTENCIA, mCourseCount);
		prefsEditor.commit();
	}
}
