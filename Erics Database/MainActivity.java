package com.example.activitease_faq;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper faqDb;
    Button btnAddData, btnViewData, btnUpdateData, btnDelete;
    EditText etQuestion, etAnswer, etID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etQuestion = (EditText) findViewById(R.id.etNewQuestion);
        etAnswer = (EditText) findViewById(R.id.etNewAnswer);
        etID = (EditText) findViewById(R.id.etID);

        btnAddData = (Button)findViewById(R.id.btnAddData);
        btnViewData = (Button)findViewById(R.id.btnViewData);
        btnUpdateData = (Button)findViewById(R.id.btnUpdateData);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        faqDb = new DatabaseHelper(this);

        AddData();
        ViewData();
        UpdateData();
        DeleteData();
    }

    public void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String question = etQuestion.getText().toString();
                String answer = etAnswer.getText().toString();

                boolean insertData = faqDb.addData(question, answer);

                if(insertData == true){
                    Toast.makeText(MainActivity.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void ViewData(){
        btnViewData.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Cursor data = faqDb.getListContents();

                if (data.getCount() == 0) {
                    display("Error", "No Data found.");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("Question: " + data.getString(1) + "\n");
                    buffer.append("Answer: " + data.getString(2) + "\n");

                    display("All Questions & Answers!", buffer.toString());
                }
            }
        });
    }

    public void UpdateData(){
        btnUpdateData.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                int tmp = etID.getText().toString().length();
                if(tmp > 0){
                    boolean update = faqDb.updateData(etID.getText().toString(),
                            etQuestion.getText().toString(),
                            etAnswer.getText().toString());
                    if(update == true){
                        Toast.makeText(MainActivity.this, "Successfully Update Data!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Something Went Wrong :(.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "You Must Enter An ID to Update :(.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                int tmp = etID.getText().toString().length();
                if(tmp > 0){
                    Integer deleteRow = faqDb.deleteData(etID.getText().toString());
                    if(deleteRow > 0){
                        Toast.makeText(MainActivity.this, "Successfully Deleted The Data!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Something Went Wrong :(.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "You Must Enter An ID to Delete :(.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
