package it.almaviva.searen.planet.sensordata.consumer;

import com.google.protobuf.ByteString;
import com.mgwy.proto.data.PbAisPosition;
import com.mgwy.proto.data.PbOptronicStatus;
import com.mgwy.proto.sentence.PbAISVesselInfo;
import com.mgwy.proto.sentence.PbFleetVessel;
import com.vms.searenactivemqbridge.protobufs.PbOilSpill;
import io.quarkus.runtime.annotations.RegisterForReflection;
import it.almaviva.searen.planet.sensordata.entity.OilSpillEntity;
import it.almaviva.searen.planet.sensordata.entity.PbFleetVesselEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.jgroups.util.ByteArray;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApplicationScoped
@RegisterForReflection(targets = {PbFleetVessel.Builder.class })

public class CamelRoutes extends RouteBuilder {

    @Override
    public void configure() {
       
        from("jms:queue:oil_spill")
                .unmarshal()
                .protobuf(PbOilSpill.class.getName())
                .log("Message from oilspill queue: ${body}")
                .process(exchange -> {
                    PbOilSpill pbOilSpill = exchange.getIn().getBody(PbOilSpill.class);
                    OilSpillEntity oilSpillEntity = new OilSpillEntity();
                    oilSpillEntity.setIdent(Long.valueOf(pbOilSpill.getId()));
                    oilSpillEntity.setTimestamp(ZonedDateTime.ofInstant(
                            Instant.ofEpochMilli(pbOilSpill.getTimestamp()), ZoneId.of("UTC")));
                    oilSpillEntity.setLatitude(pbOilSpill.getLatitude());
                    oilSpillEntity.setLongitude(pbOilSpill.getLongitude());
                    oilSpillEntity.setRadius((float) pbOilSpill.getScale());
                    exchange.getIn().setBody(oilSpillEntity);
                })
                .to("jpa:"+OilSpillEntity.class.getName())
                .log("Saved entity from OIL_SPILL: ${body}"); // Log the received message


         
        from("jms:queue:ais_vessel_info")
                .unmarshal()
                .protobuf(PbAISVesselInfo.class.getName())
                .log("Received message from ais_vessel_info: ${body}"); // Log the received message


        from("jms:queue:fleet_vessel")
                .unmarshal()
                .protobuf(PbFleetVessel.class.getName())
                .log("Message from fleet_vessel queue: ${body}")
                .process(exchange -> {
                    PbFleetVessel pbFleetVessel = exchange.getIn().getBody(PbFleetVessel.class);
                    PbAisPosition pbAisPosition = pbFleetVessel.getPosInfo();
                    PbFleetVesselEntity pbFleetVesselEntity = new PbFleetVesselEntity();
                    log.info("From Fleet Vessel call sign {} with lat {} lon {} and heading {}",
                    pbFleetVessel.getCallSign(),
                    pbAisPosition.getLatitude(), pbAisPosition.getLongitude(),
                    pbAisPosition.getHeading());
                   /*  pbFleetVesselEntity.setMmsi(pbFleetVessel.getMmsi());
                    pbFleetVesselEntity.setImo(pbFleetVessel.hasImo()?pbFleetVessel.getImo():-1); */
                    pbFleetVesselEntity.setCallSign(pbFleetVessel.getCallSign());
                    pbFleetVesselEntity.setTimestamp(Instant.now().atZone( ZoneId.of("UTC")));
                    pbFleetVesselEntity.setName(pbFleetVessel.getName());
                    pbFleetVesselEntity.setType(pbFleetVessel.getType());
                    pbFleetVesselEntity.setWidth(pbFleetVessel.getWidth());
                    pbFleetVesselEntity.setLength(pbFleetVessel.getLength());
                    pbFleetVesselEntity.setDraught(pbFleetVessel.getDraught()); 
                    pbFleetVesselEntity.setLatitude(pbAisPosition.getLatitude());
                    pbFleetVesselEntity.setLongitude( pbAisPosition.getLongitude());
                    pbFleetVesselEntity.setHeading(pbAisPosition.getHeading());
                    exchange.getIn().setBody(pbFleetVesselEntity);
                }).to("jpa:"+PbFleetVesselEntity.class.getName())
                .log("Saved entity from PbFleetVessel: ${body}"); // Log the received message


        from("jms:queue:optronic_status")
                .unmarshal()
                .protobuf(PbOptronicStatus.class.getName())
                .log("Received message from optronic_status: ${body}"); // Log the received message
    }
}
