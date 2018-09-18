package org.usfirst.frc.team2342.util;

public class PIDGains {

    private double p;
    private double i;
    private double d;
    private double ff;
    private double rr;
    private int izone;

    public PIDGains(double p, double i, double d, double ff, double rr, int izone) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.ff = ff;
        this.rr = rr;
        this.izone = izone;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getFf() {
        return ff;
    }

    public void setFf(double ff) {
        this.ff = ff;
    }

    public double getRr() {
        return rr;
    }

    public void setRr(double rr) {
        this.rr = rr;
    }

    public int getIzone() {
        return izone;
    }

    public void setIzone(int izone) {
        this.izone = izone;
    }

}
