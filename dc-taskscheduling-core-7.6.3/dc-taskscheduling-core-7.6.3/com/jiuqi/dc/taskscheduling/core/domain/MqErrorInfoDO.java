/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Table
 */
package com.jiuqi.dc.taskscheduling.core.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="GC_MQ_ERRORLOG")
public class MqErrorInfoDO
extends TenantDO {
    public static final String TABLENAME = "GC_MQ_ERRORLOG";
    private static final long serialVersionUID = 4061092029141232566L;
    @Column(name="ID")
    private String id;
    @Column(name="VER")
    private Long ver;
    @Column(name="MSGID")
    private String msgId;
    @Column(name="EXCHANGENAME")
    private String exchangeName;
    @Column(name="ROUTINGKEY")
    private String routingKey;
    @Column(name="QUEUENAME")
    private String queueName;
    @Column(name="LOGTIME")
    private Date logTime;
    @Column(name="MESSAGE")
    private String message;
    @Column(name="ERRORINFO")
    private String errorinfo;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return this.routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Date getLogTime() {
        return this.logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorinfo() {
        return this.errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }
}

