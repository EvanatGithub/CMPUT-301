package com.ytl.cardiobook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Ver 1 Created Jan 02, 2019
 * by Evan Li
 * @author ytl
 */
public class MainActivity extends AppCompatActivity {
	public static final String FILENAME = "heartCondition.sav";
	//need these 3 below otherwise wont display
	public static ListView all;
	private ArrayAdapter<entry> adapter;
	private ArrayList<entry> allEntries;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		all = (ListView) findViewById(R.id.all); //THIS IS ABSOLUTELY NECESSARY, OTHERWISE all returns null if put in addEntry.java
	}
	public void addEntry(View view){
		Intent newEntry = new Intent(this, addEntry.class);
		startActivity(newEntry);
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
	
	public void erase(View view) {
		allEntries.clear();
		adapter.notifyDataSetChanged();
		saveInFile();
	}
}
