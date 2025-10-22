/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 */
package com.jiuqi.nr.data.estimation.sub.database.entity;

import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabaseDefine;
import com.jiuqi.nr.data.estimation.sub.database.intf.ICopiedTableGenerator;
import com.jiuqi.nr.data.estimation.sub.database.intf.IMakeSubDatabaseParam;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DataSchemeSubDatabase
implements IDataSchemeSubDatabase {
    private ICopiedTableGenerator tableGenerator;
    private IDataSchemeSubDatabaseDefine subDatabaseDefine;
    private List<String> otherPrimaryColumnCodes = new ArrayList<String>();

    public DataSchemeSubDatabase(IDataSchemeSubDatabaseDefine subDatabaseDefine, IMakeSubDatabaseParam makePara) {
        this.subDatabaseDefine = subDatabaseDefine;
        this.tableGenerator = makePara.getCopiedTableGenerator(subDatabaseDefine.getDataSchemeKey());
        List<DesignColumnModelDefine> otherPrimaryColumns = makePara.getOtherPrimaryColumns(subDatabaseDefine.getDataSchemeKey());
        if (otherPrimaryColumns != null) {
            this.otherPrimaryColumnCodes = otherPrimaryColumns.stream().map(IModelDefineItem::getCode).collect(Collectors.toList());
        }
    }

    @Override
    public String getDataSchemeKey() {
        return this.subDatabaseDefine.getDataSchemeKey();
    }

    @Override
    public String getDatabaseCode() {
        return this.subDatabaseDefine.getDatabaseCode();
    }

    @Override
    public String getDatabaseTitle() {
        return this.subDatabaseDefine.getDatabaseTitle();
    }

    @Override
    public Date getCreateTime() {
        return this.subDatabaseDefine.getCreateTime();
    }

    @Override
    public String getSubTableCode(String oriTableCode) {
        return this.tableGenerator.madeCopiedTableCode(oriTableCode);
    }

    @Override
    public String getSubColumnCode(String oriTableCode, String oriColumnCode) {
        return this.tableGenerator.madeCopiedColumnCode(oriColumnCode);
    }

    @Override
    public List<String> getOtherPrimaryColumnCodes(String oriTableCode) {
        return this.otherPrimaryColumnCodes;
    }
}

