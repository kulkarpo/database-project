package de.tum.in.dbpra.finalProject.model.bean;

import java.sql.Time;
import java.util.Date;

public class ApplicationBean {
    int applicationid;
    String name;
    String contact;
    String address;
    String letter;
    boolean sellingindicator;
    Date wisheddate;
    String type;
    String result;

    public ApplicationBean() {
    }

    public int getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(int applicationid) {
        this.applicationid = applicationid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isSellingindicator() {
        return sellingindicator;
    }

    public void setSellingindicator(boolean sellingindicator) {
        this.sellingindicator = sellingindicator;
    }

    public Date getWisheddate() {
        return wisheddate;
    }

    public void setWisheddate(Date wisheddate) {
        this.wisheddate = wisheddate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
