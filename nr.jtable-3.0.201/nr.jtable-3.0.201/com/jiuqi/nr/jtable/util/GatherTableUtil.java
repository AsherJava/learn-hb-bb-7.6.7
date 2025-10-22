/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.impl.common.DefinitionHelper
 *  com.jiuqi.nr.data.engine.gather.GatherTableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.impl.common.DefinitionHelper;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatherTableUtil {
    private static final Logger logger = LoggerFactory.getLogger(GatherTableUtil.class);

    public static List<GatherTableDefine> getGatherTables(IRunTimeViewController runtimeView, IDataDefinitionRuntimeController dataDefinitionRuntimeController, FormDefine form) {
        ArrayList<GatherTableDefine> gatherTableDefineList = new ArrayList<GatherTableDefine>();
        if (!form.getIsGather()) {
            return gatherTableDefineList;
        }
        try {
            List allRegionsInForm = runtimeView.getAllRegionsInForm(form.getKey());
            for (DataRegionDefine region : allRegionsInForm) {
                FieldDefine orderFieldDefine;
                HashMap tableFieldMap = new HashMap(100);
                boolean isFixed = region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
                List allLinks = runtimeView.getAllLinksInRegion(region.getKey());
                if (region.getInputOrderFieldKey() != null && (orderFieldDefine = dataDefinitionRuntimeController.queryFieldDefine(region.getInputOrderFieldKey())) != null) {
                    List<FieldDefine> list;
                    if (tableFieldMap.containsKey(orderFieldDefine.getOwnerTableKey())) {
                        list = (List)tableFieldMap.get(orderFieldDefine.getOwnerTableKey());
                        list.add(orderFieldDefine);
                    } else {
                        list = new ArrayList<FieldDefine>();
                        list.add(orderFieldDefine);
                        tableFieldMap.put(orderFieldDefine.getOwnerTableKey(), list);
                    }
                }
                for (DataLinkDefine linkDefine : allLinks) {
                    List<FieldDefine> list;
                    FieldDefine fieldDefine;
                    if (linkDefine.getPosX() < region.getRegionLeft() || linkDefine.getPosY() < region.getRegionTop() || linkDefine.getPosX() > region.getRegionRight() || linkDefine.getPosY() > region.getRegionBottom() || linkDefine.getLinkExpression() == null || linkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FIELD || (fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(linkDefine.getLinkExpression())) == null) continue;
                    if (tableFieldMap.containsKey(fieldDefine.getOwnerTableKey())) {
                        list = (List)tableFieldMap.get(fieldDefine.getOwnerTableKey());
                        list.add(fieldDefine);
                        continue;
                    }
                    list = new ArrayList<FieldDefine>();
                    list.add(fieldDefine);
                    tableFieldMap.put(fieldDefine.getOwnerTableKey(), list);
                }
                for (String tablekey : tableFieldMap.keySet()) {
                    List list = (List)tableFieldMap.get(tablekey);
                    TableDefine tableDefine = dataDefinitionRuntimeController.queryTableDefine(tablekey);
                    GatherTableDefine gatherTableDefine = new GatherTableDefine(DefinitionHelper.toDataTable((TableDefine)tableDefine), isFixed);
                    ArrayList<DataField> dataFieldList = new ArrayList<DataField>();
                    for (FieldDefine fieldDefine : list) {
                        dataFieldList.add(DefinitionHelper.toDataField((FieldDefine)fieldDefine));
                    }
                    gatherTableDefine.setGatherFields(dataFieldList);
                    gatherTableDefineList.add(gatherTableDefine);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return gatherTableDefineList;
    }
}

