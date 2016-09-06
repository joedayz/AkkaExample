package tss.com.pe.mileage.configuration;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import tss.com.pe.mileage.extension.SpringExtension;

/**
 * Created by josediaz on 1/09/2016.
 */

@Configuration
@Lazy
@ComponentScan(basePackages = {
        "tss.com.pe.mileage.actors",
        "tss.com.pe.mileage.mock",
        "tss.com.pe.mileage.extension",
        "tss.com.pe.mileage.support",
        "tss.com.pe.mileage"
                + ".persistence" })


public class ApplicationConfiguration extends SpringBootServletInitializer {

    // The application context is needed to initialize the Akka Spring
    // Extension
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringExtension springExtension;

    @Value("${mileage.collector-interval-min}")
    private Long collectorInterval;

    /**
     * Actor system singleton for this application.
     */
    @Bean
    public ActorSystem actorSystem() {

        ActorSystem system = ActorSystem
                .create("AkkaTaskProcessing", akkaConfiguration());

        // Initialize the application context in the Akka Spring Extension
        springExtension.initialize(applicationContext);
        return system;
    }

    /**
     * Read configuration from application.conf file
     */
    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }


    @Bean
    public Long getCollectorInterval() {
        return collectorInterval;
    }

}
