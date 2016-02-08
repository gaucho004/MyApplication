package activities.doctorswall.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.QRCode;

import application.doctorswall.victor.myapplication.R;
import victor.doctorswall.commonutilties.PreferenceHelper;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class QRCodeShare extends AppCompatActivity {


    QRCode myQRGenerator;

    ImageView imgQrCode;

    PreferenceHelper pHelper;
    String contactString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qrcode_share);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        pHelper = new PreferenceHelper(getApplicationContext());

        contactString = pHelper.getFname()+"#" + pHelper.getLname() + "#" + pHelper.getEmail() + "#" + pHelper.getPhonenumber() +"#" + pHelper.getAddress() +"#" + pHelper.getUserId();


      /*  edfname.setText(parsed[0]);
        edlname.setText(parsed[1]);
        edemail.setText(parsed[2]);
        phonenumber.setText(parsed[3]);
        edaddress.setText(parsed[4]);*/




       imgQrCode = (ImageView) findViewById(R.id.imgqrcode);

        myQRGenerator = new QRCode();

        // TODO Auto-generated method stub

        //source data
        myQRGenerator.setData(contactString);

        //format
        myQRGenerator.setDataMode(QRCode.M_AUTO);

        //other required parameter settings
        myQRGenerator.setVersion(10);
        myQRGenerator.setEcl(QRCode.ECL_M);
        myQRGenerator.setUom(IBarcode.UOM_PIXEL);

        myQRGenerator.setX(3f);

        myQRGenerator.setLeftMargin(15f);
        myQRGenerator.setRightMargin(15f);
        myQRGenerator.setTopMargin(15f);
        myQRGenerator.setBottomMargin(15f);

        myQRGenerator.setResolution(200);

        myQRGenerator.setForeColor(AndroidColor.black);
        myQRGenerator.setBackColor(AndroidColor.white);

        RectF bounds = new RectF(30, 30, 0, 0);

        //prepare the blank bitmap
        Bitmap bmp = Bitmap.createBitmap(300,
               300, Bitmap.Config.ARGB_8888);

        //prepare the canvas
        Canvas canvas = new Canvas(bmp);

        //actual drawing of QRcode in a canvas
        try {
            myQRGenerator.drawBarcode(canvas, bounds);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //set the bitmap as image source
        imgQrCode.setImageBitmap(bmp);



    }



    private class myView extends View {

        public myView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            Bitmap myBitmap = BitmapFactory.decodeResource
                    (getResources(),
                            R.drawable.default_user_icon);
            canvas.drawBitmap(myBitmap, 0, 0, null);
        }
    }


}
