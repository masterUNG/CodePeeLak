package com.student.codemobile.myauthendemo;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecycleViewFragment extends Fragment {

    public static final String kFeed_trainings = "training";
    public static final String kFeed_foods = "foods";
    public static final String kFeed_superheros = "superhero";
    public static final String kFeed_songs = "songs";
    private String mFeed_type = kFeed_trainings;
    private RecyclerView mRecyclerView;
    //ข้อมูล Array = new LinkedList<>() เป้นการ Set ให้ Array ก่อน คือ new LinkedList
    private List<YoutubeResult.YoutubesBean> mDataArray = new LinkedList<>();
    private CustomAdapter mAdapter;

    public RecycleViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycle_view, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new CustomAdapter());

        mAdapter = new CustomAdapter();
        mRecyclerView.setAdapter(mAdapter);
        feedData(kFeed_songs);
        return v;
    }

    public void feedData(String type) {
        mFeed_type = type;
        //เรียก Class FeedTask
        new FeedTask().execute("http://codemobiles.com/adhoc/youtubes/index_new.php");
    }

    public class FeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Called under main/ui thread - pre update UI
            Toast.makeText(getActivity(), "Connecting", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Called under background thread
            //ห้าม Update User Interface ภายใต้ doInBackground Thread

            //Connect Server
            try{
                UserBean userbean = (UserBean) getArguments().getParcelable(UserBean.TABLE_NAME);


                OkHttpClient client = new OkHttpClient();
                RequestBody data = new FormBody.Builder()
                        .add("username",userbean.username)
                        .add("password",userbean.password)
                        .add("type",mFeed_type) //foods, songs,
                        .build();
                Request req = new Request.Builder().url(params[0]).post(data).build();
                Response response = client.newCall(req).execute();
                String result = response.body().string();
                Log.i("codemobiles", response.body().string());
                //แสดงผล ตรงนี้ไม่ได้ ต้องไปแสดงใน onPostExecute
                return result;
            }catch(Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Called under main/ui thread - after doInbackground

            //Toast.makeText(getActivity(), "Finished" + result, Toast.LENGTH_SHORT).show();
            Gson gson = new Gson();
            YoutubeResult jsonObj = gson.fromJson(result, YoutubeResult.class);
            mDataArray = jsonObj.getYoutubes();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomHolder> {
        @Override
        //สร้าง Layout แต่ละ Row เท่าที่จำเป็น เช่น หน้าจอแสดงได้ 3 Row ก็ วาด Layout แค่ 3 Row
        public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.item_listview, parent, false);

            return new CustomHolder(v);
        }


        //ตรงนี้จะวาด Layout ตลอดเวลา ตามการเลื่อนของหน้าจอ ตรงนี้ถ้าเก่งๆ จะทำเป็น Readmore ได้
        @Override
        public void onBindViewHolder(CustomHolder holder, int position) {
            YoutubeResult.YoutubesBean item = mDataArray.get(position);

            //ชื่อหัวข้อ และ รายละเอียด
            holder.mTitle.setText(item.getTitle());
            holder.mSubtitle.setText(item.getSubtitle());


            //แสดงรูป Youtube Image
            String youtubeImage = item.getYoutube_image();
            //Glide ทำการ Download รูปภาพ
            Glide.with(getContext()).load(youtubeImage).into(holder.mYoutubeImage);
            holder.mYoutubeImage.setTag(R.id.item_listview_youtube_image, item.getId());
            //Avatar Image
            String avatarImage = item.getAvatar_image();
            //คำสั่ง .bitmapTransform(new CropCircleTransformation(getActivity())) ทำให้ภาพกลม
            Glide.with(getContext()).load(avatarImage).bitmapTransform(new CropCircleTransformation(getActivity())).into(holder.mVatarImage);
        }

        @Override
        public int getItemCount() {
            //จำนวน row
            return mDataArray.size();
        }
    }

    private class CustomHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mSubtitle;
        public ImageView mVatarImage;
        public ImageView mYoutubeImage;

        public CustomHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.item_listview_title);
            mSubtitle = (TextView) itemView.findViewById(R.id.item_listview_subtitle);
            mVatarImage = (ImageView) itemView.findViewById(R.id.item_listview_avatarIcon);
            mYoutubeImage = (ImageView) itemView.findViewById(R.id.item_listview_youtube_image);

            mYoutubeImage.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String id = (String) v.getTag(R.id.item_listview_youtube_image);
                    Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
                    Intent i = YouTubeIntents.createPlayVideoIntent(getActivity(), id);
                    startActivity(i);
                }
            });
        }
    }
}
