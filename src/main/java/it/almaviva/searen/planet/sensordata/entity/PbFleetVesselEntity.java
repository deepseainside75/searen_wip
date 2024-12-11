package it.almaviva.searen.planet.sensordata.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "vessels")
@IdClass(PbFleetVesselPrimaryKey.class)
public class PbFleetVesselEntity {

    @Id
    private String callSign;

    @Id
    private ZonedDateTime timestamp;


    public Geometry getGeom() {
        return geom;
    }

    @Column(insertable = false,updatable = false)
    private Geometry geom;

    
   
    private int mmsi;

   
    private int imo;
   

    private String name;


    private int type;

 
    private float width;

  
    private float length;

  
    private float draught; 

    
    private Double latitude;

   
    private Double longitude;


    private Double heading;

    // Getters and Setters
   
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getMmsi() {
        return mmsi;
    }

    public void setMmsi(int mmsi) {
        this.mmsi = mmsi;
    }

    public int getImo() {
        return imo;
    }

    public void setImo(int imo) {
        this.imo = imo;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getDraught() {
        return draught;
    }

    public void setDraught(float draught) {
        this.draught = draught;
    } 
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }
}
