package it.almaviva.searen.planet.sensordata.entity;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;


import java.time.ZonedDateTime;

@Entity
@Table(name="oil_spill")
@IdClass(OilSpillPrimaryKey.class)
public class OilSpillEntity {
    @Id
    private Long ident;
    @Id
    private ZonedDateTime timestamp;
    private Double latitude;
    private Double longitude;
    private Float radius;

    public Geometry getGeom() {
        return geom;
    }

    @Column(insertable = false,updatable = false)
    private Geometry geom;


    public Long getIdent() {
        return ident;
    }

    public void setIdent(Long ident) {
        this.ident = ident;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
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

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }



}
