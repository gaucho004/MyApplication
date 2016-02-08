package activities.doctorswall.adapters;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ipayuterminal.cfts.chattt.ChatActivity;
import com.ipayuterminal.cfts.chattt.CommentActivity;
import com.ipayuterminal.cfts.chattt.Ipayutils;
import com.ipayuterminal.cfts.chattt.MallWallPageActivity;
import com.ipayuterminal.cfts.chattt.PeopleObject;
import com.ipayuterminal.cfts.chattt.R;
import com.ipayuterminal.cfts.chattt.Utils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import centennial.ipayu.chatbox.ContactList;

public class AlphabetListAdapter extends BaseAdapter {

    List<PeopleObject> list;
    Context ctx;
    List<PeopleObject> listdummy;
    int counter;
    Utils utils;
    String chattingToName;
    HashMap<String, String> map;
    ParseObject pbSaveMessage;
    private HttpClient httpclient;
    Handler mHandler = new Handler();
    String responseBody;

    public AlphabetListAdapter(Context ctx, List<PeopleObject> list) {
        this.ctx = ctx;
        this.list = list;
        counter = 0;
        listdummy = new ArrayList<PeopleObject>();
        utils  = new Utils(ctx);

    }

    public AlphabetListAdapter(Context ctx, List<PeopleObject> list, HashMap<String, String> map) {
        this.ctx = ctx;
        this.list = list;
        counter = 0;
        listdummy = new ArrayList<PeopleObject>();
        this.map = map;
        utils  = new Utils(ctx);
    }

    public static abstract class Row {
    }

    public static final class Section extends Row {
        public final String text;

        public Section(String text) {
            this.text = text;
        }
    }

    public static final class Item extends Row {
        public final PeopleObject ccc;

        public Item(PeopleObject contacts) {
            this.ccc = contacts;
        }
    }

    private List<Row> rows;

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Row getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Section) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (getItemViewType(position) == 0) { // Item
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_item, parent, false);
                listdummy.add(list.get(counter));
                counter++;
            }

            Item item = (Item) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.person_name);

            if(item.ccc.isGroupChat()) {
                Log.d("action", "---> its groupchat" + item.ccc.getFname());
                textView.setText(item.ccc.getFname());

            }else{
                Log.d("action", "---> its a person" + item.ccc.getFname());

                textView.setText(item.ccc.getLname() + ", " + item.ccc.getFname());

            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(utils.isForSharing(ctx)){
                        final PeopleObject p = listdummy.get(position);


                        if (p.isGroupChat()) {

                            chattingToName = p.getFname();
                        } else {

                            chattingToName = p.getFname() + p.getIpayuid();

                        }


                        JSONObject object = new JSONObject();
                        try {


                            if(p.isGroupChat()) {


                                object.put("groupchat_msg", "yes");


                                PushService.unsubscribe(ctx, chattingToName);


                            }else{


                                object.put("groupchat_msg", "no");
                            }

                            object.put("fname", utils.getFName(ctx));
                            object.put("lname", utils.getLName(ctx));
                            object.put("ipayuid", utils.getIpayuId(ctx));
                            object.put("usercode", utils.getUname(ctx));
                            object.put("channelname", chattingToName);

                            object.put("groupchat","no");
                            object.put("from",utils.getUname(ctx));
                            object.put("penname", utils.getFName(ctx)); //name nung magsesend
                            object.put("message", "");
                            object.put("alert", utils.getFName(ctx) + " shared a link ");
                            object.put("title", "IPAYU Chat");
                            object.put("action", "MyAction");

                            ParsePush push = new ParsePush();
                            push.setData(object);
                            push.setChannel(chattingToName);
                            Log.d("action", "sending to " + chattingToName);
                            push.sendInBackground(new SendCallback() {

                                @Override
                                public void done(ParseException arg0) {
                                    // TODO Auto-generated method stub
                                    if (Ipayutils.hasnoError(arg0)) {
                                        Toast.makeText(ctx,
                                                "sharing post done",
                                                Toast.LENGTH_LONG).show();


                                        saveShareCount();



                                        pbSaveMessage = new ParseObject(Ipayutils.ParseConstant.TBL_MESSAGES);
                                        pbSaveMessage.put(Ipayutils.ParseConstant.MSG_FROM, utils.getUnameForChat(ctx) );
                                        pbSaveMessage.put("isgroupchat", p.isGroupChat());
                                        // pbSaveMessage.put("from_name", );


                                        pbSaveMessage.put("datashared", map.get("mall_id"));

                                        pbSaveMessage.put(Ipayutils.ParseConstant.MSG_MESSAGE, "link");
                                        pbSaveMessage.put(Ipayutils.ParseConstant.MSG_TO_CHANNEL,chattingToName);
                                        pbSaveMessage.put(Ipayutils.ParseConstant.MSG_TO_ID, p.getPersonName());
                                        pbSaveMessage.put(Ipayutils.ParseConstant.MSG_FROM_ID, utils.getUname(ctx));
                                        pbSaveMessage.put(Ipayutils.ParseConstant.MSG_TYPE, "link");
                                        pbSaveMessage.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {

                                                if (Ipayutils.hasnoError(e)) {

                                                    Log.d("imagepath", "message saved to cloud");

                                                    //subscribe back

                                                    if(p.isGroupChat())
                                                        PushService.subscribe(ctx, chattingToName, ChatActivity.class, R.mipmap.ic_launcher);
                                                    //then save locally
                                                    //ChatPeople curChatObj = addToChat(pbSaveMessage.getCreatedAt(),chattingToName, edtMessage.getText().toString().trim(), "Sent");



                                                } else {


                                                }

                                            }
                                        });
                                    }
                                }
                            });

                        } catch (JSONException expJson) {
                            // TODO Auto-generated catch block
                            expJson.printStackTrace();
                        }



                    }else {

                        PeopleObject p = listdummy.get(position);
                        chattingToName = p.getFname() + p.getIpayuid();
                        utils = new Utils(ctx);

                        Intent intent = new Intent(ctx, ChatActivity.class);


                        intent.putExtra("chattingFrom", utils.getUnameForChat(ctx));
                        intent.putExtra("chattingToName", chattingToName);
                        intent.putExtra("targetusername", p.getPersonName());

                        if (p.isGroupChat()) {
                            intent.putExtra("isgroupchat", true);
                            intent.putExtra("groupname", p.getFname());
                        } else {
                            intent.putExtra("isgroupchat", false);
                            intent.putExtra("contactname", p.getFname());


                        }

                        ctx.startActivity(intent);
                    }
                }
            });


        } else { // Section
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_section, parent, false);
                listdummy.add(new PeopleObject());
                // counter++;


                Section section = (Section) getItem(position);
                TextView textView = (TextView) view.findViewById(R.id.textView1);
                textView.setText(section.text);
            }


        }
        return view;
    }

    private void saveShareCount() {



        if(Ipayutils.isNetworkAvailable(ctx)){



            new Thread(new Runnable() {
                @Override
                public void run() {
                    HashMap<String, String> thismap = new HashMap<String, String>();
                    thismap.put(Const.URL, Const.ServiceType.POST_SAVESHARE);
                    thismap.put("post_id", map.get("post_id"));
                    thismap.put("ipayu_user_id", map.get("ipayu_user_id"));



                    try {
                        HttpPost httppost = new HttpPost(thismap.get("url"));
                        Log.d("action", thismap.get("url"));
                        httpclient = new DefaultHttpClient();

                        thismap.remove("url");

                        HttpConnectionParams.setConnectionTimeout(
                                httpclient.getParams(), 30000);

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                        for (String key : thismap.keySet()) {
                            Log.d("action",
                                    key + "  < === >  " + thismap.get(key));

                            nameValuePairs.add(new BasicNameValuePair(key, thismap
                                    .get(key)));
                        }
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        ActivityManager manager = (ActivityManager) ctx
                                .getSystemService(Context.ACTIVITY_SERVICE);
                        if (manager.getMemoryClass() < 25) {
                            System.gc();
                        }
                        final HttpResponse response = httpclient.execute(httppost);
                        responseBody = EntityUtils.toString(response
                                .getEntity());
                        Log.d("action" ,"response is " + responseBody);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                //UPDATE YOUR UI HERE

                                if(responseBody.contains("success"))
                                Log.d("action", "success save share");
                                else
                                    Log.d("action", "failed to save share");



                            }
                        });

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Log.d("action", "response is " + e.toString());

                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.d("action", "response is " + e.toString());

                    } catch (IOException e) {
                        Log.d("action" ,"response is " + e.toString());

                        e.printStackTrace();
                    }

                }
            }).start();




        }else{
            Ipayutils.showToast("No Internet Connection", ctx);
        }



    }


    public void sendMessage(){


    }


}
