/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgConst
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.org.impl.base;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgConst;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.util.base.GcOrgBaseModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Date;

public class GcOrgBaseParam {
    protected static final String DEFAULT_ORG_TYPE = "MD_ORG_CORPORATE";
    protected static final String ORG_BASE_TABLE = "MD_ORG";
    protected String org_type = "MD_ORG_CORPORATE";
    protected Date org_ver = null;
    protected OrgTypeVO type;
    protected OrgVersionVO version;
    private TableModelDefine table;
    private TableModelDefine orgTable;
    private GcAuthorityType authType = GcAuthorityType.NONE;

    public GcOrgBaseParam(GcOrgBaseModel model, String p_org_type, Date p_org_ver) throws Exception {
        this.setOrg_type(p_org_type);
        this.setOrg_ver(p_org_ver);
        this.type = model.getQueryService().getOrgType(this.org_type);
        this.loadVersion(model);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        this.table = dataModelService.getTableModelDefineByName(this.getType().getTable());
        this.orgTable = dataModelService.getTableModelDefineByName(ORG_BASE_TABLE);
    }

    public void loadVersion(GcOrgBaseModel model) {
        this.version = model.getQueryService().getOrgVersion(this.type, this.org_ver);
    }

    protected void setOrg_type(String org_type) {
        this.org_type = StringUtils.isEmpty((String)org_type) || "null".equalsIgnoreCase(org_type) ? DEFAULT_ORG_TYPE : org_type;
    }

    protected void setOrg_ver(Date org_ver) {
        this.org_ver = org_ver != null ? org_ver : GcOrgConst.ORG_VERDATE_MAX;
    }

    protected void setAuthType(GcAuthorityType authType) {
        this.authType = authType == null ? GcAuthorityType.NONE : authType;
    }

    public String getOrgtypeName() {
        return this.org_type;
    }

    public String getCacheKey() {
        return this.authType.getCacheKey();
    }

    public GcAuthorityType getAuthType() {
        return this.authType;
    }

    public Date getOrgNowDate() {
        return this.org_ver;
    }

    public OrgTypeVO getType() {
        return this.type;
    }

    public OrgVersionVO getVersion() {
        return this.version;
    }

    public Date getStartDate() {
        return this.version.getValidTime();
    }

    public Date getEndDate() {
        return this.version.getInvalidTime();
    }

    public TableModelDefine getOrgTable() {
        return this.orgTable;
    }

    public TableModelDefine getTable() {
        return this.table;
    }

    public String getLog(String logMsg) {
        return "(" + this.getOrgtypeName() + ";" + DateUtils.format((Date)this.org_ver) + ";" + this.getAuthType() + ";)" + logMsg;
    }

    public void initQueryParam(GcOrgParam param) {
    }
}

