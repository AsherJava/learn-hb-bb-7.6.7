/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition;

import nr.midstore.core.definition.IMidstoreData;
import nr.midstore.core.definition.common.ExcutePeriodType;
import nr.midstore.core.definition.common.PublishStateType;

public interface IMidstoreSchemeInfo
extends IMidstoreData {
    public String getSchemeKey();

    public boolean isAllOrgData();

    public boolean isAllOrgField();

    public PublishStateType getPublishState();

    public ExcutePeriodType getExcutePeriodType();

    public boolean isUseUpdateOrg();

    public boolean isDeleteEmpty();

    public boolean isUsePlanTask();

    public String getExcutePeriod();

    public String getExcutePlanKey();

    public String getExcutePlanInfo();

    public boolean isAutoClean();

    public Integer getCleanMonth();

    public String getCleanPlanKey();

    public String getCleanPlanInfo();

    public String getExchangeTaskKey();

    public String getDocumentKey();
}

