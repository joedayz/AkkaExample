package tss.com.pe.mileage.dto;

/**
 * Created by josediaz on 2/09/2016.
 */
public class MileageDto {

    private Long mileageId;
    private Integer distance;
    private String countryOrigin;
    private String stateOrigin;
    private String cityOrigin;
    private String zipCodeOrigin;
    private String countryDestination;
    private String stateDestination;
    private String cityDestination;
    private String zipCodeDestination;


    public MileageDto(LocationInfo orgLoc, LocationInfo dstLoc, int distance) {
        this.countryOrigin = orgLoc.getCountry();
        this.cityOrigin = orgLoc.getCity();
        this.stateOrigin = orgLoc.getState();
        this.zipCodeOrigin = orgLoc.getZip();

        this.countryDestination = dstLoc.getCountry();
        this.cityDestination = dstLoc.getCity();
        this.stateDestination = dstLoc.getState();
        this.zipCodeDestination = dstLoc.getZip();

        this.distance= distance;
    }

    public MileageDto() {
    }

    public Long getMileageId() {
        return mileageId;
    }

    public void setMileageId(Long mileageId) {
        this.mileageId = mileageId;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }


    public String getCountryOrigin() {
        return countryOrigin;
    }

    public void setCountryOrigin(String countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public String getStateOrigin() {
        return stateOrigin;
    }

    public void setStateOrigin(String stateOrigin) {
        this.stateOrigin = stateOrigin;
    }

    public String getCityOrigin() {
        return cityOrigin;
    }

    public void setCityOrigin(String cityOrigin) {
        this.cityOrigin = cityOrigin;
    }

    public String getZipCodeOrigin() {
        return zipCodeOrigin;
    }

    public void setZipCodeOrigin(String zipCodeOrigin) {
        this.zipCodeOrigin = zipCodeOrigin;
    }

    public String getCountryDestination() {
        return countryDestination;
    }

    public void setCountryDestination(String countryDestination) {
        this.countryDestination = countryDestination;
    }

    public String getStateDestination() {
        return stateDestination;
    }

    public void setStateDestination(String stateDestination) {
        this.stateDestination = stateDestination;
    }

    public String getCityDestination() {
        return cityDestination;
    }

    public void setCityDestination(String cityDestination) {
        this.cityDestination = cityDestination;
    }

    public String getZipCodeDestination() {
        return zipCodeDestination;
    }

    public void setZipCodeDestination(String zipCodeDestination) {
        this.zipCodeDestination = zipCodeDestination;
    }


    @Override
    public String toString() {
        return "MileageDto{" +
                "mileageId=" + mileageId +
                ", distance=" + distance +
                ", countryOrigin='" + countryOrigin + '\'' +
                ", stateOrigin='" + stateOrigin + '\'' +
                ", cityOrigin='" + cityOrigin + '\'' +
                ", zipCodeOrigin='" + zipCodeOrigin + '\'' +
                ", countryDestination='" + countryDestination + '\'' +
                ", stateDestination='" + stateDestination + '\'' +
                ", cityDestination='" + cityDestination + '\'' +
                ", zipCodeDestination='" + zipCodeDestination + '\'' +
                '}';
    }
}
