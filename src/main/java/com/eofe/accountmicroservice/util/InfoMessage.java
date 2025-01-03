package com.eofe.accountmicroservice.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "info.account")
public class InfoMessage {

    private String createRequest;
    private String getRequest;
    private String updateRequest;
    private String deleteRequest;
    private String fetch;
    private String update;
    private String updateSuccess;
    private String delete;
    private String deleteSuccess;

    public String getCreateRequest() {
        return createRequest;
    }

    public void setCreateRequest(String createRequest) {
        this.createRequest = createRequest;
    }

    public String getGetRequest() {
        return getRequest;
    }

    public void setGetRequest(String getRequest) {
        this.getRequest = getRequest;
    }

    public String getUpdateRequest() {
        return updateRequest;
    }

    public void setUpdateRequest(String updateRequest) {
        this.updateRequest = updateRequest;
    }

    public String getDeleteRequest() {
        return deleteRequest;
    }

    public void setDeleteRequest(String deleteRequest) {
        this.deleteRequest = deleteRequest;
    }

    public String getFetch() {
        return fetch;
    }

    public void setFetch(String fetch) {
        this.fetch = fetch;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getUpdateSuccess() {
        return updateSuccess;
    }

    public void setUpdateSuccess(String updateSuccess) {
        this.updateSuccess = updateSuccess;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getDeleteSuccess() {
        return deleteSuccess;
    }

    public void setDeleteSuccess(String deleteSuccess) {
        this.deleteSuccess = deleteSuccess;
    }
}
