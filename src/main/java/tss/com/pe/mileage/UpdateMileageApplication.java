package tss.com.pe.mileage;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import scala.concurrent.duration.Duration;
import tss.com.pe.mileage.actors.Supervisor;
import tss.com.pe.mileage.extension.SpringExtension;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableAutoConfiguration
@ComponentScan("tss.com.pe.mileage.configuration")
public class UpdateMileageApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(UpdateMileageApplication.class, args);


		ActorSystem system = context.getBean(ActorSystem.class);

		final LoggingAdapter log = Logging.getLogger(system, "Application");
		Long collectorInterval = (Long)context.getBean("getCollectorInterval");


		log.info("Starting up mileage collector with collector interval of {}", collectorInterval);

		SpringExtension ext = context.getBean(SpringExtension.class);

		// Use the Spring Extension to create props for a named actor bean
		ActorRef supervisor = system.actorOf(
				ext.props("supervisor"));

		system.scheduler().schedule(Duration.Zero(), Duration.create(collectorInterval,
				TimeUnit.MINUTES), supervisor,
				Supervisor
						.START_COLLECTING,
				system.dispatcher(), null);
	}
}
