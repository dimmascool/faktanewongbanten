package com.example.faktanewongbanten.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.faktanewongbanten.R;

public class EditAkun extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword, etAalamat, etNohp;
    private Button btnUpdate,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        etAalamat = findViewById(R.id.et_alamat);
        etNohp = findViewById(R.id.et_nohp);
        btnUpdate = findViewById(R.id.btnUpdateAccount);
        btnCancel = findViewById(R.id.btnCancelUpdate);
    }
}