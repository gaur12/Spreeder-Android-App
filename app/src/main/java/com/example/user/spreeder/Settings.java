package com.example.user.spreeder;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.danielnilsson9.colorpickerview.dialog.ColorPickerDialogFragment;
import com.github.danielnilsson9.colorpickerview.dialog.ColorPickerDialogFragment.ColorPickerDialogListener;

public class Settings extends AppCompatActivity implements ColorPickerDialogListener{
    Button fButton;
    Button bButton;
    EditText chunk;
    EditText size;
    EditText speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        fButton = (Button) findViewById(R.id.fColor);
        fButton.setBackgroundColor(Color.parseColor(MainActivity.getInstance().getSharedPreferences().getString("textColor","#5a2bc3")));
        bButton = (Button) findViewById(R.id.bColor);
        bButton.setBackgroundColor(Color.parseColor(MainActivity.getInstance().getSharedPreferences().getString("backgroundColor","#a9a9a9")));
        chunk = (EditText) findViewById(R.id.chunk_input);
        chunk.setText(MainActivity.getInstance().getSharedPreferences().getString("chunkSize", "1"));
        size = (EditText) findViewById(R.id.text_size_input);
        size.setText(MainActivity.getInstance().getSharedPreferences().getString("textSize", "30"));
        speed = (EditText) findViewById(R.id.speed_input);
        speed.setText(MainActivity.getInstance().getSharedPreferences().getString("speed", "300"));
    }


    public void onClickTextColorPickerDialog(View view) {

        // The color picker menu item has been clicked. Show
        // a dialog using the custom ColorPickerDialogFragment class.

        ColorPickerDialogFragment f = ColorPickerDialogFragment
                .newInstance(0, null, null, Color.BLACK, true);
        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.LightPickerDialogTheme);
        f.show(getFragmentManager(), "d");

    }
    public void onClickBackgroundColorPickerDialog(View view) {

        // The color picker menu item has been clicked. Show
        // a dialog using the custom ColorPickerDialogFragment class.

        ColorPickerDialogFragment f = ColorPickerDialogFragment
                .newInstance(1, null, null, Color.BLACK, true);
        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.LightPickerDialogTheme);
        f.show(getFragmentManager(), "d");

    }

    @Override
    public void onColorSelected(int dialogId,int color) {
       Toast.makeText(getApplicationContext(), "Selected Color: " + colorToHexString(color), Toast.LENGTH_SHORT).show();
        switch (dialogId){
            case 0:
                fButton.setBackgroundColor(color);
                break;
            case 1:
                bButton.setBackgroundColor(color);
        }

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
    private static String colorToHexString(int color) {
        return String.format("#%06X", 0xFFFFFFFF & color);
    }

    public void doSave(View view) {
        ColorDrawable fButtonColor = (ColorDrawable) fButton.getBackground();
        ColorDrawable bButtonColor = (ColorDrawable) bButton.getBackground();
        MainActivity.getInstance().getSharedPreferences().edit().putString("chunkSize", chunk.getText().toString()).commit();
        MainActivity.getInstance().getSharedPreferences().edit().putString("textSize", size.getText().toString()).commit();
        MainActivity.getInstance().getSharedPreferences().edit().putString("speed", speed.getText().toString()).commit();
        MainActivity.getInstance().getSharedPreferences().edit().putString("backgroundColor", colorToHexString(bButtonColor.getColor())).commit();
        MainActivity.getInstance().getSharedPreferences().edit().putString("textColor", colorToHexString(fButtonColor.getColor())).commit();
        finish();
    }
    public void doCancel(View view) {
           finish();
    }
}
