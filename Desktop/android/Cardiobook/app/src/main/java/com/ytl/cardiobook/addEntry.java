package com.ytl.cardiobook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import static com.ytl.cardiobook.MainActivity.FILENAME;

public class addEntry extends AppCompatActivity {
	private EditText Date;
	private EditText Time;
	private EditText systolic;
	private EditText diastolic;
	private EditText heartrate;
	private EditText comments;
	private ArrayAdapter<entry> adapter;
	private ArrayList<entry> allEntries;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entries);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Button saveButton = (Button) findViewById(R.id.enter);
		Date = (EditText) findViewById(R.id.Date);
		Time = (EditText) findViewById(R.id.Time);
		systolic = (EditText) findViewById(R.id.systolic);
		diastolic = (EditText) findViewById(R.id.diastolic);
		heartrate = (EditText) findViewById(R.id.heartrate);
		comments = (EditText) findViewById(R.id.comments);
		
		//allEntries = new ArrayList<entry>();
		//adapter = new ArrayAdapter<entry>(this, R.layout.list_items, allEntries);
		//MainActivity.all.setAdapter(adapter);
		loadFromFile();
		
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//int text = new Integer(systolic.getText().toString());
				String text = comments.getText().toString();
				if (text.isEmpty()){
					Snackbar.make(getCurrentFocus(), "empty", Snackbar.LENGTH_SHORT).show();
				} else {

					entry Entry = new entry();
					Entry.setComment(text);
				/*
				Snackbar.make(getCurrentFocus(), "this works", Snackbar.LENGTH_LONG).show();
				**********just testing code here************
				 */
					allEntries.add(Entry);
					adapter.notifyDataSetChanged();
					saveInFile();
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
	private void Alert(entry msg) {
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

