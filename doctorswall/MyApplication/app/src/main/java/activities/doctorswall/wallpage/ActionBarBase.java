package activities.doctorswall.wallpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import application.doctorswall.victor.myapplication.R;
import victor.doctorswall.components.MyTitleFontTextView;

/**
 * Created by Cowboy on 1/9/2016.
 */
@SuppressLint("NewApi")
abstract public class ActionBarBase extends ActionBarActivity implements View.OnClickListener {

    public ActionBar actionBar;
    public MyTitleFontTextView tvTitle;
    public ImageButton btnNotification, btnActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        LayoutInflater inflater = (LayoutInflater) actionBar.getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.custom_action_bar,
                null);
        tvTitle = (MyTitleFontTextView) customActionBarView
                .findViewById(R.id.tvTitle);
        tvTitle.setOnClickListener(this);
        btnNotification = (ImageButton) customActionBarView
                .findViewById(R.id.btnActionNotification);
        btnNotification.setOnClickListener(this);

        btnActionMenu = (ImageButton) customActionBarView
                .findViewById(R.id.btnActionMenu);
        btnActionMenu.setOnClickListener(this);

        try {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                    ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                            | ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setCustomView(customActionBarView,
                    new ActionBar.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
