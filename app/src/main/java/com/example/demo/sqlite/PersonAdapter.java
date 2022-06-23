package com.example.demo.sqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo.R;

import java.util.List;

public class PersonAdapter extends BaseAdapter {
    List<Person> lstPerson;

    public PersonAdapter(List<Person> lstPerson) {
        this.lstPerson = lstPerson;
    }

    @Override
    public int getCount() {
        return lstPerson.size() ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHoder hoder;
        if (convertView == null) {
            hoder = new ViewHoder();
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sqlite, null);
            hoder.txtName =  convertView.findViewById(R.id.txt_name);
            hoder.txtPhone =  convertView.findViewById(R.id.txt_phone);
            hoder.txtAge =  convertView.findViewById(R.id.txt_age);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        Person person= lstPerson.get(i);
        hoder.txtName.setText(person.getName());
        hoder.txtPhone.setText(person.getPhone());
        hoder.txtAge.setText(person.getId().toString());
        return convertView;
    }

    private class ViewHoder {
        TextView txtName, txtPhone,txtAge;
    }
}
