/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.intf.JoinType
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinItem
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.logic.internal.query;

import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.intf.JoinType;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinItem;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CkrCkdJoinProvider
implements ISqlJoinProvider {
    private static final Logger logger = LoggerFactory.getLogger(CkrCkdJoinProvider.class);
    private final DataModelService dataModelService;
    private final FormSchemeDefine formSchemeDefine;
    private final String batchId;
    private String dwTableName;
    private final SplitCheckTableHelper splitTableHelper;

    public CkrCkdJoinProvider(DataModelService dataModelService, SplitCheckTableHelper splitTableHelper, FormSchemeDefine formSchemeDefine, String batchId) {
        this.dataModelService = dataModelService;
        this.formSchemeDefine = formSchemeDefine;
        this.splitTableHelper = splitTableHelper;
        this.batchId = batchId;
    }

    public SqlJoinItem getSqlJoinItem(String srcTable, String desTable) {
        SqlJoinItem joinItem = null;
        try {
            String checkResultTableName = this.splitTableHelper.getCKRTableName(this.formSchemeDefine, this.batchId);
            String checkDesTableName = this.splitTableHelper.getSplitCKDTableName(this.formSchemeDefine);
            if (!srcTable.equals(checkResultTableName)) {
                return null;
            }
            TableModelDefine checkResultTable = this.dataModelService.getTableModelDefineByName(checkResultTableName);
            TableModelDefine checkDesTable = this.dataModelService.getTableModelDefineByName(checkDesTableName);
            if (desTable.equals(checkDesTableName)) {
                joinItem = new SqlJoinItem(checkResultTable.getCode(), checkDesTable.getCode());
                joinItem.setJoinType(JoinType.Left);
                SqlJoinOneItem recidItem = new SqlJoinOneItem("CKR_RECID", "CKD_RECID");
                joinItem.addJoinItem(recidItem);
            } else {
                joinItem = new SqlJoinItem(checkResultTable.getCode(), desTable);
                joinItem.setJoinType(JoinType.Left);
                SqlJoinOneItem dimItem = desTable.equals(this.dwTableName) ? new SqlJoinOneItem("MDCODE", "CODE") : new SqlJoinOneItem(desTable.toUpperCase(), "CODE");
                joinItem.addJoinItem(dimItem);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return joinItem;
    }

    public JoinType getDefaultJoinType() {
        return JoinType.Left;
    }

    public void setDwTableName(String dwTableName) {
        this.dwTableName = dwTableName;
    }
}

