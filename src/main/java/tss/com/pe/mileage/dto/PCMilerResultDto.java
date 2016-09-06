package tss.com.pe.mileage.dto;

/**
 * Created by josediaz on 2/09/2016.
 */
public class PCMilerResultDto {

    private String errCode;
    private String errMsg;
    private Integer totalDistance;

    public PCMilerResultDto(){}


    public PCMilerResultDto(String errCode, String errMsg, Integer totalDistance) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.totalDistance = totalDistance;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public Integer getTotalDistance() {
        return this.totalDistance;
    }


    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }


    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


    public void setTotalDistance(Integer totalDistance) {
        this.totalDistance = totalDistance;
    }

    @Override
    public String toString() {
        return "PCMilerResultDto{" +
                "totalDistance=" + totalDistance +
                ", errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
