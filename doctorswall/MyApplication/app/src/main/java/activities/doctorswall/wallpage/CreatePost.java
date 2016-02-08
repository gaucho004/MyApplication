package activities.doctorswall.wallpage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import application.doctorswall.victor.myapplication.R;
import victor.croplibrary.forimage.Crop;
import victor.doctorswall.commonutilties.PreferenceHelper;
import victor.doctorswall.commonutilties.Utils;
import victor.doctorswall.components.MyFontButton;
import victor.doctorswall.components.MyFontEdittextView;

public class CreatePost extends AppCompatActivity implements View.OnClickListener {

    MyFontButton btn_post;
    MyFontEdittextView ettitle,etbody;
    boolean haspic;
    ImageView imgpost;

    ParseFile postFile;
    ParseObject pObj_Post;

    String POST_CLASS = "mypost";
    String POST_TITLE = "post_title";
    String POST_BODY = "post_body";
    String POST_IMAGE = "post_image";
    String POST_HAS_IMAGE = "hasimage";
    String POST_PROFILE_OWNER = "profile_owner";
    String POST_PROFILENAME = "profile_name";

    private String filePath = null;
    private Uri uri = null;

    PreferenceHelper pHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        pHelper = new PreferenceHelper(getApplicationContext());
        btn_post = (MyFontButton) findViewById(R.id.btn_post_now);
        ettitle = (MyFontEdittextView) findViewById(R.id.etPost_Title);
        etbody = (MyFontEdittextView) findViewById(R.id.etPost_Statement);
        imgpost = (ImageView) findViewById(R.id.doctor_image_post);

        imgpost.setOnClickListener(this);
        btn_post.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_post_now:

                if(isvalidate()) {
                    saveNow();
                }

                break;

            case R.id.doctor_image_post:

                showPictureDialog();

                break;
        }
    }

    private void saveNow() {

        if(Utils.isNetworkAvailable(CreatePost.this)){

            Utils.showCustomProgressDialog(CreatePost.this, "Writing now in your wall...", false, null);

            //prepare image for parsefile
            Bitmap bitmaptoparseFile = ((BitmapDrawable) imgpost.getDrawable())
                    .getBitmap();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmaptoparseFile.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            byte[] scaledData = bos.toByteArray();

            // Save the scaled image to Parse
            postFile = new ParseFile(Utils.randomString(7), scaledData);

            postFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e == null){
                        pObj_Post = new ParseObject(POST_CLASS);
                        pObj_Post.put("doctorobjectid",pHelper.getUserId() );
                        pObj_Post.put(POST_TITLE, ettitle.getText().toString());
                        pObj_Post.put(POST_BODY, etbody.getText().toString());
                      //  pObj_Post.put(POST_PROFILE_OWNER, pHelper.getURL_PROFILE_IMAGE());
                        pObj_Post.put(POST_PROFILENAME, pHelper.getUSER_NAME());

                        if(haspic){
                            pObj_Post.put(POST_HAS_IMAGE, true);
                            pObj_Post.put(POST_IMAGE,postFile);
                        }else{
                            pObj_Post.put(POST_HAS_IMAGE, false);

                        }

                        pObj_Post.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                Utils.removeCustomProgressDialog();


                                if (e == null) {
                                    Utils.showToast("Done Posting!", getApplicationContext());
                                    finish();

                                } else {
                                    Utils.showToast("Something went wrong saving post!", getApplicationContext());
                                    Log.d("action", "error saving  post -->" + e.getMessage());

                                }
                            }
                        });







                    }else{


                        Utils.showToast("Something went wrong saving image!", getApplicationContext());
                        Log.d("action", "error saving image post -->" + e.getMessage());


                    }
                }
            });


        } else {
            Utils.showToast("No Network", getApplicationContext());
        }





    }


    private void showPictureDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CreatePost.this);
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
        new Crop(source).output(outputUri).asSquare().start(CreatePost.this);
    }


    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {

            filePath = getRealPathFromURI(Crop.getOutput(result));
            imgpost.setImageURI(Crop.getOutput(result));
            haspic = true;
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getApplicationContext(), Crop.getError(result).getMessage(),
                    Toast.LENGTH_SHORT).show();
            haspic=false;
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 111) { //gallery
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




    public boolean isvalidate(){
        String msg = null;
        if (TextUtils.isEmpty(ettitle.getText().toString())) {
            msg = "Please enter title of your post";
            ettitle.requestFocus();
        } else if (TextUtils.isEmpty(etbody.getText().toString())) {
            msg = "Please enter body of your post";
            etbody.requestFocus();
        }


        if (msg != null) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return false;

        }


        if (msg == null) {
            return true;
        }

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        return false;
    }
}
