package kr.legossol.janusinformation.common.gson;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;

@Slf4j
@Getter(AccessLevel.PACKAGE)
@Configuration
@ConfigurationProperties(value = "spring.gson")
@Validated
public class GsonConfigProperties {

    private GsonDateTimeAdaptor.Config dateTimeFormat;

    @PostConstruct
    void init() {
        log.info("(init) support date/time formats = {}", dateTimeFormat);
    }

    void setDateTimeFormat(GsonDateTimeAdaptor.Config dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    void setDateTimeFormat(String datetimeFormat) {
        this.dateTimeFormat = new GsonDateTimeAdaptor.Config();
        this.dateTimeFormat.setSerialize(datetimeFormat);
        this.dateTimeFormat.setDeserialize(datetimeFormat);
    }
}
