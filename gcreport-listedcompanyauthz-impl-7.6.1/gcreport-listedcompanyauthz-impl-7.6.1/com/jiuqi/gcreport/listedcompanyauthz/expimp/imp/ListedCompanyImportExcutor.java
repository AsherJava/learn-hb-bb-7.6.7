/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 */
package com.jiuqi.gcreport.listedcompanyauthz.expimp.imp;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor;
import com.jiuqi.gcreport.listedcompanyauthz.expimp.base.ListedCompanyExpImpVO;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListedCompanyImportExcutor
extends AbstractImportExcelModelExecutor<ListedCompanyExpImpVO> {
    @Autowired
    private FListedCompanyAuthzService service;

    protected ListedCompanyImportExcutor() {
        super(ListedCompanyExpImpVO.class);
    }

    public String getName() {
        return "ListedCompanyImportExcutor";
    }

    protected Object importExcelModels(ImportContext context, List<ListedCompanyExpImpVO> rowDatas) {
        ArrayList<ListedCompanyAuthzVO> datas = new ArrayList();
        if (rowDatas != null && rowDatas.size() > 0) {
            datas = rowDatas.stream().map(vo -> this.convert((ListedCompanyExpImpVO)vo)).collect(Collectors.toList());
            this.service.save(datas);
        }
        return JsonUtils.writeValueAsString(datas);
    }

    private ListedCompanyAuthzVO convert(ListedCompanyExpImpVO vo) {
        ListedCompanyAuthzVO ei = new ListedCompanyAuthzVO();
        ei.setOrgCode(vo.getOrgCode());
        ei.setOrgName(vo.getOrgName());
        ei.setUserName(vo.getUserName());
        ei.setUserTitle(vo.getUserTitle());
        ei.setUserBelongOrg(vo.getUserBelongOrg());
        ei.setUserRole(vo.getUserRole());
        ei.setIsPenetrate(Boolean.valueOf("\u652f\u6301".equals(vo.getIsPenetrate())));
        return ei;
    }
}

