package com.example.demo.cp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.sqlite.Person;
import com.example.demo.sqlite.PersonAdapter;
import com.example.demo.sqlite.PersonDAO;
import com.example.demo.sqlite.PersonSQLHelper;

import java.util.ArrayList;
import java.util.List;

public class CPActivity extends AppCompatActivity {
    EditText edtName;
    EditText edtPhone;
    EditText edtAge;
    Button btnAdd;
    Button btnUpdate;
    ListView lvPerson;

    List<Person> personList = new ArrayList<>();
    PersonDAO personDAO;
    PersonSQLHelper personSQLHelper;
    PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct);
        setTitle("conttent provider");
        edtName = findViewById(R.id.edt_nameCP);
        edtPhone = findViewById(R.id.edt_phoneCP);
        edtAge = findViewById(R.id.edt_ageCP);
        btnAdd = findViewById(R.id.btn_add_personCP);
        btnUpdate=findViewById(R.id.btn_update_personCP);
        lvPerson = findViewById(R.id.lv_personCP);

        personDAO = new PersonDAO(this);
        personSQLHelper = new PersonSQLHelper(this);

        btnAdd.setOnClickListener(view -> {
            Person person = new Person();
            person.setName(edtName.getText().toString().trim());
            person.setPhone(edtPhone.getText().toString().trim());
            person.setAge(edtAge.getText().toString().trim());

            personDAO.insertPersonCP(person);




        });
    }

    private void loadData(){
        personList.clear();

//
//        Cursor cursor = getContentResolver().query(uri,null,null,null,null,null);
//
//        cursor.moveToFirst();


    }

}