/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 */
package com.jiuqi.va.bill.bd.bill.service.impl;

import com.jiuqi.va.bill.bd.bill.service.WriteBackService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteBackServiceImpl
implements WriteBackService {
    private static final Logger logger = LoggerFactory.getLogger(WriteBackServiceImpl.class);
    @Autowired
    private CommonDao commonDao;

    @Override
    public R writeBackToApplyBill(String tablename, String filedname, String regbillcode, String id) {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" update ");
            sql.append(tablename);
            sql.append(" set ");
            sql.append(filedname);
            sql.append(" =#{param." + filedname + ", jdbcType=VARCHAR} ");
            sql.append(" where id = #{param.id, jdbcType=VARCHAR} ");
            SqlDTO sqlDTO = new SqlDTO(ShiroUtil.getTenantName(), sql.toString());
            sqlDTO.addParam(filedname, (Object)regbillcode);
            sqlDTO.addParam("id", (Object)id);
            this.commonDao.executeBySql(sqlDTO);
        }
        catch (Exception e) {
            logger.error("\u56de\u5199\u5931\u8d25\uff0csql\u6267\u884c\u5931\u8d25");
            logger.error(e.getMessage(), e);
            return R.error();
        }
        return R.ok();
    }

    @Override
    public boolean checkCreating(String tablename, String billcode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(id) from ");
        sql.append(tablename);
        sql.append(" where publishstate != #{param.publishstate, jdbcType=INTEGER} ");
        sql.append(" and billcode  = #{param.billcode, jdbcType=VARCHAR} ");
        SqlDTO sqlDTO = new SqlDTO(ShiroUtil.getTenantName(), sql.toString());
        sqlDTO.addParam("publishstate", (Object)2);
        sqlDTO.addParam("billcode", (Object)billcode);
        return this.commonDao.countBySql(sqlDTO) > 0;
    }
}

