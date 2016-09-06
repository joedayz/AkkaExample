package tss.com.pe.mileage.dto;

/**
 * Created by josediaz on 2/09/2016.
 */
public class LocationDto2 implements LocationInfo {

    private String country;

    private String state;

    private String city;

    private String zip;

    public LocationDto2(LocationInfo info) {
        this.country = info.getCountry();
        this.state = info.getState();
        this.city = info.getCity();
        this.zip = info.getZip();
    }

    public LocationDto2(String country, String state, String city, String zip) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationDto2 that = (LocationDto2) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        return zip != null ? zip.equals(that.zip) : that.zip == null;

    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocDto2{" +
                "" + country +
                "/" + state +
                "/" + city +
                "/" + zip +
                '}';
    }
}
