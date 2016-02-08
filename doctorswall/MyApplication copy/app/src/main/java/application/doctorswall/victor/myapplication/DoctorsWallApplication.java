package application.doctorswall.victor.myapplication;

import android.app.Application;

import com.parse.Parse;

import victor.doctorswall.commonutilties.Constants;
import victor.doctorswall.commonutilties.Utils;

/**
 * Created by Cowboy on 1/2/2016.
 */
public class DoctorsWallApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);

        Utils.Log(Constants.PARSELOG, "parse initialized!");

    }
}
