package com.student.codemobile.myauthendemo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<ProductResult.ProductsBean> mDataArray = new LinkedList<>();
    private CustomAdapter mAdapter;

    public GridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_grid, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecyclerView.setAdapter(new CustomAdapter());

        mAdapter = new CustomAdapter();
        mRecyclerView.setAdapter(mAdapter);
        new FeedTask().execute("http://www.codemobiles.com/adhoc/products/");
        return v;
    }

    public class FeedTask extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder().url(params[0]).build();
                Response response = client.newCall(req).execute();
                return response.body().string();
            } catch (Exception e) {
                publishProgress("Connection Failed!");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getActivity(), values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Gson แปลงข้อมูล String เป็น Object ก่อน จะใช้
            Gson gson = new Gson();
            ProductResult result = gson.fromJson(s, ProductResult.class);
            //กด ALT+Enter เลือก Field แล้วเลือก Grid Fragment
            mDataArray = result.getProducts();
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomHolder> {
        @Override
        public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.item_gridview, parent, false);

            return new CustomHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomHolder holder, int position) {
            ProductResult.ProductsBean item = mDataArray.get(position);
            holder.title.setText(item.getTitle());
            holder.price.setText(item.getPrice());
            Glide.with(getActivity()).load(item.getImage()).into(holder.image);

        }

        @Override
        public int getItemCount() {
            return mDataArray.size();
        }
    }

    private class CustomHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView price;
        public ImageView image;


        public CustomHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.item_grid_title);
            price = (TextView)itemView.findViewById(R.id.item_grid_price);
            image = (ImageView)itemView.findViewById(R.id.item_grid_image);

        }
    }
}
