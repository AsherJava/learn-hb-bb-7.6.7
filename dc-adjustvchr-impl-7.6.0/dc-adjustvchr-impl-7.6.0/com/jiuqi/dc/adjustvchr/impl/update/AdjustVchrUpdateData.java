/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO
 *  com.jiuqi.dc.adjustvchr.impl.entity.AdjustVoucherEO
 *  com.jiuqi.dc.base.common.utils.BeanCopyUtil
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.adjustvchr.impl.update;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.dao.AdjustVchrDao;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVoucherEO;
import com.jiuqi.dc.adjustvchr.impl.enums.AdjustVchrTypeEnum;
import com.jiuqi.dc.base.common.utils.BeanCopyUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class AdjustVchrUpdateData
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AdjustVchrUpdateData.class);
    private static final String LOG_PREFIX = "\u8c03\u6574\u51ed\u8bc1\u6570\u636e\u5347\u7ea7-";

    public void execute(DataSource dataSource) throws Exception {
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        AdjustVchrDao vchrDao = (AdjustVchrDao)SpringContextUtils.getBean(AdjustVchrDao.class);
        String sql = "SELECT * FROM DC_ADJUSTVOUCHER WHERE GROUPID IS NULL";
        List adjustVoucherVOList = jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(AdjustVoucherVO.class));
        if (CollectionUtils.isEmpty((Collection)adjustVoucherVOList)) {
            logger.info("\u8c03\u6574\u51ed\u8bc1\u6570\u636e\u5347\u7ea7-\u8c03\u6574\u51ed\u8bc1\u9700\u8981\u5347\u7ea7\u7684\u6570\u636e\u4e3a\u7a7a\u3002");
            return;
        }
        String backUpVchrSql = "CREATE TABLE BD_DC_ADJUSTVOUCHER AS SELECT * FROM DC_ADJUSTVOUCHER";
        jdbcTemplate.update(backUpVchrSql);
        String backUpItemSql = "CREATE TABLE BD_DC_ADJUSTVCHRITEM AS SELECT * FROM DC_ADJUSTVCHRITEM";
        jdbcTemplate.update(backUpItemSql);
        String tempItemSql = "CREATE TABLE BD_20231011DC_ADJUSTVCHRITEM AS SELECT * FROM DC_ADJUSTVCHRITEM";
        jdbcTemplate.update(tempItemSql);
        String deleteVchrSql = "DELETE FROM DC_ADJUSTVOUCHER WHERE GROUPID IS NULL";
        jdbcTemplate.update(deleteVchrSql);
        List vchrIdList = adjustVoucherVOList.stream().map(AdjustVoucherVO::getId).collect(Collectors.toList());
        String deleteItemSql = "DELETE FROM DC_ADJUSTVCHRITEM WHERE " + SqlBuildUtil.getStrInCondi((String)"VCHRID", vchrIdList);
        jdbcTemplate.update(deleteItemSql);
        try {
            for (AdjustVoucherVO voucherVO : adjustVoucherVOList) {
                int periodDividend = 1;
                if ("J".equals(voucherVO.getPeriodType())) {
                    periodDividend = 3;
                } else if ("H".equals(voucherVO.getPeriodType())) {
                    periodDividend = 6;
                } else if ("N".equals(voucherVO.getPeriodType())) {
                    periodDividend = 12;
                }
                int start = "N".equals(voucherVO.getPeriodType()) ? 1 : Integer.parseInt(voucherVO.getAffectPeriodStart().substring(0, voucherVO.getAffectPeriodStart().length() - 1)) / periodDividend;
                int end = "N".equals(voucherVO.getPeriodType()) ? 1 : Integer.parseInt(voucherVO.getAffectPeriodEnd().substring(0, voucherVO.getAffectPeriodEnd().length() - 1)) / periodDividend;
                for (int i = start; i <= end; ++i) {
                    AdjustVoucherEO saveEO = (AdjustVoucherEO)BeanCopyUtil.copyObj(AdjustVoucherEO.class, (Object)voucherVO);
                    saveEO.setId(UUIDUtils.newUUIDStr());
                    saveEO.setGroupId(voucherVO.getId());
                    saveEO.setAcctPeriod(Integer.valueOf(i));
                    vchrDao.insertVoucher(saveEO);
                    this.saveItemList(i, saveEO.getId(), voucherVO.getId(), i * periodDividend);
                }
            }
            String dropItemTableSql = "DROP TABLE BD_20231011DC_ADJUSTVCHRITEM";
            jdbcTemplate.update(dropItemTableSql);
            logger.info("\u8c03\u6574\u51ed\u8bc1\u6570\u636e\u5347\u7ea7-\u8c03\u6574\u51ed\u8bc1\u5347\u7ea7\u811a\u672c\u6267\u884c\u6210\u529f\u3002");
        }
        catch (DataAccessException e) {
            logger.error("\u8c03\u6574\u51ed\u8bc1\u6570\u636e\u5347\u7ea7-\u8c03\u6574\u51ed\u8bc1\u66f4\u65b0\u8868\u6570\u636e\u5f02\u5e38\u3002", e);
        }
    }

    private void saveItemList(int newPeriod, String newVchrId, String vchrId, int period) {
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        String updateItemSql = "UPDATE BD_20231011DC_ADJUSTVCHRITEM SET VCHRID = ?, ACCTPERIOD = ?, VCHRSRCTYPE = ? WHERE VCHRID = ? AND ACCTPERIOD = ?";
        jdbcTemplate.update(updateItemSql, new Object[]{newVchrId, newPeriod, AdjustVchrTypeEnum.HANDLE_ADJUST.getCode(), vchrId, period});
        String insertItemSql = "INSERT INTO DC_ADJUSTVCHRITEM (SELECT * FROM BD_20231011DC_ADJUSTVCHRITEM WHERE VCHRID = ? AND ACCTPERIOD = ?)";
        jdbcTemplate.update(insertItemSql, new Object[]{newVchrId, newPeriod});
    }
}

