/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.efdcdatacheck.env;

import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface GcFetchDataEnvContext {
    public OrgDO getOrg();

    public FormDefine getFormDefine();

    public FormulaSchemeDefine getFormulaSchemeDefine();

    public Set<String> getErrorFormKeySet();

    public ExpressionListing getCwFmlListing();

    public GcFormOperationInfo getFormOperationInfo();

    public Map<String, Set<String>> getReportId2ZbGuidMap();

    public ConcurrentHashMap<String, Object> getDimensionValueMap();
}

