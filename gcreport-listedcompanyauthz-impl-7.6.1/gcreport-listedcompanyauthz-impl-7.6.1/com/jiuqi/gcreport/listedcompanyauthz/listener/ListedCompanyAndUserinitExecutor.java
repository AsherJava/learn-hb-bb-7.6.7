/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.listedcompanyauthz.listener;

import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.util.StringUtils;

public class ListedCompanyAndUserinitExecutor
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        ListedCompanyAndUserinitExecutor.initListedCompanyUserAuthz(null);
    }

    public static void initListedCompanyUserAuthz(String orgcode) {
        FListedCompanyAuthzService service = (FListedCompanyAuthzService)ApplicationContextRegister.getBean(FListedCompanyAuthzService.class);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT t.ENTITY_DATA_KEY_ as ORGCODE,t.IDENTITY_ID_ as USERID ");
        sql.append(" FROM NP_AUTHZ_ENTITY_IDENTITY t ");
        if (!StringUtils.isEmpty(orgcode)) {
            sql.append(" WHERE t.ENTITY_DATA_KEY_ ='").append(orgcode).append("' ");
        } else {
            sql.append(" WHERE t.ENTITY_DATA_KEY_ IN (SELECT lc.ORGCODE FROM GC_LISTEDCOMPANY lc) ");
        }
        sql.append(" AND NOT EXISTS( ");
        sql.append(" SELECT ID FROM GC_LISTEDCOMPANY_AUTHZ a ");
        sql.append(" WHERE a.ORGCODE = t.ENTITY_DATA_KEY_ AND a.USERID = t.IDENTITY_ID_ ");
        sql.append(" ) ");
        sql.append(" group by t.ENTITY_DATA_KEY_,t.IDENTITY_ID_ ");
        System.err.println(sql);
        List datas = EntNativeSqlDefaultDao.getInstance().selectListAssignResultExtractor(sql.toString(), result -> {
            ListedCompanyAuthzVO v = new ListedCompanyAuthzVO();
            v.setOrgCode((String)result.getObject("ORGCODE"));
            v.setUserId((String)result.getObject("USERID"));
            return v;
        }, new Object[0]);
        if (datas.size() > 0) {
            service.save(datas);
        }
    }
}

