/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelDefineImpl
 */
package com.jiuqi.bde.fetch.impl.result.util;

import com.jiuqi.bde.fetch.impl.result.entity.TableIndexVO;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignTableModelDefineImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchResultTableUtil {
    public static String getTableName(String tableName, int index) {
        return String.format("%1$s_%2$d", tableName, index);
    }

    public static DesignTableModelDefine getResultTable(String tableName, String title) {
        DesignTableModelDefineImpl designTableModelDefine = new DesignTableModelDefineImpl();
        designTableModelDefine.setCode(tableName);
        designTableModelDefine.setName(tableName);
        designTableModelDefine.setTitle(title);
        designTableModelDefine.setType(TableModelType.DATA);
        designTableModelDefine.setSupportI18n(false);
        designTableModelDefine.setKind(TableModelKind.DEFAULT);
        designTableModelDefine.setID(UUIDUtils.newUUIDStr());
        designTableModelDefine.setCatalogID("11000000-0000-0000-0000-000000000002");
        designTableModelDefine.setUpdateTime(new Date());
        return designTableModelDefine;
    }

    public static List<DesignColumnModelDefine> getResultMappingColumn(String tableId) {
        ArrayList<DesignColumnModelDefine> designColumnModelDefines = new ArrayList<DesignColumnModelDefine>();
        DesignColumnModelDefineImpl routeNum = new DesignColumnModelDefineImpl();
        routeNum.setCode("ROUTENUM");
        routeNum.setName("ROUTENUM");
        routeNum.setColumnType(ColumnModelType.INTEGER);
        routeNum.setPrecision(3);
        routeNum.setTableID(tableId);
        routeNum.setID(UUIDUtils.newUUIDStr());
        routeNum.setKind(ColumnModelKind.DEFAULT);
        routeNum.setNullAble(false);
        routeNum.setTitle("\u8def\u7531\u7f16\u53f7");
        designColumnModelDefines.add((DesignColumnModelDefine)routeNum);
        DesignColumnModelDefineImpl routeStatus = new DesignColumnModelDefineImpl();
        routeStatus.setCode("ROUTESTATUS");
        routeStatus.setName("ROUTESTATUS");
        routeStatus.setColumnType(ColumnModelType.INTEGER);
        routeStatus.setPrecision(1);
        routeStatus.setTableID(tableId);
        routeStatus.setID(UUIDUtils.newUUIDStr());
        routeStatus.setKind(ColumnModelKind.DEFAULT);
        routeStatus.setNullAble(false);
        routeStatus.setTitle("\u8def\u7531\u72b6\u6001");
        designColumnModelDefines.add((DesignColumnModelDefine)routeStatus);
        return designColumnModelDefines;
    }

    public static List<TableIndexVO> getResultMappingIndexList() {
        ArrayList<TableIndexVO> tableIndexVOS = new ArrayList<TableIndexVO>();
        TableIndexVO resultMappingFid = new TableIndexVO();
        resultMappingFid.setIndexName("IDX_RESULT_MAPPING_ROUTENUM");
        resultMappingFid.setIndexType(IndexModelType.NORMAL);
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("ROUTENUM");
        resultMappingFid.setFields(fields);
        tableIndexVOS.add(resultMappingFid);
        return tableIndexVOS;
    }
}

