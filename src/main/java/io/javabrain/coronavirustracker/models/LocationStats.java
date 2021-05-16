package io.javabrain.coronavirustracker.models;

public class LocationStats {

    private String state;
    private String country;
    private int latesTotalCases;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatesTotalCases() {
        return latesTotalCases;
    }

    public void setLatesTotalCases(int latesTotalCases) {
        this.latesTotalCases = latesTotalCases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latesTotalCases=" + latesTotalCases +
                '}';
    }
}
