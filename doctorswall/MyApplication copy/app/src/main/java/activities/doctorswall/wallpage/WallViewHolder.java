package activities.doctorswall.wallpage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import application.doctorswall.victor.myapplication.R;

/**
 * Created by Cowboy on 1/9/2016.
 */
public class WallViewHolder extends RecyclerView.ViewHolder {

    protected TextView headline;
    protected ImageView img, img2;
    protected TextView summary, rate, comment, mallname, datepost, talktous, text_share, text_rate, text_comment;

    protected LinearLayout ll_share, ll_comment, ll_talk;
    protected RatingBar ratingbar;
    protected ImageView imgHappy, imgshare, imgcomment, imgtalktous;

    public WallViewHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.doctor_ivprofile);
        img2 = (ImageView) itemView.findViewById(R.id.doctor_postmainimage);

        mallname = (TextView) itemView.findViewById(R.id.doctor_item_mallname);
        datepost = (TextView) itemView.findViewById(R.id.doctor_item_datepost);

        headline = (TextView) itemView.findViewById(R.id.doctor_tvheadtag);
        summary = (TextView) itemView.findViewById(R.id.doctor_tvsubtag);

        rate = (TextView) itemView.findViewById(R.id.doctor_tvrate);
        comment = (TextView) itemView.findViewById(R.id.doctor_tvcommentcount);

        ll_share = (LinearLayout) itemView.findViewById(R.id.doctor_llshare);
        ll_comment = (LinearLayout) itemView.findViewById(R.id.doctor_llcomment);
        //ll_talk  = (LinearLayout) itemView.findViewById(R.id.lltalk);

        //ratingbar = (RatingBar) itemView.findViewById(R.id.wall_myRatingBar);

        //talktous = (TextView) itemView.findViewById(R.id.wall_tvtalktous);
        text_comment = (TextView) itemView.findViewById(R.id.doctor_textcomment);
        text_share = (TextView) itemView.findViewById(R.id.doctor_text_share);
        text_rate = (TextView) itemView.findViewById(R.id.doctor_textrate);


        imgHappy = (ImageView) itemView.findViewById(R.id.doctor_Happy);
        //  imgtalktous = (ImageView) itemView.findViewById(R.id.wallimgtalktous);
        imgcomment = (ImageView) itemView.findViewById(R.id.doctor_imgcomment);
        imgshare = (ImageView) itemView.findViewById(R.id.doctor_imagshare);
    }
}
