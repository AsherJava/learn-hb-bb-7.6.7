/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.IRegionExportDataSet;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.RegionNumberManager;
import com.jiuqi.nr.jtable.dataset.impl.SimpleRegionRelationEvn;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.ErrorLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.RegionDataFactory;
import com.jiuqi.nr.jtable.util.RegionGradeDataLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionExportDataSetImpl
implements IRegionExportDataSet {
    private static final Logger logger = LoggerFactory.getLogger(RegionExportDataSetImpl.class);
    private IJtableParamService jtableParamService;
    private IJtableResourceService jtableResourceService;
    private RegionData regionData;
    private RegionNumberManager numberManager;
    private RegionQueryInfo regionQueryInfo;
    private RegionDataSet regionDataSet;
    private AbstractRegionRelationEvn regionRelationEvn;
    private int limit = 500;
    private int offset = -1;
    private boolean haveId;

    @Override
    public RegionQueryInfo getRegionQueryInfo() {
        return this.regionQueryInfo;
    }

    public RegionExportDataSetImpl(JtableContext jtableContext, RegionData regionData) {
        this.init(jtableContext, regionData, false);
    }

    public RegionExportDataSetImpl(JtableContext jtableContext, RegionData regionData, boolean haveId, PagerInfo pagerInfo, boolean noSumData) {
        if (pagerInfo.getLimit() != 0) {
            this.limit = pagerInfo.getLimit();
            this.offset = pagerInfo.getOffset();
        }
        this.haveId = haveId;
        this.init(jtableContext, regionData, noSumData);
    }

    public RegionExportDataSetImpl(JtableContext jtableContext, RegionData regionData, boolean haveId) {
        this.init(jtableContext, regionData, false);
        this.haveId = haveId;
    }

    public RegionExportDataSetImpl(JtableContext jtableContext, RegionData regionData, boolean haveId, boolean noSumData) {
        this.init(jtableContext, regionData, noSumData);
        this.haveId = haveId;
    }

    private void init(JtableContext jtableContext, RegionData regionData, boolean noSumData) {
        this.jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.jtableResourceService = (IJtableResourceService)BeanUtil.getBean(IJtableResourceService.class);
        this.regionData = regionData;
        this.regionQueryInfo = new RegionQueryInfo();
        this.regionQueryInfo.setContext(jtableContext);
        if (regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            this.regionRelationEvn = new SimpleRegionRelationEvn(regionData, jtableContext);
        } else {
            this.regionRelationEvn = new FloatRegionRelationEvn(regionData, jtableContext);
            this.regionRelationEvn.setPaginate(true);
            PagerInfo pagerInfo = new PagerInfo();
            pagerInfo.setLimit(this.limit);
            pagerInfo.setOffset(this.offset);
            this.regionQueryInfo.setPagerInfo(pagerInfo);
            if (noSumData) {
                this.regionQueryInfo.getRestructureInfo().setNoSumData(noSumData);
            }
        }
        if (!regionData.getCells().isEmpty() && this.regionQueryInfo.getFilterInfo().getCellQuerys().isEmpty()) {
            this.regionQueryInfo.getFilterInfo().setCellQuerys(regionData.getCells());
        }
        this.numberManager = new RegionNumberManager(regionData.getRegionNumber());
    }

    private void queryRangeData() {
        RegionDataFactory factory = new RegionDataFactory();
        factory.setJsonData(true);
        if (this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            this.regionRelationEvn.setPaginate(true);
            PagerInfo pagerInfo = new PagerInfo();
            pagerInfo.setLimit(this.limit);
            pagerInfo.setOffset(this.offset);
            this.regionQueryInfo.setPagerInfo(pagerInfo);
        }
        FormData formData = this.jtableParamService.getReport(this.regionData.getFormKey(), null);
        this.regionDataSet = FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType()) ? this.jtableResourceService.getFmdmData(this.regionData, this.regionQueryInfo, true) : factory.getRegionDataSet(this.regionData, this.regionQueryInfo);
        if (this.regionDataSet.getTotalCount() == this.regionDataSet.getData().size() && this.regionDataSet.getTotalCount() != 0) {
            this.limit = this.regionDataSet.getTotalCount();
        }
    }

    @Override
    public boolean hasNext() {
        if (this.regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            return this.offset == -1;
        }
        double total = this.getTotalCount();
        return this.offset == -1 || (double)this.offset < Math.ceil(total / (double)this.limit) - 1.0;
    }

    @Override
    public MemoryDataSet<Object> next() {
        ++this.offset;
        return this.getCurrPageDataRowSet();
    }

    @Override
    public RegionData getRegionData() {
        return this.regionData;
    }

    @Override
    public List<LinkData> getLinkDataList() {
        List<LinkData> links = this.jtableParamService.getLinks(this.regionData.getKey());
        Iterator<LinkData> it = links.iterator();
        while (it.hasNext()) {
            LinkData link = it.next();
            if (!(link instanceof ErrorLinkData)) continue;
            it.remove();
        }
        return links;
    }

    @Override
    public boolean isFloatRegion() {
        return this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue();
    }

    @Override
    public FieldData getFieldDataByDataLink(LinkData linkData) {
        return this.regionRelationEvn.getFieldByDataLink(linkData.getKey());
    }

    @Override
    public List<FieldData> getFieldDataList() {
        ArrayList<FieldData> fieldDefines = new ArrayList<FieldData>();
        fieldDefines.addAll(this.regionRelationEvn.getDataLinkFieldMap().values());
        return fieldDefines;
    }

    @Override
    public int getTotalCount() {
        if (this.regionDataSet == null) {
            return 0;
        }
        return this.regionDataSet.getTotalCount();
    }

    public MemoryDataSet<Object> getCurrPageDataRowSet() {
        MemoryDataSet dataRowSet = new MemoryDataSet();
        this.queryRangeData();
        List<LinkData> linkDataList = this.getLinkDataList();
        if (linkDataList.isEmpty()) {
            return dataRowSet;
        }
        if (this.regionDataSet == null) {
            return dataRowSet;
        }
        List<String> linkKeys = this.regionDataSet.getCells().get(this.regionData.getKey());
        int bizKeyIndex = linkKeys.indexOf("ID");
        int sumIndex = linkKeys.indexOf("SUM");
        int leftCol = -1;
        String leftdataLinkKey = "";
        for (LinkData linkData : linkDataList) {
            if (leftCol >= 0 && leftCol <= linkData.getCol()) continue;
            leftCol = linkData.getCol();
            leftdataLinkKey = linkData.getKey();
        }
        int leftIndex = linkKeys.indexOf(leftdataLinkKey);
        RegionGradeInfo grade = this.regionData.getGrade();
        List<GradeCellInfo> gradeCells = grade.getGradeCells();
        HashMap<String, GradeCellInfo> gradeCellMap = new HashMap<String, GradeCellInfo>();
        for (GradeCellInfo gradeCell : gradeCells) {
            gradeCellMap.put(gradeCell.getZbid(), gradeCell);
        }
        for (int linkIndex = 0; linkIndex < linkDataList.size(); ++linkIndex) {
            LinkData linkData = linkDataList.get(linkIndex);
            if (!(linkData instanceof EnumLinkData) || !gradeCellMap.containsKey(linkData.getZbid())) continue;
            GradeCellInfo gradeCellInfo = (GradeCellInfo)gradeCellMap.get(linkData.getZbid());
            EnumLinkData enumLink = (EnumLinkData)linkData;
            if (gradeCellInfo == null || !gradeCellInfo.isTrim()) continue;
            gradeCellInfo.setGradeStruct(this.jtableParamService.getEntity(enumLink.getEntityKey()).getTreeStruct());
        }
        for (int i = 0; i < this.regionDataSet.getData().size(); ++i) {
            List<Object> row = this.regionDataSet.getData().get(i);
            Object[] rowData = null;
            rowData = this.haveId && bizKeyIndex >= 0 ? new Object[linkDataList.size() + 1] : new Object[linkDataList.size()];
            if (this.numberManager != null) {
                if (sumIndex >= 0) {
                    String sumTitle = row.get(sumIndex).toString();
                    this.numberManager.setNumberStr(sumTitle);
                } else {
                    this.numberManager.setNumberStr("");
                }
            }
            try {
                for (int rowIndex = 0; rowIndex < linkDataList.size(); ++rowIndex) {
                    GradeCellInfo gradeCellInfo;
                    List<Object> rel;
                    LinkData linkData = linkDataList.get(rowIndex);
                    int linkIndex = linkKeys.indexOf(linkData.getKey());
                    Object value = row.get(linkIndex);
                    Object object = rowData[rowIndex] = null == value ? "" : value.toString();
                    if (i == 0 && leftIndex == linkIndex && this.regionDataSet.getRel().size() > 0 && (Integer)(rel = this.regionDataSet.getRel().get(i)).get(4) == RegionGradeDataLoader.groupData && (StringUtils.isEmpty((String)row.get(leftIndex).toString()) || "\u2014\u2014".equals(row.get(leftIndex).toString()))) {
                        rowData[rowIndex] = "\u5408\u8ba1";
                    }
                    if (!(linkData instanceof EnumLinkData) || !StringUtils.isNotEmpty((String)value.toString()) || !gradeCellMap.containsKey(linkData.getZbid()) || (gradeCellInfo = (GradeCellInfo)gradeCellMap.get(linkData.getZbid())) == null || !gradeCellInfo.isTrim() || !StringUtils.isNotEmpty((String)gradeCellInfo.getGradeStruct())) continue;
                    String gradeStruct = gradeCellInfo.getGradeStruct();
                    String[] gradeStructLevels = gradeStruct.split(";");
                    ArrayList<Integer> levels = gradeCellInfo.getLevels();
                    if (this.regionDataSet.getRel().size() <= i) continue;
                    List<Object> rel2 = this.regionDataSet.getRel().get(i);
                    Integer rowDeep = (Integer)rel2.get(2);
                    if ((Integer)rel2.get(4) != RegionGradeDataLoader.groupData || !levels.isEmpty() && !levels.contains(rowDeep)) continue;
                    int newLength = 0;
                    for (int level = 0; level < rowDeep; ++level) {
                        newLength += Integer.parseInt(gradeStructLevels[level]);
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    List json = (List)mapper.readValue(value.toString(), (TypeReference)new TypeReference<List<Object>>(){});
                    ArrayList jsonOfFormate = new ArrayList();
                    if (!json.isEmpty()) {
                        for (Object one : json) {
                            String oneJson = mapper.writeValueAsString(one);
                            Map oneMap = (Map)mapper.readValue(oneJson, (TypeReference)new TypeReference<Map<String, Object>>(){});
                            String enumTitle = (String)oneMap.get("title");
                            String enumTitleTrim = enumTitle.substring(0, newLength);
                            oneMap.put("title", enumTitleTrim);
                            one = mapper.writeValueAsString((Object)oneMap);
                            jsonOfFormate.add(one);
                        }
                    }
                    rowData[rowIndex] = jsonOfFormate != null && jsonOfFormate.size() > 0 ? ((Object)jsonOfFormate).toString() : json.toString();
                }
                if (this.haveId && bizKeyIndex >= 0) {
                    rowData[rowData.length - 1] = row.get(bizKeyIndex).toString();
                }
                boolean isEmpty = true;
                for (Object cellData : rowData) {
                    if (!StringUtils.isNotEmpty((String)cellData.toString())) continue;
                    isEmpty = false;
                }
                if (isEmpty) continue;
                dataRowSet.add(rowData);
                continue;
            }
            catch (DataSetException | RuntimeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                continue;
            }
            catch (JsonMappingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                continue;
            }
            catch (JsonProcessingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return dataRowSet;
    }

    @Override
    public RegionNumberManager getNumberManager() {
        return this.numberManager;
    }

    @Override
    public LinkData getLinkDataByField(FieldData fieldDefine) {
        LinkData dataLink = this.regionRelationEvn.getDataLinkByFiled(fieldDefine.getFieldKey());
        return dataLink;
    }

    @Override
    public List<RegionTab> getRegionTabs() {
        return this.regionData.getTabs();
    }

    @Override
    public void setRegionTab(RegionTab regionTabSettingDefine, int limitNum) {
        ArrayList<String> filterFormulaList = new ArrayList<String>();
        if (regionTabSettingDefine == null) {
            this.regionQueryInfo.getFilterInfo().setFilterFormula(filterFormulaList);
        } else {
            if (StringUtils.isNotEmpty((String)regionTabSettingDefine.getFilter())) {
                filterFormulaList.add(regionTabSettingDefine.getFilter());
            }
            this.regionQueryInfo.getFilterInfo().setFilterFormula(filterFormulaList);
        }
        this.offset = -1;
        this.limit = limitNum;
        if (this.numberManager != null) {
            this.numberManager.resetNumber();
        }
    }

    @Override
    public void setRegionFilter(List<String> regionFilter) {
        List<String> filterFormulaList = this.regionQueryInfo.getFilterInfo().getFilterFormula();
        if (!filterFormulaList.isEmpty()) {
            filterFormulaList.addAll(regionFilter);
            this.regionQueryInfo.getFilterInfo().setFilterFormula(filterFormulaList);
        } else {
            this.regionQueryInfo.getFilterInfo().setFilterFormula(regionFilter);
        }
    }

    public RegionDataSet getRegionDataSet() {
        return this.regionDataSet;
    }
}

