package com.example.farouk.roomx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.util.List;

public class BeHostt extends AppCompatActivity {

    Button add ;
    Button save;
    EditText nameo;
    pl.polak.clicknumberpicker.ClickNumberPickerView pickergust;
    pl.polak.clicknumberpicker.ClickNumberPickerView pickerbed;
    pl.polak.clicknumberpicker.ClickNumberPickerView pickerbathroom;

    EditText horzintl ;
    EditText vertical;
    EditText deproperty;
    CheckBox  air;
    CheckBox pool;
    CheckBox tv;
    CheckBox kitchen;
    CheckBox  net;

    int AddPicNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_hostt);
        add = (Button) findViewById(R.id.bt_behost_add);
        save = (Button) findViewById(R.id.bt_behost_save);
        pickergust = (pl.polak.clicknumberpicker.ClickNumberPickerView) findViewById(R.id.clickNumberPickerView3);
        pickerbed = (pl.polak.clicknumberpicker.ClickNumberPickerView) findViewById(R.id.clickNumberPickerView2);
        pickerbathroom = (pl.polak.clicknumberpicker.ClickNumberPickerView) findViewById(R.id.clickNumberPickerView1);
        nameo = (EditText) findViewById(R.id.ed_BeHost_Nowner);
        horzintl = (EditText) findViewById(R.id.ed_BeHost_H);
        vertical = (EditText) findViewById(R.id.ed_BeHost_V);
        deproperty = (EditText) findViewById(R.id.ed_BeHost_deproperty);

        air = (CheckBox) findViewById(R.id.cb_behost_air);
        pool = (CheckBox) findViewById(R.id.cb_behost_pool);
        tv = (CheckBox) findViewById(R.id.cb_behost_tv);
        kitchen = (CheckBox) findViewById(R.id.cb_behost_kitchen);
        net = (CheckBox) findViewById(R.id.cb_behost_net);






        //pickergust.getValue();

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




             GalleryConfig config = new GalleryConfig.Build()
                        .limitPickPhoto(8)
                        .singlePhoto(false)
                        .hintOfPick("this is pick hint")
                        .filterMimeTypes(new String[]{"image/jpeg"})
                        .build();


                    GalleryActivity.openActivity(BeHostt.this, 9, config);

            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //save on click

            }

        });

        horzintl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getApplication(),vertical.getText(),Toast.LENGTH_LONG).show();

            }
        });
        vertical.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Toast.makeText(getApplication(),vertical.getText(),Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


try {

    //list of photos of seleced
    List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);


    for (int i = 0; i < photos.size(); i++) {
        AddPicNumber = photos.size();
    }

    save.setText(getString(R.string.NImages)+AddPicNumber);
}catch (Exception e ){

    save.setText(getString(R.string.apictures));
}
    }




}
