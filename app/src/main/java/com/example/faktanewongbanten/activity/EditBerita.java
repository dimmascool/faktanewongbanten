package com.example.faktanewongbanten.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.faktanewongbanten.R;
import com.example.faktanewongbanten.model.ModelAuthor;
import com.example.faktanewongbanten.model.ModelBerita;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditBerita extends AppCompatActivity {
    Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    EditText judul,isi;
    ImageView gambar;
    Spinner spinner;
    ArrayList<String> listSpinner = new ArrayList<>();
    String sID;
    Button update;
    public SharedPreferences sh;
    String encodeImageString;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_berita);
        context=EditBerita.this;
        requestQueue = Volley.newRequestQueue(this);

        judul = findViewById(R.id.etJudul);
        isi = findViewById(R.id.etIsiBerita);
        gambar = findViewById(R.id.uplodBeritaGambar);
        spinner = findViewById(R.id.item_spinner);
        update = findViewById(R.id.btnUpdate);
        judul.setText(getIntent().getStringExtra("judul"));
        isi.setText(getIntent().getStringExtra("isi"));
        sID = getIntent().getStringExtra("idkategori").trim();
        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });
        Picasso picasso = new Picasso.Builder(context.getApplicationContext()).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    }
                })
                .build();
        encodeImageString = getIntent().getStringExtra("gambar");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanBerita();
            }
        });

        picasso.load(encodeImageString)
                .fit()
                .error(R.drawable.ic_launcher_background)
                .centerCrop().into(gambar);
        jsonParse();
    }
    private void jsonParse() {
        String url = "https://dimas.bantani.net.id/github/get_all_kategori";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String spinnerData = jsonObject.getString("kategori");
                        String spinnerid = jsonObject.getString("id_kategori");
                        //memasukkan data ke spinner
                        listSpinner.add(spinnerData);

                    }catch (JSONException e) {
                        e.printStackTrace();
                        //set data ke spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditBerita.this,
                                android.R.layout.simple_spinner_item, listSpinner);
                        spinner.setAdapter(adapter);
                        spinner.setSelection(Integer.parseInt(sID)-1);
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
    private void simpanBerita() {
        ModelAuthor ma = new ModelAuthor();
        Button btnUpload = findViewById(R.id.btnUplod);
        EditText etJudul = findViewById(R.id.etJudul);
        EditText etIsiBerita = findViewById(R.id.etIsiBerita);
        String judul = etJudul.getText().toString().trim();
        String isiberita = etIsiBerita.getText().toString().trim();
        String linkSimpan = "https://dimas.bantani.net.id/github/edit_berita";
        SharedPreferences sh = getSharedPreferences("author", Context.MODE_PRIVATE);
        Log.e("id",sh.getString("id",""));
        Log.e("idapalah",getIntent().getStringExtra("id"));
        stringRequest = new StringRequest(Request.Method.POST, linkSimpan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(context, "Simpan Data Sukses", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(context, KontenSayaActvty.class));
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
                params.put("id_berita", getIntent().getStringExtra("id"));
                params.put("isi", isiberita);
                params.put("kategori",sID);
                params.put("img_thumbnail",encodeImageString );
                //...
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void pickImageFromGallery() {
        Dexter.withActivity(EditBerita.this)
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
                gambar.setImageBitmap(bitmap);
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