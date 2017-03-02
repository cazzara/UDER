package uder.uder.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import uder.uder.Activities.GetterActivity;
import uder.uder.ImageAdapter;
import uder.uder.R;

/**
 * Created by cazza223 on 2/27/2017.
 */

public class ItemListFragment extends Fragment {
    View myView;
    private ArrayAdapter<String> adapter;
    private GridView gridView;
    private String[] test = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.item_list_frag, container, false);
        gridView  = (GridView) myView.findViewById(R.id.list_item);
        final Context con = getContext();
        adapter = new ArrayAdapter<String>(con, R.layout.tv_layout, R.id.tv_list_item, test);
        //gridView.setAdapter(new ImageAdapter(con));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(con, ""+ position, Toast.LENGTH_SHORT).show();

            }
        });

        return myView;

    }

}
