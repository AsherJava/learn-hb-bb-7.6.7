/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class TableData {
    private String tableKey;
    private String tableCode;
    private String tableName;
    private String tableTitle;
    private TableKind kind;
    private String description;
    private List<String> bizKeyFields = new ArrayList<String>();
    private String gatherType;

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableTitle() {
        return this.tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }

    public TableKind getKind() {
        return this.kind;
    }

    public void setKind(TableKind kind) {
        this.kind = kind;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getBizKeyFields() {
        return this.bizKeyFields;
    }

    public void setBizKeyFields(List<String> bizKeyFields) {
        this.bizKeyFields = bizKeyFields;
    }

    public String getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(String gatherType) {
        this.gatherType = gatherType;
    }

    public void init(TableDefine tableDefine) {
        this.tableKey = tableDefine.getKey();
        this.tableCode = tableDefine.getCode();
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        List deployInfos = dataSchemeService.getDeployInfoByDataTableKey(this.tableKey);
        this.tableName = ((DataFieldDeployInfo)deployInfos.get(0)).getTableName();
        this.tableTitle = tableDefine.getTitle();
        this.description = tableDefine.getDescription();
        Object[] bizKeyFieldsID = tableDefine.getBizKeyFieldsID();
        if (!ArrayUtils.isEmpty((Object[])bizKeyFieldsID)) {
            for (Object bizKey : bizKeyFieldsID) {
                this.bizKeyFields.add((String)bizKey);
            }
        }
        this.gatherType = tableDefine.getGatherType().name();
    }
}

