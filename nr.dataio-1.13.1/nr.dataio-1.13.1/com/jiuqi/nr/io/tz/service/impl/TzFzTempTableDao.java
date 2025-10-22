/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.bean.TempIndex;
import com.jiuqi.nr.io.tz.service.impl.BaseTmpTableCreateDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class TzFzTempTableDao
extends BaseTmpTableCreateDao {
    @Override
    protected Set<TempIndex> getIndexFields(DataSchemeTmpTable tmpTable) {
        HashSet<TempIndex> tempIndices = new HashSet<TempIndex>();
        TempIndex sbIndex = new TempIndex();
        sbIndex.setFiledNames(Collections.singletonList("SBID"));
        tempIndices.add(sbIndex);
        TempIndex sbOptIndex = new TempIndex();
        sbOptIndex.setFiledNames(Arrays.asList("SBID", "OPT"));
        tempIndices.add(sbOptIndex);
        TempIndex optIndex = new TempIndex();
        optIndex.setFiledNames(Collections.singletonList("OPT"));
        tempIndices.add(optIndex);
        TempIndex tableDimIndex = new TempIndex();
        tableDimIndex.setFiledNames(tmpTable.getTableDimDeploys().stream().map(DataFieldDeployInfo::getFieldName).collect(Collectors.toList()));
        tempIndices.add(tableDimIndex);
        if (tmpTable.isFull() || tmpTable.getPeriodicDeploys().isEmpty()) {
            return tempIndices;
        }
        TempIndex bizKeyOrderOptIndex = new TempIndex();
        bizKeyOrderOptIndex.setFiledNames(Arrays.asList("BIZKEYORDER", "RPT_OPT"));
        tempIndices.add(bizKeyOrderOptIndex);
        TempIndex rptOptIndex = new TempIndex();
        rptOptIndex.setFiledNames(Collections.singletonList("RPT_OPT"));
        tempIndices.add(rptOptIndex);
        return tempIndices;
    }

    @Override
    protected List<String> pkFieldName(DataSchemeTmpTable tmpTable) {
        ArrayList<String> pk = new ArrayList<String>();
        pk.add("MDCODE");
        Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys()).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).forEach(pk::add);
        pk.add("ORDINAL");
        return pk;
    }

    @Override
    protected List<LogicField> buildExtTmpFields(DataSchemeTmpTable tmpTable) {
        ArrayList<LogicField> list = new ArrayList<LogicField>();
        LogicField ordinal = new LogicField();
        ordinal.setFieldName("ORDINAL");
        ordinal.setDataType(6);
        ordinal.setSize(10);
        list.add(ordinal);
        LogicField mdOrdinal = new LogicField();
        mdOrdinal.setFieldName("MD_ORDINAL");
        mdOrdinal.setDataType(5);
        mdOrdinal.setSize(10);
        mdOrdinal.setScale(10);
        mdOrdinal.setDefaultValue("0");
        mdOrdinal.setNullable(true);
        list.add(mdOrdinal);
        LogicField sbid = new LogicField();
        sbid.setFieldName("SBID");
        sbid.setSize(50);
        sbid.setDataType(6);
        sbid.setNullable(true);
        list.add(sbid);
        LogicField opt = new LogicField();
        opt.setFieldName("OPT");
        opt.setScale(1);
        opt.setDataType(5);
        opt.setNullable(true);
        opt.setDefaultValue("0");
        list.add(opt);
        if (tmpTable.isFull() || tmpTable.getPeriodicDeploys().isEmpty()) {
            return list;
        }
        LogicField rptBizKeyOrder = new LogicField();
        rptBizKeyOrder.setFieldName("BIZKEYORDER");
        rptBizKeyOrder.setSize(50);
        rptBizKeyOrder.setDataType(6);
        rptBizKeyOrder.setNullable(true);
        list.add(rptBizKeyOrder);
        LogicField rptOpt = new LogicField();
        rptOpt.setFieldName("RPT_OPT");
        rptOpt.setScale(1);
        rptOpt.setDataType(5);
        rptOpt.setNullable(true);
        rptOpt.setDefaultValue("0");
        list.add(rptOpt);
        return list;
    }
}

