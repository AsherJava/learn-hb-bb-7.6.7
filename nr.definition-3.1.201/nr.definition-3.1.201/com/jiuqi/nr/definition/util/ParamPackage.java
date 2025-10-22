/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineData;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineData;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateDefineData;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateSchemeDefineData;
import com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineData;
import com.jiuqi.nr.definition.internal.impl.DesignTaskDefineData;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class ParamPackage {
    public static DesignTaskDefine packageTask(DesignTaskDefine taskDefine, DesignBigDataService designBigDataService) {
        if (null == taskDefine) {
            return null;
        }
        return new DesignTaskDefineData(taskDefine, designBigDataService);
    }

    public static List<DesignTaskDefine> packageTask(List<DesignTaskDefine> taskDefines, DesignBigDataService designBigDataService) {
        if (null == taskDefines || taskDefines.size() == 0) {
            return taskDefines;
        }
        List<DesignTaskDefine> dataList = taskDefines.stream().map(e -> new DesignTaskDefineData((DesignTaskDefine)e, designBigDataService)).collect(Collectors.toList());
        return dataList;
    }

    public static DesignFormDefine packageForm(DesignFormDefine formDefine, DesignBigDataService designBigDataService) {
        if (null == formDefine) {
            return null;
        }
        return new DesignFormDefineData(formDefine, designBigDataService);
    }

    public static List<DesignFormDefine> packageForm(List<DesignFormDefine> formDefines, DesignBigDataService designBigDataService) {
        if (null == formDefines || formDefines.size() == 0) {
            return formDefines;
        }
        List<DesignFormDefine> dataList = formDefines.stream().map(e -> new DesignFormDefineData((DesignFormDefine)e, designBigDataService)).collect(Collectors.toList());
        return dataList;
    }

    public static DesignRegionSettingDefine packageRegionSetting(DesignRegionSettingDefine regionSettingDefine, DesignBigDataService designBigDataService) {
        if (null == regionSettingDefine) {
            return null;
        }
        return new DesignRegionSettingDefineData(regionSettingDefine, designBigDataService);
    }

    public static List<DesignRegionSettingDefine> packageRegionSetting(List<DesignRegionSettingDefine> regionSettingDefines, DesignBigDataService designBigDataService) {
        if (null == regionSettingDefines || regionSettingDefines.size() == 0) {
            return regionSettingDefines;
        }
        List<DesignRegionSettingDefine> dataList = regionSettingDefines.stream().map(e -> new DesignRegionSettingDefineData((DesignRegionSettingDefine)e, designBigDataService)).collect(Collectors.toList());
        return dataList;
    }

    public static DesignDataLinkDefine packageDataLink(DesignDataLinkDefine dataLinkDefine, DesignBigDataService designBigDataService) {
        if (null == dataLinkDefine) {
            return null;
        }
        return new DesignDataLinkDefineData(dataLinkDefine, designBigDataService);
    }

    public static List<DesignDataLinkDefine> packageDataLink(List<DesignDataLinkDefine> dataLinkDefines, DesignBigDataService designBigDataService) {
        if (null == dataLinkDefines || dataLinkDefines.size() == 0) {
            return dataLinkDefines;
        }
        List<DesignDataLinkDefine> dataList = dataLinkDefines.stream().map(e -> {
            if (e instanceof DesignDataLinkDefineData) {
                return e;
            }
            return new DesignDataLinkDefineData((DesignDataLinkDefine)e, designBigDataService);
        }).collect(Collectors.toList());
        return dataList;
    }

    public static DesignPrintTemplateSchemeDefine packagePrintScheme(DesignPrintTemplateSchemeDefine printTemplateSchemeDefine, DesignBigDataService designBigDataService) {
        if (null == printTemplateSchemeDefine) {
            return null;
        }
        return new DesignPrintTemplateSchemeDefineData(printTemplateSchemeDefine, designBigDataService);
    }

    public static List<DesignPrintTemplateSchemeDefine> packagePrintScheme(List<DesignPrintTemplateSchemeDefine> printTemplateSchemeDefines, DesignBigDataService designBigDataService) {
        if (null == printTemplateSchemeDefines || printTemplateSchemeDefines.size() == 0) {
            return printTemplateSchemeDefines;
        }
        List<DesignPrintTemplateSchemeDefine> dataList = printTemplateSchemeDefines.stream().map(e -> new DesignPrintTemplateSchemeDefineData((DesignPrintTemplateSchemeDefine)e, designBigDataService)).collect(Collectors.toList());
        return dataList;
    }

    public static DesignPrintTemplateDefine packagePrintTemplate(DesignPrintTemplateDefine printTemplateDefine, DesignBigDataService designBigDataService) {
        if (null == printTemplateDefine) {
            return null;
        }
        return new DesignPrintTemplateDefineData(printTemplateDefine, designBigDataService);
    }

    public static List<DesignPrintTemplateDefine> packagePrintTemplate(List<DesignPrintTemplateDefine> printTemplateDefines, DesignBigDataService designBigDataService) {
        if (null == printTemplateDefines || printTemplateDefines.size() == 0) {
            return printTemplateDefines;
        }
        List<DesignPrintTemplateDefine> dataList = printTemplateDefines.stream().map(e -> new DesignPrintTemplateDefineData((DesignPrintTemplateDefine)e, designBigDataService)).collect(Collectors.toList());
        return dataList;
    }

    public static DesignPrintTemplateDefine convertPrintTemplate(DesignPrintTemplateDefine designPrintTemplateDefine) {
        if (designPrintTemplateDefine instanceof DesignPrintTemplateDefineData) {
            DesignPrintTemplateDefineData convertData = (DesignPrintTemplateDefineData)designPrintTemplateDefine;
            DesignPrintTemplateDefine designPrintTemplateSchemeDefine = convertData.getDesignPrintTemplateDefine();
            if (null == designPrintTemplateSchemeDefine.getLabelData()) {
                designPrintTemplateSchemeDefine.setLabelData(convertData.getLabelData());
            }
            if (null == designPrintTemplateSchemeDefine.getTemplateData()) {
                designPrintTemplateSchemeDefine.setTemplateData(convertData.getTemplateData());
            }
            return designPrintTemplateSchemeDefine;
        }
        return designPrintTemplateDefine;
    }

    public static DesignPrintTemplateDefine[] convertPrintTemplate(DesignPrintTemplateDefine[] designPrintTemplateDefines) {
        ArrayList<DesignPrintTemplateDefine> result = new ArrayList<DesignPrintTemplateDefine>();
        for (DesignPrintTemplateDefine designPrintTemplateDefine : designPrintTemplateDefines) {
            result.add(ParamPackage.convertPrintTemplate(designPrintTemplateDefine));
        }
        return result.toArray(new DesignPrintTemplateDefine[0]);
    }

    public static DesignPrintTemplateSchemeDefine convertPrintScheme(DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefines) {
        if (designPrintTemplateSchemeDefines instanceof DesignPrintTemplateSchemeDefineData) {
            DesignPrintTemplateSchemeDefineData convertData = (DesignPrintTemplateSchemeDefineData)designPrintTemplateSchemeDefines;
            DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine = convertData.getDesignPrintTemplateSchemeDefine();
            if (null == designPrintTemplateSchemeDefine.getCommonAttribute()) {
                designPrintTemplateSchemeDefine.setCommonAttribute(convertData.getCommonAttribute());
            }
            if (null == designPrintTemplateSchemeDefine.getGatherCoverData()) {
                designPrintTemplateSchemeDefine.setGatherCoverData(convertData.getGatherCoverData());
            }
            return designPrintTemplateSchemeDefine;
        }
        return designPrintTemplateSchemeDefines;
    }

    public static DesignTaskDefine convertTask(DesignTaskDefine taskDefine) {
        if (taskDefine instanceof DesignTaskDefineData) {
            DesignTaskDefineData convertData = (DesignTaskDefineData)taskDefine;
            DesignTaskDefine designTaskDefine = convertData.getDesignTaskDefine();
            if (null == designTaskDefine.getFlowsSetting()) {
                designTaskDefine.setFlowsSetting((DesignTaskFlowsDefine)convertData.getFlowsSetting());
            }
            return designTaskDefine;
        }
        return taskDefine;
    }

    public static DesignFormDefine convertForm(DesignFormDefine formDefine) {
        if (formDefine instanceof DesignFormDefineData) {
            DesignFormDefineData convertData = (DesignFormDefineData)formDefine;
            DesignFormDefine designFormDefine = convertData.getDesignFormDefine();
            if (!StringUtils.hasLength(designFormDefine.getFillingGuide())) {
                designFormDefine.setFillingGuide(convertData.getFillingGuide());
            }
            if (null == designFormDefine.getBinaryData()) {
                designFormDefine.setBinaryData(convertData.getBinaryData());
            }
            if (!StringUtils.hasLength(designFormDefine.getSurveyData())) {
                designFormDefine.setSurveyData(convertData.getSurveyData());
            }
            if (!StringUtils.hasLength(designFormDefine.getScriptEditor())) {
                designFormDefine.setScriptEditor(convertData.getScriptEditor());
            }
            return designFormDefine;
        }
        return formDefine;
    }

    public static DesignRegionSettingDefine convertRegionSetting(DesignRegionSettingDefine regionSettingDefine) {
        if (regionSettingDefine instanceof DesignRegionSettingDefineData) {
            DesignRegionSettingDefineData convertData = (DesignRegionSettingDefineData)regionSettingDefine;
            DesignRegionSettingDefine designRegionSettingDefine = convertData.getDesignRegionSettingDefine();
            if (null == designRegionSettingDefine.getRegionTabSetting()) {
                designRegionSettingDefine.setRegionTabSetting(convertData.getRegionTabSetting());
            }
            if (null == designRegionSettingDefine.getRowNumberSetting()) {
                designRegionSettingDefine.setRowNumberSetting(convertData.getRowNumberSetting());
            }
            if (null == designRegionSettingDefine.getLastRowStyles()) {
                designRegionSettingDefine.setLastRowStyle(convertData.getLastRowStyles());
            }
            if (null == designRegionSettingDefine.getCardRecord()) {
                designRegionSettingDefine.setCardRecord(convertData.getCardRecord());
            }
            return designRegionSettingDefine;
        }
        return regionSettingDefine;
    }

    public static DesignDataLinkDefine[] convertDataLink(DesignDataLinkDefine[] dataLinkDefines) {
        ArrayList<DesignDataLinkDefine> result = new ArrayList<DesignDataLinkDefine>();
        for (DesignDataLinkDefine dataLinkDefine : dataLinkDefines) {
            if (dataLinkDefine instanceof DesignDataLinkDefineData) {
                DesignDataLinkDefineData convertData = (DesignDataLinkDefineData)dataLinkDefine;
                DesignDataLinkDefine designDataLinkDefine = convertData.getDesignDataLinkDefine();
                result.add(designDataLinkDefine);
                continue;
            }
            result.add(dataLinkDefine);
        }
        return (DesignDataLinkDefine[])result.stream().toArray(DesignDataLinkDefine[]::new);
    }
}

