/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 */
package com.jiuqi.gcreport.journalsingle.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class GcNpUtil {
    private static GcNpUtil tool;

    private GcNpUtil() {
    }

    public static GcNpUtil getInstance() {
        if (null == tool) {
            tool = new GcNpUtil();
        }
        return tool;
    }

    public String getFieldKey(String tableNameAndFieldCode) {
        if (StringUtils.isEmpty(tableNameAndFieldCode)) {
            return null;
        }
        String regex = "(.*)\\[(.*)\\]";
        Pattern pattern = Pattern.compile(regex);
        int groupCount = 2;
        Matcher matcher = pattern.matcher(tableNameAndFieldCode);
        if (!matcher.find() || matcher.groupCount() < groupCount) {
            return null;
        }
        try {
            TableDefine tableDefine = ((IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class)).queryTableDefineByCode(matcher.group(1));
            if (null == tableDefine) {
                return null;
            }
            FieldDefine fieldDefine = ((IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class)).queryFieldByCodeInTable(matcher.group(2), tableDefine.getKey());
            if (null != fieldDefine) {
                return fieldDefine.getKey();
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u6307\u6807\u3010" + tableNameAndFieldCode + "\u3011\u4fe1\u606f\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
        return null;
    }

    public List<FieldDefine> listFieldDefines(String formKey) {
        RunTimeAuthViewController runTimeAuthViewController = (RunTimeAuthViewController)SpringContextUtils.getBean(RunTimeAuthViewController.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        ArrayList<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
        try {
            List allRegionDefines = runTimeAuthViewController.getAllRegionsInForm(formKey);
            if (allRegionDefines == null) {
                return fieldDefines;
            }
            for (DataRegionDefine regionDefine : allRegionDefines) {
                List allLinkDefines = runTimeAuthViewController.getAllLinksInRegion(regionDefine.getKey());
                if (allLinkDefines == null) continue;
                for (DataLinkDefine linkDefine : allLinkDefines) {
                    FieldDefine fieldDefine;
                    if (linkDefine.getPosX() < regionDefine.getRegionLeft() || linkDefine.getPosY() < regionDefine.getRegionTop() || linkDefine.getPosX() > regionDefine.getRegionRight() || linkDefine.getPosY() > regionDefine.getRegionBottom() || linkDefine.getLinkExpression() == null || (fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(linkDefine.getLinkExpression())) == null) continue;
                    fieldDefines.add(fieldDefine);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return fieldDefines;
    }

    public Set<String> getFieldKeySet(String formKey) {
        List<FieldDefine> fieldDefines = this.listFieldDefines(formKey);
        return fieldDefines.stream().map(item -> item.getKey()).collect(Collectors.toSet());
    }

    public Map<String, DimensionValue> buildDimensionMap(String taskId, String currencyId, String periodStr, String orgTypeId, String orgId, String adjustStr) {
        ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>(16);
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName("DATATIME");
        dimensionValue.setValue(periodStr);
        dimensionSetMap.put("DATATIME", dimensionValue);
        DimensionValue dimensionValue2 = new DimensionValue();
        dimensionValue2.setName("MD_ORG");
        dimensionValue2.setValue(orgId);
        dimensionSetMap.put("MD_ORG", dimensionValue2);
        DimensionValue dimensionValue3 = new DimensionValue();
        dimensionValue3.setName("MD_GCORGTYPE");
        dimensionValue3.setValue(orgTypeId);
        dimensionSetMap.put("MD_GCORGTYPE", dimensionValue3);
        DimensionValue dimensionValue4 = new DimensionValue();
        dimensionValue4.setName("ADJUST");
        dimensionValue4.setValue(adjustStr);
        dimensionSetMap.put("ADJUST", dimensionValue4);
        DimensionUtils.dimensionMapSetAdjType((String)taskId, dimensionSetMap);
        DimensionValue dimensionValue5 = new DimensionValue();
        dimensionValue5.setName("MD_CURRENCY");
        dimensionValue5.setValue(currencyId);
        dimensionSetMap.put("MD_CURRENCY", dimensionValue5);
        return dimensionSetMap;
    }
}

