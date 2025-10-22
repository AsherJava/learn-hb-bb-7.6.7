/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.gcreport.bde.fetch.impl.handler;

import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.gcreport.bde.fetch.impl.handler.AbstractFetchResultHandler;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FixedFetchResultHandler
extends AbstractFetchResultHandler {
    private Map<String, Object> fixedResult;
    private List<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();

    public FixedFetchResultHandler(FetchRequestDTO fetchRequestDTO, FetchResultDTO fetchResponseDTO) {
        super(fetchRequestDTO, fetchResponseDTO);
        this.fixedResult = fetchResponseDTO.getFixedResults();
        if (this.fixedResult == null) {
            this.fixedResult = new HashMap<String, Object>();
        }
        this.initFieldDefineList(fetchRequestDTO.getFetchContext().getFetchSchemeId(), fetchRequestDTO.getFetchContext().getFormSchemeId(), fetchRequestDTO.getFetchContext().getFormId(), fetchRequestDTO.getFetchContext().getRegionId());
    }

    @Override
    public void save() {
        IDataTable dataTable;
        IDataQuery dataQuery = this.newDataQuery(this.getDataRegion().getKey(), this.getFetchFields());
        try {
            dataTable = dataQuery.executeQuery(this.getExecutorContext());
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u67e5\u8be2\u8868\u5355\u201c" + this.getFormDefine().getTitle() + "\u201d\u56fa\u5b9a\u533a\u57df\u6570\u636e\u5931\u8d25:" + e.getMessage(), (Throwable)e);
        }
        IDataRow destDataRow = null;
        if (dataTable.getCount() == 0) {
            try {
                destDataRow = dataTable.appendRow(this.getDimensionValueSet());
            }
            catch (IncorrectQueryException e) {
                throw new BdeRuntimeException("\u7ed9\u8868\u5355\u201c" + this.getFormDefine().getTitle() + "\u201d\u56fa\u5b9a\u533a\u57df\u63d2\u5165\u884c\u5931\u8d25:" + e.getMessage(), (Throwable)e);
            }
        } else {
            destDataRow = dataTable.getItem(0);
        }
        for (FieldDefine fieldDefine : this.getFetchFields()) {
            if (!this.fixedResult.containsKey(fieldDefine.getKey())) continue;
            destDataRow.setValue(fieldDefine, this.fixedResult.get(fieldDefine.getKey()));
        }
        try {
            dataTable.commitChanges(true);
        }
        catch (Exception e) {
            String message = e.getMessage();
            if (e.getCause() != null) {
                message = message + e.getCause().getMessage();
            }
            throw new BdeRuntimeException(message, (Throwable)e);
        }
    }

    private void initFieldDefineList(String formulaSchemeId, String formSchemeId, String formId, String regionId) {
        try {
            Set fieldDefineIds = this.fetchSettingDao.listFormulaFieldId(formulaSchemeId, formSchemeId, formId, regionId);
            for (String fieldDefineId : fieldDefineIds) {
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldDefineId);
                this.fieldDefines.add(fieldDefine);
            }
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u67e5\u8be2\u8868\u5355\u201c" + this.getFormDefine().getTitle() + "\u201d\u53d6\u6570\u6307\u6807\u5931\u8d25:" + e.getMessage(), (Throwable)e);
        }
    }

    public List<FieldDefine> getFetchFields() {
        return this.fieldDefines;
    }
}

