package com.parentof.mai.views.adapters.homefragdpadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.SelectedDpTOActivtyCallback;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.utils.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by sandeep HR on 13/03/17.
 */

public class HomeFragNewDPAdapter extends RecyclerView.Adapter<HomeFragNewDPAdapter.ItemViewHolder> {

    private static final String TAG = "HorizRVAdapter ";
    private int mRowIndex = -1;
    Context mContext;
    private int counter = 0;
    SelectedDpTOActivtyCallback selectedDpTOActivtyCallback;

    List<AllDP> allDPList;


    public HomeFragNewDPAdapter() {
    }

    public HomeFragNewDPAdapter(Context context, List<AllDP> data, SelectedDpTOActivtyCallback selectedDpTOActivtyCallback) {
        this.selectedDpTOActivtyCallback = selectedDpTOActivtyCallback;
        allDPList = data;
        mContext = context;
    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dps_horizontal_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        Logger.logD(TAG, " rowIndex val : " + mRowIndex);
        AllDP item= allDPList.get(position);
        setDPs(holder, position, item);
    }

    void setDPs(final ItemViewHolder holder, final int position, AllDP singleItem){

        holder.title.setText(singleItem.getName());
        Logger.logD(TAG, " onBindViewHolder   item data : ");


            holder.progressBar.setVisibility(View.GONE);
            holder.progressPercentText.setVisibility(View.GONE);



        if (singleItem.getImage() != null) {
            Picasso.with(mContext).load(singleItem.getImage()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.imageLayout.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }


        holder.imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean gotoChat = true;
                if (counter == 1)
                    gotoChat = true;

                selectedDpTOActivtyCallback.selectedDPtoCallSkillsAPI(allDPList.get(position), gotoChat);

            }
        });
    }





    @Override
    public int getItemCount() {
        return allDPList.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout imageLayout;
        ProgressBar progressBar;
        TextView progressPercentText;
        TextView title;
        TextView subTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            //subTitle = (TextView) itemView.findViewById(R.id.sub_title);
            imageLayout = (RelativeLayout) itemView.findViewById(R.id.imageLayout);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar_DB);

            progressPercentText = (TextView) itemView.findViewById(R.id.percentTV);

        }

    }

}
