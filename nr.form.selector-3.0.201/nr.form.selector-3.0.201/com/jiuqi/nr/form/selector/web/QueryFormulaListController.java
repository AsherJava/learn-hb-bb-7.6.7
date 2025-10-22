/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.form.selector.web;

import com.jiuqi.nr.form.selector.entity.FormulaDataInputParam;
import com.jiuqi.nr.form.selector.entity.FormulaTableDataSet;
import com.jiuqi.nr.form.selector.entity.OneAuditTypeData;
import com.jiuqi.nr.form.selector.service.IQueryAllAuditTypeService;
import com.jiuqi.nr.form.selector.service.IQueryFormulaListService;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import java.util.LinkedList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/report-forms/formulas"})
public class QueryFormulaListController {
    @Autowired
    IQueryFormulaListService service;
    @Autowired
    IQueryAllAuditTypeService queryAllAuditTypeService;

    @PostMapping(value={"/loading-formula-datas"})
    @ResponseBody
    public IReturnObject<FormulaTableDataSet> queryFormulaListOfForms(@Valid @RequestBody FormulaDataInputParam inputParam) throws Exception {
        FormulaTableDataSet tableDataSet = null;
        IReturnObject returnObject = null;
        try {
            tableDataSet = this.service.queryFormulaListOfForms(inputParam);
            returnObject = IReturnObject.getSuccessInstance((Object)tableDataSet);
        }
        catch (Exception e) {
            e.printStackTrace();
            returnObject = IReturnObject.getErrorInstance((String)("\u52a0\u8f7d-\u62a5\u8868\u6811\u5f02\u5e38\uff1a" + e.getMessage()), (Object)tableDataSet);
        }
        return returnObject;
    }

    @RequestMapping(value={"/loading-allAuditType-datas"})
    public IReturnObject<LinkedList<OneAuditTypeData>> queryAllAuditType() throws Exception {
        LinkedList<OneAuditTypeData> allAuditTypeReturnData = null;
        IReturnObject returnObject = null;
        try {
            allAuditTypeReturnData = this.queryAllAuditTypeService.queryAllAuditType();
            returnObject = IReturnObject.getSuccessInstance(allAuditTypeReturnData);
        }
        catch (Exception e) {
            e.printStackTrace();
            returnObject = IReturnObject.getErrorInstance((String)("\u52a0\u8f7d-\u62a5\u8868\u6811\u5f02\u5e38\uff1a" + e.getMessage()), allAuditTypeReturnData);
        }
        return returnObject;
    }
}

