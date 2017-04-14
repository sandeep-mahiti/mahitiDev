package com.parentof.mai.views.activity.growthCharts.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.db.DbConstants;
import com.parentof.mai.model.ChildBean;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.views.activity.growthCharts.UpdateResponse;

import java.util.List;


/**
 * Created by mahiti on 20/10/16.
 */
public class ChildAgeHeightAdapter extends RecyclerView.Adapter<ChildAgeHeightAdapter.ChildrenViewHolder> {
    private static final String TAG = "chldrnAdaptr ";
    Context context;
    List<ChildBean> childBeanList;
    AlertDialog alertDialog1;
    DatabaseHelper databaseHelper;
    UpdateResponse updateResponse;
    String childOption;
    String childNameGrowth;

    public ChildAgeHeightAdapter(Context context, List<ChildBean> childBeanList, UpdateResponse updateResponse, String childOption, String childName) {
        this.context = context;
        this.childBeanList = childBeanList;
        this.updateResponse = updateResponse;
        this.childOption = childOption;
        this.childNameGrowth = childName;
    }

    @Override
    public ChildrenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.snippet_row_age_height, parent, false);
        databaseHelper = new DatabaseHelper(context);
        return new ChildrenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChildrenViewHolder holder, final int position) {

        final ChildBean childBean = childBeanList.get(position);
        Logger.logD(Constants.PROJECT, "Age-->" + childBean.getChildAge());
        holder.age.setText(String.valueOf(childBean.getChildAge()) + " " + childBean.getAgeMode());
        if (DbConstants.Weight.equalsIgnoreCase(childBean.getChildOptionFlag()))
            holder.height.setText(String.valueOf(childBean.getChildHeight()) + " kgs");
        else
            holder.height.setText(String.valueOf(childBean.getChildHeight()) + " cms");
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAddAgePopup(String.valueOf(childBean.getChildAge()), String.valueOf(childBean.getChildHeight()), childBean.getpId(), childBean.getChildName(), childBean.getAgeMode(),childBean.getChildId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return childBeanList.size();
    }


    public class ChildrenViewHolder extends RecyclerView.ViewHolder {

        TextView age, height, edit;

        public ChildrenViewHolder(View itemView) {
            super(itemView);
            age = (TextView) itemView.findViewById(R.id.tv_age);
            height = (TextView) itemView.findViewById(R.id.tv_height);
            edit = (TextView) itemView.findViewById(R.id.tv_child_edit);

        }
    }

    void loadAddAgePopup(String age, String height, final int id, String childNameGrowth, String childAgeMode, String childId) {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View dialogView = layoutInflater.inflate(R.layout.add_child_age_popup, null);
            alertDialog.setView(dialogView);
            alertDialog1 = alertDialog.create();
            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            final EditText ageEdit = (EditText) dialogView.findViewById(R.id.edt_child_age);
            final EditText heightEdit = (EditText) dialogView.findViewById(R.id.edt_child_height);
            ImageView closeImage = (ImageView) dialogView.findViewById(R.id.close);
            TextView saveChildAge = (TextView) dialogView.findViewById(R.id.save_child_age);
            TextView childNameTv = (TextView) dialogView.findViewById(R.id.childName);
            TextView cms = (TextView) dialogView.findViewById(R.id.cms);
            final Spinner ageModeSp = (Spinner) dialogView.findViewById(R.id.ageMode);
            ImageView childImageGraph = (ImageView) dialogView.findViewById(R.id.childImage_graph);
            TextView childOptionTv = (TextView) dialogView.findViewById(R.id.child_option);

            childNameTv.setText(childNameGrowth);
            Logger.logD(Constants.PROJECT, "HW--" + height);
            if (age != null)
                ageEdit.setText(age);
            if (height != null)
                heightEdit.setText(height);

            if (DbConstants.Weight.equalsIgnoreCase(childOption))
                cms.setText("kgs");
            else
                cms.setText("cms");


            if (childOption != null)
                childOptionTv.setText(childOption);

            selectSpinnerValue(ageModeSp, childAgeMode);
            if (childId != null)
                CommonClass.getImageFromDirectory(childImageGraph, childId);

            saveChildAge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateResponse.updateChildData(ageEdit.getText().toString(), heightEdit.getText().toString(), ageModeSp.getSelectedItem().toString(), id, alertDialog1);
                   /* callUpdateQuery(ageEdit.getText().toString(),
                            heightEdit.getText().toString(), ageMode.getSelectedItem().toString(), id);*/
                }
            });
            closeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                }
            });
            alertDialog1.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void selectSpinnerValue(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

}