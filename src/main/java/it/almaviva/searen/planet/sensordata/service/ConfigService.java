package it.almaviva.searen.planet.sensordata.service;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfigService {

    public String getProperty(String propertyName, String defaultValue) {
        Config config = ConfigProvider.getConfig();
        return config.getOptionalValue(propertyName, String.class).orElse(defaultValue);
    }
}

