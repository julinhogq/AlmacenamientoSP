package com.example.almacenamientosp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;

import java.util.Arrays;

public class SegundaActividad extends AppCompatActivity {
    private static final String TAG = "SegundaActividad";
    private TextView mName;
    EditText etSize;
    Spinner spFonts;

    private Button btnPrimaryDark;
    private Button btnPrimary;
    private Button btnBackground;

    private Window window;

    ColorPickerDialog cpdPrimaryDark;
    ColorPickerDialog cpdPrimary;
    ColorPickerDialog cpdBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        this.window = getWindow();

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();

        String PrimaryDark = mPreferences.getString(getString(R.string.app_color_primary_dark), "x");
        String Primary = mPreferences.getString(getString(R.string.app_color_primary), "x");
        String Background = mPreferences.getString(getString(R.string.app_color_background), "x");

        if(PrimaryDark.equals("x")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                PrimaryDark = String.format("#%06X", (0xFFFFFF & window.getStatusBarColor()));
            }

            editor.putString(getString(R.string.app_color_primary_dark), PrimaryDark);
            editor.commit();
        }

        if(Primary.equals("x")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Primary = String.format("#%06X", (0xFFFFFF & window.getNavigationBarColor()));
            }

            editor.putString(getString(R.string.app_color_primary), PrimaryDark);
            editor.commit();
        }

        if(Background.equals("x")){
            Background = String.format("#%06X", (0xFFFFFF & ((ColorDrawable) window.getDecorView().getBackground()).getColor()));

            editor.putString(getString(R.string.app_color_background), Background);
            editor.commit();
        }

        mName = (TextView) findViewById(R.id.etNombre_SP);

        etSize = (EditText) findViewById(R.id.etSize);
        spFonts = (Spinner) findViewById(R.id.spFonts);

        btnPrimaryDark = (Button) findViewById(R.id.btnPrimaryDark);
        btnPrimary = (Button) findViewById(R.id.btnPrimary);
        btnBackground = (Button) findViewById(R.id.btnBackground);

        cpdPrimaryDark = ColorPickerDialog.createColorPickerDialog(this);
        cpdPrimary = ColorPickerDialog.createColorPickerDialog(this);
        cpdBackground = ColorPickerDialog.createColorPickerDialog(this);

        etSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    float size = Float.parseFloat(s.toString());

                    editor.putFloat(getString(R.string.font_size), size);
                    editor.commit();

                    mName.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String[] fuentes = {"sans-serif", "monospace", "casual", "cursive", "serif-monospace"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, fuentes);
        spFonts.setAdapter(adapter);

        spFonts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mName.setTypeface(Typeface.create(fuentes[position], Typeface.NORMAL));

                editor.putString(getString(R.string.font_family), fuentes[position]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cpdPrimaryDark.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                editor.putString(getString(R.string.app_color_primary_dark), hexVal);
                editor.commit();

                actualizarColor(mPreferences);
            }
        });

        cpdPrimary.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                editor.putString(getString(R.string.app_color_primary), hexVal);
                editor.commit();

                actualizarColor(mPreferences);
            }
        });

        cpdBackground.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                editor.putString(getString(R.string.app_color_background), hexVal);
                editor.commit();

                actualizarColor(mPreferences);
            }
        });

        btnPrimaryDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PrimaryDark = mPreferences.getString(getString(R.string.app_color_primary_dark), "x");
                cpdPrimaryDark.setHexaDecimalTextColor(Color.parseColor(PrimaryDark));
                cpdPrimaryDark.show();
            }
        });

        btnPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Primary = mPreferences.getString(getString(R.string.app_color_primary), "x");
                cpdPrimary.setHexaDecimalTextColor(Color.parseColor(Primary));
                cpdPrimary.show();
            }
        });

        btnBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Background = mPreferences.getString(getString(R.string.app_color_background), "x");
                cpdBackground.setHexaDecimalTextColor(Color.parseColor(Background));
                cpdBackground.show();
            }
        });

        String name = mPreferences.getString(getString(R.string.name), "");
        mName.setText(name);

        float size = mPreferences.getFloat(getString(R.string.font_size), 25);
        mName.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        etSize.setText("" + size);

        String fuente = mPreferences.getString(getString(R.string.font_family), "sans-serif");
        mName.setTypeface(Typeface.create(fuente, Typeface.NORMAL));
        spFonts.setSelection(Arrays.asList(fuentes).indexOf(fuente));

        cambiarColor(PrimaryDark, Primary, Background);
    }

    private void actualizarColor(SharedPreferences mPreferences){
        String PrimaryDark = mPreferences.getString(getString(R.string.app_color_primary_dark), "x");
        String Primary = mPreferences.getString(getString(R.string.app_color_primary), "x");
        String Background = mPreferences.getString(getString(R.string.app_color_background), "x");

        cambiarColor(PrimaryDark, Primary, Background);
    }

    private void cambiarColor(String primaryDark, String primary, String background){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor(primaryDark));
            window.setNavigationBarColor(Color.parseColor(primary));
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
    }
}