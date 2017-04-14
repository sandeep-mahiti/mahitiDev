package com.parentof.mai.views.activity.immuniazation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ToastUtils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by mahiti on 20/10/16.
 */
public class ImmunizationAdapter extends RecyclerView.Adapter<ImmunizationAdapter.ImmunizationViewHolder> {
    private static final String TAG = "ImmunizationAdapter ";
    private Context context;
    private List<ImmunizationBean> immunizationBeanList;
    private DatabaseHelper databaseHelper;
    private AlertDialog alertDialog1;
    String childId;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    ImmunizationBean immunizationBeanMain;


    public ImmunizationAdapter(Context context, List<ImmunizationBean> childBeanList,
                               String childId) {
        this.context = context;
        this.immunizationBeanList = childBeanList;
        this.childId = childId;


    }

    @Override
    public ImmunizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.snippet_immunization_row, parent, false);
        databaseHelper = new DatabaseHelper(context);
        return new ImmunizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImmunizationViewHolder holder, final int position) {

        immunizationBeanMain = immunizationBeanList.get(position);
        databaseHelper.getImmunizationRecords();
        Logger.logD(Constants.PROJECT, "Age-->" + immunizationBeanMain.getVaccineName());
        if (immunizationBeanMain.isPrimaryFlag()) {
            holder.vaccineCategoryName.setVisibility(View.VISIBLE);
            if (immunizationBeanMain.getVaccineCategoryName() != null)
                holder.vaccineCategoryName.setText(immunizationBeanMain.getVaccineCategoryName());
        } else
            holder.vaccineCategoryName.setVisibility(View.GONE);

        if (immunizationBeanMain.getVaccineName() != null)
            holder.vaccineName.setText(immunizationBeanMain.getVaccineName());

        holder.vaccineYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (immunizationBeanList.get(position).getVaccineName() != null)
                    loadVaccineDatePopup(immunizationBeanList.get(position).getVaccineId(), immunizationBeanList.get(position).getVaccineName(), position, holder.vaccineDate, holder.vaccineYes);
            }
        });
        holder.imgVaccineHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(Constants.helpText[position]);
                builder.show();
            }
        });
        try {

            List<ImmunizationBean> immunizationBean1 = databaseHelper.getImmunizationRecords(childId);
            Type collectionType = new TypeToken<List<ImmunizationBean>>() {
            } // end new
                    .getType();
            String gsonString = new Gson().toJson(immunizationBean1, collectionType);
            // gsonString = gsonString.replace("[", "").replace("]", "");
            Logger.logD(Constants.PROJECT, "immunizationBean1 Conv to String--" + gsonString);
            Logger.logD(Constants.PROJECT, "Get Immu Size--" + immunizationBean1.size());
            Logger.logD(Constants.PROJECT, "Get Immu Size--" + immunizationBeanList.size());
            for (int i = 0; i < immunizationBean1.size(); i++) {
                if (immunizationBean1.get(i).getVaccineId().equals(immunizationBeanList.get(position).getVaccineId())) {
                    Logger.logD(Constants.PROJECT, "Get Immu Date--" + immunizationBean1.get(i).getVaccineDate());
                    Logger.logD(Constants.PROJECT, "Get Immu ID--" + immunizationBean1.get(i).getVaccineId());
                    Logger.logD(Constants.PROJECT, "Get Immu Date COnver--" + immunizationBean1.get(i).getVaccineDate());
                    holder.vaccineDate.setText(immunizationBean1.get(i).getVaccineDate());
                    holder.vaccineYes.setImageResource(R.drawable.righticon_immunization);
                    holder.vaccineYes.setClickable(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return immunizationBeanList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ImmunizationViewHolder extends RecyclerView.ViewHolder {

        TextView vaccineName, vaccineCategoryName, vaccineDate;
        ImageView imgVaccineHelpText, vaccineYes;
        LinearLayout llCat;

        public ImmunizationViewHolder(View itemView) {
            super(itemView);
            vaccineName = (TextView) itemView.findViewById(R.id.vaccine_name);
            vaccineCategoryName = (TextView) itemView.findViewById(R.id.vaccine_category);
            vaccineDate = (TextView) itemView.findViewById(R.id.vaccine_date);

            imgVaccineHelpText = (ImageView) itemView.findViewById(R.id.vaccine_help_text);
            vaccineYes = (ImageView) itemView.findViewById(R.id.vaccine_yes);
            llCat = (LinearLayout) itemView.findViewById(R.id.ll_vcat);


        }
    }

    private void loadVaccineDatePopup(final String vaccineId, final String vaccineName, final int pos, final TextView date, final ImageView imageView) {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View dialogView = layoutInflater.inflate(R.layout.snippet_add_vaccine_date_popup, null);
            alertDialog.setView(dialogView);
            alertDialog1 = alertDialog.create();
            alertDialog1.show();
            dateFormatter = new SimpleDateFormat("dd MMM,yyyy", Locale.ENGLISH);
            final EditText edtVaccineDate = (EditText) dialogView.findViewById(R.id.edt_vaccineDate);
            edtVaccineDate.setInputType(InputType.TYPE_NULL);
            TextView vaccineLabel = (TextView) dialogView.findViewById(R.id.pp_vaccine_label);
            TextView vacchineDone = (TextView) dialogView.findViewById(R.id.vacchine_done);
            TextView vacchineDismiss = (TextView) dialogView.findViewById(R.id.vacchine_dismiss);
            vaccineLabel.setText(vaccineName + " Vaccine");

            setDateTimeField(edtVaccineDate);
            vacchineDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtVaccineDate.getText().toString().isEmpty())
                        ToastUtils.displayToast("Please select date", context);
                    else if (!edtVaccineDate.getText().toString().isEmpty()) {
                        ImmunizationBean immunizationBean = new ImmunizationBean();
                        immunizationBean.setChildId(childId);
                        immunizationBean.setVaccineDate(edtVaccineDate.getText().toString());
                        immunizationBean.setVaccineId(vaccineId);
                        immunizationBean.setVaccineName(vaccineName);
                        immunizationBean.setVaccineHelpText(Constants.helpText[pos]);
                        databaseHelper.insertImmunizationRecord(immunizationBean);
                        if (alertDialog1 != null)
                            alertDialog1.dismiss();
                        //getImmunzationmethod(vaccineId,pos,date,imageView);
                        List<ImmunizationBean> immunizationBean1 = databaseHelper.getImmunizationRecords(childId);

                        //    if(immunizationBean1.get(pos).getVaccineId().equalsIgnoreCase(immunizationBeanList.get(pos).getVaccineId()))
                        getImmunzationmethod(immunizationBean1, pos, date, imageView);

                        /// notifyDataSetChanged();
                    }
                }
            });
            vacchineDismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                }
            });
            edtVaccineDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fromDatePickerDialog.show();
                    fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setDateTimeField(final EditText editText) {


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.YEAR, year);
                newDate.set(Calendar.MONTH, monthOfYear);
                newDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                newDate.set(year, monthOfYear, dayOfMonth);
                editText.setText(dateFormatter.format(newDate.getTime()));
                fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }


    private void getImmunzationmethod(String vId, int pos, TextView date, ImageView yesImage) {
        List<ImmunizationBean> list = new ArrayList<>();
        list = databaseHelper.getImmunizationRecords(childId);


        if (immunizationBeanList.get(pos).getVaccineDate() != null) {
            immunizationBeanList.get(pos).getVaccineDate();
            if (date != null) {
                date.setText(immunizationBeanList.get(pos).getVaccineDate());
                yesImage.setImageResource(R.drawable.question_icon);
            }
        }
    }

    private void getImmunzationmethod(List<ImmunizationBean> immunizationBean, int pos, TextView date, ImageView yesImage) {
        for (int i = 0; i < immunizationBean.size(); i++) {
            if (immunizationBean.get(i).getVaccineDate() != null) {
                date.setText(immunizationBean.get(i).getVaccineDate());
                yesImage.setImageResource(R.drawable.righticon_immunization);

            }
        }
    }


}
