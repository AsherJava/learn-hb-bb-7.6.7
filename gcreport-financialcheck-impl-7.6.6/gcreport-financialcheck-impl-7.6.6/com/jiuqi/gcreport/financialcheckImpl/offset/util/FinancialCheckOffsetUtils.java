/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nvwa.certification.bean.NvwaApp
 *  com.jiuqi.nvwa.certification.service.INvwaAppService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nvwa.certification.bean.NvwaApp;
import com.jiuqi.nvwa.certification.service.INvwaAppService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

public class FinancialCheckOffsetUtils {
    public static String getItemKey(GcOffsetRelatedItemEO item) {
        String key = item.getRelatedItemId() + item.getSystemId();
        return DigestUtils.md5DigestAsHex(key.toString().getBytes(Charset.defaultCharset()));
    }

    public static String convertPeriodBySystemId(String systemId, String dataTime) {
        ConsolidatedSystemEO system = ((ConsolidatedSystemService)SpringBeanUtils.getBean(ConsolidatedSystemService.class)).getConsolidatedSystemEO(systemId);
        if (system == null) {
            return null;
        }
        List dataDimensions = ((IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class)).getDataSchemeDimension(system.getDataSchemeKey(), DimensionType.PERIOD);
        if (CollectionUtils.isEmpty(dataDimensions)) {
            return null;
        }
        PeriodType periodType = ((DataDimension)dataDimensions.get(0)).getPeriodType();
        if (!EnumSet.of(PeriodType.YEAR, PeriodType.HALFYEAR, PeriodType.SEASON, PeriodType.MONTH).contains(periodType)) {
            return null;
        }
        PeriodWrapper periodWrapper = periodType.fromCalendar(PeriodType.MONTH.toCalendar(new PeriodWrapper(dataTime)));
        return periodWrapper.toString();
    }

    public static List<Map<String, String>> getOffsetGroupingField() {
        ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
        try {
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByName("GC_RELATED_ITEM", OuterDataSourceUtils.getOuterDataSourceCode((String)"jiuqi.gcreport.mdd.datasource"));
            List allFieldsInTable = dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
            Map<String, String> colName2TitleMap = allFieldsInTable.stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getTitle));
            result.add(new HashMap<String, String>(){
                {
                    this.put("key", "UNITID");
                    this.put("label", "\u672c\u65b9\u5355\u4f4d");
                }
            });
            result.add(new HashMap<String, String>(){
                {
                    this.put("key", "OPPUNITID");
                    this.put("label", "\u5bf9\u65b9\u5355\u4f4d");
                }
            });
            result.add(new HashMap<String, String>(){
                {
                    this.put("key", "SUBJECTCODE");
                    this.put("label", "\u79d1\u76ee");
                }
            });
            result.add(new HashMap<String, String>(){
                {
                    this.put("key", "ORIGINALCURR");
                    this.put("label", "\u6e90\u5e01\u5e01\u79cd");
                }
            });
            List dims = ((DimensionService)SpringBeanUtils.getBean(DimensionService.class)).findDimFieldsByTableName("GC_RELATED_ITEM");
            dims.forEach(dim -> result.add(new HashMap<String, String>(){
                {
                    this.put("key", dim.getCode());
                    this.put("label", dim.getTitle());
                }
            }));
            result.forEach(column -> {
                String cfr_ignored_0 = (String)column.put("label", colName2TitleMap.getOrDefault(column.get("key"), (String)column.get("label")));
            });
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u62b5\u6d88\u5206\u7ec4\u5b57\u6bb5\u5931\u8d25", (Throwable)e);
        }
        return result;
    }

    public static String getFcServerUrl() {
        NvwaApp reltxn = ((INvwaAppService)SpringBeanUtils.getBean(INvwaAppService.class)).selectByCode("GLJY");
        if (reltxn == null || StringUtils.isEmpty(reltxn.getUrl())) {
            return null;
        }
        return reltxn.getUrl().trim();
    }
}

