package com.ytl.cardiobook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.ytl.cardiobook.MainActivity.FILENAME;

public class addEntry extends AppCompatActivity {
	
	private static final String TAG = "addEntry";
	
	private EditText Date;
	private DatePickerDialog.OnDateSetListener mDateSetListener;
	private EditText Time;
	private EditText systolic;
	private EditText diastolic;
	private EditText heartrate;
	private EditText comments;
	private ArrayAdapter<entry> adapter;
	private ArrayList<entry> allEntries;
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entries);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		Button saveButton = (Button) findViewById(R.id.enter);
		
		Date = (EditText) findViewById(R.id.Date);
		Date.setShowSoftInputOnFocus(false); //requires lollipop
		Time = (EditText) findViewById(R.id.Time);
		systolic = (EditText) findViewById(R.id.systolic);
		diastolic = (EditText) findViewById(R.id.diastolic);
		heartrate = (EditText) findViewById(R.id.heartrate);
		comments = (EditText) findViewById(R.id.comments);
		
		//allEntries = new ArrayList<entry>();
		//adapter = new ArrayAdapter<entry>(this, R.layout.list_items, allEntries);
		//MainActivity.all.setAdapter(adapter);
		loadFromFile();
		
		Date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dialog = new DatePickerDialog(addEntry.this,
						android.R.style.Theme_Holo_Light,
						mDateSetListener,
						year, month, day);
				//dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.WHITE)));
				dialog.setContentView(R.layout.entries);
				Window window = dialog.getWindow();
				dialog.show();
			}
		});
		mDateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				month = month + 1;
				//Log.d(TAG, "onDateSet: " + year + "/" + month + "/" + dayOfMonth);
				String date = year + "/" + month + "/" + dayOfMonth;
				Date.setText(date);
			}
		};
		//should do all the error checking before here so save button can run free
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//int text = new Integer(systolic.getText().toString());
				String comment = comments.getText().toString();
				String date = Date.getText().toString();
				if (comment.isEmpty()){
					Snackbar.make(getCurrentFocus(), "empty", Snackbar.LENGTH_SHORT).show();
				} else {

					entry Entry = new entry();
					Entry.setComment(comment);
					Entry.setDate(date);
					//Entry.setDate(mDisplayDate);
				/*
				Snackbar.make(getCurrentFocus(), "this works", Snackbar.LENGTH_LONG).show();
				**********just testing code here************
				 */
					allEntries.add(Entry);
					adapter.notifyDataSetChanged();
					saveInFile();
					
					//clear field when entered, show popup saying done!
					comments.getText().clear();
					Date.getText().clear();
					Alert("Entered!");
				}
			}
		});
	}
	
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
	private void Alert(String msg) {
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

