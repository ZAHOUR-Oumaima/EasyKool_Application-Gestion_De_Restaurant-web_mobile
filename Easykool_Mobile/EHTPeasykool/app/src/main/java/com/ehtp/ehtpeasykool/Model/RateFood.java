package com.ehtp.ehtpeasykool.Model;

public class RateFood {
   private String rateValue;
   private String comment;

    public RateFood(String rateValue, String comment) {
        this.rateValue = rateValue;
        this.comment = comment;
    }

    public RateFood() {
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
