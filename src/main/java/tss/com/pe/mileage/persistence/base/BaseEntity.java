package tss.com.pe.mileage.persistence.base;

import java.util.Date;

/**
 * Created by josediaz on 2/09/2016.
 */
public interface BaseEntity {

    public String getCreatedBy();

    public void setCreatedBy(String createdBy);

    public Date getCreatedDate() ;

    public void setCreatedDate(Date createdDate);

    public String getUpdatedBy();

    public void setUpdatedBy(String updatedBy);

    public Date getUpdatedDate();

    public void setUpdatedDate(Date updatedDate);
}
