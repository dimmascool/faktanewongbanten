package com.example.faktanewongbanten.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.faktanewongbanten.R;
import com.example.faktanewongbanten.model.ModelKategori;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class TambahBeritaActvty extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    Context context;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ArrayList<String> listSpinner = new ArrayList<>();
    ArrayList<ModelKategori> idSpinner = new ArrayList<>();
    Spinner spinner;
    ImageView imageView;
    String sID;
    ImageView logo;
    Bitmap bitmap;
    String encodeImageString;
    Button uplod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_berita_actvty);

        context = TambahBeritaActvty.this;
        uplod = findViewById(R.id.btnUplod);
        requestQueue = Volley.newRequestQueue(this);
        imageView = findViewById(R.id.uplodBeritaGambar);
        spinner = findViewById(R.id.item_spinner);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });
        uplod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanBerita();
            }
        });
        jsonParse();
    }

    //method jsonParse()
    private void jsonParse() {
        String url = "https://dimas.bantani.net.id/github/get_all_kategori";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                        try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String spinnerData = jsonObject.getString("kategori");
                                sID = jsonObject.getString("id_kategori");
                                //memasukkan data ke spinner
                                listSpinner.add(spinnerData);
                            }catch (JSONException e) {
                            e.printStackTrace();
                            //set data ke spinner
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahBeritaActvty.this,
                                    android.R.layout.simple_spinner_item, listSpinner);

                            spinner.setAdapter(adapter);
                        }
                        }
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(position);
                                    sID = jsonObject.getString("id_kategori");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    //method isNetworkAvailable()
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void simpanBerita() {
        Button btnUpload = findViewById(R.id.btnUplod);
        EditText etJudul = findViewById(R.id.etJudul);
        EditText etIsiBerita = findViewById(R.id.etIsiBerita);
        String judul = etJudul.getText().toString().trim();
        String isiberita = etIsiBerita.getText().toString().trim();
        String linkSimpan = "https://dimas.bantani.net.id/github/create_berita";
        stringRequest = new StringRequest(Request.Method.POST, linkSimpan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(context, "Simpan Data Sukses", Toast.LENGTH_LONG).show();

                } else {
                    //Displaying an error message on toast
                    Toast.makeText(context, "Gagal Simpan Data", Toast.LENGTH_LONG).show();
                    Log.e("aaa", response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("judul", judul);
                params.put("isi", isiberita);
//                params.put("author_id", Namagame);
                params.put("kategori",sID);
                params.put("img_thumbnail",encodeImageString );
                Log.e("kategori",sID);
                //...
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void pickImageFromGallery() {
        Dexter.withActivity(TambahBeritaActvty.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        Intent intent=new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Log.e("data", response.toString());
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }
}