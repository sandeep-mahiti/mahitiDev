package com.parentof.mai.utils;

import com.parentof.mai.model.DaysBean;
import com.parentof.mai.model.ImprovementPlanModel.HomeTaskBean;
import com.parentof.mai.model.dayLoggedModel.DayLoggedModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahiti on 3/3/17.
 */
public class TaskUtils {

    public List<DaysBean> setAnsweredData(int currentPos, List<DaysBean> list, List<DayLoggedModel> loggedModels) {
        Logger.logD(Constants.PROJECT, "setAnsweredData DaysBean list--->" + list.size());
        Logger.logD(Constants.PROJECT, "setAnsweredData DayLoggedModel list--->" + loggedModels.size());
        Logger.logD(Constants.PROJECT, "setAnsweredData currentPos list--->" + currentPos);
        for (int i = 0; i <= currentPos && i < list.size(); i++) {
            Logger.logD("TaskUtils ", " list pos i" + list.get(i).getLabel());
            for (int j = 0; j < loggedModels.size(); j++) {
                Logger.logD("TaskUtils ", "setAnsweredData  list pos j " + list.get(i).getLabel());
                Logger.logD("TaskUtils ", "setAnsweredData list pos j" + loggedModels.get(j).getTypeRem());
                if (list.get(i).getLabel().equalsIgnoreCase(loggedModels.get(j).getTypeRem())) {
                    Logger.logD("TaskUtils ", " list pos if " + list.get(i).getLabel());
                    Logger.logD("TaskUtils ", " list pos if " + loggedModels.get(j).getTypeRem());
                    list.get(i).setAnswer(loggedModels.get(j).getAns());
                    break;
                }

            }

            if ((list.get(i).getAnswer() == null || list.get(i).getAnswer().isEmpty()) && currentPos != i) {
                Logger.logD("TaskUtils ", " list pos No" + list.get(i).getLabel());
                list.get(i).setAnswer("No");
            }
        }
        return list;
    }

    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public List<DaysBean> loopDaysList(List<HomeTaskBean> homeTaskBeansList) {
        List<DaysBean> daysBeanList = new ArrayList<>();
        if (homeTaskBeansList.size() <= 0)
            return null;

        for (int i = 0; i < homeTaskBeansList.get(0).getData().getDuration(); i++) {
            // Logger.logD(Constants.PROJECT, "getReminder loopDaysList-- " + homeTaskBeansList.get(0).getData().getReminder());
            String labelText = "";
            String date = "";
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
            Date activationDate = null;
            try {
                activationDate = dateFormat.parse(homeTaskBeansList.get(0).getActivationDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            switch (homeTaskBeansList.get(0).getData().getReminder()) {
                case "Daily":
                    labelText = "Day " + (i + 1);
                    if (i > 0) {
                        Calendar calendar = toCalendar(activationDate);
                        calendar.add(Calendar.DAY_OF_YEAR, i);
                        Date tomorrow = calendar.getTime();
                        date = dateFormat.format(tomorrow);
                        Logger.logD(Constants.PROJECT, "Tomorrow Date--" + date);
                    } else {
                        date = homeTaskBeansList.get(0).getActivationDate(); // Activation Date --  Current date
                        Logger.logD(Constants.PROJECT, "getActivationDate Date 2--" + date);
                    }
                    //1*(i+1)
                    break;
                case "Weekly":
                    labelText = "Week " + i;
                    if (i > 0) {
                        Calendar calendar = toCalendar(activationDate);
                        Date today = calendar.getTime();
                        calendar.add(Calendar.DAY_OF_YEAR, i * 7);
                        Date tomorrow = calendar.getTime();
                        date = dateFormat.format(tomorrow);
                    } else {
                        date = homeTaskBeansList.get(0).getActivationDate(); // Activation Date --  Current date
                    }
                    break;
                case "Fortnight":
                    if (i > 0) {
                        Calendar calendar = toCalendar(activationDate);
                        calendar.add(Calendar.DAY_OF_YEAR, i * 15);
                        Date tomorrow = calendar.getTime();
                        date = dateFormat.format(tomorrow);
                    } else {
                        date = homeTaskBeansList.get(0).getActivationDate(); // Activation Date --  Current date
                    }
                    break;
                case "Monthly":
                    if (i > 0) {
                        Calendar calendar = toCalendar(activationDate);
                        calendar.add(Calendar.DAY_OF_YEAR, i * 30);
                        Date tomorrow = calendar.getTime();
                        date = dateFormat.format(tomorrow);
                    } else {
                        date = homeTaskBeansList.get(0).getActivationDate(); // Activation Date --  Current date
                    }
                    break;
                case "Yearly":
                    if (i > 0) {
                        Calendar calendar = toCalendar(activationDate);
                        calendar.add(Calendar.DAY_OF_YEAR, i * 365);
                        Date tomorrow = calendar.getTime();
                        date = dateFormat.format(tomorrow);
                    } else {
                        date = homeTaskBeansList.get(0).getActivationDate(); // Activation Date --  Current date
                    }
                    break;
                default:
                    break;
            }
            Logger.logD(Constants.PROJECT, "getReminder labelText-- " + labelText);
            Logger.logD(Constants.PROJECT, "getReminder date-- " + date);
            DaysBean daysBean = new DaysBean();
            daysBean.setLabel(labelText);
            daysBean.setDate(date);
            daysBeanList.add(daysBean);
            Logger.logD(Constants.PROJECT, "daysBeanList labelText-- " + daysBeanList.size());
        }
        return daysBeanList;
    }

    public int getCurrentItemFromList(List<DaysBean> daysBeanList, DateFormat dateFormat, int current, Date todayDate) {

        if (daysBeanList != null)
            for (int i = 0; i < daysBeanList.size(); i++) {
                if (daysBeanList.size() == i + 1) {
                    try {
                        Date loopDate = dateFormat.parse(daysBeanList.get(i).getDate());
                        if (todayDate.equals(loopDate) || todayDate.after(loopDate)) {
                            current = i;
                            break;
                        } else {
                            return -1;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        Date loopDate = dateFormat.parse(daysBeanList.get(i).getDate());
                        Date nextDate = dateFormat.parse(daysBeanList.get(i + 1).getDate());
                        if ((todayDate.after(loopDate) || todayDate.equals(loopDate)) && todayDate.before(nextDate)) {
                            current = i;
                            break;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }
        return current;
    }


}
