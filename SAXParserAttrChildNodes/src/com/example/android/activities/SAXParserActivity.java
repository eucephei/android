package com.example.android.activities;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.android.activities.R;
import com.example.android.beans.Employee;
import com.example.android.xml.SAXXMLParser;
 
public class SAXParserActivity extends Activity implements
        OnClickListener, OnItemClickListener {
 
    Button button;
    ListView listView;
    List<Employee> employees = null;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        findViewsById();
        button.setOnClickListener(this);
 
    }
 
    private void findViewsById() {
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.employeeList);
    }
 
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Employee employee = employees.get(position);
        Toast.makeText(parent.getContext(), employee.getDetails(),
                Toast.LENGTH_LONG).show();
    }
 
    @Override
    public void onClick(View view) {
        try {
            employees = SAXXMLParser.parse(getAssets().open("employees.xml"));
            ArrayAdapter<Employee> adapter = new ArrayAdapter<Employee>(this,
                    R.layout.list_item, employees);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}