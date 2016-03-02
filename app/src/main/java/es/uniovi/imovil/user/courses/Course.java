package es.uniovi.imovil.user.courses;

public class Course {
	
	private String mName;
	private String mTeacher;
	private String mDetails;
	
	public Course(String name, String teacher, String details) {
		
		if (name == null || teacher == null || details == null || name.isEmpty() || teacher.isEmpty() || details.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		mName = name;
		mTeacher = teacher;
		mDetails = details;
	}

	public String getName() {
		
		return mName;
	}

	public String getTeacher() {
		
		return mTeacher;
	}

	public String getDetails() {
		return mDetails;
	}
}
