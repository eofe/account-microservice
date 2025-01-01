package com.eofe.accountmicroservice.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "error.account")
public class ErrorMessage {

    private String nullDto;
    private String notExist;
    private String notFound;
    private String resourceNotFound;
    private String nullOrEmpty;
    private String badAccountNumberFormat;

    public String getNullDto() {
        return nullDto;
    }

    public void setNullDto(String nullDto) {
        this.nullDto = nullDto;
    }

    public String getNotExist() {
        return notExist;
    }

    public void setNotExist(String notExist) {
        this.notExist = notExist;
    }

    public String getNotFound() {
        return notFound;
    }

    public void setNotFound(String notFound) {
        this.notFound = notFound;
    }

    public String getResourceNotFound() {
        return resourceNotFound;
    }

    public void setResourceNotFound(String resourceNotFound) {
        this.resourceNotFound = resourceNotFound;
    }

    public String getNullOrEmpty() {
        return nullOrEmpty;
    }
    public void setNullOrEmpty(String nullOrEmpty) {
        this.nullOrEmpty = nullOrEmpty;
    }

    public String getBadAccountNumberFormat() {
        return badAccountNumberFormat;
    }

    public void setBadAccountNumberFormat(String badAccountNumberFormat) {
        this.badAccountNumberFormat = badAccountNumberFormat;
    }
}
