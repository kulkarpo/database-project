package de.tum.in.dbpra.finalProject.model.bean;

import java.sql.Time;
import java.util.Date;

public class TimeslotBean {
    Date dateBegin;
    Date dateEnd;
    Time begintime;
    Time endtime;
    int timeslotid;
    int stageid;
    String band;

    public TimeslotBean() {
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Time getBegintime() {
        return begintime;
    }

    public void setBegintime(Time begintime) {
        this.begintime = begintime;
    }

    public Time getEndtime() {
        return endtime;
    }

    public void setEndtime(Time endtime) {
        this.endtime = endtime;
    }

    public int getTimeslotid() {
        return timeslotid;
    }

    public void setTimeslotid(int timeslotid) {
        this.timeslotid = timeslotid;
    }

    public int getStageid() {
        return stageid;
    }

    public void setStageid(int stageid) {
        this.stageid = stageid;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }
}
