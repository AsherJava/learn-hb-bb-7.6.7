/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.DataTableInfo
 *  com.jiuqi.dc.mappingscheme.impl.common.DateTableEnum
 *  com.jiuqi.dc.mappingscheme.impl.init.AbstractDataSchemeInit
 */
package com.jiuqi.bde.bizmodel.define.adaptor;

import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.DataTableInfo;
import com.jiuqi.dc.mappingscheme.impl.common.DateTableEnum;
import com.jiuqi.dc.mappingscheme.impl.init.AbstractDataSchemeInit;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBdeDataSchemeInit
extends AbstractDataSchemeInit {
    public List<DataTableInfo> getTableInfoList(DataSchemeDTO dto) {
        ArrayList<DataTableInfo> dataTableInfos = new ArrayList<DataTableInfo>();
        DataTableInfo mainCodeTableInfo = new DataTableInfo("BDE_TEMP_MAINCODE", DateTableEnum.SESSION_TEMPORARY);
        String mainCodeTableSql = "CREATE TABLE BDE_TEMP_MAINCODE (CODE VARCHAR(60));";
        mainCodeTableInfo.setSql(mainCodeTableSql);
        mainCodeTableInfo.setPrimaryFields("CODE");
        dataTableInfos.add(mainCodeTableInfo);
        DataTableInfo assistCodeTableInfo = new DataTableInfo("BDE_TEMP_ASSISTCODE", DateTableEnum.SESSION_TEMPORARY);
        String assistCodeSql = "CREATE TABLE BDE_TEMP_ASSISTCODE (CODE VARCHAR(60));";
        assistCodeTableInfo.setSql(assistCodeSql);
        assistCodeTableInfo.setPrimaryFields("CODE");
        dataTableInfos.add(assistCodeTableInfo);
        return dataTableInfos;
    }
}

