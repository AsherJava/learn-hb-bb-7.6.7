/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.midstore.core.definition.common.ExcutePeriodType
 *  com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType
 */
package nr.midstore2.design.domain;

import com.jiuqi.nvwa.midstore.core.definition.common.ExcutePeriodType;
import com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType;
import nr.midstore2.design.domain.BaseMidstoreDTO;

public class SchemeInfoDTO
extends BaseMidstoreDTO {
    private String schemeKey;
    private boolean allOrgData;
    private boolean allOrgField;
    private PublishStateType publishState;
    private boolean autoClean;
    private boolean useUpdateOrg;
    private boolean usePlanTask;
    private boolean deleteEmpty;
    private ExcutePeriodType excutePeriodType;
    private String excutePeriod;
    private String excutePlanKey;
    private String excutePlanInfo;
    private Integer cleanMonth;
    private String cleanPlanKey;
    private String cleanPlanInfo;
    private String exchangeTaskKey;
    private String documentKey;

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public boolean isAllOrgData() {
        return this.allOrgData;
    }

    public void setAllOrgData(boolean allOrgData) {
        this.allOrgData = allOrgData;
    }

    public boolean isAllOrgField() {
        return this.allOrgField;
    }

    public void setAllOrgField(boolean allOrgField) {
        this.allOrgField = allOrgField;
    }

    public PublishStateType getPublishState() {
        return this.publishState;
    }

    public void setPublishState(PublishStateType publishState) {
        this.publishState = publishState;
    }

    public boolean isAutoClean() {
        return this.autoClean;
    }

    public void setAutoClean(boolean autoClean) {
        this.autoClean = autoClean;
    }

    public String getExcutePeriod() {
        return this.excutePeriod;
    }

    public void setExcutePeriod(String excutePeriod) {
        this.excutePeriod = excutePeriod;
    }

    public String getExcutePlanKey() {
        return this.excutePlanKey;
    }

    public void setExcutePlanKey(String excutePlanKey) {
        this.excutePlanKey = excutePlanKey;
    }

    public String getCleanPlanKey() {
        return this.cleanPlanKey;
    }

    public void setCleanPlanKey(String cleanPlanKey) {
        this.cleanPlanKey = cleanPlanKey;
    }

    public String getExcutePlanInfo() {
        return this.excutePlanInfo;
    }

    public void setExcutePlanInfo(String excutePlanInfo) {
        this.excutePlanInfo = excutePlanInfo;
    }

    public String getCleanPlanInfo() {
        return this.cleanPlanInfo;
    }

    public void setCleanPlanInfo(String cleanPlanInfo) {
        this.cleanPlanInfo = cleanPlanInfo;
    }

    public String getExchangeTaskKey() {
        return this.exchangeTaskKey;
    }

    public void setExchangeTaskKey(String exchangeTaskKey) {
        this.exchangeTaskKey = exchangeTaskKey;
    }

    public ExcutePeriodType getExcutePeriodType() {
        return this.excutePeriodType;
    }

    public void setExcutePeriodType(ExcutePeriodType excutePeriodType) {
        this.excutePeriodType = excutePeriodType;
    }

    public Integer getCleanMonth() {
        return this.cleanMonth;
    }

    public void setCleanMonth(Integer cleanMonth) {
        this.cleanMonth = cleanMonth;
    }

    public String getDocumentKey() {
        return this.documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public boolean isUsePlanTask() {
        return this.usePlanTask;
    }

    public void setUsePlanTask(boolean usePlanTask) {
        this.usePlanTask = usePlanTask;
    }

    public boolean isUseUpdateOrg() {
        return this.useUpdateOrg;
    }

    public void setUseUpdateOrg(boolean useUpdateOrg) {
        this.useUpdateOrg = useUpdateOrg;
    }

    public boolean isDeleteEmpty() {
        return this.deleteEmpty;
    }

    public void setDeleteEmpty(boolean deleteEmpty) {
        this.deleteEmpty = deleteEmpty;
    }
}

