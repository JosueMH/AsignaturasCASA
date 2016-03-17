package es.uniovi.imovil.user.courses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Course implements Parcelable, Serializable {
	
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

	public Course(Parcel parcel) {
		readFromParcel(parcel);
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mName);
		dest.writeString(mTeacher);
		dest.writeString(mDetails);
	}
	private void readFromParcel(Parcel parcel) {
		mName = parcel.readString();
		mTeacher = parcel.readString();
		mDetails = parcel.readString();
	}
	public static final Parcelable.Creator<Course> CREATOR =
			new Parcelable.Creator<Course>() {

				public Course createFromParcel(Parcel in) {
					return new Course(in);
				}

				public Course[] newArray(int size) {
					return new Course[size];
				}
			};
}
