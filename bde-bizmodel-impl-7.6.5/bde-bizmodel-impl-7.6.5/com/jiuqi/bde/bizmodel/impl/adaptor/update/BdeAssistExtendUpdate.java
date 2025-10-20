/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.TableParseUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.executor.IDimAttributeUpdate
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.update;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.TableParseUtils;
import com.jiuqi.dc.mappingscheme.impl.service.executor.IDimAttributeUpdate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BdeAssistExtendUpdate
implements IDimAttributeUpdate {
    private static final Logger logger = LoggerFactory.getLogger(BdeAssistExtendUpdate.class);
    @Autowired
    private AssistExtendDimService extendDimService;

    public void dimAttributeUpdate(String dataSourceCode) {
        try {
            this.doUpdate(dataSourceCode);
        }
        catch (Exception e) {
            logger.error("\u7ef4\u5ea6\u5c5e\u6027\u5347\u7ea7\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a", (Object)e.getMessage(), (Object)e);
        }
    }

    public void doUpdate(String dataSourceCode) {
        boolean tableExists = TableParseUtils.tableExist((String)dataSourceCode, (List)CollectionUtils.newArrayList((Object[])new String[]{"BDE_ASSISTEXTENDDIM"}));
        if (!tableExists) {
            logger.info("\u7ef4\u5ea6\u5c5e\u6027\u5347\u7ea7-\u5e95\u8868\u6821\u9a8c\u5931\u8d25\uff0c\u8df3\u8fc7\u7ef4\u5ea6\u5c5e\u6027\u5347\u7ea7\uff01\uff01\uff01");
            return;
        }
        this.doExtDimUpdate(dataSourceCode);
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void doExtDimUpdate(String dataSourceCode) {
        String sql;
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
        List extAssistList = bizJdbcTemplate.query(sql = "SELECT DIM.ID,DIM.ASSISTDIMCODE,DIM.REFFIELD,DIM.CODE,DIM.NAME,DIM.MATCHRULE,DIM.STOPFLAG FROM BDE_ASSISTEXTENDDIM DIM ORDER BY ORDINAL DESC", (rs, row) -> {
            AssistExtendDimVO assistExtendDimVO = new AssistExtendDimVO();
            assistExtendDimVO.setId(rs.getString(1));
            assistExtendDimVO.setAssistDimCode(rs.getString(2));
            assistExtendDimVO.setRefField(rs.getString(3));
            assistExtendDimVO.setCode(rs.getString(4));
            assistExtendDimVO.setName(rs.getString(5));
            assistExtendDimVO.setMatchRule(rs.getString(6));
            assistExtendDimVO.setStopFlagNum(Integer.valueOf(rs.getInt(7)));
            return assistExtendDimVO;
        });
        if (CollectionUtils.isEmpty((Collection)extAssistList)) {
            logger.info("\u7ef4\u5ea6\u5c5e\u6027\u5347\u7ea7-\u7ef4\u5ea6\u5c5e\u6027\u4e0d\u5b58\u5728\u914d\u7f6e\uff0c\u8df3\u8fc7\u7ef4\u5ea6\u5c5e\u6027\u5347\u7ea7\uff01\uff01\uff01");
            return;
        }
        List<AssistExtendDimVO> existDimList = this.extendDimService.getAllAssistExtendDim();
        HashMap existsDimMap = CollectionUtils.isEmpty(existDimList) ? CollectionUtils.newHashMap() : existDimList.stream().collect(Collectors.toMap(AssistExtendDimVO::getCode, item -> item));
        HashMap existsDimNameMap = CollectionUtils.isEmpty(existDimList) ? CollectionUtils.newHashMap() : existDimList.stream().collect(Collectors.toMap(AssistExtendDimVO::getName, item -> item));
        for (AssistExtendDimVO extDim : extAssistList) {
            if (existsDimMap.containsKey(extDim.getCode())) {
                logger.info("\u7ef4\u5ea6\u5c5e\u6027\u5347\u7ea7-\u7ef4\u5ea6\u5c5e\u6027{}{}\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)extDim.getCode(), (Object)((AssistExtendDimVO)existsDimMap.get(extDim.getCode())).getName());
                continue;
            }
            if (existsDimNameMap.containsKey(extDim.getName())) {
                logger.info("\u7ef4\u5ea6\u5c5e\u6027\u5347\u7ea7-\u7ef4\u5ea6\u5c5e\u6027{}{}\u5df2\u7ecf\u5b58\u5728\u914d\u7f6e\uff0c\u81ea\u52a8\u8df3\u8fc7", (Object)extDim.getCode(), (Object)extDim.getName());
                continue;
            }
            try {
                this.extendDimService.save(extDim);
            }
            catch (Exception e) {
                logger.info("\u7ef4\u5ea6\u5c5e\u6027\u5347\u7ea7-\u8d22\u52a1\u6a21\u578b{}{}\u4fdd\u5b58\u51fa\u73b0\u9519\u8bef\uff0c\u81ea\u52a8\u8df3\u8fc7", extDim.getCode(), ((AssistExtendDimVO)existsDimMap.get(extDim.getCode())).getName(), e);
            }
        }
    }
}

