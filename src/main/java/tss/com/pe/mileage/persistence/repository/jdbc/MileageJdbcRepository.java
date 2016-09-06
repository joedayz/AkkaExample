package tss.com.pe.mileage.persistence.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tss.com.pe.mileage.dto.MileageDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by josediaz on 2/09/2016.
 */
@Repository
public class MileageJdbcRepository implements MileageCustomRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MileageJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    MileageJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<MileageDto> getMileages(int rowNums) {
        Map<String, String> queryParams = new HashMap<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select mileage_id as mileageId, distance as distance,   ");
        sql.append("country_origin as countryOrigin, ");
        sql.append("state_origin as stateOrigin, ");
        sql.append("city_origin as cityOrigin, ");
        sql.append("zip_code_origin as zipCodeOrigin, ");
        sql.append("country_destination as countryDestination, ");
        sql.append("state_destination as stateDestination, ");
        sql.append("city_destination as cityDestination, ");
        sql.append("zip_code_destination as zipCodeDestination ");
        sql.append("from mileage ");
        sql.append("where distance is null and  rownum <=" + rowNums);


        List<MileageDto> searchResults = jdbcTemplate.query(sql.toString(), queryParams,
                new BeanPropertyRowMapper<>(MileageDto.class));

        return searchResults;
    }

    public void updateDistanceMiles(Integer distance, MileageDto mileage) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> queryParams = new HashMap<>();

        queryParams.put("distance", distance);


        sql.append("update mileage set ");


        sql.append("distance = :distance ");
        sql.append(" where mileage_id =  " + mileage.getMileageId());


        int rowsUpdate = jdbcTemplate.update(sql.toString(), queryParams);
        LOGGER.info("Mileage updated for Origin = [" + mileage.getCountryOrigin() + "," + mileage.getStateOrigin() + "," + mileage.getCityOrigin() + "," +
                mileage.getCityOrigin() + "," + mileage.getZipCodeOrigin() + "] , Destination = [" + mileage.getCountryDestination() + "," +
                mileage.getStateDestination() + "," + mileage.getCityDestination() + "," + mileage.getZipCodeDestination() + "] "
                 + ", Distance =" + distance + ". " + rowsUpdate
                + " updated");

    }


}
