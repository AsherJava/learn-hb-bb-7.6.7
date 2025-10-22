/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.job.service;

import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstorePlanTaskDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;

public interface IMidstorePlanRegService {
    public void regPlanTaskJobFactory();

    public MidstoreResultObject regPlanTaskByMidstoreScheme(String var1) throws MidstoreException;

    public MidstoreResultObject regPlanTaskByMidstoreScheme(MidstoreSchemeDTO var1, MidstoreSchemeInfoDTO var2) throws MidstoreException;

    public MidstoreResultObject deletePlanTaskByMidstoreScheme(String var1) throws MidstoreException;

    public MidstoreResultObject stopPlanTaskByMidstoreScheme(String var1) throws MidstoreException;

    public boolean existPlanTask(String var1);

    public MidstorePlanTaskDTO queryPlanTask(String var1) throws MidstoreException;

    public void updatePlanTask(MidstorePlanTaskDTO var1) throws MidstoreException;

    public void insertPlanTask(MidstorePlanTaskDTO var1) throws MidstoreException;

    public String getMidstorePlanTaskGroup();

    public String getPlanTaskLogDetail(String var1) throws MidstoreException;
}

