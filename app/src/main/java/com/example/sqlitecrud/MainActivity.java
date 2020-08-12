package com.example.sqlitecrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName,editSurename,editMarks,editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnviewUpdate;
    Button btndelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText_name);
        editSurename = (EditText)findViewById(R.id.editText_surname);
        editMarks = (EditText)findViewById(R.id.editTextmarks);
        editTextId = (EditText)findViewById(R.id.textid);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.buttonViewall);
        btnviewUpdate = (Button) findViewById(R.id.buttonUpdate);
        btndelete = (Button) findViewById(R.id.buttondelete);

        //call AddData method
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void DeleteData(){
        btndelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteData(editTextId.getText().toString());
                        if(deleteRows > 0){
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }
    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(editTextId.getText().toString(),
                                editName.getText().toString(),
                                editSurename.getText().toString(),
                                editMarks.getText().toString());

                        if( isUpdated == true){
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    //when ADDDATA button clicked add data to the database
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //calling inserData method using instance datahelper class
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurename.getText().toString(),
                                editMarks.getText().toString()
                        );
                        if( isInserted == true){
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor res = myDb.getAllData();
                       if(res.getCount() == 0){
                           //show message
                           showMessage("Error","Nothing Found");
                           return;
                       }else{
                           StringBuffer buffer = new StringBuffer();
                           //get all the data one by one
                           while (res.moveToNext()){
                               buffer.append("Id :"+res.getString(0)+"\n");// index is 0
                               buffer.append("Name :"+res.getString(1)+"\n");// index is 1
                               buffer.append("Surname :"+res.getString(2)+"\n");// index is 2
                               buffer.append("Marks :"+res.getString(3)+"\n\n");// index is 3

                           }
                           //show all data
                           showMessage("Data",buffer.toString());
                       }
                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
