package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button submitBtn, viewBtn, updateBtn;
    private EditText etId, etName, etRole, etSalary;
    private SQLiteDatabase db;
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.et1);
        etName = findViewById(R.id.et2);
        etRole = findViewById(R.id.et3);
        etSalary = findViewById(R.id.et4);
        submitBtn = findViewById(R.id.submit_btn);
        viewBtn = findViewById(R.id.view_btn);
        updateBtn = findViewById(R.id.update_btn);
        recyclerView = findViewById(R.id.recyclerView);


        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();


        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(employeeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(employeeAdapter);


        submitBtn.setOnClickListener(this::insertData);
        viewBtn.setOnClickListener(this::viewData);
        updateBtn.setOnClickListener(this::updateData);
    }


    private void insertData(View view) {
        String employeeId = etId.getText().toString().trim();
        String employeeName = etName.getText().toString().trim();
        String employeeRole = etRole.getText().toString().trim();
        String employeeSalary = etSalary.getText().toString().trim();

        if (employeeId.isEmpty() || employeeName.isEmpty() || employeeRole.isEmpty() || employeeSalary.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("employeId", employeeId);
        values.put("employeName", employeeName);
        values.put("employeRole", employeeRole);
        values.put("employeSalary", employeeSalary);
        long result = db.insert("employe", null, values);

        if (result != -1) {
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Data insertion failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewData(View view) {
        employeeList.clear(); // Clear the existing list
        Cursor cursor = db.rawQuery("SELECT * FROM employe", null);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String role = cursor.getString(2);
            String salary = cursor.getString(3);
            employeeList.add(new Employee(id, name, role, salary));
        }

        employeeAdapter.notifyDataSetChanged(); // Notify the adapter about data changes
        cursor.close();
    }


    private void updateData(View view) {
        String employeeId = etId.getText().toString().trim();
        String employeeName = etName.getText().toString().trim();
        String employeeRole = etRole.getText().toString().trim();
        String employeeSalary = etSalary.getText().toString().trim();

        if (employeeId.isEmpty()) {
            Toast.makeText(this, "Please provide an employee ID to update", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        if (!employeeName.isEmpty()) values.put("employeName", employeeName);
        if (!employeeRole.isEmpty()) values.put("employeRole", employeeRole);
        if (!employeeSalary.isEmpty()) values.put("employeSalary", employeeSalary);

        int rowsAffected = db.update("employe", values, "employeId=?", new String[]{employeeId});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            viewData(null); // Refresh data after update
        } else {
            Toast.makeText(this, "Employee ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etId.setText("");
        etName.setText("");
        etRole.setText("");
        etSalary.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close(); // Close the database to free resources
        }
    }
}