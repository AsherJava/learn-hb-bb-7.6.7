/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.va.domain.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ServiceProvidersEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value="ServiceProviders")
    private List<ServiceProvider> serviceProviders;

    public List<ServiceProvider> getServiceProviders() {
        return this.serviceProviders;
    }

    public void setServiceProviders(List<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

    public static class Filed
    implements Serializable {
        private static final long serialVersionUID = 1L;
        @JsonProperty(value="code")
        private String code;
        @JsonProperty(value="name")
        private String name;
        @JsonProperty(value="value")
        private String value;
        @JsonProperty(value="remark")
        private String remark;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class Interface
    implements Serializable {
        private static final long serialVersionUID = 1L;
        @JsonProperty(value="code")
        private String code;
        @JsonProperty(value="title")
        private String title;
        @JsonProperty(value="startflag")
        private boolean startflag;
        @JsonProperty(value="fields")
        private List<Filed> fields;
        @JsonProperty(value="ordernum")
        private BigDecimal ordernum;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isStartflag() {
            return this.startflag;
        }

        public void setStartflag(boolean startflag) {
            this.startflag = startflag;
        }

        public List<Filed> getFields() {
            return this.fields;
        }

        public void setFields(List<Filed> fields) {
            this.fields = fields;
        }

        public BigDecimal getOrdernum() {
            return this.ordernum;
        }

        public void setOrdernum(BigDecimal ordernum) {
            this.ordernum = ordernum;
        }
    }

    public static class Generic
    implements Serializable {
        private static final long serialVersionUID = 1L;
        @JsonProperty(value="code")
        private String code;
        @JsonProperty(value="title")
        private String title;
        @JsonProperty(value="value")
        private String value;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ServiceProvider
    implements Serializable {
        private static final long serialVersionUID = 1L;
        @JsonProperty(value="code")
        private String code;
        @JsonProperty(value="title")
        private String title;
        @JsonProperty(value="generic")
        private List<Generic> generic;
        @JsonProperty(value="Interfaces")
        private List<Interface> interfaces;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Interface> getInterfaces() {
            return this.interfaces;
        }

        public void setInterfaces(List<Interface> interfaces) {
            this.interfaces = interfaces;
        }

        public List<Generic> getGeneric() {
            return this.generic;
        }

        public void setGeneric(List<Generic> generic) {
            this.generic = generic;
        }
    }
}

