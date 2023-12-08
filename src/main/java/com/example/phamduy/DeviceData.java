package com.example.phamduy;

public class DeviceData {
    private double electricity;
    private double naturalGas;
    private double heatingOil;
    private double coal;
    private double lpg;
    private double propane;
    private double wood;
    private double electricity2;

    public DeviceData() {
        // Constructor mặc định để phù hợp với Firebase
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getElectricity2() {
        return electricity2;
    }

    public void setElectricity2(double electricity2) {
        this.electricity2 = electricity2;
    }

    public double getNaturalGas() {
        return naturalGas;
    }

    public void setNaturalGas(double naturalGas) {
        this.naturalGas = naturalGas;
    }

    public double getHeatingOil() {
        return heatingOil;
    }

    public void setHeatingOil(double heatingOil) {
        this.heatingOil = heatingOil;
    }

    public double getCoal() {
        return coal;
    }

    public void setCoal(double coal) {
        this.coal = coal;
    }

    public double getLpg() {
        return lpg;
    }

    public void setLpg(double lpg) {
        this.lpg = lpg;
    }

    public double getPropane() {
        return propane;
    }

    public void setPropane(double propane) {
        this.propane = propane;
    }

    public double getWood() {
        return wood;
    }

    public void setWood(double wood) {
        this.wood = wood;
    }
}


