package it.almaviva.searen.planet.sensordata.butler;
import it.almaviva.searen.planet.sensordata.entity.OilSpillEntity;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import static it.almaviva.searen.planet.sensordata.utils.Utils.getTableName;


@ApplicationScoped
public class OilSpillCamelButler extends RouteBuilder {


    private final String DEFAULT_TIME_ZONE = "UTC";
    private final String CAMEL_ZONED_NOW_EXPRESSION = "${date-with-timezone:now:"+DEFAULT_TIME_ZONE+":yyyy-MM-dd HH:mm:ss}";
    private final String FULLQUALIFIED_NAME = OilSpillEntity.class.getName();
    private final String CANONICAL_NAME = OilSpillEntity.class.getCanonicalName();

    @ConfigProperty (name="consumer.butler.oilspill.cron", defaultValue = "0 0 0 ? * * *")
    private String cronExpression;

    @ConfigProperty (name="consumer.butler.oilspill.pgspan", defaultValue = "1 DAY" )
    private String messageOlderThen;



    @Override
    public void configure() {
        String deleteQuery = "delete from "+ getTableName(OilSpillEntity.class)+" os where os.timestamp < NOW() - INTERVAL '"+messageOlderThen+"'";
        String routeID = CANONICAL_NAME + "_ButlerCronRoute";


        // Schedule using cron expression
        from("quartz://"+this.getClass().getName()+"/"+ CANONICAL_NAME +"?cron="+cronExpression)
                .routeId(routeID)
                .toD("jpa://"+ FULLQUALIFIED_NAME +"?nativeQuery="+ deleteQuery)
                .log(routeID+" QUERY:"+ deleteQuery +" executed successfully at "+DEFAULT_TIME_ZONE+" time:"+CAMEL_ZONED_NOW_EXPRESSION);
    }





}
