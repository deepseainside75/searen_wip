import com.google.protobuf.InvalidProtocolBufferException;
import com.mgwy.proto.data.PbAisPosition;
import com.mgwy.proto.sentence.PbFleetVessel;
import com.vms.searenactivemqbridge.protobufs.PbOilSpill;
import io.netty.buffer.ByteBufInputStream;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ProtoBufTest {
    @Test
    void testFleetVesselNestedObjectRoundTrip() throws InvalidProtocolBufferException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PbFleetVessel pbFleetVessel = PbFleetVessel.newBuilder()
                .setCountryId(10)
                .setDimensioneA(20)
                .setDimensioneB(30)
                .setDimensioneC(40)
                .setDimensioneD(50)
                .setDraught(60.0f)
                .setHeading(70.0d)
                .setImo(80)
                .setLength(90.0f)
                .setMmsi(100)
                .setPith(110.0d)
                .setPosInfo(
                        PbAisPosition.newBuilder()
                                .setLatitude(39.0f)
                                .setLongitude(17.0f)
                                .setHeading(110)
                                .build()
                )
                .build();
        try {
            pbFleetVessel.writeTo(baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assert(baos!=null && baos.size()>0);
        PbFleetVessel pbFleetVesselDecoded = PbFleetVessel.parseFrom(baos.toByteArray());
        assert ( pbFleetVesselDecoded.getPosInfo().getLatitude()==pbFleetVessel.getPosInfo().getLatitude());
        assert ( pbFleetVesselDecoded.getPosInfo().getLongitude()==pbFleetVessel.getPosInfo().getLongitude());
        assert ( pbFleetVesselDecoded.getPosInfo().getHeading()==pbFleetVessel.getPosInfo().getHeading());



    }
}

