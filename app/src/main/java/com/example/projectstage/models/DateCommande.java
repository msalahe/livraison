package com.example.projectstage.models;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DateCommande {
    private Date dateComMin;
    private Date dateMax;

    public Date getDateComMin() {
        return dateComMin;
    }

    public void setDateComMin(Date dateComMin) {
        this.dateComMin = dateComMin;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
    }


    public void setDateCom(Date dateCom) {
        this.dateComMin = dateCom;
    }

    public DateCommande(String dateCom1,String dateMax) throws ParseException {
        SimpleDateFormat format= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateComMin = format.parse(dateCom1);
        this.dateMax = format.parse(dateMax);

    }
    public boolean exicts() throws ParseException {

        SimpleDateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateobj = new Date();
        String now= df.format(dateobj);
        Date now1 = df.parse(now);

        Log.d("after",now1.after(dateComMin)+"");
        Log.d("before",now1.before(dateMax)+"");
        if(!now1.after(dateComMin) && !now1.before(dateMax)){
            return  true;
        }
        return  false;
    }

}
