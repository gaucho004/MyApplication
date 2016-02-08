package activities.doctorswall.wallpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import application.doctorswall.victor.myapplication.R;
import victor.doctorswall.commonutilties.PreferenceHelper;
import victor.doctorswall.commonutilties.Utils;

/**
 * Created by Cowboy on 1/9/2016.
 */
public class WallAdapter extends  RecyclerView.Adapter<WallViewHolder> {

    private List<BroadcastPost> feedItemList;
    private Context mContext;
    private int thispposition;

    private int[] imagesprofile;
    private int[] imagepost;
    PreferenceHelper pHelper;

    public WallAdapter(Context context, List<BroadcastPost> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        setHasStableIds(true);
      //  imagesprofile = new int[]{R.drawable.sample_post1, R.drawable.landmark};
        imagepost = new int[]{R.drawable.sample_post1, R.drawable.sample_post3, R.drawable.sample_post3};
        pHelper = new PreferenceHelper(context);
    }

    @Override
    public WallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= null;
        WallViewHolder viewHolder = null;
        try {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_wall_item, parent, false);
            viewHolder = new WallViewHolder(view);
        }
        catch(Exception e) {
            Log.d("action", "error: " + e.getMessage());
        }

        viewHolder.setIsRecyclable(false);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WallViewHolder holder, int position) {
        this.thispposition = position;
        BroadcastPost bpost = feedItemList.get(position);

        Log.d("action", "position: " + this.thispposition + "\n head:" + bpost.getPostHeadTasg() + "\n rate: " + bpost.getCurrentRate());

        //set some typeface
        holder.headline.setTypeface(Utils.getTypefaceHelveticaLight(mContext));
        holder.summary.setTypeface(Utils.getTypefaceHelveticaultralight(mContext));
        holder.rate.setTypeface(Utils.getTypefaceHelveticaultralight(mContext));
        holder.comment.setTypeface(Utils.getTypefaceHelveticaultralight(mContext));
        holder.mallname.setTypeface(Utils.getTypefaceHelveticaultralight(mContext));
        holder.datepost.setTypeface(Utils.getTypefaceHelveticaultralight(mContext));
        //holder.talktous.setTypeface(Ipayutils.getTypefaceHelveticaultralight(mContext));

        holder.text_comment.setTypeface(Utils.getTypefaceHelveticaultralight(mContext));
        holder.text_rate.setTypeface(Utils.getTypefaceHelveticaultralight(mContext));
        holder.text_share.setTypeface(Utils.getTypefaceHelveticaultralight(mContext));




       /* //set profile pic poster
        Picasso.with(mContext).load(bpost.getUrlProfileImage())
                .error(R.drawable.default_user_icon)
                .placeholder(R.drawable.default_user_icon)
                .into(holder.img);*/
        Picasso.with(mContext).load(pHelper.getURLPhoto()).placeholder(R.drawable.default_user_icon).error(R.drawable.default_user_icon).into(holder.img);



        //set mall name
        holder.mallname.setText(bpost.getMallName());

        //set date posted
        holder.datepost.setText(Utils.getDateFormatTimeOnly().format(bpost.getDatetime()));
        //set total rate
        holder.rate.setText("3");
        // holder.ratingbar.setRating();

        //set count of comments
        holder.comment.setText("1");
        //set head title
        holder.headline.setText(bpost.getPostHeadTasg());

        //set body post
        holder.summary.setText(bpost.getPostSummary());


        if(bpost.isHasimage()){
            holder.img2.setVisibility(View.VISIBLE);

            Picasso.with(mContext).load(bpost.getUrlPostImage())
                    .error(R.drawable.default_postimage)
                    .placeholder(R.drawable.default_postimage)
                    .into(holder.img2);
        }else{
            holder.img2.setVisibility(View.GONE);
        }




        //prepare for the view listeners

        /*holder.ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                BroadcastPost bpost = feedItemList.get(thispposition);
                bpost.setCurrentRate("" + rating);
             //   notifyDataSetChanged();
                notifyItemChanged(thispposition);
              //  notify
            }

        });*/

        holder.imgHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("action","yehey!");


            }
        });

        holder.imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("action","you click share!");

            }
        });


        holder.imgcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("action", "you click comment!");

            }
        });

   /*     holder.imgtalktous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("action","you click talk to us!");

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return this.feedItemList.size();
    }
}
