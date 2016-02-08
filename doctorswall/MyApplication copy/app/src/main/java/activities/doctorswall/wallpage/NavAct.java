package activities.doctorswall.wallpage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.doctorswall.victor.myapplication.MainActivity;
import application.doctorswall.victor.myapplication.R;
import victor.doctorswall.commonutilties.PreferenceHelper;
import victor.doctorswall.commonutilties.Utils;
import victor.doctorswall.components.CircularImageView;
import victor.doctorswall.components.MyTitleFontTextView;

public class NavAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ProgressDialog pdialog;

    List<BroadcastPost> broadcastlist;

    private RecyclerView mRecyclerView;
    BroadcastPost bpost;
    CircularImageView cPf, wallProfilePic;
    private WallAdapter adapter;
    TextView tvnameWall, tvspecialty;
    ParseFile pf, wf;
    public ImageButton btnNotification, btnActionMenu;
    public MyTitleFontTextView tvTitle;

    RelativeLayout wallimage;

    ImageView btn_addpost;

    PreferenceHelper pHelper;

    LinearLayout wallDrawer;

    String POST_CLASS = "mypost";
    String POST_TITLE = "post_title";
    String POST_BODY = "post_body";
    String POST_IMAGE = "post_image";
    String POST_HAS_IMAGE = "hasimage";
    String POST_USERID = "doctorobjectid";
    String POST_PROFILE_OWNER = "profile_owner";
    String POST_PROFILENAME = "profile_name";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        pHelper = new PreferenceHelper(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        wallProfilePic = (CircularImageView) findViewById(R.id.docprofilepic);
        tvnameWall = (TextView) findViewById(R.id.doc_textname);
        tvnameWall.setText(pHelper.getUSER_NAME());
        wallimage = (RelativeLayout) findViewById(R.id.docwall);

        tvspecialty = (TextView) findViewById(R.id.doc_specialization);
        tvspecialty.setText(pHelper.getSpecialties());

        View vHeader = navigationView.getHeaderView(0);
        TextView tvname = (TextView) vHeader.findViewById(R.id.nav_header_name);
        TextView tvemail = (TextView) vHeader.findViewById(R.id.nav_header_email);
         cPf = (CircularImageView) vHeader.findViewById(R.id.doctor_ivprofilepic_drawer);
        wallDrawer = (LinearLayout) vHeader.findViewById(R.id.drawerwall);


        tvname.setText(pHelper.getUSER_NAME());
        tvemail.setText(pHelper.getEmail());



        btn_addpost = (ImageView) findViewById(R.id.doctor_addpost);
        btn_addpost.setOnClickListener(this);


        mRecyclerView = (RecyclerView) findViewById(R.id.listpost);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        broadcastlist = new ArrayList<BroadcastPost>();

        // new loadDummyData().execute();

       // getPic();

    }

    private void getPic() {

        ParseQuery<ParseObject> pq = new ParseQuery<ParseObject>("Doctors");
        pq.whereEqualTo("objectId", pHelper.getUserId());
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null && objects.size() > 0){

                     pf = objects.get(0).getParseFile("profilepic");
                    wf = objects.get(0).getParseFile("wallpic");

                    Picasso.with(getApplicationContext()).load(pf.getUrl()).placeholder(R.drawable.default_user_icon).error(R.drawable.default_user_icon).into(wallProfilePic);



                    Picasso.with(getApplicationContext()).load(pf.getUrl()).placeholder(R.drawable.default_user_icon).error(R.drawable.default_user_icon).into(cPf);


                    Picasso.with(getApplicationContext()).load(wf.getUrl()).placeholder(R.drawable.backdefault).error(R.drawable.backdefault).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


                            wallimage.setBackgroundDrawable(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                            wallDrawer.setBackgroundDrawable(new BitmapDrawable(getApplicationContext().getResources(), bitmap));

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });



                }else{
                    Log.d("action","unable to fetch image");
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
refreshPage();    }

    private void getMyPosts() {

        if(broadcastlist!=null){
            if(broadcastlist.size()!=0){
                broadcastlist.clear();
            }
        }

        if (Utils.isNetworkAvailable(NavAct.this)) {


            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(POST_CLASS);
            query.whereEqualTo(POST_USERID, pHelper.getUserId());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        if (objects != null && objects.size() > 0) {


                            for (ParseObject pobj : objects) {

                                bpost = new BroadcastPost();
                                bpost.setBroadcastid(pobj.getObjectId());
                                bpost.setPostHeadTasg(pobj.getString(POST_TITLE));
                                bpost.setPostSummary(pobj.getString(POST_BODY));
                                bpost.setHasimage(pobj.getBoolean(POST_HAS_IMAGE));
                                bpost.setDatetime(pobj.getCreatedAt());
                                bpost.setMallName(pobj.getString(POST_PROFILENAME));
                                bpost.setUrlProfileImage(pobj.getString(POST_PROFILE_OWNER));

                                if (pobj.getBoolean(POST_HAS_IMAGE)) {

                                    bpost.setUrlPostImage(pobj.getParseFile(POST_IMAGE).getUrl());


                                }


                                broadcastlist.add(bpost);


                            }


                           // Collections.sort(broadcastlist);
                            Collections.reverse(broadcastlist);

                            adapter = new WallAdapter(NavAct.this, broadcastlist);
                            mRecyclerView.setAdapter(adapter);


                        } else {
                            Utils.showToast("You Have no Posts! Write your post now!", getApplicationContext());
                        }


                    } else {
                        Utils.showToast("Something went wrong Getting Posts!", getApplicationContext());
                    }


                }
            });


        } else {
            Utils.showToast("No Network!", getApplicationContext());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            refreshPage();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshPage() {


        getPic();
        getMyPosts();


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_contacts) {

        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_activity_log) {

        } else if (id == R.id.nav_badges) {

        } else if (id == R.id.nav_alerts) {

        } else if (id == R.id.nav_logout) {


            new AlertDialog.Builder(NavAct.this)
                    .setTitle(getString(R.string.dialog_logout))
                    .setMessage(getString(R.string.dialog_logout_text))
                    .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {

                                    pHelper.Logout();
                                    startActivity(new Intent(NavAct.this, MainActivity.class));
                                    finish();


                                }
                            })
                    .setNegativeButton(android.R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    // do nothing
                                    dialog.cancel();
                                }
                            })
                    .setIcon(android.R.drawable.ic_dialog_alert).show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.doctor_addpost:

                startActivity(new Intent(NavAct.this, CreatePost.class));


                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }

    private class loadDummyData extends AsyncTask<Void, Void, String> {

        List<BroadcastPost> thislist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            thislist = new ArrayList<BroadcastPost>();
            pdialog = new ProgressDialog(NavAct.this);
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
            adapter = new WallAdapter(NavAct.this, thislist);
            mRecyclerView.setAdapter(adapter);
            pdialog.dismiss();
        }
    }
}
