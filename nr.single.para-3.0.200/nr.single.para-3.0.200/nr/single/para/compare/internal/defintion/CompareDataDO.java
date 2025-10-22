/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import nr.single.para.compare.definition.ICompareData;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.definition.exception.DBAnno;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_DATA")
public class CompareDataDO
implements ICompareData {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="CD_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="CD_INFOKEY")
    protected String infoKey;
    @DBAnno.DBField(dbField="CD_DATATYPE", tranWith="transCompareDataType", dbType=Integer.class, appType=CompareDataType.class)
    protected CompareDataType dataType;
    @DBAnno.DBField(dbField="CD_SINGLECODE")
    protected String singleCode;
    @DBAnno.DBField(dbField="CD_SINGLETITLE")
    protected String singleTitle;
    @DBAnno.DBField(dbField="CD_SINGLEDATA", tranWith="transClob", dbType=Clob.class, appType=String.class)
    protected String singleData;
    @DBAnno.DBField(dbField="CD_MATCHKEY")
    protected String matchKey;
    @DBAnno.DBField(dbField="CD_NETKEY")
    protected String netKey;
    @DBAnno.DBField(dbField="CD_NETCODE")
    protected String netCode;
    @DBAnno.DBField(dbField="CD_NETTITLE")
    protected String netTitle;
    @DBAnno.DBField(dbField="CD_NETDATA", tranWith="transClob", dbType=Clob.class, appType=String.class)
    protected String netData;
    @DBAnno.DBField(dbField="CD_UPDATETYPE", tranWith="transCompareUpdateType", dbType=Integer.class, appType=CompareUpdateType.class)
    protected CompareUpdateType updateType;
    @DBAnno.DBField(dbField="CD_CHANGETYPE", tranWith="transCompareChangeType", dbType=Integer.class, appType=CompareChangeType.class)
    protected CompareChangeType changeType;
    @DBAnno.DBField(dbField="CD_ORDER", isOrder=true)
    protected String order;
    @DBAnno.DBField(dbField="CD_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    protected Instant updateTime;
    protected Map<String, Object> datas = new HashMap<String, Object>();

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getStringValue(String code) {
        return (String)this.datas.get(code);
    }

    @Override
    public Integer getIntValue(String code) {
        return (Integer)this.datas.get(code);
    }

    public void setStringValue(String code, String value) {
        this.datas.put(code, value);
    }

    public void setStringValue(String code, Integer value) {
        this.datas.put(code, value);
    }

    @Override
    public Object getObjectValue(String code) {
        return this.datas.get(code);
    }

    public void setObjectValue(String code, Object value) {
        this.datas.put(code, value);
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getSingleCode() {
        return this.singleCode;
    }

    @Override
    public String getSingleTitle() {
        return this.singleTitle;
    }

    @Override
    public String getNetKey() {
        return this.netKey;
    }

    @Override
    public String getNetCode() {
        return this.netCode;
    }

    @Override
    public String getNetTitle() {
        return this.netTitle;
    }

    @Override
    public CompareDataType getDataType() {
        return this.dataType;
    }

    @Override
    public CompareChangeType getChangeType() {
        return this.changeType;
    }

    @Override
    public CompareUpdateType getUpdateType() {
        return this.updateType;
    }

    @Override
    public String getSingleData() {
        return this.singleData;
    }

    @Override
    public String getNetData() {
        return this.netData;
    }

    public void setDataType(CompareDataType dataType) {
        this.dataType = dataType;
    }

    public void setSingleCode(String singleCode) {
        this.singleCode = singleCode;
    }

    public void setSingleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
    }

    public void setSingleData(String singleData) {
        this.singleData = singleData;
    }

    public void setNetKey(String netKey) {
        this.netKey = netKey;
    }

    public void setNetCode(String netCode) {
        this.netCode = netCode;
    }

    public void setNetTitle(String netTitle) {
        this.netTitle = netTitle;
    }

    public void setNetData(String netData) {
        this.netData = netData;
    }

    public void setUpdateType(CompareUpdateType updateType) {
        this.updateType = updateType;
    }

    public void setChangeType(CompareChangeType changeType) {
        this.changeType = changeType;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getInfoKey() {
        return this.infoKey;
    }

    public void setInfoKey(String infoKey) {
        this.infoKey = infoKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public CompareDataDO clone() {
        try {
            return (CompareDataDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public String toString() {
        return "CompareDataDO{key='" + this.key + '\'' + ", infoKey='" + this.infoKey + '\'' + ", dataType=" + (Object)((Object)this.dataType) + ", singleCode='" + this.singleCode + '\'' + ", singleTitle='" + this.singleTitle + '\'' + ", singleData='" + this.singleData + '\'' + ", netKey='" + this.netKey + '\'' + ", netCode='" + this.netCode + '\'' + ", netTitle='" + this.netTitle + '\'' + ", netData='" + this.netData + ", updateType='" + (Object)((Object)this.updateType) + ", changeType='" + (Object)((Object)this.changeType) + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CompareDataDO that = (CompareDataDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static CompareDataDO valueOf(CompareDataDO o) {
        if (o == null) {
            return null;
        }
        CompareDataDO t = new CompareDataDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public String getMatchKey() {
        return this.matchKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }
}

