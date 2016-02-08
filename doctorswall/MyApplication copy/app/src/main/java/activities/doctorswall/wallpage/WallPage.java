package activities.doctorswall.wallpage;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import application.doctorswall.victor.myapplication.R;
import victor.doctorswall.components.MyTitleFontTextView;

public class WallPage extends AppCompatActivity {
    ProgressDialog pdialog;

    List<BroadcastPost> broadcastlist;

    private RecyclerView mRecyclerView;

    private WallAdapter adapter;

    public ImageButton btnNotification, btnActionMenu;
    public MyTitleFontTextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_page);

        // Inflate your custom layout
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.custom_action_bar,
                null);

        // Set up your ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        btnNotification = (ImageButton) actionBarLayout
                .findViewById(R.id.btnActionNotification);
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvTitle = (MyTitleFontTextView) actionBarLayout
                .findViewById(R.id.tvTitle);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnActionMenu = (ImageButton) actionBarLayout
                .findViewById(R.id.btnActionMenu);
        btnActionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.listpost);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        broadcastlist = new ArrayList<BroadcastPost>();

        new loadDummyData().execute();
    }


    private class loadDummyData extends AsyncTask<Void,Void,String> {

        List<BroadcastPost> thislist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            thislist = new ArrayList<BroadcastPost>();
            pdialog = new ProgressDialog(WallPage.this);
            pdialog.setMessage("Please wait");
            pdialog.show();
            pdialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected String doInBackground(Void... params) {

            BroadcastPost bpos = new BroadcastPost();
            bpos.setMallName("SM Mall");
            bpos.setDatePosted("yesterday");
            bpos.setUrlPostImage("http://files.parsetfss.com/926a6895-ff6d-4b54-bd4f-0fa6a7b0d4a2/tfss-4ec7dca0-19d4-4e84-a5b5-a0dbf23841dd-chatphoto.jpg");
            bpos.setPostHeadTasg("See you There!");
            bpos.setPostSummary("see you all this Saturday!\n" +
                    "The first ever Sneaker Carnival in the world! A one day sneaker celebration complemented by a curation of Food from the best culinary minds in the metro as well as a showcase of cutting edge Music, and amazing Sneaker Art that happened at SM Mall of Asia's Concert Grounds last November 29, 2015");
            bpos.setCurrentRate("1k");
            bpos.setNumComments("6");
            thislist.add(bpos);

            bpos = new BroadcastPost();
            bpos.setMallName("SM Mall");
            bpos.setDatePosted("5 hours ago");
            bpos.setUrlPostImage("http://files.parsetfss.com/926a6895-ff6d-4b54-bd4f-0fa6a7b0d4a2/tfss-4ec7dca0-19d4-4e84-a5b5-a0dbf23841dd-chatphoto.jpg");
            bpos.setPostHeadTasg("Today's the day!!");
            bpos.setPostSummary(" Experience Cebu's NEWEST 'It' destination as the MallAboveAllElse opens its doors to bring you fun, food, and finds like no other! See you!");
            bpos.setCurrentRate("500");
            bpos.setNumComments("12");
            thislist.add(bpos);

            return "yes";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new WallAdapter(WallPage.this, thislist);
            mRecyclerView.setAdapter(adapter);
            pdialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
