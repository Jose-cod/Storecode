package com.example.storecode_android.Presenter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.storecode_android.entidades.RespUserData;
import com.example.storecode_android.utils.SharedPref;
import com.example.storecode_android.view.LoginActivity;
import com.example.storecode_android.R;
import com.example.storecode_android.entidades.ReqLoginDto;
import com.example.storecode_android.entidades.RespLoginDto;
import com.example.storecode_android.service.RestClientService;
import com.example.storecode_android.service.RestClientServiceImpl;
import com.example.storecode_android.utils.AnimacionesGenerales;
import com.example.storecode_android.utils.LogFile;
import com.example.storecode_android.view.MainDrawerActivity;

import org.apache.log4j.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.storecode_android.utils.Constantes.RESP_CODE_WEB_OK;
/*
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.R;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.kotlin.ui.main.MainPrincipalActivity;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.model.dto.ReqBitacoraLoginAppDto;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.model.dto.ReqLoginDto;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.model.dto.RespBitacoraLoginDto;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.model.dto.RespLoginDto;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.model.dto.ResponseMasterDto;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.service.rest.RestClientService;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.service.rest.impl.RestClientServiceImpl;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.utils.AnimacionesGenerales;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.utils.Constantes;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.utils.LogFile;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.utils.SharedPref;
import mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.view.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;

import static mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.utils.Constantes.RESP_CODE_OK;
import static mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.utils.Constantes.RESP_CODE_WEB_OK;
import static mx.com.telcel.di.sds.gsac.dapmov.precio_inteligente.utils.VariablesSesion.usuarioSession;
*/


/**
 * Description: Clase encargada de tener las reglas del negocio para el Login
 */
public class LoginPresenter {
    private static final Logger log = LogFile.getLogger(LoginPresenter.class);
    private final LoginActivity view;

    public LoginPresenter(LoginActivity view) {
        this.view = view;
    }

    /**
     * Description: Funci??n encargada de cargar las preferencias de de sessi??n
     */
    /*
    public void cargarPreferenciasSession() {
        try {
            String idUsuario = SharedPref.getString(Constantes.SHAR_PREF_USUARIO);
            if (idUsuario != null) {
                view.etIdUsuario.setText(idUsuario);
                view.mSwitch.setChecked(true);
            }
        } catch (Exception e) {
            log.error("Error:" + e.getMessage());
        }
    }*/

    public void cambiarColorBoton() {
        if (view.etIdUsuario != null && view.etContrasenia != null && !view.etIdUsuario.getText().toString().isEmpty() && !view.etContrasenia.getText().toString().isEmpty()) {
            view.btnEntrar.setEnabled(true);
        } else {
            view.btnEntrar.setEnabled(false);
        }
    }

    /**
     * Description: Funci??n encargada de validar que los campos usuario y contrase??a se encuentren llenos
     */
    private boolean validaCamposLlenos() {
        return (!view.etIdUsuario.getText().toString().isEmpty() && !view.etContrasenia.getText().toString().isEmpty());
    }

    /**
     * Description: Funci??n encargada de consumir el servicio Login
     */

    private void consumirServicioLogin() {
        log.info("--consumirServicioLogin--");
        AnimacionesGenerales.mostrarLoader(true, view, view.getString(R.string.validando_credenciales), view.getString(R.string.wait_retrofit));
        ReqLoginDto reqLoginDto = new ReqLoginDto();
        reqLoginDto.setEmailUsuario(view.etIdUsuario.getText().toString().replaceAll("\\s", ""));
        reqLoginDto.setContraUsuario(view.etContrasenia.getText().toString().replaceAll("\\s", ""));
        RestClientService api = new RestClientServiceImpl();
        Call<RespLoginDto> call = api.login(reqLoginDto);
        log.info("REQUEST:" + reqLoginDto.toString());
        Log.d("LOGIN APP PRESENTER REQUEST: ",reqLoginDto.toString());
        System.out.println(reqLoginDto.toString());
        //guardar idUsuario en un shared preference
        call.enqueue(new Callback<RespLoginDto>() {
            @Override
            public void onResponse(Call<RespLoginDto> call, retrofit2.Response<RespLoginDto> response) {
                if (response != null && response.code() == RESP_CODE_WEB_OK ) {
                    AnimacionesGenerales.mostrarLoader(false, view, null, null);
                    /*usuarioSession = response.body().getPayLoad();
                    usuarioSession.setIdUsuario(view.etIdUsuario.getText().toString());
                    log.info("RESPONSE:" + usuarioSession.toString());
                    //Si est?? chequeado seteamos el usuario, en caso contrario seteamos null para olvidar el usuario
                    SharedPref.setString(Constantes.SHAR_PREF_USUARIO, view.mSwitch.isChecked() ? view.etIdUsuario.getText().toString() : null);
                    Ocultamos el di??logo
                    */

                    //Si el inicio de sesi??n es exitoso entonces guardamos el Primer evento de LOG
                    try{
                        System.out.println("");
                        Log.d("LOGIN APP PRESENTER","RESPONSE EXITOSO");
                        System.out.println(response.body());
                        SharedPref.guardarIdUsuario(view,response.body().getIdUsuario());
                        Toast.makeText(view,"Respuesta exitosa",Toast.LENGTH_SHORT).show();
                        cargarDatosUsuario();
                        //consumirServicioBitacoraLogin(response.body().getPayLoad().getIdFuerzaDeVenta(), view.etIdUsuario.getText().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    // Cambia de activity
                    Intent intent = new Intent(view, MainDrawerActivity.class);
                    view.startActivity(intent);
                    view.finish();

                    view.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    //Cambiar el Navigation

                    /*NavController navController = Navigation.findNavController(view, R.id.fragContentLoged);
                    navController.navigate(R.id.toMainDrawerActivity);*/



                    
                } else {
                    view.etContrasenia.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    view.etIdUsuario.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    view.etIdUsuario.requestFocus();
                    AnimacionesGenerales.mostrarLoader(false, view, null, null);
                    //log.info("Response:"+response.body().toString());
                    /*if (response != null && response.code() == RESP_CODE_WEB_OK ) {
                        log.error(response.body().getDetailResponse().getCode() + " " + response.body().getDetailResponse().getBusinessMeaning());
                        AnimacionesGenerales.mostrarAlertDialogError(view, response.body().getDetailResponse().getBusinessMeaning(), response.body().getDetailResponse().getCode());
                    } else {
                        AnimacionesGenerales.mostrarAlertDialogErrorServer(view);
                        Toast.makeText(view, view.getResources().getString(R.string.msj_password_incorrecto), Toast.LENGTH_LONG).show();
                    }*/
                }
            }

            @Override
            public void onFailure(Call<RespLoginDto> call, final Throwable t) {
                t.printStackTrace();
                AnimacionesGenerales.mostrarLoader(false, view, null, null);
                AnimacionesGenerales.mostrarAlertDialogErrorServer(view);
            }
        });
    }

    //

    public void cargarDatosUsuario(){
        System.out.println("--consultar datos del usuario--");
        //obtener el id con el shared preferences
        Integer idUsuario = SharedPref.obtenerIdUsuario(view);
        RestClientService api = new RestClientServiceImpl();
        Call<RespUserData> call = api.getUserById(idUsuario.toString());

        call.enqueue(new Callback<RespUserData>() {
            @Override
            public void onResponse(Call<RespUserData> call, Response<RespUserData> response) {
                System.out.println("code response:"+response.code());
                if (response != null && response.code() == RESP_CODE_WEB_OK ) {
                    AnimacionesGenerales.mostrarLoader(false,view, null, null);

                    try{
                        System.out.println("");
                        Log.d("GET USER DATA BY ID","RESPONSE EXITOSO");
                        System.out.println(response.body().toString());
                        SharedPref.guardarUsuario(view,response.body().toString());
                        Toast.makeText(view,"Respuesta exitosa",Toast.LENGTH_SHORT).show();
                        //consumirServicioBitacoraLogin(response.body().getPayLoad().getIdFuerzaDeVenta(), view.etIdUsuario.getText().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                        AnimacionesGenerales.mostrarLoader(false,view, null, null);
                        AnimacionesGenerales.mostrarAlertDialogErrorServer(view);
                    }



                } else {

                    System.out.println("No se obtuvieron los datos");
                    Log.d("errorMessage", "El response no fue exitoso al obtener los datos del usuario");

                    AnimacionesGenerales.mostrarLoader(false,view
                            , null, null);

                }
            }

            @Override
            public void onFailure(Call<RespUserData> call, Throwable t) {
                t.printStackTrace();
                System.out.println("No se obtuvieron los datos");
                AnimacionesGenerales.mostrarLoader(false, view, null, null);
                AnimacionesGenerales.mostrarAlertDialogErrorServer(view);
            }
        });
    }

    /**
     * Description: Funci??n encargada de validar que el usuario y contrase??a existan
     */
    public void validaUsuarioYContrasenia() {
        log.info("--validaUsuarioYPassword--");
        if (validaCamposLlenos()) {
            consumirServicioLogin();
        } else {
            Toast.makeText(view, view.getString(R.string.input_credentials), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Description: Funci??n encargada de consumir el servicio de Bitacora Login
     */

    /*
    private void consumirServicioBitacoraLogin(String fuerza_venta, String num_empleado) {
        log.info("--consumirServicioBitacoraLogin--");

        ReqBitacoraLoginAppDto reqLoginDto = new ReqBitacoraLoginAppDto();
        reqLoginDto.setFuerzaVentaM2k(fuerza_venta);
        reqLoginDto.setNumeroEmpleado(num_empleado);

        String imei="";
        try {
            imei = getDeviceUniqueID(view);
            log.info("IMEI: LOGIN "+ imei);
        }catch (Exception e){
            e.printStackTrace();
        }
        reqLoginDto.setImei(imei);
        reqLoginDto.setTipo(view.getString(R.string.name_login));
        reqLoginDto.setRegion(usuarioSession.getRegion());

        RestClientService api = new RestClientServiceImpl();
        Call<ResponseMasterDto<RespBitacoraLoginDto>> call = api.guardaLoginApp(reqLoginDto);
        log.info("REQUEST_BITACORA:" + reqLoginDto.toString());
        call.enqueue(new Callback<ResponseMasterDto<RespBitacoraLoginDto>>() {
            @Override
            public void onResponse(Call<ResponseMasterDto<RespBitacoraLoginDto>> call, retrofit2.Response<ResponseMasterDto<RespBitacoraLoginDto>> response) {
                if (response != null && response.code() == RESP_CODE_WEB_OK && response.body().getDetailResponse().getDescription().equals("Response exitoso")) {
                       log.info("Respuesta Bitacora: " + response.body().getDetailResponse().getDescription());
                } else {
                    if (response != null && response.code() == RESP_CODE_WEB_OK &&
                            !response.body().getDetailResponse().getCode().equals(RESP_CODE_OK)) {
                        log.error(response.body().getDetailResponse().getCode() + " " + response.body().getDetailResponse().getBusinessMeaning());
                    } else {
                       log.info("ERROR EN ALMACENAR BITACORA LOGIN");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMasterDto<RespBitacoraLoginDto>> call, final Throwable t) {
                log.error(t.getMessage());
                t.printStackTrace();
                log.info("ERROR EN ALMACENAR BITACORA LOGIN_3");
            }
        });
    }*/


    /*
    @SuppressLint("HardwareIds")
    public static String getDeviceUniqueID(Context activity){
        return Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
    */

}
