/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.summary.service;

import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.vo.EntityQueryParam;
import com.jiuqi.nr.summary.vo.EntityTitleQueryParam;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public interface SummaryParamService {
    public List<TaskDefine> getAllTaskDefines();

    public List<TaskDefine> getRelTaskDefines(String var1) throws SummaryCommonException;

    public TaskDefine getTaskDefine(String var1) throws SummaryCommonException;

    public List<IEntityDefine> getSceneDimensions(String var1) throws SummaryCommonException;

    public IEntityDefine getDimension(String var1) throws SummaryCommonException;

    public List<IEntityDefine> getTargetDimensions(String var1) throws SummaryCommonException;

    public List<IEntityAttribute> getDimensionAttributes(String var1);

    public List<IEntityRow> getEntityRows(EntityQueryParam var1) throws Exception;

    public List<IEntityRow> getEntityRows(EntityTitleQueryParam var1) throws Exception;

    public List<CalibreDefineDTO> getCalibreDefines(String var1);

    public List<CalibreDataDTO> getCalibreDatas(String var1);

    public List<IEntityDefine> getRelDimensions(String var1);

    public List<TableModelDefine> getTableModelDefines(String var1);

    public List<DataField> getTableDimensions(List<String> var1) throws SummaryCommonException;

    public List<DataScheme> getDataSchemes(String var1) throws SummaryCommonException;

    public List<DataTable> getAllDataTable(String var1);

    public List<DataField> getAllDataField(String var1);

    public DataScheme getSummaryDataScheme(String var1);
}

