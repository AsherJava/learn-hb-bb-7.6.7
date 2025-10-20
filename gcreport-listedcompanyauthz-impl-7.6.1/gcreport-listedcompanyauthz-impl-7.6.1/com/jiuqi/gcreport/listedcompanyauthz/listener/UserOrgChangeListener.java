/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 *  com.jiuqi.np.authz2.event.EntityIdentityGrantedEvent
 *  com.jiuqi.np.authz2.event.EntityIdentityRevokedEvent
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.listedcompanyauthz.listener;

import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyAuthzEO;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import com.jiuqi.np.authz2.event.EntityIdentityGrantedEvent;
import com.jiuqi.np.authz2.event.EntityIdentityRevokedEvent;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserOrgChangeListener {
    @Autowired
    private FListedCompanyAuthzService service;
    @Autowired
    private DataModelService dataModelService;

    @EventListener
    public void onListedCompanyChanged(EntityIdentityGrantedEvent event) {
        ArrayList<ListedCompanyAuthzVO> datas = new ArrayList<ListedCompanyAuthzVO>();
        event.getChangedItemIterator().forEachRemaining(vo -> {
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(vo.getEntityTableKey());
            if ("MD_ORG".equals(tableModelDefine.getName())) {
                ListedCompanyAuthzVO authzVo = new ListedCompanyAuthzVO();
                authzVo.setOrgCode(vo.getEntityKeyData());
                authzVo.setUserId(vo.getIdentityId());
                datas.add(authzVo);
            }
        });
        if (datas.size() > 0) {
            this.service.save(datas);
        }
    }

    @EventListener
    public void onIdentityRoleChanged(EntityIdentityGrantedEvent event) {
        ArrayList<ListedCompanyAuthzVO> datas = new ArrayList<ListedCompanyAuthzVO>();
        event.getChangedItemIterator().forEachRemaining(vo -> {
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(vo.getEntityTableKey());
            if ("MD_ORG".equals(tableModelDefine.getName())) {
                ListedCompanyAuthzVO authzVo = new ListedCompanyAuthzVO();
                authzVo.setOrgCode(vo.getEntityKeyData());
                authzVo.setUserId(vo.getIdentityId());
                datas.add(authzVo);
            }
        });
        if (datas.size() > 0) {
            this.service.save(datas);
        }
    }

    @EventListener
    public void onPrivilegeChanged(EntityIdentityRevokedEvent event) {
        event.getChangedItemIterator().forEachRemaining(vo -> {
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(vo.getEntityTableKey());
            if ("MD_ORG".equals(tableModelDefine.getName())) {
                ListedCompanyAuthzVO authzVo = new ListedCompanyAuthzVO();
                authzVo.setOrgCode(vo.getEntityKeyData());
                authzVo.setUserId(vo.getIdentityId());
                List<ListedCompanyAuthzVO> query = this.service.query(authzVo);
                if (query != null && query.size() > 0) {
                    query.forEach(v -> {
                        if (v.getCreateUser() == null && v.getCreateTime() == null) {
                            ListedCompanyAuthzEO param = new ListedCompanyAuthzEO();
                            param.setId(v.getId());
                            this.service.delete(param);
                        }
                    });
                }
            }
        });
    }
}

