package com.example.demo.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

public class SQLiteActivity extends AppCompatActivity {

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

    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        setTitle("SQLite");
        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtAge = findViewById(R.id.edt_age);
        btnAdd = findViewById(R.id.btn_add_person);
        btnUpdate = findViewById(R.id.btn_update_person);
        lvPerson = findViewById(R.id.lv_person);

        personDAO = new PersonDAO(this);
        personSQLHelper = new PersonSQLHelper(this);
        loadList();

        btnAdd.setOnClickListener(view -> {
            Person person = new Person();
            person.setName(edtName.getText().toString().trim());
            person.setPhone(edtPhone.getText().toString().trim());
            person.setAge(edtAge.getText().toString().trim());

            if (personDAO.insertPersonDAO(person) > 0) {
                Toast.makeText(this, "add success", Toast.LENGTH_SHORT).show();
                resettxt();
                loadList();
            } else
                Toast.makeText(this, "add fail", Toast.LENGTH_SHORT).show();
        });
        lvPerson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                registerForContextMenu(adapterView);
                temp = i;
                return false;
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sql, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Person person = personList.get(temp);
        Log.e("check", String.valueOf(person.getId()));


        switch (item.getItemId()) {
            case R.id.itemMenuDel:
                if (personDAO.deleteEntryDAO(person.getId()) > 0) {
                    loadList();
                    Toast.makeText(this, "delete done", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                temp = -1;
                break;
            case R.id.itemMenuUpdate:
                btnAdd.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.VISIBLE);
                edtName.setText(person.getName());
                edtPhone.setText(person.getPhone());
                edtAge.setText(person.getAge());
                btnUpdate.setOnClickListener(view -> {
                    Person person1 = new Person();
                    person1.setId(person.getId());
                    person1.setName(edtName.getText().toString());
                    person1.setPhone(edtPhone.getText().toString());
                    person1.setAge(edtAge.getText().toString());
                    if (personDAO.updateDAO(person1) > 0) {
                        loadList();
                        resettxt();
                        btnAdd.setVisibility(View.VISIBLE);
                        btnUpdate.setVisibility(View.GONE);
                        Toast.makeText(this, "update success", Toast.LENGTH_SHORT).show();
                    } else {
                        btnAdd.setVisibility(View.VISIBLE);
                        btnUpdate.setVisibility(View.GONE);
                        Toast.makeText(this, "update fail", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
        return super.onContextItemSelected(item);
    }

    private void loadList() {
        personList.clear();
        personList = personDAO.getAllPersonDAO();
        adapter = new PersonAdapter(personList);
        lvPerson.setAdapter(adapter);
    }

    void resettxt() {
        edtName.setText("");
        edtPhone.setText("");
        edtAge.setText("");
    }
}