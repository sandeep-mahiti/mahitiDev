package com.parentof.mai.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.PassChildToActivityCallBack;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.getchildrenmodel.GetChildrenRespModel;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;

import java.util.List;


/**
 * Created by mahiti on 20/10/16.
 */
public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ChildrenViewHolder> {
    private static final String TAG = "chldrnAdaptr ";
    Context context;
    GetChildrenRespModel childrenList;
    PassChildToActivityCallBack passChildToActivityCallBack;
    List<Child> sortListChild;

    public ChildrenAdapter(Context context, GetChildrenRespModel childrenList, PassChildToActivityCallBack passChildToActivityCallBack, List<Child> sortListChild) {
        this.context = context;
        this.childrenList = childrenList;
        this.passChildToActivityCallBack = passChildToActivityCallBack;
        this.sortListChild = sortListChild;
    }

    @Override
    public ChildrenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.childrenicons, parent, false);
        return new ChildrenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChildrenViewHolder holder, final int position) {


        holder.civ.setImageResource(R.drawable.child);
        try {
            holder.childName.setText(sortListChild.get(position).getChild().getFirstName());
        } catch (Exception e) {
            Logger.logE(TAG, "onBndvwHldr 1 :", e);
        }


        holder.civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap image = ((BitmapDrawable) holder.civ.getDrawable()).getBitmap();
                    Logger.logD(Constants.PROJECT, "Sel Image bit map->" + image);

                    passChildToActivityCallBack.passSelectedChildToActivity(sortListChild.get(position), CommonClass.BitMapToString(image));


                } catch (Exception e) {
                    Logger.logE(TAG, "onBndvwHldr :", e);
                }
            }
        });
        if (sortListChild.get(position).getChild().getId() != null) {
            boolean flag = CommonClass.getImageFromDirectory(holder.civ, sortListChild.get(position).getChild().getId());
            Logger.logD(Constants.PROJECT, "Image Falg--" + flag);
            int passRemindValue = position % Constants.CHILD_GIRL_IMAGES.length;
            Logger.logD(Constants.PROJECT, "Image passRemindValue--" + passRemindValue);
            switch (passRemindValue) {
                case 0:
                    setGravtarImages(holder.civ, passRemindValue, flag,position);
                    break;
                case 1:
                    setGravtarImages(holder.civ, passRemindValue, flag,position);
                    break;
                case 2:
                    setGravtarImages(holder.civ, passRemindValue, flag,position);
                    break;
                case 3:
                    setGravtarImages(holder.civ, passRemindValue, flag,position);
                    break;
                case 4:
                    setGravtarImages(holder.civ, passRemindValue, flag,position);
                    break;
                default:
                    break;
            }

        }
        Logger.logD(Constants.PROJECT, "Child Genders-->" + sortListChild.get(position).getChild().getGender());
    }

    @Override
    public int getItemCount() {
        return sortListChild.size();
    }

    public class ChildrenViewHolder extends RecyclerView.ViewHolder {

        CircularImageView civ;
        TextView childName;

        public ChildrenViewHolder(View itemView) {
            super(itemView);
            civ = (CircularImageView) itemView.findViewById(R.id.childicon);
            childName = (TextView) itemView.findViewById(R.id.child_Name);

        }
    }

    void setGravtarImages(ImageView images, int position, boolean flag,int adapterPosition) {
        if (!flag && "male".equalsIgnoreCase(sortListChild.get(adapterPosition).getChild().getGender())) {
            images.setImageResource(Constants.CHILD_BOY_IMAGES[position]);
        } else if (!flag && "female".equalsIgnoreCase(sortListChild.get(adapterPosition).getChild().getGender())) {
            images.setImageResource(Constants.CHILD_GIRL_IMAGES[position]);
        }
    }

}