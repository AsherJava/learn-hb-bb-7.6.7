/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 */
package com.jiuqi.gcreport.listedcompanyauthz.expimp.exp;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.listedcompanyauthz.expimp.base.ListedCompanyExpImpVO;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListedCompanyExportExcutor
extends AbstractExportExcelModelExecutor<ListedCompanyExpImpVO> {
    @Autowired
    private FListedCompanyAuthzService service;

    public ListedCompanyExportExcutor() {
        super(ListedCompanyExpImpVO.class);
    }

    public String getName() {
        return "ListedCompanyExportExcutor";
    }

    protected List<ListedCompanyExpImpVO> exportExcelModels(ExportContext context) {
        ListedCompanyAuthzVO param = new ListedCompanyAuthzVO();
        param.setOrgCode(context.getParam());
        List<ListedCompanyAuthzVO> query = this.service.query(param);
        return query.stream().map(v -> this.convert((ListedCompanyAuthzVO)v)).collect(Collectors.toList());
    }

    private ListedCompanyExpImpVO convert(ListedCompanyAuthzVO vo) {
        ListedCompanyExpImpVO ei = new ListedCompanyExpImpVO();
        ei.setOrgCode(vo.getOrgCode());
        ei.setOrgName(vo.getOrgName());
        ei.setUserName(vo.getUserName());
        ei.setUserTitle(vo.getUserTitle());
        ei.setUserBelongOrg(vo.getUserBelongOrg());
        ei.setUserRole(vo.getUserRole());
        return ei;
    }
}

