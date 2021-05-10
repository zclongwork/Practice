/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-09-18 23:47:01
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.zcl.practice.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.zcl.practice.R;

import java.util.ArrayList;
import java.util.List;


public class ZoomAdapter extends RecyclerView.Adapter<ZoomAdapter.MyViewHolder> {

    public static List<String> data = new ArrayList<>();

    static {
        for (int i = 1; i <= 2; i++) {
            data.add(i + "");
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //    Log.i("GCS", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_zoom, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //   Log.i("GCS","onBindViewHolder = "+position);
        final String title = data.get(position);
        if (position == 0) {
            holder.iv_image.setImageResource(R.drawable.image1);
        } else {
            holder.iv_image.setImageResource(R.drawable.image2);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "item" + title + " 被点击了", Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
