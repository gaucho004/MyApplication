package application.doctorswall.victor.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import activities.doctorswall.app.RegisterActivity;
import activities.doctorswall.wallpage.NavAct;
import activities.doctorswall.wallpage.WallPage;
import victor.doctorswall.commonutilties.Constants;
import victor.doctorswall.commonutilties.PreferenceHelper;
import victor.doctorswall.commonutilties.Utils;
import victor.doctorswall.components.MyFontButton;
import victor.doctorswall.components.MyFontEdittextView;

public class MainActivity extends AppCompatActivity {

    ImageButton btnlogin, btnregister;
    MyFontEdittextView etmail, etpassword;
MyFontButton btnforgetpass;

    PreferenceHelper pHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pHelper = new PreferenceHelper(getApplicationContext());
        btnlogin = (ImageButton) findViewById(R.id.btnSignIn);
        btnregister = (ImageButton) findViewById(R.id.btnRegister);

        etmail = (MyFontEdittextView) findViewById(R.id.etEmail);
        etpassword = (MyFontEdittextView) findViewById(R.id.etPassword);

        btnforgetpass = (MyFontButton) findViewById(R.id.btnForgetPassword);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    login();
                }
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(pHelper.getUserId()!=null){
            startActivity(new Intent(MainActivity.this, NavAct.class));
            finish();
        }
    }

    private void login() {

        if (!Utils.isNetworkAvailable(MainActivity.this)) {
            Utils.showToast(getResources().getString(R.string.no_internet),
                    getApplicationContext());
            return;
        }


        Utils.showCustomProgressDialog(MainActivity.this, "Logging you in. Please wait.", false, null);

        ParseQuery<ParseObject> pq = new ParseQuery("Doctors");
        pq.whereEqualTo(Constants.Params.EMAIL, etmail.getText().toString());
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Utils.removeCustomProgressDialog();
                if(e ==  null){

                    if(objects.size()>0){

                        if(objects.get(0).getString(Constants.Params.PASSWORD).equals(etpassword.getText().toString())){

                            String ad = objects.get(0).getString("addresee");
                            String fname = objects.get(0).getString("first_name");
                            String lname = objects.get(0).getString("last_name");
                            String titles  = objects.get(0).getString("degrees");


                            pHelper.putUserId(objects.get(0).getObjectId());
                            Log.d("action", "object id: " + pHelper.getUserId());
                           // pHelper.putProfilePic(((ParseFile) objects.get(0).get("profilepic")).getUrl());
                           // Log.d("action", "profile pic " + pHelper.getURL_PROFILE_IMAGE());


                            pHelper.putURLImagePhoto(objects.get(0).getParseFile("profilepic").getUrl());
                            Log.d("action", "url photo pic " + pHelper.getURLPhoto());

                            pHelper.putURLWallPic(objects.get(0).getParseFile("wallpic").getUrl());
                            Log.d("action", "url photo pic " + pHelper.getWallPhoto());


                            pHelper.putUserName(ad + " " + lname + ", " + titles);
                            Log.d("action", "profile name " + pHelper.getUSER_NAME());

                            pHelper.putEmail(objects.get(0).getString("email"));
                            Log.d("action", "email :  " + pHelper.getEmail());

                            pHelper.putSpecialties(objects.get(0).getString("others"));
                            Log.d("action", "specialty :  " + pHelper.getSpecialties());


                            startActivity(new Intent(MainActivity.this, NavAct.class));
                            finish();


                        }else {
                            Utils.showToast("Invalid User", getApplicationContext());

                        }

                    }else{
                        Utils.showToast("Invalid User", getApplicationContext());

                    }

                }else{
                    Utils.showToast("Invalid User", getApplicationContext());
                }
            }
        });
    }


    public boolean isValidate() {
        // TODO Auto-generated method stub
        String msg = null;
        if (TextUtils.isEmpty(etmail.getText().toString())) {
            msg = getResources().getString(R.string.text_enter_email);
        } else if (!Utils.eMailValidation(etmail.getText().toString())) {
            msg = getResources().getString(R.string.text_enter_valid_email);
        }
        if (TextUtils.isEmpty(etpassword.getText().toString())) {
            msg = getResources().getString(R.string.text_enter_password);
        }
        if (msg == null)
            return true;

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        return false;
    }

}
