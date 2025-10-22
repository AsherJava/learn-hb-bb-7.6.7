/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.consts.ZBDataType
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ReportTableType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package nr.single.para.parain.service;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.consts.ZBDataType;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.single.para.parain.internal.cache.SchemeInfoCache;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;

public interface IParaImportCommonService {
    public void MakeTaskFieldsCache(TaskImportContext var1) throws Exception;

    public void MakeTaskFieldsCache(TaskImportContext var1, String var2) throws Exception;

    public void MakeTaskFieldsCacheByDataScheme(SchemeInfoCache var1, List<String> var2) throws Exception;

    public String[] getBizKeyFieldsID(String var1);

    public void doMeasureUnitMap(TaskImportContext var1);

    public int getTaskPeriodCount(TaskImportContext var1);

    public EnumsItemModel getPeriodEnum(TaskImportContext var1, EnumsItemModel var2);

    public EnumsItemModel getPeriodEnumByPeriodType(TaskImportContext var1, EnumsItemModel var2, PeriodType var3, boolean var4);

    public PeriodType getTaskPeriod(TaskImportContext var1);

    public PeriodType getTaskPeriod(String var1);

    public String getPeriodTypeCode(PeriodType var1);

    public String getLasPeriodCodeType(String var1, PeriodType var2);

    public FieldType getFieldType(ZBDataType var1);

    public DataFieldType getDataFieldType(ZBDataType var1);

    public DataModelType.ColumnType getColumnType(ZBDataType var1);

    public int getDataModelType(ZBDataType var1);

    public FieldType getFieldTypeByColumType(ColumnModelType var1);

    public FormType getFormTypeFromSingle(ReportTableType var1, boolean var2);

    public boolean getFMDMIsData(TaskImportContext var1);

    public String UpdatePeriodEntity(TaskImportContext var1) throws Exception;

    public void UpdateEnumMap(TaskImportContext var1, EnumsItemModel var2, TableInfoDefine var3);

    public void setEnityParentField(ParaInfo var1, String var2);

    public void setEnityCodeField(ParaInfo var1, String var2);

    public boolean getUniqueField(FMRepInfo var1, List<RepInfo> var2);

    public Map<String, Set<String>> getReportRepeatFields(FMRepInfo var1, List<RepInfo> var2);

    public Map<String, String> getEnumMapNets(TaskImportContext var1);
}

