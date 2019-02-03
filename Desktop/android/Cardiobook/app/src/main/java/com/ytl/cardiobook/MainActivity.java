package com.ytl.cardiobook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Ver 4 Created Feb 03, 2019 2:53am
 * by Evan Li
 * @author ytl
 */
public class MainActivity extends AppCompatActivity{
	public static final String FILENAME = "file.sav";
	//need these 3 below otherwise wont display
	public static ListView all;
	private ArrayAdapter<entry> adapter; //pro-tip, changing all entry -> String corrupts all caches so dont do that
	private ArrayList<entry> allEntries;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		all = (ListView) findViewById(R.id.all); //THIS IS ABSOLUTELY NECESSARY, OTHERWISE all returns null if put in addEntry.java
		all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//allEntries.set(position, allEntries.get(0));
				editDialog(view, position);
			}
		});
	}
	public void addEntry(View view){
		Intent newEntry = new Intent(this, addEntry.class);
		startActivity(newEntry);
	}
	public void editDialog(View view, final int position) { //IM SURE THERE'S A BETTER WAY OF DOING THIS BUT I DON'T KNOW JAVA
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.edit);
		dialog.setTitle("Hello");
		//String old = allEntries.get(position).toString();
		TextView textViewUser = (TextView) dialog.findViewById(R.id.editE);
		textViewUser.setText(allEntries.get(position).toString());
		dialog.show();
		EditText editText = (EditText) dialog.findViewById(R.id.editE);
		Button button = (Button) dialog.findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
			@Override
			public void onClick(View v) {
				dialog.cancel();
				final String date = allEntries.get(position).getDate();
				final String time = allEntries.get(position).getTime();
				String sys = allEntries.get(position).getSystolicPressure();
				String dia = allEntries.get(position).getDiastolicPressure();
				String hrt = allEntries.get(position).getHeartRate();
				String com = allEntries.get(position).getComment();
				setContentView(R.layout.entries);
				final EditText Date;
				final EditText Time;
				final EditText Systolic;
				final EditText Diastolic;
				final EditText Heart;
				final EditText comments;
				Date = (EditText) findViewById(R.id.Date);
				Date.setShowSoftInputOnFocus(false); //requires lollipop
				Time = (EditText) findViewById(R.id.Time);
				Time.setShowSoftInputOnFocus(false); //requires lollipop
				Systolic = (EditText) findViewById(R.id.systolic);
				Diastolic = (EditText) findViewById(R.id.diastolic);
				Heart = (EditText) findViewById(R.id.heartrate);
				comments = (EditText) findViewById(R.id.comments);
				
				//this bottom chunck here presets values to clicked on values
				Date.setText(date);
				Time.setText(time);
				Systolic.setText(sys);
				Diastolic.setText(dia);
				Heart.setText(hrt);
				comments.setText(com);
				
				
				Button saveButton = findViewById(R.id.enter);
				saveButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String date = Date.getText().toString();
						String time = Time.getText().toString();
						String comment = comments.getText().toString();
						
						//oooooooooooook, this is kind of a "cheat"
						//but i dont know how to "flag" text in red
						//because the setters in entry.java take int arguments
						//i just flag it with a - sign
						int systolic = new Integer(Systolic.getText().toString());
						int diastolic = new Integer(Diastolic.getText().toString());
						int rate = new Integer(Heart.getText().toString());
						entry newEntry = new entry();
						newEntry.setComment(comment);
						newEntry.setDate(date);
						newEntry.setTime(time);
						
						newEntry.setSystolicPressure(systolic);
						newEntry.setDiastolicPressure(diastolic);
						newEntry.setHeartRate(rate);
						
						allEntries.set(position, newEntry);
						adapter.notifyDataSetChanged();
						saveInFile();
						testAlert("Entered!");
					}
				});

			}
		});

		//entry Entry = new entry();
		//allEntries.set(position, dialog.)
	}

	/**
	 * have the exact same methods in addEntry.java, probably necessary, won't work without
	 */
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		allEntries = new ArrayList<entry>();
		loadFromFile();
		adapter = new ArrayAdapter<entry>(this, R.layout.list_items, allEntries);
		MainActivity.all.setAdapter(adapter);
	}
	private void loadFromFile() {
		try {
			FileReader in = new FileReader(new File(getFilesDir(),MainActivity.FILENAME));
			Gson gson = new Gson();
			Type type = new TypeToken<ArrayList<entry>>(){}.getType();
			allEntries = gson.fromJson(in, type);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void saveInFile() {
		try {
			
			FileWriter out = new FileWriter(new File(getFilesDir(),MainActivity.FILENAME));
			Gson gson = new Gson();
			gson.toJson(allEntries, out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clearAll(View view) {
		allEntries.clear();
		adapter.notifyDataSetChanged();
		saveInFile();
	}
	private void testAlert(String msg) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage((CharSequence) msg);
		alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated catch block
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
}
