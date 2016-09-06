package tss.com.pe.mileage.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josediaz on 2/09/2016.
 */

public enum VersionPCMilerEnum {
    V200("200", "200"),
    V210("210", "210"),
    V220("220", "220"),
    V230("230", "230"),
    V240("240", "240"),
    V250("250", "250"),
    V260("260", "260"),
    V270("270", "270"),
    V280("280", "280"),
    V290("290", "290");

    public static VersionPCMilerEnum DEFAULT = VersionPCMilerEnum.V210;

    private String code;
    private String label;

    VersionPCMilerEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static List<VersionPCMilerEnum> findAll() {
        List<VersionPCMilerEnum> list = new ArrayList<VersionPCMilerEnum>();

        for (VersionPCMilerEnum status : VersionPCMilerEnum.values()) {
            list.add(status);
        }

        return list;
    }

    public static VersionPCMilerEnum findByCode(String value) {
        VersionPCMilerEnum[] array = VersionPCMilerEnum.values();
        VersionPCMilerEnum result = null;
        for (int i = 0; i < array.length; i++) {
            if (array[i].getCode().equals(value)) {
                result = array[i];
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
