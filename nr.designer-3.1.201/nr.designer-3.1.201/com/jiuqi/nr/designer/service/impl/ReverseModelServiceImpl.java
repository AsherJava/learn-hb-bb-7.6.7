/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.common.ReverseItemState;
import com.jiuqi.nr.designer.service.IReverseModelService;
import com.jiuqi.nr.designer.util.IReverseState;
import com.jiuqi.nr.designer.util.ReverseModelUtils;
import com.jiuqi.nr.designer.web.rest.param.ReverseBatchCheckPM;
import com.jiuqi.nr.designer.web.rest.param.ReverseCreateFieldPM;
import com.jiuqi.nr.designer.web.rest.param.ReverseCreateTablePM;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataFieldVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataTableVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseFormVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseLinkVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseRegionVO;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ReverseModelServiceImpl
implements IReverseModelService {
    public static final String UNDERLINE = "_";
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private ReverseModelUtils reverseModelUtils;
    @Autowired
    private DataFieldDesignService dataFieldDesignService;
    @Autowired
    private DesignDataLinkDefineService designDataLinkDefineService;
    @Autowired
    private DesignFormDefineService designFormDefineService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    private static final Logger logger = LoggerFactory.getLogger(ReverseModelServiceImpl.class);

    private String fillIndexNum(int i) {
        StringBuilder originBuilder = new StringBuilder().append(i);
        while (originBuilder.length() < 3) {
            originBuilder.insert(0, "0");
        }
        return originBuilder.toString();
    }

    @Override
    public ReverseDataTableVO createZBTables(ReverseCreateTablePM tablePM, ReverseCreateTablePM.Region region, List<DesignDataTable> dataTables) {
        String code = this.formatSchemePrefix(tablePM.getSchemePrefix()) + tablePM.getFormCode();
        List<String> tableCodes = tablePM.getTableCodes();
        ArrayList<String> tableTitles = new ArrayList<String>();
        dataTables.forEach(d -> {
            tableCodes.add(d.getCode());
            tableTitles.add(d.getTitle());
        });
        ReverseDataTableVO reverseDataTableVO = new ReverseDataTableVO();
        reverseDataTableVO.setKey(UUIDUtils.getKey());
        reverseDataTableVO.setCode(this.judgeTableCode(tableCodes, code));
        reverseDataTableVO.setTitle(this.judgeTableTitle(tableTitles, region, tablePM));
        reverseDataTableVO.setState(ReverseItemState.NEW);
        reverseDataTableVO.setDataSchemeKey(tablePM.getDataSchemeKey());
        reverseDataTableVO.setDataTableType(DataTableType.TABLE);
        return reverseDataTableVO;
    }

    @Override
    public ReverseDataTableVO createMXTables(ReverseCreateTablePM tablePM, ReverseCreateTablePM.Region region, List<DesignDataTable> dataTables) {
        String code = this.formatSchemePrefix(tablePM.getSchemePrefix()) + tablePM.getFormCode() + UNDERLINE + "F";
        List<String> tableCodes = tablePM.getTableCodes();
        ArrayList<String> tableTitles = new ArrayList<String>();
        dataTables.forEach(d -> {
            tableCodes.add(d.getCode());
            tableTitles.add(d.getTitle());
        });
        ReverseDataTableVO reverseDataTableVO = new ReverseDataTableVO();
        reverseDataTableVO.setKey(UUIDUtils.getKey());
        if (region.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST.getValue()) {
            reverseDataTableVO.setCode(this.judgeTableCode(tableCodes, code + region.getRegionTop()));
        } else if (region.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) {
            reverseDataTableVO.setCode(this.judgeTableCode(tableCodes, code + region.getRegionLeft()));
        } else {
            reverseDataTableVO.setCode(this.judgeTableCode(tableCodes, code + 0));
        }
        reverseDataTableVO.setTitle(this.judgeTableTitle(tableTitles, region, tablePM));
        reverseDataTableVO.setState(ReverseItemState.NEW);
        reverseDataTableVO.setDataSchemeKey(tablePM.getDataSchemeKey());
        reverseDataTableVO.setDataTableType(DataTableType.DETAIL);
        reverseDataTableVO.setDataTableGatherType(DataTableGatherType.NONE);
        reverseDataTableVO.setRepeatCode(true);
        return reverseDataTableVO;
    }

    private String judgeTableTitle(List<String> tableTitles, ReverseCreateTablePM.Region region, ReverseCreateTablePM tablePM) {
        if (tablePM.getFormType() == FormType.FORM_TYPE_FIX.getValue() || tablePM.getFormType() == FormType.FORM_TYPE_NEWFMDM.getValue()) {
            return this.judgeTableCode(tableTitles, tablePM.getFormTitle());
        }
        if (tablePM.getFormType() == FormType.FORM_TYPE_FLOAT.getValue() && region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            return this.judgeTableCode(tableTitles, tablePM.getFormTitle() + "\u56fa\u5b9a\u533a\u57df");
        }
        if (tablePM.getFormType() == FormType.FORM_TYPE_FLOAT.getValue() && region.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST.getValue()) {
            return this.judgeTableCode(tableTitles, tablePM.getFormTitle() + "\u6d6e\u52a8\u533a\u57df" + region.getRegionTop());
        }
        if (tablePM.getFormType() == FormType.FORM_TYPE_FLOAT.getValue() && region.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) {
            return this.judgeTableCode(tableTitles, tablePM.getFormTitle() + "\u6d6e\u52a8\u533a\u57df" + region.getRegionLeft());
        }
        return "\u9519\u8bef\u7c7b\u578b\u7684\u6570\u636e\u8868";
    }

    private String judgeTableCode(List<String> tableCodes, String code) {
        int start = 1;
        StringBuilder builder = new StringBuilder(code).append(UNDERLINE).append(start);
        if (tableCodes.contains(code)) {
            while (tableCodes.contains(builder.toString())) {
                builder = new StringBuilder(code).append(UNDERLINE).append(++start);
            }
        } else {
            tableCodes.add(code);
            return code;
        }
        tableCodes.add(builder.toString());
        return builder.toString();
    }

    private String formatSchemePrefix(String schemePrefix) {
        return schemePrefix == null ? "" : schemePrefix + UNDERLINE;
    }

    @Override
    public List<ReverseDataFieldVO> createField(ReverseCreateFieldPM fieldsPM, ReverseCreateFieldPM.Region region, List<DesignDataField> dbFields) {
        List<String> fieldCodes = region.getFieldCodes();
        String formCode = fieldsPM.getFormCode();
        if (dbFields != null && !dbFields.isEmpty()) {
            dbFields.forEach(f -> fieldCodes.add(f.getCode()));
        }
        HashSet<String> strings = new HashSet<String>(fieldCodes);
        int start = 1;
        ArrayList<ReverseDataFieldVO> list = new ArrayList<ReverseDataFieldVO>();
        for (int i = 0; i < region.getFieldNum(); ++i) {
            ReverseDataFieldVO fieldVO = new ReverseDataFieldVO();
            fieldVO.setDataSchemeKey(fieldsPM.getDataSchemeKey());
            fieldVO.setCode(this.judgeFieldCode(strings, formCode, start++));
            this.initField(list, fieldVO, region);
        }
        return list;
    }

    private void initField(List<ReverseDataFieldVO> list, ReverseDataFieldVO fieldVO, ReverseCreateFieldPM.Region region) {
        fieldVO.setDataFieldType(region.getFieldType());
        fieldVO.setKey(UUIDUtils.getKey());
        fieldVO.setDataTableKey(region.getTableKey());
        fieldVO.setState(ReverseItemState.NEW);
        fieldVO.setDataFieldKind(region.getFieldKind());
        fieldVO.setNullable(true);
        fieldVO.setDataFieldGatherType(DataFieldGatherType.NONE);
        fieldVO.setGatherTypeTitle(DataFieldGatherType.NONE.getTitle());
        fieldVO.setUseAuthority(false);
        this.initFieldByType(fieldVO, region.getFieldType());
        fieldVO.setFormatVO(this.reverseModelUtils.fillFormatVO(null));
        if (DataFieldType.valueOf((int)region.getFieldType()) == DataFieldType.INTEGER || DataFieldType.valueOf((int)region.getFieldType()) == DataFieldType.BIGDECIMAL) {
            fieldVO.setDimension(0);
            fieldVO.setMeasureUnit("YUAN");
        } else {
            fieldVO.setDimension(-1);
            fieldVO.setMeasureUnit("");
        }
        list.add(fieldVO);
    }

    @Override
    public List<ReverseDataFieldVO> createZDField(ReverseCreateFieldPM fieldsPM, ReverseCreateFieldPM.Region region) {
        List<String> fieldCodes = region.getFieldCodes();
        List dbFields = this.designDataSchemeService.getDataFieldByTableKeyAndKind(region.getTableKey(), new DataFieldKind[]{DataFieldKind.FIELD});
        if (dbFields != null && !dbFields.isEmpty()) {
            dbFields.forEach(f -> fieldCodes.add(f.getCode()));
        }
        HashSet<String> strings = new HashSet<String>(fieldCodes);
        int start = 1;
        ArrayList<ReverseDataFieldVO> list = new ArrayList<ReverseDataFieldVO>();
        for (int i = 0; i < region.getFieldNum(); ++i) {
            ReverseDataFieldVO fieldVO = new ReverseDataFieldVO();
            fieldVO.setDataSchemeKey(fieldsPM.getDataSchemeKey());
            fieldVO.setCode(this.judgeFieldCode(strings, this.rmPrefix(fieldsPM.getSchemePrefix(), region.getTableCode()), start++));
            this.initField(list, fieldVO, region);
        }
        return list;
    }

    private String formatZDCode(String formCode, int regionTop) {
        return formCode + UNDERLINE + "F" + regionTop;
    }

    private String rmPrefix(String schemePrefix, String tableCode) {
        if (schemePrefix == null) {
            return tableCode;
        }
        if (tableCode.startsWith(schemePrefix)) {
            return tableCode.replaceFirst(schemePrefix + UNDERLINE, "");
        }
        return tableCode;
    }

    @Override
    public String queryTableGroup(ReverseFormVO reverseFormObj) {
        return null;
    }

    @Override
    public String queryTableGroup(String dataSchemeKey, String formSchemeTitle, String formGroupTitle, String formTitle) {
        List dataGroupList = this.designDataSchemeService.getAllDataGroup(dataSchemeKey);
        DesignDataGroup dsGroup = dataGroupList.parallelStream().filter(g -> g.getTitle().equals(formSchemeTitle) && !StringUtils.hasLength(g.getParentKey())).findFirst().orElse(null);
        if (dsGroup == null) {
            DesignDataGroup designDataGroup1 = this.reverseModelUtils.initDataGroup(dataSchemeKey, formSchemeTitle, null);
            DesignDataGroup designDataGroup2 = this.reverseModelUtils.initDataGroup(dataSchemeKey, formGroupTitle, designDataGroup1.getKey());
            DesignDataGroup designDataGroup3 = this.reverseModelUtils.initDataGroup(dataSchemeKey, formTitle, designDataGroup2.getKey());
            String[] strings = this.designDataSchemeService.insertDataGroups(Arrays.asList(designDataGroup1, designDataGroup2, designDataGroup3));
            return strings[strings.length - 1];
        }
        DesignDataGroup fgGroup = dataGroupList.parallelStream().filter(g -> dsGroup.getKey().equals(g.getParentKey()) && formGroupTitle.equals(g.getTitle())).findFirst().orElse(null);
        if (fgGroup == null) {
            DesignDataGroup designDataGroup2 = this.reverseModelUtils.initDataGroup(dataSchemeKey, formGroupTitle, dsGroup.getKey());
            DesignDataGroup designDataGroup3 = this.reverseModelUtils.initDataGroup(dataSchemeKey, formTitle, designDataGroup2.getKey());
            String[] strings = this.designDataSchemeService.insertDataGroups(Arrays.asList(designDataGroup2, designDataGroup3));
            return strings[strings.length - 1];
        }
        DesignDataGroup fGroup = dataGroupList.parallelStream().filter(g -> fgGroup.getKey().equals(g.getParentKey()) && formTitle.equals(g.getTitle())).findFirst().orElse(null);
        if (fGroup == null) {
            fGroup = this.reverseModelUtils.initDataGroup(dataSchemeKey, formTitle, fgGroup.getKey());
            return this.designDataSchemeService.insertDataGroup(fGroup);
        }
        return fGroup.getKey();
    }

    private String judgeFieldCode(HashSet<String> fieldCodes, String tableCodePrefix, int start) {
        StringBuilder build = new StringBuilder(tableCodePrefix).append(UNDERLINE).append(this.fillIndexNum(start));
        while (fieldCodes.contains(build.toString())) {
            build = new StringBuilder(tableCodePrefix).append(UNDERLINE).append(this.fillIndexNum(++start));
        }
        fieldCodes.add(build.toString());
        return build.toString();
    }

    private void initFieldByType(ReverseDataFieldVO field, int fieldType) {
        switch (fieldType) {
            case 6: {
                field.setDecimal(0);
                field.setPrecision(50);
                field.setMeasureUnit(null);
                field.setAllowMultipleSelect(false);
                field.setApplyType(null);
                break;
            }
            case 10: {
                field.setDecimal(2);
                field.setPrecision(20);
                field.setRefDataFieldKey(null);
                field.setAllowMultipleSelect(null);
                field.setAllowUndefinedCode(null);
                field.setDataFieldGatherType(DataFieldGatherType.SUM);
                field.setGatherTypeTitle(DataFieldGatherType.SUM.getTitle());
                field.setMeasureUnit("YUAN");
                break;
            }
            case 5: {
                field.setDecimal(0);
                field.setPrecision(10);
                field.setDataFieldGatherType(DataFieldGatherType.SUM);
                field.setGatherTypeTitle(DataFieldGatherType.SUM.getTitle());
                field.setMeasureUnit("YUAN");
                field.setRefDataFieldKey(null);
                field.setAllowMultipleSelect(null);
                field.setAllowUndefinedCode(null);
                break;
            }
            default: {
                field.setPrecision(0);
                field.setApplyType(null);
                field.setMeasureUnit(null);
                field.setDecimal(0);
                field.setRefDataFieldKey(null);
                field.setAllowMultipleSelect(false);
                field.setAllowUndefinedCode(null);
            }
        }
    }

    @Override
    public Map<String, List<ReverseDataFieldVO>> createFieldsAndCodes(ReverseCreateFieldPM fieldsPM) {
        int formType;
        HashMap<String, List<ReverseDataFieldVO>> res = new HashMap<String, List<ReverseDataFieldVO>>();
        FieldSearchQuery query = new FieldSearchQuery();
        query.setKeyword(fieldsPM.getFormCode());
        query.setScheme(fieldsPM.getDataSchemeKey());
        List fields = this.dataFieldDesignService.filterField(query);
        int n = formType = fieldsPM.getFormType() == null ? 0 : fieldsPM.getFormType();
        if (FormType.FORM_TYPE_NEWFMDM.getValue() == formType || FormType.FORM_TYPE_FMDM.getValue() == formType) {
            for (ReverseCreateFieldPM.Region region : fieldsPM.getRegions()) {
                if (region == null) continue;
                res.put(region.getRegionKey(), this.createField(fieldsPM, region, fields));
            }
            return res;
        }
        for (ReverseCreateFieldPM.Region region : fieldsPM.getRegions()) {
            if (region.getFieldKind() == DataFieldKind.FIELD_ZB.getValue()) {
                res.put(region.getRegionKey(), this.createField(fieldsPM, region, fields));
                continue;
            }
            if (region.getFieldKind() != DataFieldKind.FIELD.getValue()) continue;
            res.put(region.getRegionKey(), this.createZDField(fieldsPM, region));
        }
        return res;
    }

    @Override
    public Map<String, Boolean> reverseFieldChecks(ReverseBatchCheckPM checkPM) {
        String dataSchemeKey = checkPM.getDataSchemeKey();
        List<ReverseDataFieldVO> dataFields = checkPM.getDataFields();
        if (CollectionUtils.isEmpty(dataFields)) {
            return Collections.emptyMap();
        }
        HashMap zdData = new HashMap();
        HashSet<String> zbCodes = new HashSet<String>();
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        List allDataField = this.designDataSchemeService.getAllDataField(dataSchemeKey);
        for (DesignDataField designDataField : allDataField) {
            if (designDataField.getDataFieldKind() == DataFieldKind.FIELD_ZB) {
                zbCodes.add(designDataField.getCode());
                continue;
            }
            if (designDataField.getDataFieldKind() == DataFieldKind.FIELD || designDataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) {
                zdData.computeIfAbsent(designDataField.getDataTableKey(), key -> new HashSet());
                ((Set)zdData.get(designDataField.getDataTableKey())).add(designDataField.getCode());
                continue;
            }
            zdData.computeIfAbsent(designDataField.getDataTableKey(), key -> new HashSet());
            ((Set)zdData.get(designDataField.getDataTableKey())).add(designDataField.getCode());
            zbCodes.add(designDataField.getCode());
        }
        for (ReverseDataFieldVO reverseDataFieldVO : dataFields) {
            Set aDefault;
            if (reverseDataFieldVO.getDataFieldKind() == DataFieldKind.FIELD_ZB) {
                result.put(reverseDataFieldVO.getKey(), !zbCodes.contains(reverseDataFieldVO.getCode()));
                continue;
            }
            if (reverseDataFieldVO.getDataFieldKind() == DataFieldKind.FIELD || reverseDataFieldVO.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) {
                aDefault = zdData.getOrDefault(reverseDataFieldVO.getDataTableKey(), Collections.emptySet());
                result.put(reverseDataFieldVO.getKey(), !aDefault.contains(reverseDataFieldVO.getCode()));
                continue;
            }
            aDefault = zdData.getOrDefault(reverseDataFieldVO.getDataTableKey(), Collections.emptySet());
            result.put(reverseDataFieldVO.getKey(), !aDefault.contains(reverseDataFieldVO.getCode()) && !zbCodes.contains(reverseDataFieldVO.getCode()));
        }
        return result;
    }

    @Override
    public void reverseFieldsSave(ReverseFormVO reverseFormVO) throws JQException {
        Map<String, ReverseRegionVO> regions = reverseFormVO.getRegions();
        ArrayList<DesignDataTableDO> insertTables = new ArrayList<DesignDataTableDO>();
        ArrayList<ReverseDataTableVO> updateTables = new ArrayList<ReverseDataTableVO>();
        ArrayList<DesignDataLinkDefineImpl> insertLinks = new ArrayList<DesignDataLinkDefineImpl>();
        ArrayList<DesignDataFieldDO> insertFields = new ArrayList<DesignDataFieldDO>();
        HashMap<String, String> floatRegions = new HashMap<String, String>();
        String groupKey = this.queryTableGroup(reverseFormVO.getDataSchemeKey(), reverseFormVO.getFormSchemeTitle(), reverseFormVO.getFormGroupTitle(), reverseFormVO.getFormTitle());
        for (Map.Entry<String, ReverseRegionVO> mapEntry : regions.entrySet()) {
            String regionKey = mapEntry.getKey();
            ReverseRegionVO region = mapEntry.getValue();
            if (region.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST || region.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST || region.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_AND_ROW_LIST) {
                this.putInFloatRegions(floatRegions, regionKey, region.getTables());
            }
            if (region.getTables() != null) {
                for (Map.Entry<String, IReverseState> entry : region.getTables().entrySet()) {
                    String tableKey = entry.getKey();
                    ReverseDataTableVO table = (ReverseDataTableVO)entry.getValue();
                    if (table.getState() == ReverseItemState.NEW) {
                        table.setDataGroupKey(groupKey);
                        insertTables.add(this.reverseModelUtils.convertTableDO(table));
                    } else if (table.getState() == ReverseItemState.UPDATE) {
                        updateTables.add(table);
                    }
                    if (table.getDataFields() == null) continue;
                    for (Map.Entry<String, ReverseDataFieldVO> entry2 : table.getDataFields().entrySet()) {
                        String fieldKey = entry2.getKey();
                        ReverseDataFieldVO field = entry2.getValue();
                        if (field.getState() != ReverseItemState.NEW) continue;
                        field.setDataTableKey(tableKey);
                        insertFields.add(this.reverseModelUtils.convertFieldDO(field));
                    }
                }
            }
            if (region.getLinks() == null) continue;
            for (Map.Entry<String, IReverseState> entry : region.getLinks().entrySet()) {
                String linkKey = entry.getKey();
                ReverseLinkVO link = (ReverseLinkVO)entry.getValue();
                if (link.getState() != ReverseItemState.NEW) continue;
                insertLinks.add(this.reverseModelUtils.convertLinkDO(link));
            }
        }
        try {
            if (!insertTables.isEmpty()) {
                this.designDataSchemeService.insertDataTables(insertTables);
            }
            if (!updateTables.isEmpty()) {
                this.designDataSchemeService.updateDataTables(this.reverseModelUtils.updateDataTableByVO(updateTables));
            }
            this.saveDataLinks(reverseFormVO.getFormKey(), insertLinks);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_208, e.getMessage());
        }
        try {
            List collect = insertFields.parallelStream().sorted(Comparator.comparing(DataFieldDO::getCode)).collect(Collectors.toList());
            for (DesignDataFieldDO f : collect) {
                f.setOrder(OrderGenerator.newOrder());
            }
            this.designDataSchemeService.insertDataFields(collect);
        }
        catch (SchemeDataException e) {
            logger.info(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_208, e.getMessage());
        }
        this.updateFormStyle(reverseFormVO.getFormKey(), reverseFormVO.getFormStyle());
        this.updateRegions(floatRegions);
    }

    private void saveDataLinks(String formKey, List<DesignDataLinkDefineImpl> insertLinks) throws Exception {
        List links = this.designDataLinkDefineService.getAllLinksInForm(formKey);
        Set oldLinkSet = links.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        HashMap<String, DataLinkDefine> posMap = new HashMap<String, DataLinkDefine>();
        HashSet<String> deleteLinks = new HashSet<String>();
        links.addAll(insertLinks);
        for (DataLinkDefine link2 : links) {
            String pos = link2.getPosX() + "-" + link2.getPosY();
            DataLinkDefine oldLink = (DataLinkDefine)posMap.get(pos);
            if (oldLink == null) {
                posMap.put(pos, link2);
                continue;
            }
            if (oldLink.getUniqueCode().compareTo(link2.getUniqueCode()) < 0) {
                posMap.put(pos, link2);
                deleteLinks.add(oldLink.getKey());
                continue;
            }
            deleteLinks.add(link2.getKey());
        }
        DesignDataLinkDefine[] insert = (DesignDataLinkDefine[])insertLinks.stream().filter(link -> !deleteLinks.contains(link.getKey())).toArray(DesignDataLinkDefine[]::new);
        if (insert.length > 0) {
            this.designDataLinkDefineService.insert(insert);
        }
        deleteLinks.retainAll(oldLinkSet);
        if (!deleteLinks.isEmpty()) {
            this.designDataLinkDefineService.delete(deleteLinks.toArray(new String[deleteLinks.size()]));
        }
    }

    private void filterDataLink(Map<String, DataLinkDefine> posMap, List<String> deleteLinks, List<DataLinkDefine> links) {
    }

    private void putInFloatRegions(Map<String, String> floatRegions, String regionKey, Map<String, ReverseDataTableVO> tables) {
        if (tables != null && !tables.values().isEmpty()) {
            ArrayList<ReverseDataTableVO> collect = new ArrayList<ReverseDataTableVO>(tables.values());
            floatRegions.put(regionKey, ((ReverseDataTableVO)collect.get(0)).getKey());
        }
    }

    private void updateRegions(Map<String, String> floatRegions) {
        for (Map.Entry<String, String> entry : floatRegions.entrySet()) {
            DesignDataField field;
            DesignDataRegionDefine regionDefine = this.designTimeViewController.queryDataRegionDefine(entry.getKey());
            if (StringUtils.hasLength(regionDefine.getInputOrderFieldKey()) || (field = this.designDataSchemeService.getDataFieldByTableKeyAndCode(entry.getValue(), "FLOATORDER")) == null) continue;
            regionDefine.setInputOrderFieldKey(field.getKey());
            this.designTimeViewController.updateDataRegionDefine(regionDefine);
        }
    }

    private void updateFormStyle(String formKey, Grid2Data formStyle) throws JQException {
        if (!StringUtils.hasText(formKey) || null == formStyle) {
            return;
        }
        try {
            this.designFormDefineService.updateReportData(formKey, Grid2Data.gridToBytes((Grid2Data)formStyle));
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_211, (Throwable)e);
        }
    }
}

