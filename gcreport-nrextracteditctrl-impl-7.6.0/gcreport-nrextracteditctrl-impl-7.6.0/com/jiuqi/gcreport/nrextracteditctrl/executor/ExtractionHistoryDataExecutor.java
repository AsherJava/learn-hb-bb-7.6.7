/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.nrextracteditctrl.executor;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.nrextracteditctrl.dao.NrExtractEditCtrlItemDao;
import com.jiuqi.gcreport.nrextracteditctrl.entity.NrExtractEditCtrlEO;
import com.jiuqi.gcreport.nrextracteditctrl.entity.NrExtractEditCtrlItemEO;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class ExtractionHistoryDataExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ExtractionHistoryDataExecutor.class);
    public static final String BF_TABLE_NAME = "BK250115_GC_NR_EXTRACT_EDIT_CTRL";
    private static final String FILED_STRING_BF = " ID,TASKKEY,FORMSCHEMEKEY,UNITCODE,FORMKEY,STOPFLAG,CREATETIME ";

    private void initHistoryData() {
        NrExtractEditCtrlItemDao nrExtractEditCtrlItemDao = (NrExtractEditCtrlItemDao)BeanUtil.getBean(NrExtractEditCtrlItemDao.class);
        List<NrExtractEditCtrlEO> nrExtractEditCtrlEOS = this.queryAllHistory();
        logger.info("\u83b7\u53d6\u5408\u5e76\u8d22\u52a1\u62a5\u8868\u7f16\u8f91\u6743\u9650\u63a7\u5236\u5386\u53f2\u6570\u636e{}\u6761", (Object)nrExtractEditCtrlEOS.size());
        for (NrExtractEditCtrlEO editCtrlEO : nrExtractEditCtrlEOS) {
            NrExtractEditCtrlItemEO nrExtractEditCtrlItemEO = new NrExtractEditCtrlItemEO();
            nrExtractEditCtrlItemEO.setEditCtrlConfId(editCtrlEO.getId());
            List forms = Arrays.stream(editCtrlEO.getFormKey().split(",")).collect(Collectors.toList());
            logger.info("\u5f53\u524dformkey:{},\u62c6\u5206{}\u6761\u6570\u636e", (Object)editCtrlEO.getFormKey(), (Object)forms.size());
            for (String formkey : forms) {
                nrExtractEditCtrlItemEO.setFormKey(formkey);
                String uuid = UUIDUtils.newUUIDStr();
                nrExtractEditCtrlItemEO.setId(uuid);
                nrExtractEditCtrlItemEO.setLinkKey("*");
                logger.info("\u521d\u59cb\u5316\u5b50\u8868\u6570\u636eUUID:{}formKey:{}linkKey:{}\u4e3b\u8868ID:{}", uuid, formkey, "*", editCtrlEO.getId());
                nrExtractEditCtrlItemDao.save(nrExtractEditCtrlItemEO);
            }
        }
    }

    public void execute(DataSource dataSource) throws Exception {
        logger.info("\u5408\u5e76\u8d22\u52a1\u62a5\u8868\u7f16\u8f91\u6743\u9650\u63a7\u5236\u5386\u53f2\u6570\u636e\u5904\u7406");
        this.initHistoryData();
    }

    public List<NrExtractEditCtrlEO> queryAllHistory() {
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT ").append(FILED_STRING_BF).append("\n");
        sql.append("    FROM ").append(BF_TABLE_NAME).append(" T \n");
        sql.append("   ORDER BY CREATETIME\n");
        JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
        return jdbcTemplate.query(sql.toString(), (rs, row) -> this.nrExtractEditCtrlEOHistoryMapping(rs));
    }

    private NrExtractEditCtrlEO nrExtractEditCtrlEOHistoryMapping(ResultSet rs) throws SQLException {
        NrExtractEditCtrlEO nrExtractEditCtrlEO = new NrExtractEditCtrlEO();
        nrExtractEditCtrlEO.setId(rs.getString(1));
        nrExtractEditCtrlEO.setTaskKey(rs.getString(2));
        nrExtractEditCtrlEO.setFormSchemeKey(rs.getString(3));
        nrExtractEditCtrlEO.setUnitCode(rs.getString(4));
        nrExtractEditCtrlEO.setFormKey(rs.getString(5));
        nrExtractEditCtrlEO.setStopFlag(rs.getInt(6));
        nrExtractEditCtrlEO.setCreateTime(rs.getDate(7));
        return nrExtractEditCtrlEO;
    }
}

