package tss.com.pe.mileage.actors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tss.com.pe.mileage.dto.LocationDto2;
import tss.com.pe.mileage.dto.LocationInfo;
import tss.com.pe.mileage.dto.MileageDto;
import tss.com.pe.mileage.dto.PCMilerResultDto;
import tss.com.pe.mileage.enums.RouteTypePCMilerEnum;
import tss.com.pe.mileage.enums.VersionPCMilerEnum;
import tss.com.pe.mileage.persistence.repository.jdbc.MileageCustomRepository;
import tss.com.pe.mileage.support.PCMiler;
import tss.com.pe.mileage.util.MileageRuntimeException;
import tss.com.pe.mileage.util.StringUtil;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by josediaz on 2/09/2016.
 */
@Component
@Scope("prototype")
public class ListMethodsActor extends MileageActor {

    private static final Logger log = LoggerFactory.getLogger(ListMethodsActor.class);

    private static AtomicInteger COUNTER = new AtomicInteger(0);

    @Autowired
    private PCMiler pcMiler;

    @Autowired
    private MileageCustomRepository mileageCustomRepository;

    @Override
    protected boolean handleMessage(Object message) {

        if (message instanceof MileageDto) {

            log.info("{} onReceive {}", COUNTER.addAndGet(1), this.hashCode());
            MileageDto mileage = (MileageDto) message;
            try {
                log.info("Calling to PCMiler for " + mileage);

                LocationInfo orgLoc = new LocationDto2(mileage.getCountryOrigin(), mileage.getStateOrigin(), mileage.getCityOrigin(), mileage.getZipCodeOrigin());
                LocationInfo dstLoc = new LocationDto2(mileage.getCountryDestination(), mileage.getStateDestination(), mileage.getCityDestination(), mileage.getZipCodeDestination());
                BigDecimal miles = callPCMiler(orgLoc, dstLoc);

                if(miles!=null) {
                    mileageCustomRepository.updateDistanceMiles(miles.intValue(), mileage);
                    log.info("Distance updated = " + miles.intValue());
                }else{
                    log.info("NULL NULL NULL NULL - Miles is NULL for Mileage " + mileage);
                }

                return true;
            } catch (Exception ex) {
                log.error("Failed to get miles from PCMiler {} \n {}", mileage, ex.toString());
                throw new MileageRuntimeException("PCMiler Error", ex);
            }


        } else {
            return false;
        }
    }

    private BigDecimal callPCMiler(LocationInfo orgLoc, LocationInfo dstLoc) {
        VersionPCMilerEnum pcMilerVersion = VersionPCMilerEnum.DEFAULT;

        RouteTypePCMilerEnum pcRouteType = RouteTypePCMilerEnum.DEFAULT;

        try {
            assert orgLoc != null;
            assert dstLoc != null;

            PCMilerResultDto milerResultDto = pcMiler.calculateMilesNonTransactional(pcMilerVersion.getCode(), pcRouteType.getCode(), orgLoc, dstLoc);

            Integer totalDistance = milerResultDto.getTotalDistance();
            if (totalDistance == null || !StringUtil.eq(milerResultDto.getErrCode(), "1"))
                log.warn("PCMiller error:" + milerResultDto + " org:" + orgLoc + " dst:" + dstLoc);

            return totalDistance == null ? null : new BigDecimal(totalDistance.doubleValue());
        } catch (Throwable e) {
            log.warn("Error obteniendo mileage:" + e, e);
            return null;
        }
    }
}
