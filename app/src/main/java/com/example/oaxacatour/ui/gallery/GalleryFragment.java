package com.example.oaxacatour.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.oaxacatour.BasilicaSoledadActivity;
import com.example.oaxacatour.MetropolitanaActivity;
import com.example.oaxacatour.R;
import com.example.oaxacatour.SantoDomingoActivity;
import com.example.oaxacatour.databinding.FragmentGalleryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    private ListView listview;
    private ListView listview2;

    private ArrayList<String> names;

    ArrayList<String> ar = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        listview = view.findViewById(R.id.listCatalogo);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ar.add("Templo de Santo Domingo de Guzmán / Ex convento");
        ar.add("Basílica de Nuestra Señora de la Soledad");
        ar.add("Catedral Metropolitana de Oaxaca");

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1,ar);

        listview.setAdapter(adapter);

        //listView.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View v, int i, long l) {
                //Toast.makeText(VentasActivity.this, arr.get(i), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), ar.get((i)), Toast.LENGTH_SHORT).show();

                if(ar.get(i) == "Templo de Santo Domingo de Guzmán / Ex convento"){
                    Intent intent = new Intent(getActivity(), SantoDomingoActivity.class);
                    startActivity(intent);
                }

                if(ar.get(i) == "Basílica de Nuestra Señora de la Soledad"){
                    Intent intent = new Intent(getActivity(), BasilicaSoledadActivity.class);
                    startActivity(intent);
                }

                if(ar.get(i) == "Catedral Metropolitana de Oaxaca"){
                    Intent intent = new Intent(getActivity(), MetropolitanaActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}