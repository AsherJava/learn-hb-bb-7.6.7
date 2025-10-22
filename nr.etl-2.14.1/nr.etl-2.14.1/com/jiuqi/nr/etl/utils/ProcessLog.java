/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.JAXBElement
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 *  javax.xml.bind.annotation.XmlElementRef
 *  javax.xml.bind.annotation.XmlType
 */
package com.jiuqi.nr.etl.utils;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ProcessLog", namespace="http://service.server.etl.jiuqi.com", propOrder={"errorCode", "guid", "level", "message", "source", "stackTrace", "time"})
public class ProcessLog {
    @XmlElementRef(name="errorCode", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> errorCode;
    @XmlElementRef(name="guid", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> guid;
    protected Integer level;
    @XmlElementRef(name="message", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> message;
    @XmlElementRef(name="source", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<String> source;
    @XmlElementRef(name="stackTrace", namespace="http://service.server.etl.jiuqi.com", type=JAXBElement.class)
    protected JAXBElement<byte[]> stackTrace;
    protected Long time;

    public JAXBElement<String> getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(JAXBElement<String> value) {
        this.errorCode = value;
    }

    public JAXBElement<String> getGuid() {
        return this.guid;
    }

    public void setGuid(JAXBElement<String> value) {
        this.guid = value;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer value) {
        this.level = value;
    }

    public JAXBElement<String> getMessage() {
        return this.message;
    }

    public void setMessage(JAXBElement<String> value) {
        this.message = value;
    }

    public JAXBElement<String> getSource() {
        return this.source;
    }

    public void setSource(JAXBElement<String> value) {
        this.source = value;
    }

    public JAXBElement<byte[]> getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(JAXBElement<byte[]> value) {
        this.stackTrace = value;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long value) {
        this.time = value;
    }
}

