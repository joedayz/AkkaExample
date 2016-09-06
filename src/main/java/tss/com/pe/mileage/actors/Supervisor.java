package tss.com.pe.mileage.actors;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.routing.SmallestMailboxPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.Duration;
import tss.com.pe.mileage.extension.SpringExtension;

import static akka.actor.SupervisorStrategy.restart;

/**
 * Created by josediaz on 1/09/2016.
 */
@Component
@Scope("prototype")
public class Supervisor extends MileageActor {

    private static final Logger log = LoggerFactory.getLogger(ListMethodsActor.class);

    public static final String START_COLLECTING = "StartCollecting";

    public static final String LIST_CLIENTS_ACTOR_ROUTER = "list-clients-actor-router";
    public static final String LIST_METHODS_ACTOR_ROUTER = "list-methods-actor-router";

    @Autowired
    private SpringExtension springExtension;

    private ActorRef listClientsPoolRouter;

    @Value("${mileage.list-methods-pool-size}")
    private int listMethodsPoolSize;


    @Override
    public void preStart() throws Exception {

        log.info("Starting up");

        // for this pool, supervisor strategy restarts each clientActor if it fails.
        // currently this is not needed and could "resume" just as well
        listClientsPoolRouter = getContext().actorOf(new SmallestMailboxPool(1)
                        .withSupervisorStrategy(new OneForOneStrategy(-1,
                                Duration.Inf(),
                                (Throwable t) -> restart()))
                        .props(springExtension.props("listClientsActor")),
                LIST_CLIENTS_ACTOR_ROUTER);

        getContext().actorOf(new SmallestMailboxPool(listMethodsPoolSize)
                        .props(springExtension.props("listMethodsActor")),
                LIST_METHODS_ACTOR_ROUTER);


        super.preStart();
    }

    @Override
    protected boolean handleMessage(Object message) {
        if (START_COLLECTING.equals(message)) {
            listClientsPoolRouter.tell(ListClientsActor.START_COLLECTING, getSelf());
            return true;
        } else {
            return false;
        }
    }
}
