/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.client.assistdim.enums.AssistDimEffectTableEnum
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.dimension.dto.DimensionDTO
 *  com.jiuqi.gcreport.dimension.internal.service.impl.DefaultPublishServiceImpl
 *  com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils$FieldType
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.jdialect.model.ColumnModel
 */
package com.jiuqi.dc.base.impl.assistdim.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.client.assistdim.enums.AssistDimEffectTableEnum;
import com.jiuqi.dc.base.impl.acctperiod.service.AcctPeriodService;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.internal.service.impl.DefaultPublishServiceImpl;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.jdialect.model.ColumnModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AssistDimUtil {
    public static final Integer FN_DEFAULT_VALUELENGTH = 60;
    public static final Integer FD_DEFAULT_VALUEPRECISION = 0;
    private static final String EFFECT_TABLE_NAME_TEMPLATE = "\u3010%1$s\u3011%2$s";
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z]\\w*");

    private AssistDimUtil() {
    }

    public static boolean codeMatchPattern(String code) {
        return CODE_PATTERN.matcher(code).matches();
    }

    public static List<DimensionVO> listPublished() {
        DimensionService dimensionService = (DimensionService)ApplicationContextRegister.getBean(DimensionService.class);
        return dimensionService.loadAllDimensions();
    }

    public static List<DimensionVO> listByEffectTable(String tableName) {
        DimensionService dimensionService = (DimensionService)ApplicationContextRegister.getBean(DimensionService.class);
        return dimensionService.findDimFieldsVOByTableName(tableName);
    }

    public static List<DimensionVO> listByEffectTable(AssistDimEffectTableEnum effectTable) {
        DimensionService dimensionService = (DimensionService)ApplicationContextRegister.getBean(DimensionService.class);
        return dimensionService.findDimFieldsVOByTableName(effectTable.name());
    }

    public static List<DimensionVO> listByEffectTable(List<String> tableNames) {
        DimensionService dimensionService = (DimensionService)ApplicationContextRegister.getBean(DimensionService.class);
        Map<Object, Object> result = new HashMap();
        for (String tableName : tableNames) {
            List dimFieldsVOByTableName = dimensionService.findDimFieldsVOByTableName(tableName);
            Map<String, DimensionVO> dimMap = dimFieldsVOByTableName.stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (k1, k2) -> k2));
            if (result.size() == 0) {
                result = dimMap;
                continue;
            }
            result = result.entrySet().stream().filter(entry -> dimMap.containsKey(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return new ArrayList<DimensionVO>(result.values());
    }

    public static List<DimensionVO> listConvertByOppositeAssistDim() {
        return AssistDimUtil.listByEffectTable(AssistDimEffectTableEnum.DC_VOUCHERITEMASS.name()).stream().filter(dim -> dim.getConvertByOpposite() != null && 1 == dim.getConvertByOpposite()).collect(Collectors.toList());
    }

    public static ColumnModel addAssistColumn(JTableModel jTableModel, DimensionVO assistDim) {
        if (!StringUtils.isEmpty((String)assistDim.getReferField())) {
            return jTableModel.column(assistDim.getCode()).comment(assistDim.getTitle()).NVARCHAR(assistDim.getFieldSize()).defaultValue("'#'");
        }
        if (FieldTypeUtils.FieldType.FIELD_TYPE_DATE.getNrValue() == assistDim.getFieldType().intValue()) {
            return jTableModel.column(assistDim.getCode()).comment(assistDim.getTitle()).NVARCHAR(assistDim.getFieldSize()).defaultValue("1970-01-01");
        }
        return jTableModel.column(assistDim.getCode()).comment(assistDim.getTitle()).NVARCHAR(assistDim.getFieldSize()).defaultValue("'#'");
    }

    public static void syncVchrTableWithItemByItemAssistDim(String tenantName, String assistFlag) {
        List<Object> yearList;
        DefaultPublishServiceImpl publishService = (DefaultPublishServiceImpl)ApplicationContextRegister.getBean(DefaultPublishServiceImpl.class);
        DimensionService dimensionService = (DimensionService)ApplicationContextRegister.getBean(DimensionService.class);
        Set tableList = dimensionService.listTableNamesByDimCode(assistFlag);
        String fieldName = String.format("SRC_%1$s_ID", assistFlag);
        try {
            yearList = ((AcctPeriodService)ApplicationContextRegister.getBean(AcctPeriodService.class)).listYear();
        }
        catch (Exception e) {
            yearList = CollectionUtils.newArrayList();
        }
        if (CollectionUtils.isEmpty((Collection)yearList)) {
            yearList.add(DateUtils.getYearOfDate((Date)new Date()));
        }
        yearList.add(DateUtils.getYearOfDate((Date)new Date()) + 1);
        if (!CollectionUtils.isEmpty(yearList)) {
            yearList.forEach(year -> {
                TableModelDefine tableModelDefine = publishService.checkDesignAndRunTimeDiff("DC_VOUCHERITEMASS_" + year, fieldName);
                DimensionDTO dimensionDTO = new DimensionDTO();
                dimensionDTO.setCode(fieldName);
                dimensionDTO.setTitle(fieldName);
                dimensionDTO.setFieldType(Integer.valueOf(DBColumn.DBType.NVarchar.getType()));
                dimensionDTO.setFieldSize(FN_DEFAULT_VALUELENGTH);
                dimensionDTO.setDefaultValue("'#'");
                publishService.publish(tableModelDefine, dimensionDTO);
                if (tableList.contains("DC_CFVOUCHERITEMASS_".substring(0, "DC_CFVOUCHERITEMASS_".length() - 1))) {
                    TableModelDefine cfTableModelDefine = publishService.checkDesignAndRunTimeDiff("DC_CFVOUCHERITEMASS_" + year, fieldName);
                    publishService.publish(cfTableModelDefine, dimensionDTO);
                }
            });
        }
    }
}

