package com.example.madpropertypal_v3;

public class ReportModelClass {

    Integer rid;
    String rdate;
    String rinterest;
    String rprice;
    String rexpiry;
    String rconditions;
    String rcomments;


    public ReportModelClass(String rdate, String rinterest, String rprice, String rexpiry, String rconditions, String rcomments) {
        this.rdate = rdate;
        this.rinterest = rinterest;
        this.rprice = rprice;
        this.rexpiry = rexpiry;
        this.rconditions = rconditions;
        this.rcomments = rcomments;
    }

    public ReportModelClass(Integer rid, String rdate, String rinterest, String rprice, String rexpiry, String rconditions, String rcomments) {
        this.rid = rid;
        this.rdate = rdate;
        this.rinterest = rinterest;
        this.rprice = rprice;
        this.rexpiry = rexpiry;
        this.rconditions = rconditions;
        this.rcomments = rcomments;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getRinterest() {
        return rinterest;
    }

    public void setRinterest(String rinterest) {
        this.rinterest = rinterest;
    }

    public String getRprice() {
        return rprice;
    }

    public void setRprice(String rprice) {
        this.rprice = rprice;
    }

    public String getRexpiry() {
        return rexpiry;
    }

    public void setRexpiry(String rexpiry) {
        this.rexpiry = rexpiry;
    }

    public String getRconditions() {
        return rconditions;
    }

    public void setRconditions(String rconditions) {
        this.rconditions = rconditions;
    }

    public String getRcomments() {
        return rcomments;
    }

    public void setRcomments(String rcomments) {
        this.rcomments = rcomments;
    }

}
