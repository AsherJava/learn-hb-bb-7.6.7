/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.util.Base64
 */
package com.jiuqi.nr.analysisreport.helper;

import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import com.jiuqi.nr.analysisreport.internal.controller.IAnalysisReportController;
import com.jiuqi.nr.analysisreport.rest.SaveAnalysisController;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.analysisreport.vo.ReportGeneratorVO;
import com.jiuqi.nr.common.params.DimensionType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.util.Base64;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisHelper {
    @Autowired
    IAnalysisReportController iAnalysisReportController;
    @Autowired
    private SaveAnalysisController saveAnalysisController;
    private static final Logger log = LoggerFactory.getLogger(AnalysisHelper.class);

    public List<AnalysisReportDefine> getList() throws Exception {
        return this.iAnalysisReportController.getList();
    }

    public List<AnalysisReportDefine> getListByGroupKey(String key) throws Exception {
        return this.iAnalysisReportController.getListByGroupKey(key);
    }

    public AnalysisReportDefine getListByKey(String key) throws Exception {
        return this.iAnalysisReportController.getListByKey(key);
    }

    public String insertModel(AnalysisReportDefine define) throws Exception {
        return this.iAnalysisReportController.insertModel(define);
    }

    public String updateModel(AnalysisReportDefine define) throws Exception {
        return this.iAnalysisReportController.updateModel(define);
    }

    public String deleteModel(String key) throws Exception {
        return this.iAnalysisReportController.deleteModel(key);
    }

    public List<AnalysisReportGroupDefine> getGroupList() throws Exception {
        return this.iAnalysisReportController.getGroupList();
    }

    public List<AnalysisReportGroupDefine> getGroupByParent(String parent) throws Exception {
        return this.iAnalysisReportController.getGroupByParent(parent);
    }

    public AnalysisReportGroupDefine getGroupByKey(String key) throws Exception {
        return this.iAnalysisReportController.getGroupByKey(key);
    }

    public String insertGroup(AnalysisReportGroupDefine define) throws Exception {
        return this.iAnalysisReportController.insertGroup(define);
    }

    public String updateGroup(AnalysisReportGroupDefine define) throws Exception {
        return this.iAnalysisReportController.updateGroup(define);
    }

    public String deleteGroup(String key) throws Exception {
        return this.iAnalysisReportController.deleteGroup(key);
    }

    public static AnalysisTemp returnAnalysisTemp(AnalysisReportDefine define) {
        AnalysisTemp analysisTemp = new AnalysisTemp();
        analysisTemp.setKey(define.getKey());
        analysisTemp.setGroupKey(define.getGroupKey());
        analysisTemp.setTitle(define.getTitle());
        analysisTemp.setDescription(define.getDescription());
        analysisTemp.setCreateuser(define.getCreateuser());
        analysisTemp.setModifyuser(define.getModifyuser());
        analysisTemp.setOrder(define.getOrder());
        return analysisTemp;
    }

    public static Map<String, DimensionValue> entityListAndUnitsCalcDimensionValue(List<EntityViewData> entitys, List<Map<String, String>> unitMapList, final String periodCode) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (final EntityViewData entity : entitys) {
            for (Map<String, String> unitMap : unitMapList) {
                String unitTemp;
                if (!entity.getKey().equals(unitMap.get("viewKey"))) continue;
                if (DimensionType.DIMENSION_UNIT.getValue() == AnaUtils.getDimensionType(entity).getValue()) {
                    String unitsStr = "";
                    unitsStr = dimensionSet.get(entity.getDimensionName()) != null ? ((DimensionValue)dimensionSet.get(entity.getDimensionName())).getValue() + ";" + unitMap.get("key") : unitMap.get("key");
                    unitTemp = unitsStr;
                    dimensionSet.put(entity.getDimensionName(), new DimensionValue(){
                        private static final long serialVersionUID = -1228989081368372357L;
                        {
                            this.setType(DimensionType.DIMENSION_UNIT.getValue());
                            this.setName(entity.getDimensionName());
                            this.setValue(unitTemp);
                        }
                    });
                }
                if (DimensionType.DIMENSION_NOMAL.getValue() == AnaUtils.getDimensionType(entity).getValue()) {
                    String nomalsStr = "";
                    nomalsStr = dimensionSet.get(entity.getDimensionName()) != null && ((DimensionValue)dimensionSet.get(entity.getDimensionName())).getValue() != null ? ((DimensionValue)dimensionSet.get(entity.getDimensionName())).getValue() + ";" + unitMap.get("key") : unitMap.get("key");
                    unitTemp = nomalsStr;
                    dimensionSet.put(entity.getDimensionName(), new DimensionValue(){
                        private static final long serialVersionUID = 6673148917188748029L;
                        {
                            this.setType(DimensionType.DIMENSION_NOMAL.getValue());
                            this.setName(entity.getDimensionName());
                            this.setValue(unitTemp);
                        }
                    });
                }
                if (DimensionType.DIMENSION_VERSION.getValue() != AnaUtils.getDimensionType(entity).getValue()) continue;
                String versionsStr = "";
                versionsStr = dimensionSet.get(entity.getDimensionName()) != null ? ((DimensionValue)dimensionSet.get(entity.getDimensionName())).getValue() + ";" + unitMap.get("key") : unitMap.get("key");
                unitTemp = versionsStr;
                dimensionSet.put(entity.getDimensionName(), new DimensionValue(){
                    private static final long serialVersionUID = 2395277767948443057L;
                    {
                        this.setType(DimensionType.DIMENSION_VERSION.getValue());
                        this.setName(entity.getDimensionName());
                        this.setValue(unitTemp);
                    }
                });
            }
            if (DimensionType.DIMENSION_PERIOD.getValue() == AnaUtils.getDimensionType(entity).getValue()) {
                dimensionSet.put(entity.getDimensionName(), new DimensionValue(){
                    private static final long serialVersionUID = -1090571350404709231L;
                    {
                        this.setType(DimensionType.DIMENSION_PERIOD.getValue());
                        this.setName(entity.getDimensionName());
                        this.setValue(periodCode);
                    }
                });
            }
            if (dimensionSet.get(entity.getDimensionName()) != null) continue;
            dimensionSet.put(entity.getDimensionName(), new DimensionValue(){
                private static final long serialVersionUID = 3907182085551083721L;
                {
                    this.setType(AnaUtils.getDimensionType(entity).getValue());
                    this.setName(entity.getDimensionName());
                }
            });
        }
        return dimensionSet;
    }

    public List<AnalyVersionDefine> checkVersion(String analytemplateKey, String entitykey, String date, String name) throws Exception {
        try {
            return this.iAnalysisReportController.getVersionListByname(analytemplateKey, entitykey, date, name);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public List<AnalyVersionDefine> getVersionList(String analytemplateKey, String entitykey, String date) throws Exception {
        try {
            return this.iAnalysisReportController.getVersionList(analytemplateKey, entitykey, date);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public List<AnalyVersionDefine> getVersionList(String analytemplateKey) throws Exception {
        try {
            return this.iAnalysisReportController.getVersionList(analytemplateKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public String insertVersion(AnalyVersionDefine define) throws Exception {
        return this.iAnalysisReportController.insertVersion(define);
    }

    public String updateVersion(AnalyVersionDefine define) throws Exception {
        return this.iAnalysisReportController.updateVersion(define);
    }

    public AnalyVersionDefine getVersionBykey(String define) throws Exception {
        return this.iAnalysisReportController.getVersionBykey(define);
    }

    public Integer deleteVersionByKey(String key) throws Exception {
        return this.iAnalysisReportController.deleteVersionBykey(key);
    }

    public Integer deleteVersionByModelKey(String key) throws Exception {
        return this.iAnalysisReportController.deleteVersionByModelKey(key);
    }

    public String generateArc(String arcKey, String content, ReportGeneratorVO reportGeneratorVO) throws Exception {
        String centents = new String(Base64.base64ToByteArray((String)content));
        reportGeneratorVO.setArcKey(arcKey);
        reportGeneratorVO.setContents(centents);
        ReturnObject returnObject = this.saveAnalysisController.analysisReportGenerator(reportGeneratorVO, null, null);
        AnalysisTemp temp = (AnalysisTemp)returnObject.getObj();
        return temp.getData();
    }

    public Date getCatalogUpdateTime(String key) throws Exception {
        return this.iAnalysisReportController.getCatalogUpdateTime(key);
    }

    public List<String> getGroupPathKey(String groupKey) {
        ArrayList<String> pathKey = new ArrayList<String>();
        try {
            AnalysisReportGroupDefine groupDefine;
            while (!"".equals(groupKey) && !"0".equals(groupKey) && (groupDefine = this.getGroupByKey(groupKey)) != null) {
                pathKey.add(groupDefine.getKey());
                groupKey = groupDefine.getParentgroup();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        Collections.reverse(pathKey);
        return pathKey;
    }
}

