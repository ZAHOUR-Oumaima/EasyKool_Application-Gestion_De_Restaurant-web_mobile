package com.ehtp.EasyKool.Model;

public class AllArticles {

    private Articles breakfastNames, lunchNames, dinnerNames;

    public AllArticles() { }

    public AllArticles(Articles breakfastNames, Articles lunchNames, Articles dinnerNames) {
        this.breakfastNames = breakfastNames;
        this.lunchNames = lunchNames;
        this.dinnerNames = dinnerNames;
    }

    public Articles getBreakfastNames() {
        return breakfastNames;
    }

    public void setBreakfastNames(Articles breakfastNames) {
        this.breakfastNames = breakfastNames;
    }

    public Articles getLunchNames() {
        return lunchNames;
    }

    public void setLunchNames(Articles lunchNames) {
        this.lunchNames = lunchNames;
    }

    public Articles getDinnerNames() {
        return dinnerNames;
    }

    public void setDinnerNames(Articles dinnerNames) {
        this.dinnerNames = dinnerNames;
    }
}
