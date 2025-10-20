/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  javax.persistence.Column
 */
package com.jiuqi.dc.taskscheduling.core.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import javax.persistence.Column;

@DBTable(name="GC_MQ_ERRORLOG", title="MQ\u9519\u8bef\u65e5\u5fd7\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class MqErrorLogEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = 5538514978711950268L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true, order=1)
    private Long ver;
    @DBColumn(nameInDB="MSGID", title="\u6d88\u606fID", dbType=DBColumn.DBType.NVarchar, length=36, order=2)
    private String msgId;
    @DBColumn(nameInDB="EXCHANGENAME", title="\u4ea4\u6362\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=50, order=3)
    private String exchangeName;
    @DBColumn(nameInDB="ROUTINGKEY", title="\u8def\u7531Key", dbType=DBColumn.DBType.NVarchar, length=50, order=4)
    private String routingKey;
    @DBColumn(nameInDB="QUEUENAME", title="\u961f\u5217\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=50, order=5)
    private String queueName;
    @DBColumn(nameInDB="LOGTIME", title="\u65e5\u5fd7\u8bb0\u5f55\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, isRequired=true, order=6)
    private String logTime;
    @DBColumn(nameInDB="MESSAGE", title="\u6d88\u606f\u5185\u5bb9", dbType=DBColumn.DBType.Text, isRequired=true, order=7)
    private String message;
    @DBColumn(nameInDB="ERRORINFO", title="\u9519\u8bef\u65e5\u5fd7", dbType=DBColumn.DBType.Text, isRequired=true, order=8)
    private String errorInfo;

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

    public String getLogTime() {
        return this.logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}

