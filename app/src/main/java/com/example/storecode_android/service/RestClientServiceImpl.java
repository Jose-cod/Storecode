package com.example.storecode_android.service;

import com.example.storecode_android.entidades.ReqLoginDto;
import com.example.storecode_android.entidades.RespLoginDto;
import com.example.storecode_android.entidades.RespObtenerProducto;
import com.example.storecode_android.entidades.RespUserData;
import com.example.storecode_android.entidades.ResponseMasterDto;
import com.example.storecode_android.utils.LogFile;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import static com.example.storecode_android.utils.Constantes.TIME_OUT_RETROFIT;
import static com.example.storecode_android.utils.Constantes.URL_BASE;


/**
 * Description: Implementación que contiene los métodos/servicios a los que se conectará la aplicación,
 * así como las configuraciones de retrofit
 * Created by EX383473 on 04/01/2019.
 */
public class RestClientServiceImpl implements RestClientService {
    private static final Logger log = LogFile.getLogger(RestClientServiceImpl.class);
    private RestClientService restClient;

    /**
     * En el constructor de la implementación metemos las configuraciones del retrofit
     */
    public RestClientServiceImpl() {
            try {

                /*// Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                */
                OkHttpClient client = new OkHttpClient.Builder()
                //.sslSocketFactory(sslSocketFactory)
                //.hostnameVerifier((hostname, session) -> true)
                .connectTimeout(TIME_OUT_RETROFIT, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_RETROFIT, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restClient = retrofit.create(RestClientService.class);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Construye Retrofit con listener de descarga
     */
    /*public RestClientServiceImpl(RetrofitDownloadListener listener) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .client(getOkHttpDownloadClientBuilder(listener).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restClient = retrofit.create(RestClientService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    /*public RestClientServiceImpl(RxJava2CallAdapterFactory adapter) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30*//*TIME_OUT_RETROFIT*//*, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30*//*TIME_OUT_RETROFIT*//*, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(adapter)
                .build();
        restClient = retrofit.create(RestClientService.class);
    }*/

    /*private OkHttpClient.Builder getOkHttpDownloadClientBuilder(final RetrofitDownloadListener progressListener) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        try {
            // You might want to increase the timeout
            httpClientBuilder.connectTimeout(TIME_OUT_RETROFIT, TimeUnit.SECONDS);
            httpClientBuilder.writeTimeout(0, TimeUnit.SECONDS);
            httpClientBuilder.readTimeout(TIME_OUT_RETROFIT, TimeUnit.SECONDS);
            httpClientBuilder.addInterceptor(chain -> {
                if (progressListener == null)
                    return chain.proceed(chain.request());
                Response response = chain.proceed(chain.request());
                ResponseBody body = response.body();
                String bodyString = body.string();
                MediaType contentType = body.contentType();
                return response.newBuilder().body(new ProgressResponseBody(ResponseBody.create(contentType, bodyString), progressListener, bodyString.getBytes().length)).build();
            });

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return httpClientBuilder;
    }*/

    @Override
    public Call<RespLoginDto> login(ReqLoginDto request) {
        return restClient.login(request);
    }

    @Override
    public Call<List<RespObtenerProducto>> cargarProductos(String id) {
        return restClient.cargarProductos(id);
    }


    @Override
    public Call<RespUserData> getUserById(String id) {
        return restClient.getUserById(id);
    }

    @Override
    public Call<List<RespObtenerProducto>> cargarAllProductos() {
        return restClient.cargarAllProductos();
    }



    /*@Override
    public Call<ResponseMasterDto<RespLogin_AutorizadoDto>> login_autorizado(ReqLogin_AutorizadoDto request) {
        return restClient.login_autorizado(request);
    }

    @Override
    public Call<ResponseMasterDto<RespLoginDto>> login(ReqLoginDto request) {
        return restClient.login(request);
    }

    @Override
    public Call<ResponseMasterDto<List<ResObtenerMarcaDto>>> obtenerMarcas(ReqObtenerMarcaDto request) {
        return restClient.obtenerMarcas(request);
    }

    @Override
    public Call<ResponseMasterDto<List<RespObtenerModelosDto>>> obtenerModelos(ReqObtenerModelosDto request) {
        return restClient.obtenerModelos(request);
    }

    @Override
    public Call<ResponseMasterDto<List<RespColor>>> obtenerColores(ReqObtenerColoresDto request) {
        return restClient.obtenerColores(request);
    }

    @Override
    public Call<ResponseMasterDto<List<RespObtenerPlanDto>>> obtenerPlanes(ReqObtenerPlanDto request) {
        return restClient.obtenerPlanes(request);
    }

    @Override
    public Call<ResponseMasterDto<PreciadorDispositivoDto>> construyePreciador(ReqPreciadorDispositivoDto request) {
        return restClient.construyePreciador(request);
    }

    @Override
    public Call<ResponseMasterDto<List<PreciadorDispositivoDto>>> construyePreciadores(List<ReqPreciadorDispositivoDto> request) {
        return restClient.construyePreciadores(request);
    }

    *//*@Override
    public Call<ResponseMasterDto<RespConsultaVideosDto>> consultaVideosPorModelo(ReqConsultaVideosDto request) {
        return restClient.consultaVideosPorModelo(request);
    }*//*

    @Override
    public Call<ResponseMasterDto<RespConsultaVideosDto2>> consultaVideosPorModelo(ReqConsultaVideosDto2 request) {
        return restClient.consultaVideosPorModelo(request);
    }

    @Override
    public Call<ResponseMasterDto<RespConsultaVideosPorMod>> consultaInformacionVideos(RequestConsultaVideosPorMod request) {
        return restClient.consultaInformacionVideos(request);
    }

    @Override
    public Observable<ResponseMasterDto<RespConsultaVideosDto2>> consultaVideosPorModeloObserver(@Body ReqConsultaVideosDto2 request) {
        return restClient.consultaVideosPorModeloObserver(request);
    }

    @Override
    public Call<ResponseMasterDto<RespBitacoraLoginDto>> guardaLoginApp(ReqBitacoraLoginAppDto request) {
        return restClient.guardaLoginApp(request);
    }

    @Override
    public Call<ResponseMasterDto<RespBitacoraLoginDto>> guardaColocacionApp(ReqBitacoraColocacionDto request) {
        return restClient.guardaColocacionApp(request);
    }

    @Override
    public Call<ResponseMasterDto<RespFlagStock>> getFlagStock(ReqFlagStock request) {
        return restClient.getFlagStock(request);
    }*/
}
