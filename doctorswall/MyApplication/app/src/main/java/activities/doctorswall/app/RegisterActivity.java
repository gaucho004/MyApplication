package activities.doctorswall.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import application.doctorswall.victor.myapplication.R;
import victor.croplibrary.forimage.Crop;
import victor.doctorswall.commonutilties.Constants;
import victor.doctorswall.commonutilties.ReadFiles;
import victor.doctorswall.commonutilties.Utils;
import victor.doctorswall.components.CircularImageView;
import victor.doctorswall.components.MyFontButton;
import victor.doctorswall.components.MyFontEdittextView;
import victor.doctorswall.components.MyFontTextView;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {

    ArrayList<String> list;
    ArrayList<String> listCountryCode;

    ArrayList<String> listOfSpecialties;

    RelativeLayout rWall;
    CircularImageView circProfilepic;
    
    MyFontTextView spAddresee,etSpecialties, spCountryCode;
    MyFontEdittextView  etFname, etLname, etEmail, etPass, etNumber, etAddress, etProfStmnt;
    MyFontButton btnNext;
    Drawable yourdrawable;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    private Bitmap bmp;
    private Uri uri = null;
    ParseFile photoFile, wallphotoFile;
    private String filePath = "no image";

    ParseObject pUser;
    private boolean forwall=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        list = new ArrayList<String>();
        list.add("Dr.");
        list.add("Mr.");
        list.add("Mrs.");
        list.add("Miss");
        list.add("Prof.");
        listCountryCode = parseCountryCodes();
        btnNext = (MyFontButton) findViewById(R.id.btnNext);

        rWall = (RelativeLayout) findViewById(R.id.wallpaperimage);
        circProfilepic = (CircularImageView) findViewById(R.id.contacts_ivChooseProPic);

        yourdrawable = getResources().getDrawable(R.drawable.backdefault);

        spAddresee = (MyFontTextView) findViewById(R.id.spAddressee);
        spCountryCode = (MyFontTextView) findViewById(R.id.spCCode);

        etSpecialties = (MyFontTextView) findViewById(R.id.etSpecialties);
        etFname = (MyFontEdittextView) findViewById(R.id.etFName);
        etLname = (MyFontEdittextView) findViewById(R.id.etLName);
        etEmail = (MyFontEdittextView) findViewById(R.id.etEmail);
        etPass = (MyFontEdittextView) findViewById(R.id.etPassword);
        etNumber = (MyFontEdittextView) findViewById(R.id.etNumber);
        etAddress = (MyFontEdittextView) findViewById(R.id.etAddress);
        etProfStmnt = (MyFontEdittextView) findViewById(R.id.etProfStmnt);

        rWall.setOnClickListener(this);
        spAddresee.setOnClickListener(this);
        spCountryCode.setOnClickListener(this);
        etSpecialties.setOnClickListener(this);
        circProfilepic.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    //    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

       /* // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Register Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://activities.doctorswall.app/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    public void onStop() {
        super.onStop();

     /*   // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Register Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://activities.doctorswall.app/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wallpaperimage:
                //start set wall photo (camera or gallery)
                forwall=true;
                showPictureDialog();
                break;

            case R.id.contacts_ivChooseProPic:
                //start set profile photo (camera or gallery)
                forwall=false;
                showPictureDialog();
                break;

            case R.id.etSpecialties:
                //start intent for adding titles

                startActivityForResult(new Intent(RegisterActivity.this, AddSpecialties.class), 101);

                break;
            case R.id.spAddressee:

                AlertDialog.Builder countryBuilder = new AlertDialog.Builder(RegisterActivity.this);
                countryBuilder.setTitle("how should we address you?");

                final String[] countryListArray = new String[list.size()];
                list.toArray(countryListArray);
                countryBuilder.setItems(countryListArray,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                final int which) {

                                spAddresee.setText(""+list.get(which));

                            }
                        }).show();

                break;

            case R.id.spCCode:

                AlertDialog.Builder cbuilder = new AlertDialog.Builder(RegisterActivity.this);
                cbuilder.setTitle("Country codes");

                final String[] array = new String[listCountryCode.size()];
                listCountryCode.toArray(array);
                cbuilder.setItems(array,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                spCountryCode.setText(array[which].substring(0,
                                        array[which].indexOf(" ")));
                            }
                        }).show();

                break;

            case R.id.btnNext:



                if (isvalidate()) {

                    register();

                }


                break;
            
          
        }
    }

    private void register() {

        if(!Utils.isNetworkAvailable(this)){
            Utils.showToast(getString(R.string.no_internet), getApplicationContext());
        }else {
            Utils.showCustomProgressDialog(RegisterActivity.this, "Saving your profile...", false, null);

            ParseQuery<ParseObject> pq = new ParseQuery("Doctors");
            pq.whereEqualTo("email", etEmail.getText().toString());
            pq.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        if (objects.size() > 0) {
                            Utils.removeCustomProgressDialog();
                            Toast.makeText(getApplicationContext(), "Email already Used", Toast.LENGTH_SHORT).show();

                        } else {


                            //prepare image for parsefile
                            Bitmap bitmaptoparseFile = ((BitmapDrawable) circProfilepic.getDrawable())
                                    .getBitmap();

                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmaptoparseFile.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                            byte[] scaledData = bos.toByteArray();

                            // Save the scaled image to Parse
                            photoFile = new ParseFile(etEmail.getText()
                                    .toString(), scaledData);

                            photoFile.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {


                                    if (e == null) {

                                        //save the wall paper photo
                                        //prepare image for parsefile
                                        Bitmap bitmaptoparseFile = ((BitmapDrawable) rWall.getBackground())
                                                .getBitmap();

                                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                        bitmaptoparseFile.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                                        byte[] scaledData = bos.toByteArray();

                                        // Save the scaled image to Parse
                                        wallphotoFile = new ParseFile("mywallphoto", scaledData);

                                        wallphotoFile.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                //saving now the ParseObject User

                                                if(e == null){
                                                    pUser = new ParseObject("Doctors");
                                                    pUser.put(Constants.Params.FIRSTNAME, etFname.getText().toString());
                                                    pUser.put(Constants.Params.LAST_NAME, etLname.getText().toString());
                                                    pUser.put(Constants.Params.EMAIL, etEmail.getText().toString());
                                                    pUser.put(Constants.Params.PASSWORD, etPass.getText().toString());
                                                    pUser.put("profilepic", photoFile);
                                                    pUser.put("wallpic", wallphotoFile);
                                                    pUser.put(Constants.Params.PICTURE, filePath);
                                                    pUser.put(Constants.Params.PHONE, spCountryCode.getText().toString().trim()
                                                            + etNumber.getText().toString());
                                                    pUser.put("degrees", etSpecialties.getText().toString());
                                                    pUser.put("addresee", spAddresee.getText().toString());
                                                    pUser.put("others", etProfStmnt.getText().toString());

                                                    pUser.put(Constants.Params.DEVICE_TYPE, Constants.DEVICE_TYPE_ANDROID);
                                                    pUser.put(Constants.Params.ADDRESS, etAddress.getText().toString());
                                                    pUser.put(Constants.Params.BIO, "");
                                                    pUser.put(Constants.Params.ZIPCODE, "");
                                                    pUser.put(Constants.Params.STATE, "");
                                                    pUser.put(Constants.Params.COUNTRY, "");
                                                    pUser.put(Constants.Params.LOGIN_BY, Constants.MANUAL);
                                                    pUser.saveEventually(new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            Utils.removeCustomProgressDialog();
                                                            if (e == null) {

                                                                Toast.makeText(getApplicationContext(),
                                                                        getString(R.string.toast_register_success),
                                                                        Toast.LENGTH_SHORT).show();

                                                                finish();

                                                            } else {

                                                                Utils.showToast("Something went wrong saving profile", getApplicationContext());

                                                            }
                                                        }
                                                    });
                                                }else{
                                                    Utils.showToast("Something went wrong saving your wall image", getApplicationContext());

                                                }


                                            }//end saving wall pic
                                        });






                                    } else {
                                        Utils.showToast("Something went wrong saving your profile pic", getApplicationContext());
                                    }


                                }
                            });


                        }

                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }

                }
            });

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                etSpecialties.setText(data.getStringExtra("degrees"));
            }
        }
        else if(requestCode == 111) { //gallery
            if (data != null) {

                uri = data.getData();
                if (uri != null) {

                    beginCrop(uri);
                } else {
                    Toast.makeText(this, "unable to select image",
                            Toast.LENGTH_LONG).show();
                }
            }


        }
        else if(requestCode == 112) { //from camera

            if (uri != null) {



                    beginCrop(uri);
                } else {
                    Toast.makeText(this, "unable to select image",
                            Toast.LENGTH_LONG).show();
                }


        }
        else if(requestCode == Crop.REQUEST_CROP){
            if (data != null)
                handleCrop(resultCode, data);

        }
    }

    public ArrayList<String> parseCountryCodes() {
        String response = "";
        ArrayList<String> list = new ArrayList<String>();
        try {
            response = ReadFiles.readRawFileAsString(RegisterActivity.this,
                    R.raw.countrycodes);

            JSONArray array = new JSONArray(response);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                list.add(object.getString("phone-code") + " "
                        + object.getString("name"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }


    private void showPictureDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
        dialog.setTitle(getString(R.string.text_choosepicture));
        String[] items = { getString(R.string.text_gallary),
                getString(R.string.text_camera) };

        dialog.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                switch (which) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;

                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rWall.setBackground(yourdrawable);
    }

    private void choosePhotoFromGallary() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 111);

    }

    private void takePhotoFromCamera() {
        Calendar cal = Calendar.getInstance();
        File file = new File(Environment.getExternalStorageDirectory(),
                (cal.getTimeInMillis() + ".jpg"));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {

            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        uri = Uri.fromFile(file);
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(i, 112);
    }

    private void beginCrop(Uri source) {
        // Uri outputUri = Uri.fromFile(new File(registerActivity.getCacheDir(),
        // "cropped"));
        Uri outputUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), (Calendar.getInstance()
                .getTimeInMillis() + ".jpg")));
        new Crop(source).output(outputUri).asSquare().start(RegisterActivity.this);
    }


    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {

            filePath = getRealPathFromURI(Crop.getOutput(result));

            if(!forwall) {
                circProfilepic.setImageURI(Crop.getOutput(result));
            }
            else{


                try {
                    InputStream inputStream = getContentResolver().openInputStream(Crop.getOutput(result));
                    yourdrawable = Drawable.createFromStream(inputStream, Crop.getOutput(result).toString());
                    rWall.setBackground(yourdrawable);
                } catch (FileNotFoundException e) {
                    yourdrawable = getResources().getDrawable(R.drawable.backdefault);
                    rWall.setBackground(yourdrawable);
                    Utils.showToast("Unable to set Wall Image", getApplicationContext());
                    Log.d("action", "something went wrong on image wall setting");
                }
            }

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getApplicationContext(), Crop.getError(result).getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null,
                null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public boolean isvalidate(){
        String msg = null;
        if (TextUtils.isEmpty(etFname.getText().toString())) {
            msg = getString(R.string.text_enter_name);
            etFname.requestFocus();
        } else if (TextUtils.isEmpty(etLname.getText().toString())) {
            msg = getString(R.string.text_enter_lname);
            etLname.requestFocus();
        } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
            msg = getString(R.string.text_enter_email);
            etEmail.requestFocus();
        } else if (!Utils.eMailValidation((etEmail.getText().toString()))) {
            msg = getString(R.string.text_enter_valid_email);
            etEmail.requestFocus();
        } else if (etPass.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(etPass.getText().toString())) {
                msg = getString(R.string.text_enter_password);
                etPass.requestFocus();
            } else if (etPass.getText().toString().length() < 6) {
                msg = getString(R.string.text_enter_password_valid);
                etPass.requestFocus();
            }
        }else if (TextUtils.isEmpty(spCountryCode.getText().toString())){
            msg = "Please Tell your Degree(s)";

        }


        if (msg != null) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return false;

        }



        if (TextUtils.isEmpty(etNumber.getText().toString())) {
           // msg = getString(R.string.text_enter_number);
           // etNumber.requestFocus();
            etNumber.setText("");
        }

        if(TextUtils.isEmpty(etProfStmnt.getText().toString())){
            etProfStmnt.setText("");
        }

        if(TextUtils.isEmpty(etAddress.getText().toString())){
            etAddress.setText("");
        }

        if (msg == null) {
            return true;
        }

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        return false;
    }
}
