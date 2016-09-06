package tss.com.pe.mileage.actors;

import akka.actor.ActorRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tss.com.pe.mileage.dto.MileageDto;
import tss.com.pe.mileage.persistence.repository.jdbc.MileageCustomRepository;


import java.util.List;

/**
 * Created by josediaz on 1/09/2016.
 */
@Component
@Scope("prototype")
public class ListClientsActor extends MileageActor {

    private static final Logger log = LoggerFactory.getLogger(ListClientsActor.class);

    public static final String START_COLLECTING = "StartCollecting";


    // supervisor-created pool of list methods actors
    protected ActorRef listMethodsPoolRef;


    @Autowired
    private MileageCustomRepository mileageCustomRepository;

    @Override
    public void preStart() throws Exception {
        log.info("preStart {}", this.hashCode());
        listMethodsPoolRef = new RelativeActorRefUtil(getContext())
                .resolvePoolRef(Supervisor.LIST_METHODS_ACTOR_ROUTER);
        super.preStart();
    }

    @Override
    protected boolean handleMessage(Object message) {
        if (START_COLLECTING.equals(message)) {

            log.info("Getting mileage list from database");
            List<MileageDto>  mileageList =  mileageCustomRepository.getMileages(1000);
            for(MileageDto mileage: mileageList){
                listMethodsPoolRef.tell(mileage, getSelf());
            }
            return true;
        } else {
            return false;
        }
    }


}