package tss.com.pe.mileage.persistence.repository.jdbc;

import tss.com.pe.mileage.dto.MileageDto;


import java.util.List;

/**
 * Created by josediaz on 2/09/2016.
 */
public interface MileageCustomRepository {

    List<MileageDto> getMileages(int rowNums);

    void updateDistanceMiles(Integer distance, MileageDto mileage);

}
