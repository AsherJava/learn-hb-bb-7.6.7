/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.internal.definition;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.midstore.core.definition.IMidstoreSchemeInfo;
import nr.midstore.core.definition.common.ExcutePeriodType;
import nr.midstore.core.definition.common.PublishStateType;
import nr.midstore.core.definition.db.DBAnno;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_MIDSTORE_SCHEME_INFO")
public class MidstoreSchemeInfoDO
extends MidstoreDataDO
implements IMidstoreSchemeInfo {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="MDI_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="MDI_SCHEMEKEY")
    protected String schemeKey;
    @DBAnno.DBField(dbField="MDI_ORDER")
    protected String order;
    @DBAnno.DBField(dbField="MDI_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    protected Instant updateTime;
    @DBAnno.DBField(dbField="MDI_ALLORGDATA", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean allOrgData;
    @DBAnno.DBField(dbField="MDI_ALLORGFIELD", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean allOrgField;
    @DBAnno.DBField(dbField="MDI_PUBLISHSTATE", tranWith="transPublishStateType", dbType=Integer.class, appType=PublishStateType.class)
    protected PublishStateType publishState;
    @DBAnno.DBField(dbField="MDI_USEUPDATEORG", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean useUpdateOrg;
    @DBAnno.DBField(dbField="MDI_DELETEEMPTY", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean deleteEmpty;
    @DBAnno.DBField(dbField="MDI_USEPLANTASK", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean usePlanTask;
    @DBAnno.DBField(dbField="MDI_EXCUTEPEROIDTYPE", tranWith="transExcutePeriodType", dbType=Integer.class, appType=ExcutePeriodType.class)
    protected ExcutePeriodType excutePeriodType;
    @DBAnno.DBField(dbField="MDI_EXCUTEPEROID")
    protected String excutePeriod;
    @DBAnno.DBField(dbField="MDI_EXCUTEPLANKEY")
    protected String excutePlanKey;
    @DBAnno.DBField(dbField="MDI_EXCUTEPLANINFO")
    protected String excutePlanInfo;
    @DBAnno.DBField(dbField="MDI_AUTOCLEAN", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean autoClean;
    @DBAnno.DBField(dbField="MDI_CLEANMONTH")
    protected Integer cleanMonth;
    @DBAnno.DBField(dbField="MDI_CLEANPLANKEY")
    protected String cleanPlanKey;
    @DBAnno.DBField(dbField="MDI_CLEANPLANINFO")
    protected String cleanPlanInfo;
    @DBAnno.DBField(dbField="MDI_EXCHANGETASKKEY")
    protected String exchangeTaskKey;
    @DBAnno.DBField(dbField="MDI_DOCUMENTKEY")
    protected String documentKey;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public MidstoreSchemeInfoDO clone() {
        return (MidstoreSchemeInfoDO)super.clone();
    }

    @Override
    public String toString() {
        return "MidstoreSchemeDO{key='" + this.key + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MidstoreSchemeInfoDO that = (MidstoreSchemeInfoDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    @Override
    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static MidstoreSchemeInfoDO valueOf(MidstoreSchemeInfoDO o) {
        if (o == null) {
            return null;
        }
        MidstoreSchemeInfoDO t = new MidstoreSchemeInfoDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    @Override
    public boolean isAllOrgData() {
        return this.allOrgData;
    }

    @Override
    public boolean isAllOrgField() {
        return this.allOrgField;
    }

    @Override
    public PublishStateType getPublishState() {
        return this.publishState;
    }

    @Override
    public String getExcutePeriod() {
        return this.excutePeriod;
    }

    @Override
    public String getExcutePlanKey() {
        return this.excutePlanKey;
    }

    @Override
    public String getCleanPlanKey() {
        return this.cleanPlanKey;
    }

    @Override
    public boolean isAutoClean() {
        return this.autoClean;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public void setAllOrgData(boolean allOrgData) {
        this.allOrgData = allOrgData;
    }

    public void setAllOrgField(boolean allOrgField) {
        this.allOrgField = allOrgField;
    }

    public void setPublishState(PublishStateType publishState) {
        this.publishState = publishState;
    }

    public void setAutoClean(boolean autoClean) {
        this.autoClean = autoClean;
    }

    public void setExcutePeriod(String excutePeriod) {
        this.excutePeriod = excutePeriod;
    }

    public void setExcutePlanKey(String excutePlanKey) {
        this.excutePlanKey = excutePlanKey;
    }

    public void setCleanPlanKey(String cleanPlanKey) {
        this.cleanPlanKey = cleanPlanKey;
    }

    @Override
    public String getCleanPlanInfo() {
        return this.cleanPlanInfo;
    }

    @Override
    public String getExcutePlanInfo() {
        return this.excutePlanInfo;
    }

    public void setExcutePlanInfo(String excutePlanInfo) {
        this.excutePlanInfo = excutePlanInfo;
    }

    public void setCleanPlanInfo(String cleanPlanInfo) {
        this.cleanPlanInfo = cleanPlanInfo;
    }

    @Override
    public String getExchangeTaskKey() {
        return this.exchangeTaskKey;
    }

    public void setExchangeTaskKey(String exchangeTaskKey) {
        this.exchangeTaskKey = exchangeTaskKey;
    }

    @Override
    public ExcutePeriodType getExcutePeriodType() {
        return this.excutePeriodType;
    }

    public void setExcutePeriodType(ExcutePeriodType excutePeriodType) {
        this.excutePeriodType = excutePeriodType;
    }

    @Override
    public Integer getCleanMonth() {
        return this.cleanMonth;
    }

    public void setCleanMonth(Integer cleanMonth) {
        this.cleanMonth = cleanMonth;
    }

    @Override
    public String getDocumentKey() {
        return this.documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    @Override
    public boolean isUsePlanTask() {
        return this.usePlanTask;
    }

    public void setUsePlanTask(boolean usePlanTask) {
        this.usePlanTask = usePlanTask;
    }

    @Override
    public boolean isUseUpdateOrg() {
        return this.useUpdateOrg;
    }

    public void setUseUpdateOrg(boolean useUpdateOrg) {
        this.useUpdateOrg = useUpdateOrg;
    }

    @Override
    public boolean isDeleteEmpty() {
        return this.deleteEmpty;
    }

    public void setDeleteEmpty(boolean deleteEmpty) {
        this.deleteEmpty = deleteEmpty;
    }
}

