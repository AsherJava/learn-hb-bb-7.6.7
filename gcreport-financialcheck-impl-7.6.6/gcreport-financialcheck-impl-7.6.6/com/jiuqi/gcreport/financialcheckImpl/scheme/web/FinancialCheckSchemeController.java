/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.financialcheckapi.scheme.FinancialCheckSchemeClient
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckBusinessRoleOptionVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckMatchSchemeVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeBaseDataVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeInitDataVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeNumVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeTreeVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckapi.scheme.FinancialCheckSchemeClient;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckBusinessRoleOptionVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckMatchSchemeVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeBaseDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeInitDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeNumVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeTreeVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FinancialCheckSchemeController
implements FinancialCheckSchemeClient {
    @Autowired
    private FinancialCheckSchemeService schemeService;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<FinancialCheckSchemeInitDataVO> initData() {
        FinancialCheckSchemeInitDataVO vo = this.schemeService.initData();
        return BusinessResponseEntity.ok((Object)vo);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<FinancialCheckSchemeVO> addCheckScheme(@RequestBody FinancialCheckSchemeVO schemeV) {
        schemeV = this.schemeService.addOrUpdate(schemeV);
        return BusinessResponseEntity.ok((Object)schemeV);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> updateCheckScheme(@RequestBody FinancialCheckSchemeVO schemeV) {
        this.schemeService.addOrUpdate(schemeV);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<FinancialCheckSchemeTreeVO> saveCheckScheme(FinancialCheckSchemeVO schemeV) {
        return BusinessResponseEntity.ok((Object)this.schemeService.save(schemeV));
    }

    public BusinessResponseEntity<Object> validCheckScheme(String id) {
        return BusinessResponseEntity.ok((Object)this.schemeService.validCheckScheme(id));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> deleteCheckScheme(@PathVariable(name="id") String id) {
        this.schemeService.delete(id);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<FinancialCheckSchemeTreeVO>> treeCheckGroup(FinancialCheckGroupVO checkGroupVO) {
        return BusinessResponseEntity.ok(this.schemeService.treeCheckGroup(checkGroupVO, false));
    }

    public BusinessResponseEntity<List<FinancialCheckSchemeBaseDataVO>> treeEnableScheme(FinancialCheckGroupVO checkGroupVO) {
        return BusinessResponseEntity.ok(this.schemeService.treeEnableScheme(checkGroupVO));
    }

    public BusinessResponseEntity<FinancialCheckSchemeBaseDataVO> singleEnableScheme(FinancialCheckGroupVO checkGroupVO) {
        return BusinessResponseEntity.ok((Object)this.schemeService.singleEnableScheme(checkGroupVO));
    }

    public BusinessResponseEntity<List<FinancialCheckSchemeBaseDataVO>> allEnableScheme(FinancialCheckGroupVO checkGroupVO) {
        return BusinessResponseEntity.ok(this.schemeService.allEnableScheme(checkGroupVO));
    }

    public BusinessResponseEntity<FinancialCheckSchemeNumVO> countScheme(FinancialCheckGroupVO checkGroupVO) {
        return BusinessResponseEntity.ok((Object)this.schemeService.countScheme(checkGroupVO));
    }

    public BusinessResponseEntity<FinancialCheckSchemeVO> queryCheckScheme(String id) {
        return BusinessResponseEntity.ok((Object)this.schemeService.queryCheckScheme(id));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> startCheckScheme(@PathVariable(name="id") String id, @PathVariable(name="startFlag") boolean startFlag) {
        this.schemeService.start(id, startFlag);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> moveCheckScheme(@PathVariable(name="id") String id, @PathVariable(name="step") double step) {
        this.schemeService.move(id, step);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> matchScheme(FinancialCheckMatchSchemeVO financialCheckMatchSchemeVO) {
        this.schemeService.matchScheme(financialCheckMatchSchemeVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> cancelMatch(FinancialCheckMatchSchemeVO financialCheckMatchSchemeVO) {
        this.schemeService.cancelCheckScheme(financialCheckMatchSchemeVO.getId(), financialCheckMatchSchemeVO.getAcctYear(), financialCheckMatchSchemeVO.getAcctPeriod(), financialCheckMatchSchemeVO.getSubjectCodes());
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<CheckBusinessRoleOptionVO>> queryBusinessRoleOptions(Map<String, String> params) {
        return BusinessResponseEntity.ok(this.schemeService.queryBusinessRoleOptions(params));
    }
}

