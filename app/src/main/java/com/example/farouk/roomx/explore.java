package com.example.farouk.roomx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class explore extends AppCompatActivity {

    ListView list;
    CustomListAdapter adapter;
    ImageButton love ;

    String[] titel ={
            "new flate fdsdfm bdfkfg 120 m can play what you want fsdfsdfsdfsf",
            "new Room fdsdfm bdfkfg 120 m can play what you want fsdfsdfsdfsf",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War"
    };
    String[] NMEE ={
            "farouk h albakri",
            "abir",
            "ahmad",
            "amera",
            "most",
            "ssds",
            "sdf",
            "dfsdfwe"
    };
    String[] ss ={
            "23/2/2017",
            "22/1/2018",
            "22/1/2020",
            "22/1/2020",
            "22/1/2020",
            "22/1/2020",
            "22/1/2020",
            "22/1/2020"
    };
    String[] cityy ={
            "gaza",
            "cairo",
            "Egypt",
            "maroco",
            "sdfsdf",
            "sdff",
            "sdffsd",
            "2ewr"
    };
    Integer[] imgid={
            R.drawable.flate,
            R.drawable.room,
            R.drawable.building,
            R.drawable.building,
            R.drawable.building,
            R.drawable.building,
            R.drawable.building,
            R.drawable.building
    };
    Integer[] imd={
            R.drawable.ttt,
            R.drawable.tttt,
            R.drawable.angry,
            R.drawable.confused,
            R.drawable.like,
            R.drawable.happy,
            R.drawable.confused,
            R.drawable.angry
    };


    Integer[] like={
            R.drawable.like,
            R.drawable.unliike,
            R.drawable.like,
            R.drawable.like,
            R.drawable.unliike,
            R.drawable.unliike,
            R.drawable.like,
            R.drawable.unliike
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        love = (ImageButton)findViewById(R.id.imge_love);
        list=(ListView)findViewById(R.id.ep_list);
         adapter=new CustomListAdapter(this, titel, imgid,like,NMEE,ss,cityy,NMEE,imd);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new  OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= titel[+position];
                Toast.makeText(getApplicationContext(),"fff"+ Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });




    }

}
