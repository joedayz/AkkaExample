package tss.com.pe.mileage.support;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tss.com.pe.mileage.dto.LocationInfo;
import tss.com.pe.mileage.dto.PCMilerResultDto;
import tss.com.pe.mileage.persistence.repository.jdbc.MileageCustomRepository;
import tss.com.pe.mileage.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josediaz on 2/09/2016.

 */
@Service
@Scope("prototype")
public class PCMiler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PCMiler.class);

    @Autowired
    private MileageCustomRepository repository;

    @Value("${spring.pcmiler.url}")
    String urlPCMiler;

    @Value("${spring.pcmiler.tmsUrlBase}")
    String tmsUrlBase;

    @Value("${spring.pcmiler.srvUrlBase}")
    String srvUrlBase;




    private final static String USER_AGENT = "Mozilla/5.0";

    private String getHtmlOriginal(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private String getXMLRespuesta(String htmlOriginal) {
        int posInicial = htmlOriginal.indexOf("<TEXTAREA name=\"xmlRetText\"");
        int posFinal = htmlOriginal.lastIndexOf("</message></TEXTAREA>");
        String respuesta = htmlOriginal.substring(posInicial,
                posInicial + (posFinal - posInicial + "</message></TEXTAREA>".length()));
        return respuesta;
    }

    private PCMilerResultDto getPCMilerResultDto(String xmlRespuesta) {
        int totalDistance = 0;
        int posInicial = xmlRespuesta.indexOf("<errCode>");
        int posFinal = xmlRespuesta.lastIndexOf("</errCode>");

        String errCode = xmlRespuesta.substring(posInicial + "<errCode>".length(),
                posInicial + (posFinal - posInicial));

        posInicial = xmlRespuesta.indexOf("<errMsg>");
        posFinal = xmlRespuesta.lastIndexOf("</errMsg>");

        String errMsg = xmlRespuesta.substring(posInicial + "<errMsg>".length(), posInicial + (posFinal - posInicial));

        if (errCode.equals("1")) {

            posInicial = xmlRespuesta.indexOf("<totalDistance>");
            posFinal = xmlRespuesta.lastIndexOf("</totalDistance>");
            String strTotalDistance = xmlRespuesta.substring(posInicial + "<totalDistance>".length(),
                    posInicial + (posFinal - posInicial));
            totalDistance = Integer.parseInt(strTotalDistance);
        }

        PCMilerResultDto dto = new PCMilerResultDto(errCode, errMsg, totalDistance);

        return dto;
    }

    private List<NameValuePair> initializePCMailerParameters(String version, String routeType, LocationInfo originDto,
                                                             LocationInfo destinationDto) {

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("urlStr", "TMSP"));
        urlParameters.add(new BasicNameValuePair("appName", "PCM"));
        urlParameters.add(new BasicNameValuePair("appVer", version));
        urlParameters.add(new BasicNameValuePair("routeType", routeType));

        String destinationDtoZip = destinationDto.getZip();
        String originDtoZip = originDto.getZip();
        boolean excludeZip = StringUtil.isEmpty(originDtoZip) || StringUtil.isEmpty(destinationDtoZip);
        if (excludeZip) destinationDtoZip = "";
        if (excludeZip) originDtoZip = "";

        if ((originDtoZip != null && !originDtoZip.trim().equals("") )
                && (destinationDtoZip != null && !destinationDtoZip.trim().equals("") )) {
            urlParameters.add(new BasicNameValuePair("locMethod", "ZIP"));
            urlParameters.add(new BasicNameValuePair("locMethod1", "ZIP"));
            urlParameters.add(new BasicNameValuePair("appUrl",
                    tmsUrlBase + "?PCMVersion=" + version + "&routeType=" + routeType + "&method=ZIP"));
        } else {
            urlParameters.add(new BasicNameValuePair("locMethod", "CITY"));
            urlParameters.add(new BasicNameValuePair("locMethod1", "CITY"));
            urlParameters.add(new BasicNameValuePair("appUrl",
                    tmsUrlBase + "?PCMVersion=" + version + "&routeType=" + routeType + "&method=CITY"));
        }


        urlParameters.add(new BasicNameValuePair("xmlText", "<message> " + "<header> " + "<userID>dneuser</userID>"
                + "<password>dneuser</password>" + "<action>GetHHGMiles</action>" + "<errCode></errCode>" + "</header>"
                + "<body>" + "<points>" + "<point>" + "<city>" + originDto.getCity() + "</city>" + "<county></county>"
                + "<state>" + originDto.getState() + "</state>" + "<country>" + originDto.getCountry() + "</country>"
                + "<destinationDtoZip>" + originDtoZip + "</destinationDtoZip>" + "</point>" + "<point>" + "<city>" + destinationDto.getCity()
                + "</city>" + "<county></county>" + "<state>" + destinationDto.getState() + "</state>" + "<country>"
                + destinationDto.getCountry() + "</country>" + "<destinationDtoZip>" + destinationDtoZip + "</destinationDtoZip>" + "</point>"
                + "</points>" + "</body>" + "</message>"));

        urlParameters.add(new BasicNameValuePair("xmlRetText", ""));
        urlParameters.add(new BasicNameValuePair("testThreadNum", ""));
        urlParameters.add(new BasicNameValuePair("testMsgNum", ""));
        urlParameters.add(new BasicNameValuePair("urlStr1", "TMSP"));
        urlParameters.add(new BasicNameValuePair("appName1", "PCM"));
        urlParameters.add(new BasicNameValuePair("appVer1", version));
        urlParameters.add(new BasicNameValuePair("routeType1", routeType));

        urlParameters.add(new BasicNameValuePair("srvList",
                srvUrlBase + "?PCMVersion=200;" + srvUrlBase + "?PCMVersion=210;" + srvUrlBase + "?PCMVersion=220;"
                        + srvUrlBase + "?PCMVersion=230;" + srvUrlBase + "?PCMVersion=240;" + srvUrlBase
                        + "?PCMVersion=250;" + srvUrlBase + "?PCMVersion=260;" + srvUrlBase + "?PCMVersion=270;"
                        + srvUrlBase + "?PCMVersion=280;" + srvUrlBase + "?PCMVersion=290"));
        urlParameters.add(new BasicNameValuePair("submit", "Submit"));
        return urlParameters;
    }



//    @Transactional (propagation = Propagation.REQUIRED)
//    public void persistMileageIfNeeded(String version, String routeType, LocationInfo originDto, LocationInfo destinationDto, PCMilerResultDto dto) {
//        if (dto.getErrCode().equals("1") && originDto != null & destinationDto != null)
//            repository.insertDistance(version, routeType, dto.getTotalDistance(), originDto, destinationDto);
//    }

    @Transactional (propagation = Propagation.NEVER)
    public PCMilerResultDto calculateMilesNonTransactional(String version, String routeType, LocationInfo originDto, LocationInfo destinationDto) throws UnsupportedEncodingException {
        PCMilerResultDto dto;HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(urlPCMiler);
        post.setHeader("User-Agent", USER_AGENT);
        List<NameValuePair> urlParameters = initializePCMailerParameters(version, routeType, originDto, destinationDto);
        HttpResponse response;
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        try {
            LOGGER.debug("Calling PC Miler:" + urlParameters);
            response = client.execute(post);
            LOGGER.debug("Called PC Miler:" + response);
            String htmlOriginal1 = getHtmlOriginal(response);
            String xmlRespuesta = getXMLRespuesta(htmlOriginal1);
            LOGGER.debug("Called PC Miler:" + xmlRespuesta);
            dto = getPCMilerResultDto(xmlRespuesta);
        } catch (Exception ex) {
            LOGGER.debug("Called PC Miler with error:" + ex);
            dto = new PCMilerResultDto();
            dto.setErrCode("-1");
            dto.setErrMsg("Error PCMiler: " + ex.getMessage());
        }
        return dto;
    }

}