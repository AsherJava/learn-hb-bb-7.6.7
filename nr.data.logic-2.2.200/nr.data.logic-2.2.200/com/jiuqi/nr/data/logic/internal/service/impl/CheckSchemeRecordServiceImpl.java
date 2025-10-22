/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckSchemeRecordDTO;
import com.jiuqi.nr.data.logic.facade.service.ICheckSchemeRecordService;
import com.jiuqi.nr.data.logic.internal.entity.CheckSchemeRecordDO;
import com.jiuqi.nr.data.logic.internal.entity.CheckSchemeRecordRowMapper;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.SerializationUtil;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

public class CheckSchemeRecordServiceImpl
implements ICheckSchemeRecordService {
    private static final Logger logger = LoggerFactory.getLogger(CheckSchemeRecordServiceImpl.class);
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected ParamUtil paramUtil;

    protected String getTableName() {
        return CheckTableNameUtil.getRWIFTableName();
    }

    @Override
    public CheckSchemeRecordDTO queryCheckSchemeRecord(String formSchemeKey) {
        String userId = NpContextHolder.getContext().getUser().getId();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", this.getTableName(), "RWIF_FM_SCHEME", "RWIF_USER");
        List result = this.jdbcTemplate.query(sql, (RowMapper)new CheckSchemeRecordRowMapper(), new Object[]{formSchemeKey, userId});
        if (!result.isEmpty()) {
            return new CheckSchemeRecordDTO((CheckSchemeRecordDO)result.get(0));
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveCheckSchemeRecord(CheckResultQueryParam checkResultQueryParam) {
        String userId = NpContextHolder.getContext().getUser().getId();
        long time = new Date().getTime();
        String formSchemeKey = this.paramUtil.getFormSchemeByFormulaSchemeKeys(checkResultQueryParam.getFormulaSchemeKeys()).getKey();
        String key = UUID.randomUUID().toString();
        String serializeParam = "";
        try {
            byte[] serialize = SerializationUtil.serialize(checkResultQueryParam);
            serializeParam = Base64.getEncoder().encodeToString(serialize);
        }
        catch (Exception e) {
            logger.error("\u5ba1\u6838\u53c2\u6570\u5e8f\u5217\u5316\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        this.jdbcTemplate.update(String.format("DELETE FROM %s WHERE %s=? AND %s=?", this.getTableName(), "RWIF_FM_SCHEME", "RWIF_USER"), new Object[]{formSchemeKey, userId});
        this.jdbcTemplate.update(String.format("INSERT INTO %s(%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)", this.getTableName(), "RWIF_KEY", "RWIF_USER", "RWIF_FM_SCHEME", "RWIF_DATETIME", "RWIF_CHECK_INFO"), new Object[]{key, userId, formSchemeKey, time, serializeParam});
    }

    @Override
    public int deleteCheckSchemeRecord(String formSchemeKey) {
        String userId = NpContextHolder.getContext().getUser().getId();
        String sql = String.format("DELETE FROM %s WHERE %s=? AND %s=?", this.getTableName(), "RWIF_FM_SCHEME", "RWIF_USER");
        return this.jdbcTemplate.update(sql, new Object[]{new CheckSchemeRecordRowMapper(), formSchemeKey, userId});
    }
}

