package tss.com.pe.mileage.actors;

import akka.actor.Terminated;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;
import tss.com.pe.mileage.util.MileageRuntimeException;

/**
 * Created by josediaz on 1/09/2016.
 */
public abstract class MileageActor extends UntypedActor {

    private static final Logger log = LoggerFactory.getLogger(MileageActor.class);

    protected abstract boolean handleMessage(Object message);

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("{} handleMileageMessage {}", this.hashCode());
        if (handleMessage(message)) {
            return;
        } else if (message instanceof Terminated) {
            throw new MileageRuntimeException("Terminated: " + message);
        } else {
            log.error("Unable to handle message {}", message);
            throw new MileageRuntimeException("Unable to handle message");
        }
    }

    @Override
    public void postStop() throws Exception {
        log.info("postStop");
        super.postStop();
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        log.info("preRestart {} {} {} ", this.hashCode(), reason, message);
        super.preRestart(reason, message);
    }
}
