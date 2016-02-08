package victor.doctorswall.commonutilties;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.doctorswall.victor.myapplication.R;



import victor.doctorswall.components.CustomGIFView;
import victor.doctorswall.interfaces.OnProgressCancelListener;

/**
 * Created by Cowboy on 1/2/2016.
 */
public class Utils {

    static float density = 1;
    private static ProgressDialog mProgressDialog;
    private static Dialog mDialog;



    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }

    public static boolean eMailValidation(String emailstring) {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }

    public static void showOkDialog(String title, String msg, Activity act) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(act);
        if (title != null) {

            TextView dialogTitle = new TextView(act);
            dialogTitle.setText(title);
            dialogTitle.setPadding(10, 10, 10, 10);
            dialogTitle.setGravity(Gravity.CENTER);
            dialogTitle.setTextColor(Color.WHITE);
            dialogTitle.setTextSize(20);
            dialog.setCustomTitle(dialogTitle);

        }
        if (msg != null) {
            dialog.setMessage(msg);
        }
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        AlertDialog dlg = dialog.show();
        TextView messageText = (TextView) dlg
                .findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);

    }

    public static float getDisplayMetricsDensity(Context context) {
        density = context.getResources().getDisplayMetrics().density;

        return density;
    }

    public static int getPixel(Context context, int p) {
        if (density != 1) {
            return (int) (p * density + 0.5);
        }
        return p;
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void showToast(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static String UppercaseFirstLetters(String str) {
        boolean prevWasWhiteSp = true;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                if (prevWasWhiteSp) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                prevWasWhiteSp = false;
            } else {
                prevWasWhiteSp = Character.isWhitespace(chars[i]);
            }
        }
        return new String(chars);
    }

    public static void showCustomProgressDialog(Context context, String title,
                                                boolean iscancelable,String n
                                                ) {
        if (mDialog != null && mDialog.isShowing())
            return;

        mDialog = new Dialog(context, R.style.MyDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.progress_bar_cancel);

       // CustomGIFView imageView = (CustomGIFView) mDialog.findViewById(R.id.gifview);

        if (!TextUtils.isEmpty(title)) {
            TextView tvTitle = (TextView) mDialog.findViewById(R.id.tvTitle);
            tvTitle.setText(title);
        }
        mDialog.setCancelable(iscancelable);

      //  imageView.setBackgroundResource(R.raw.loading);
       /* final AnimationDrawable frameAnimation = (AnimationDrawable) imageView
                .getBackground();
        mDialog.setCancelable(iscancelable);
        imageView.post(new Runnable() {

            @Override
            public void run() {
                frameAnimation.start();
                frameAnimation.setOneShot(false);
            }
        });*/
        mDialog.show();

    }

    public static void removeCustomProgressDialog() {
        try {

            if (mDialog != null) {
                // if (mProgressDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
                // }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static final void Log(String tag, String message) {

            android.util.Log.i(tag, message + "");
        }

    public static Typeface getTypefaceGlenLight(Context ctx){
        return Typeface.createFromAsset(ctx.getAssets(), "fonts/genlight.otf");
    }

    public static Typeface getTypefaceHelveticaLight(Context ctx){
        return Typeface.createFromAsset(ctx.getAssets(), "fonts/helveticaneuelight.ttf");
    }

    public static Typeface getTypefaceHelveticaMedium(Context ctx){
        return Typeface.createFromAsset(ctx.getAssets(), "fonts/helveticaneuemedium.ttf");
    }

    public static Typeface getTypefaceHelveticaultralight(Context ctx){
        return Typeface.createFromAsset(ctx.getAssets(), "fonts/helveticaneueultralight.ttf");
    }

    public static Typeface getTypeFaceOfThis(Context ctx, String typefacename){
        return Typeface.createFromAsset(ctx.getAssets(), typefacename);
    }




    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Random rnd = new Random();

    public static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


    public static final SimpleDateFormat getDateFormatTimeOnly() {
        return new SimpleDateFormat(" hh:mm aa", Locale.ENGLISH); //12 hr format - HH:mm aa --> 24 hour format
    }

    public static final SimpleDateFormat getDateFormatFull() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    }
}




