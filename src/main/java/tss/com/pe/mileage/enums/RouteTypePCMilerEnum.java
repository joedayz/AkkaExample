package tss.com.pe.mileage.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josediaz on 2/09/2016.
 */
public enum RouteTypePCMilerEnum {
    PRACTICAL("PRACTICAL","PRACTICAL"),
    NATIONAL("NATIONAL","NATIONAL"),
    FIFTYTHREE("FIFTYTHREE","FIFTYTHREE"),
    AVOIDTOLL("AVOIDTOLL","AVOIDTOLL");

    public static RouteTypePCMilerEnum  DEFAULT = RouteTypePCMilerEnum.PRACTICAL;
    private String code;
    private String label;

    RouteTypePCMilerEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static List<RouteTypePCMilerEnum> findAll(){
        List<RouteTypePCMilerEnum> list= new ArrayList<RouteTypePCMilerEnum>();

        for(RouteTypePCMilerEnum status: RouteTypePCMilerEnum.values()){
            list.add(status);
        }

        return list;
    }

    public static RouteTypePCMilerEnum findByCode(String value){
        RouteTypePCMilerEnum[] array= RouteTypePCMilerEnum.values();
        RouteTypePCMilerEnum result=null;
        for (int i=0;i<array.length;i++){
            if(array[i].getCode().equals(value)){
                result=array[i];
                break;
            }
        }
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



}
