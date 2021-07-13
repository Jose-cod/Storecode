package com.example.storecode_android.view.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.storecode_android.Presenter.ProductPresenter;
import com.example.storecode_android.R;
import com.example.storecode_android.entidades.RespObtenerProducto;
import com.example.storecode_android.utils.LogFile;
import com.example.storecode_android.utils.SharedPref;
import com.example.storecode_android.view.adapters.ModeloAdapterInstaladas;
import com.example.storecode_android.view.adapters.ModeloAdapterListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 */
public class HomeNoLogedFragment extends Fragment implements ModeloAdapterListener {
    private static final Logger log = LogFile.getLogger(HomeLogedFragment.class);
    public RecyclerView recyclerView;
    public Activity mContext;
    public List<RespObtenerProducto> respuesta;

    //declarar presenter y adapter
    private ModeloAdapterInstaladas modeloAdapterInstaladas;
    private ProductPresenter productPresenter;

    private RelativeLayout rlBaseHome;


    public HomeNoLogedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeNoLogedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeNoLogedFragment newInstance(String param1, String param2) {
        HomeNoLogedFragment fragment = new HomeNoLogedFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*System.out.println("OnCreate HomeNoLogedFragments");
        String response = SharedPref.obtenerAplicaciones(getContext());
        if(!response.equals("Vacio")) {
            log.info("Lista Aplicaciones: "+ response);

            Type listType = new TypeToken<ArrayList<RespObtenerProducto>>(){}.getType();
            //Type listType = new TypeToken<RespObtenerProducto>(){}.getType();
            respuesta = new Gson().fromJson(response, listType);
            log.info("Lista Productos Convertidas: "+ respuesta.toString());
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_no_loged, container, false);
        mContext = getActivity();

        //recyclerView = view.findViewById(R.id.rv_all_products);
        //SearchView searchView = view.findViewById(R.id.svHomeNoLoged);


        /*if(respuesta != null){
            if(respuesta.isEmpty()){
                log.info("No hay Registros Disponibles");
            }else{

                Iterator<RespObtenerProducto> it = respuesta.iterator();

                while(it.hasNext()){
                    RespObtenerProducto item=it.next();
                    log.info("Nombre Aplicación: " + item.getNombreProducto());

                    *//*if (!FuncionesGenerales.validaAplicacionInstalado(mContext.getApplicationContext(), item.getPackageName().trim())) {
                        it.remove();
                    }*//*

                    *//*recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 1));
                    ModeloAdapterInstaladas adapter = new ModeloAdapterInstaladas(respuesta, getActivity(), searchView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);*//*
                }
            }
        }else{
            *//*Intent intent = new Intent(getActivity(), SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            getActivity().finish();*//*
        }*/
        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_no_loged, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rlBaseHome = view.findViewById(R.id.rlBaseHomeNoLoged);
        //checar si manda error
        //productPresenter = new ProductPresenter(getContext());
        productPresenter = new ProductPresenter(getContext(), getView());
        recyclerView= view.findViewById(R.id.rv_all_products);
        SearchView searchView = view.findViewById(R.id.svHomeNoLoged);


        productPresenter.refreshAllProducts();


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL, false));
        modeloAdapterInstaladas = new ModeloAdapterInstaladas(this, getActivity(), searchView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(modeloAdapterInstaladas);

        observePresenter();
    }

    public void observePresenter(){

        // Create the observer which updates the UI.
        final Observer<List<RespObtenerProducto>> productsObserver = new Observer<List<RespObtenerProducto>>() {
            @Override
            public void onChanged(List<RespObtenerProducto> products) {
                modeloAdapterInstaladas.updateData(products);
            }

        };
        productPresenter.listAllProducts.observe(getViewLifecycleOwner(), productsObserver);

        productPresenter.isLoadingAll.observe(getViewLifecycleOwner(),new Observer<Boolean>(){

            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading!=null){
                    //cambiar la visibilidad del relative layout
                    //rlBaseHome.setVisibility(View.INVISIBLE);
                    rlBaseHome.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    @Override
    public void OnProductClicked(RespObtenerProducto producto, int position) {
        System.out.println("producto:"+ producto.getNombreProducto());
    }
}