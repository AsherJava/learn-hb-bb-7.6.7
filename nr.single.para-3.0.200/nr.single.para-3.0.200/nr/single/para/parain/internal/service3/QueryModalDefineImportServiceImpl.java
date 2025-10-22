/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.query.QueryModalCondition
 *  com.jiuqi.nr.single.core.para.parser.query.QueryModalInfo
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.query.QueryModalCondition;
import com.jiuqi.nr.single.core.para.parser.query.QueryModalInfo;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import nr.single.para.parain.bean.SingleQueryFieldDefine;
import nr.single.para.parain.bean.SingleQueryTemplateDefine;
import nr.single.para.parain.bean.result.SingleQueryImportItemResult;
import nr.single.para.parain.bean.result.SingleQueryImportResult;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IQueryModalDefineImportService;
import nr.single.para.parain.service.extension.ISingleQueryTemplateImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryModalDefineImportServiceImpl
implements IQueryModalDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(QueryModalDefineImportServiceImpl.class);
    @Autowired(required=false)
    private Map<String, ISingleQueryTemplateImportService> uploadQueryServiceMap;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;

    @Override
    public void importQueryModalDefines(TaskImportContext importContext) throws Exception {
        if (this.isNeedImportQuery()) {
            log.info("\u5bfc\u5165JIO\u53c2\u6570\uff1a\u67e5\u8be2\u6a21\u7248");
            List<SingleQueryTemplateDefine> queryList = this.buildSingleQueryList(importContext);
            log.info("\u5bfc\u5165JIO\u53c2\u6570\uff1a\u67e5\u8be2\u6a21\u7248\uff0c\u5355\u673a\u7248\u4e2a\u6570:" + queryList.size());
            for (Map.Entry<String, ISingleQueryTemplateImportService> entry : this.uploadQueryServiceMap.entrySet()) {
                String serviceInfo = "\u5bfc\u5165\u67e5\u8be2\u6a21\u677f\u63a5\u53e3\uff1a" + entry.getKey();
                log.info(serviceInfo + ",\u6570\u91cf:" + queryList.size());
                ISingleQueryTemplateImportService queryService = entry.getValue();
                SingleQueryImportResult importResult = queryService.importSingleQueryTemplates(queryList);
                if (importResult == null) continue;
                if (importResult.isSuccess()) {
                    log.info(serviceInfo + "\uff0c\u6210\u529f");
                    continue;
                }
                log.info(serviceInfo + "\uff0c\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + importResult.getMessage());
                if (importResult.getItemList().isEmpty()) continue;
                for (SingleQueryImportItemResult item : importResult.getItemList()) {
                    if (item.isSuccess()) continue;
                    log.info(serviceInfo + "\uff0c\u5931\u8d25\uff0c" + item.getCode() + ",\u539f\u56e0\uff1a" + importResult.getMessage());
                }
            }
        }
    }

    @Override
    public boolean isNeedImportQuery() {
        return this.uploadQueryServiceMap != null && !this.uploadQueryServiceMap.isEmpty();
    }

    private List<SingleQueryTemplateDefine> buildSingleQueryList(TaskImportContext importContext) {
        ArrayList<SingleQueryTemplateDefine> queryList = new ArrayList<SingleQueryTemplateDefine>();
        ParaInfo para = importContext.getParaInfo();
        Map singleQueryModals = para.getHorzQueryModalList();
        if (null != singleQueryModals) {
            double startPos = importContext.getCurProgress();
            for (String modalName : singleQueryModals.keySet()) {
                importContext.onProgress(startPos += 0.1 / (double)singleQueryModals.size(), "\u5bfc\u5165\u67e5\u8be2\u6a21\u677f\uff1a" + modalName);
                QueryModalInfo singleModal = (QueryModalInfo)singleQueryModals.get(modalName);
                SingleQueryTemplateDefine singleQueryTemplate = this.buildSingleQueryModal(importContext, singleModal);
                queryList.add(singleQueryTemplate);
            }
        }
        return queryList;
    }

    private SingleQueryTemplateDefine buildSingleQueryModal(TaskImportContext importContext, QueryModalInfo singleModal) {
        SingleQueryTemplateDefine singleQueryTemplate = new SingleQueryTemplateDefine();
        singleQueryTemplate.setGroupNames(singleModal.getGroupNames());
        singleQueryTemplate.setTitle(singleModal.getModalName());
        singleQueryTemplate.setFormSchemeKey(importContext.getFormSchemeKey());
        singleQueryTemplate.setTaskKey(importContext.getTaskKey());
        this.setTemplateAttr(importContext, singleQueryTemplate, singleModal.getRepInfo().getReportData().getGridBytes(), singleModal);
        return singleQueryTemplate;
    }

    private void setTemplateAttr(TaskImportContext importContext, SingleQueryTemplateDefine singleQueryTemplate, byte[] gridDataBytes, QueryModalInfo singleModal) {
        if (singleModal.getQueryScript() != null) {
            singleQueryTemplate.setColNum(singleModal.getQueryScript().getRowNumColNum());
            singleQueryTemplate.setDoSumary(singleModal.getQueryScript().getDoSumary() > 0);
        }
        Grid2Data gridData = Grid2Data.bytesToGrid((byte[])gridDataBytes);
        Grid2Data gridData2 = Grid2Data.bytesToGrid((byte[])gridDataBytes);
        try {
            for (int i = 1; i <= gridData2.getHeaderRowCount(); ++i) {
                for (int j = 1; j <= gridData2.getHeaderColumnCount(); ++j) {
                    GridCellData cell2 = gridData2.getGridCellData(j, i);
                    GridCellData cell = gridData.getGridCellData(j, i);
                    if (cell.getBackImage() == null) continue;
                    cell2.setBackImage(null);
                    cell.setBackImage(null);
                }
            }
            singleQueryTemplate.setReportData(Grid2Data.gridToBytes((Grid2Data)gridData2));
            singleQueryTemplate.setCondition(singleModal.getCondition());
            if (singleModal.getConditionList() != null && !singleModal.getConditionList().isEmpty() && singleModal.getConditionIndex() >= 0 && singleModal.getConditionIndex() < singleModal.getConditionList().size()) {
                QueryModalCondition qmConditon = (QueryModalCondition)singleModal.getConditionList().get(singleModal.getConditionIndex());
                singleQueryTemplate.setConditionTitle(qmConditon.getTitle());
                if (StringUtils.isEmpty((String)singleModal.getCondition())) {
                    singleQueryTemplate.setCondition(qmConditon.getExpression());
                }
            }
            ArrayList<SingleQueryFieldDefine> selectFields = new ArrayList<SingleQueryFieldDefine>();
            for (int i = 1; i < gridData.getColumnCount(); ++i) {
                DesignDataField fieldDefine;
                SingleQueryFieldDefine selectField = new SingleQueryFieldDefine();
                String fieldExp = gridData.getGridCellData(i, 1 + singleModal.getQueryScript().getHeadRowEnd()).getEditText();
                String fieldDot = gridData.getGridCellData(i, 2 + singleModal.getQueryScript().getHeadRowEnd()).getEditText();
                if (StringUtils.isNotEmpty((String)fieldDot)) {
                    selectField.setDotNum(Integer.parseInt(fieldDot));
                }
                selectField.setCol(i);
                selectField.setRow(1 + singleModal.getQueryScript().getHeadRowEnd());
                String fieldCode = fieldExp;
                String tableCode = "";
                int id1 = fieldExp.indexOf("[");
                int id2 = fieldExp.indexOf("]");
                if (id1 > 0 || id2 > 0) {
                    tableCode = fieldExp.substring(0, id1);
                    fieldCode = fieldExp.substring(id1 + 1, id2);
                }
                if (StringUtils.isEmpty((String)tableCode)) {
                    tableCode = "FMDM";
                }
                if (null != (fieldDefine = this.findFieldByCode(importContext, importContext.getSchemeInfoCache().getFormScheme(), tableCode, fieldCode, selectField))) {
                    selectField.setFieldKey(fieldDefine.getKey());
                } else {
                    selectField.setExpression(fieldExp);
                }
                selectFields.add(selectField);
            }
            singleQueryTemplate.getQueryFields().addAll(selectFields);
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private DesignDataField findFieldByCode(TaskImportContext importContext, DesignFormSchemeDefine formScheme, String tableCode, String fieldCode, SingleQueryFieldDefine selectField) {
        DesignDataField fieldDefine = null;
        String curRegionKey = null;
        try {
            block11: {
                List regions;
                DesignFormDefine formDefine;
                block13: {
                    block12: {
                        DesignDataLinkDefine link;
                        formDefine = this.viewController.getFormByFormSchemeAndCode(formScheme.getKey(), tableCode);
                        regions = null;
                        if (null != formDefine) {
                            regions = this.viewController.listDataRegionByForm(formDefine.getKey());
                        }
                        String tableKey = null;
                        if (null != importContext.getEntityRunTimeTableKey() && (StringUtils.isEmpty((String)tableCode) || "FMDM".equalsIgnoreCase(tableCode))) {
                            tableKey = importContext.getEntityRunTimeTableKey();
                            fieldDefine = this.dataSchemeService.getDataFieldByTableKeyAndCode(tableKey, fieldCode);
                            if (null != regions && regions.size() > 0) {
                                curRegionKey = ((DesignDataRegionDefine)regions.get(0)).getKey();
                            }
                        }
                        if (null != fieldDefine) break block11;
                        int idx = fieldCode.indexOf(",");
                        if (idx <= 0) break block12;
                        String posx = fieldCode.substring(0, idx);
                        String posy = fieldCode.substring(idx + 1, fieldCode.length());
                        if (null == formDefine || null == (link = this.viewController.getDataLinkByFormAndColRow(formDefine.getKey(), Integer.parseInt(posy), Integer.parseInt(posx))) || link.getType() != DataLinkType.DATA_LINK_TYPE_FIELD) break block11;
                        fieldDefine = this.dataSchemeService.getDataField(link.getLinkExpression());
                        curRegionKey = link.getRegionKey();
                        break block11;
                    }
                    DesignDataTable tableDefine = this.dataSchemeService.getDataTableByCode(formScheme.getFilePrefix() + '_' + tableCode);
                    if (null == tableDefine) break block13;
                    fieldDefine = this.dataSchemeService.getDataFieldByTableKeyAndCode(tableDefine.getKey(), fieldCode);
                    if (null == regions || regions.size() <= 0) break block11;
                    curRegionKey = ((DesignDataRegionDefine)regions.get(0)).getKey();
                    break block11;
                }
                if (null != formDefine) {
                    HashSet<String> TableMap = new HashSet<String>();
                    boolean hasFind = false;
                    for (DesignDataRegionDefine region : regions) {
                        List linkfields = this.viewController.listFieldKeyByDataRegion(region.getKey());
                        for (String fieldKey : linkfields) {
                            DesignDataField field = this.dataSchemeService.getDataField(fieldKey);
                            if (null != field && null != field.getDataTableKey()) {
                                if (!TableMap.contains(field.getDataTableKey())) {
                                    fieldDefine = this.dataSchemeService.getDataFieldByTableKeyAndCode(field.getDataTableKey(), fieldCode);
                                    hasFind = null != fieldDefine;
                                }
                                TableMap.add(field.getDataTableKey());
                            }
                            if (!hasFind) continue;
                            curRegionKey = region.getKey();
                            break;
                        }
                        if (!hasFind) continue;
                        break;
                    }
                }
            }
            if (StringUtils.isNotEmpty(curRegionKey)) {
                log.debug(fieldCode + ",\u5339\u914d\u5230\u533a\u57df\uff1a" + curRegionKey);
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return fieldDefine;
    }
}

