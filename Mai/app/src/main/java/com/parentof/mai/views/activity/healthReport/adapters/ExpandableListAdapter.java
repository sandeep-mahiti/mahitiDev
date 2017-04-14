package com.parentof.mai.views.activity.healthReport.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parentof.mai.R;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;

import java.util.List;

/**
 * Created by mahiti on 23/5/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    ImageView rowArrow;

    public ExpandableListAdapter(Context context, List<String> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View expandableList, ViewGroup parent) {

        LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (_listDataHeader.get(groupPosition)) {
            case Constants.BIOMETRICS:
                expandableList = infalInflater.inflate(R.layout.logout_popup, null);
                break;
            case Constants.CONDITION:
                expandableList = infalInflater.inflate(R.layout.user_locked_popup, null);
                break;
            case Constants.EYE:
                expandableList = infalInflater.inflate(R.layout.addparent_popup, null);
                break;
            case Constants.DENTAL:
                expandableList = infalInflater.inflate(R.layout.logout_popup, null);
                break;
            case Constants.PHYSICAL_EXAMINATION:
                expandableList = infalInflater.inflate(R.layout.logout_popup, null);
                break;
            case Constants.SYSTEMATIC_EXAMINATION:
                expandableList = infalInflater.inflate(R.layout.user_locked_popup, null);
                break;
            case Constants.ANAEMIA_SCREENING:
                expandableList = infalInflater.inflate(R.layout.addparent_popup, null);
                break;
            case Constants.SUMMARY:
                expandableList = infalInflater.inflate(R.layout.logout_popup, null);
                break;
            default:
                break;

        }
        return expandableList;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.health_report_header_view, null);
            ImageView imgHeader = (ImageView) convertView.findViewById(R.id.img_ex_header);
            TextView headerTxt = (TextView) convertView.findViewById(R.id.tv_headerTv);
            rowArrow = (ImageView) convertView.findViewById(R.id.ex_row_arrow);
            headerTxt.setText(_listDataHeader.get(groupPosition).toUpperCase());
            final LinearLayout llHeader = (LinearLayout) convertView.findViewById(R.id.rootHead);


        }
        return convertView;
    }



    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        Logger.logD(Constants.PROJECT, "EXPAND");
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    void headClick(String headerTitle, boolean isExpanded, ImageView rowArrow) {
        switch (headerTitle) {
            case Constants.BIOMETRICS:
                if (isExpanded) {
                    rowArrow.setImageResource(R.drawable.topwhitearrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.white));
                    Logger.logD(Constants.PROJECT, "EXPAND");
                } else {
                    rowArrow.setImageResource(R.drawable.tab_arrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.colorPrimaryDark));
                    Logger.logD(Constants.PROJECT, "NON EXPAND");
                }
                break;
            case Constants.CONDITION:
                if (isExpanded) {
                    rowArrow.setImageResource(R.drawable.topwhitearrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.white));
                    Logger.logD(Constants.PROJECT, "EXPAND");
                } else {
                    rowArrow.setImageResource(R.drawable.tab_arrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.colorPrimaryDark));
                    Logger.logD(Constants.PROJECT, "NON EXPAND");
                }
                break;
            case Constants.EYE:
                if (isExpanded) {
                    rowArrow.setImageResource(R.drawable.topwhitearrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.white));
                    Logger.logD(Constants.PROJECT, "EXPAND");
                } else {
                    rowArrow.setImageResource(R.drawable.tab_arrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.colorPrimaryDark));
                    Logger.logD(Constants.PROJECT, "NON EXPAND");
                }
                break;
            case Constants.DENTAL:
                if (isExpanded) {
                    rowArrow.setImageResource(R.drawable.topwhitearrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.white));
                    Logger.logD(Constants.PROJECT, "EXPAND");
                } else {
                    rowArrow.setImageResource(R.drawable.tab_arrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.colorPrimaryDark));
                    Logger.logD(Constants.PROJECT, "NON EXPAND");
                }
                break;
            case Constants.PHYSICAL_EXAMINATION:
                if (isExpanded) {
                    rowArrow.setImageResource(R.drawable.topwhitearrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.white));
                    Logger.logD(Constants.PROJECT, "EXPAND");
                } else {
                    rowArrow.setImageResource(R.drawable.tab_arrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.colorPrimaryDark));
                    Logger.logD(Constants.PROJECT, "NON EXPAND");
                }
                break;
            case Constants.SYSTEMATIC_EXAMINATION:
                if (isExpanded) {
                    rowArrow.setImageResource(R.drawable.topwhitearrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.white));
                    Logger.logD(Constants.PROJECT, "EXPAND");
                } else {
                    rowArrow.setImageResource(R.drawable.tab_arrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.colorPrimaryDark));
                    Logger.logD(Constants.PROJECT, "NON EXPAND");
                }
                break;
            case Constants.ANAEMIA_SCREENING:
                if (isExpanded) {
                    rowArrow.setImageResource(R.drawable.topwhitearrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.white));
                    Logger.logD(Constants.PROJECT, "EXPAND");
                } else {
                    rowArrow.setImageResource(R.drawable.tab_arrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.colorPrimaryDark));
                    Logger.logD(Constants.PROJECT, "NON EXPAND");
                }
                break;
            case Constants.SUMMARY:
                if (isExpanded) {
                    rowArrow.setImageResource(R.drawable.topwhitearrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.white));
                    Logger.logD(Constants.PROJECT, "EXPAND");
                } else {
                    rowArrow.setImageResource(R.drawable.tab_arrow);
                    // llHeader.setBackgroundColor(CommonClass.getColor(_context, R.color.colorPrimaryDark));
                    Logger.logD(Constants.PROJECT, "NON EXPAND");
                }
                break;

        }
    }
}
