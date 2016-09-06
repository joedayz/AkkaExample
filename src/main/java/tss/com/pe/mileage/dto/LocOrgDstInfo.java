package tss.com.pe.mileage.dto;

import tss.com.pe.mileage.util.StringUtil;

/**
 * Created by josediaz on 2/09/2016.
 */
public class LocOrgDstInfo {
    LocationDto2 orgLoc;
    LocationDto2 dstLoc;

    public LocOrgDstInfo(LocationInfo orgLoc, LocationInfo dstLoc) {
        this.orgLoc = new LocationDto2(orgLoc);
        this.dstLoc = new LocationDto2(dstLoc);
        ;
    }

    public void uniformizeZip() {
        if (StringUtil.isEmpty(orgLoc.getZip()) || StringUtil.isEmpty(dstLoc.getZip())) {
            orgLoc.setZip(null);
            dstLoc.setZip(null);
        }
    }

    public LocationInfo getOrgLoc() {
        return orgLoc;
    }

    public LocationInfo getDstLoc() {
        return dstLoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocOrgDstInfo that = (LocOrgDstInfo) o;

        if (orgLoc != null ? !orgLoc.equals(that.orgLoc) : that.orgLoc != null) return false;
        return dstLoc != null ? dstLoc.equals(that.dstLoc) : that.dstLoc == null;

    }

    @Override
    public int hashCode() {
        int result = orgLoc != null ? orgLoc.hashCode() : 0;
        result = 31 * result + (dstLoc != null ? dstLoc.hashCode() : 0);
        return result;
    }
}
