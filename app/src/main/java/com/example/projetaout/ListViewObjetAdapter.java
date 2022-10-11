package com.example.projetaout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetaout.R;

import java.util.ArrayList;

public class ListViewObjetAdapter extends ArrayAdapter<Object> {

    public ArrayList<Object> objets;

    public ListViewObjetAdapter(Context context, ArrayList<Object> objets)
    {
        super(context,0,objets);
        this.objets = objets;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.canva_listobjects, parent, false
            );
        }

        TextView Nom = convertView.findViewById(R.id.NomObjet);

        Object currentObject = getItem(position);

        Nom.setText(currentObject.GetNom());

        return convertView;
    }
}
