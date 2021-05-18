package com.example.almacenamientosp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText mUsuario, mClave;
    private Button btnLogin;
    private CheckBox mCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsuario = (EditText) findViewById(R.id.edtUsuario);
        mClave = (EditText) findViewById(R.id.edtClave);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);


        mPreferences =              PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox.isChecked()) {
                    mEditor.putString(getString(R.string.checkbox), "True");
                    mEditor.commit();

                    String name = mUsuario.getText().toString();
                    mEditor.putString(getString(R.string.name)
                            ,name);
                    mEditor.commit();

                    String password = mClave.getText().toString();
                    mEditor.putString(getString
                            (R.string.password), password);
                    mEditor.commit();

                    Intent intent = new Intent(MainActivity.this
                            , SegundaActividad.class);
                    startActivity(intent);

                } else {
                    mEditor.putString(getString(R.string.checkbox), "False");
                    mEditor.commit();
                    mEditor.putString(getString(R.string.name), "");
                    mEditor.commit();
                    mEditor.putString(getString(R.string.password), "");
                    mEditor.commit();
                }
            }
        });


    }

    private void checkSharedPreferences(){
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String name = mPreferences.getString(getString(R.string.name), "");
        String password = mPreferences.getString(getString(R.string.password), "");


        mUsuario.setText(name);
        mClave.setText(password);

        if(checkbox.equals("True")){
            mCheckBox.setChecked(true);
        }else{
            mCheckBox.setChecked(false);
        }

    }

}