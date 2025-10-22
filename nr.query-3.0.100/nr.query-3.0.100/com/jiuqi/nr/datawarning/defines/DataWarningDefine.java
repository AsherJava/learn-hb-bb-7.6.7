/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.datawarning.defines;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.datawarning.defines.DataWarnigScop;
import com.jiuqi.nr.datawarning.defines.DataWarningDefineDeserializer;
import com.jiuqi.nr.datawarning.defines.DataWarningDefineSerializer;
import com.jiuqi.nr.datawarning.defines.DataWarningIdentify;
import com.jiuqi.nr.datawarning.defines.DataWarningProperties;
import com.jiuqi.nr.datawarning.defines.DataWarningType;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DBAnno.DBTable(dbTable="SYS_DATAWARNINGDEFINE")
@JsonSerialize(using=DataWarningDefineSerializer.class)
@JsonDeserialize(using=DataWarningDefineDeserializer.class)
public class DataWarningDefine {
    private static final Logger log = LoggerFactory.getLogger(DataWarningDefine.class);
    public static final String DATAWARNINGDEFINE_ID = "id";
    public static final String DATAWARNINGDEFINE_DATAWARNINGTYPE = "warnType";
    public static final String DATAWARNINGDEFINE_PROPERTIES = "properties";
    public static final String DATAWARNINGDEFINE_DATAWARNINGPROPERTIES = "property";
    public static final String DATAWARNINGDEFINE_KEY = "Key";
    public static final String DATAWARNINGDEFINE_DATAWARNINGIDENTIFY = "identify";
    public static final String DATAWARNINGDEFINE_DATAWARNIGSCOP = "scop";
    public static final String DATAWARNINGDEFINE_ORDER = "order";
    public static final String DATAWARNINGDEFINE_UPDATETIME = "updatetime";
    public static final String DATAWARNINGDEFINE_FIELDCODE = "fieldCode";
    public static final String DATAWARNINGDEFINE_FIELDSETTINGCODE = "fieldSettingCode";
    public static final String DATAWARNINGDEFINE_ISSAVE = "isSave";
    @DBAnno.DBField(dbField="DWN_ID", dbType=String.class, isPk=true)
    private String id;
    @DBAnno.DBField(dbField="DWN_WARNTYPE", tranWith="transDataWarningType", dbType=String.class, appType=DataWarningType.class)
    private DataWarningType warnType;
    @DBAnno.DBField(dbField="DWN_PROPERTIES", dbType=Clob.class)
    private String properties;
    private DataWarningProperties property;
    @DBAnno.DBField(dbField="DWN_KEY", dbType=String.class, isPk=false)
    private String Key;
    @DBAnno.DBField(dbField="DWN_IDENTIFY", tranWith="transDataWarningIdentify", dbType=String.class, appType=DataWarningIdentify.class)
    private DataWarningIdentify identify;
    @DBAnno.DBField(dbField="DWN_SCOP", tranWith="transDataWarnigScop", dbType=String.class, appType=DataWarnigScop.class)
    private DataWarnigScop scop;
    @DBAnno.DBField(dbField="DWN_ORDER", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updatetime;
    @DBAnno.DBField(dbField="DWN_FIELDCODE", dbType=String.class, isPk=false)
    private String fieldCode;
    @DBAnno.DBField(dbField="DWN_FIELDSETTINGCODE", dbType=String.class, isPk=false)
    private String fieldSettingCode;
    @DBAnno.DBField(dbField="DWN_ISSAVE", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean isSave;

    public Boolean getIsSave() {
        return this.isSave;
    }

    public void setIsSave(Boolean isSave) {
        this.isSave = isSave;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldSettingCode() {
        return this.fieldSettingCode;
    }

    public void setFieldSettingCode(String fieldSettingCode) {
        this.fieldSettingCode = fieldSettingCode;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataWarningType getWarnType() {
        return this.warnType;
    }

    public void setWarnType(DataWarningType warnType) {
        this.warnType = warnType;
    }

    public String getProperties() {
        return this.properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public DataWarningProperties getProperty() {
        if (this.property == null) {
            return this.convertJsonToDataWarningProperties(this.properties);
        }
        return this.property;
    }

    public String getPropertyStr() {
        return this.convertDataWarningPropertiesToJson();
    }

    public void setProperty(DataWarningProperties property) {
        this.property = property;
    }

    public void setPropertyStr(String propertyStr) {
        this.setProperty(this.convertJsonToDataWarningProperties(propertyStr));
    }

    private DataWarningProperties convertJsonToDataWarningProperties(String str) {
        try {
            if (StringUtil.isNullOrEmpty((String)str)) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            DataWarningProperties item = (DataWarningProperties)objectMapper.readValue(str, DataWarningProperties.class);
            return item;
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private String convertDataWarningPropertiesToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String propertyJson = objectMapper.writeValueAsString((Object)this.property);
            return propertyJson;
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public String getKey() {
        return this.Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public DataWarningIdentify getIdentify() {
        return this.identify;
    }

    public void setIdentify(DataWarningIdentify identify) {
        this.identify = identify;
    }

    public DataWarnigScop getScop() {
        return this.scop;
    }

    public void setScop(DataWarnigScop scop) {
        this.scop = scop;
    }
}

