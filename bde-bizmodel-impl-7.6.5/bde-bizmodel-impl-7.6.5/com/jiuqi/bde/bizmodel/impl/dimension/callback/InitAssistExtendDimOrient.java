/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.bde.bizmodel.impl.dimension.callback;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.bizmodel.impl.dimension.service.AssistExtendDimService;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class InitAssistExtendDimOrient
implements CustomClassExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitAssistExtendDimOrient.class);

    public void execute(DataSource dataSource) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        AssistExtendDimService assistExtendDimService = (AssistExtendDimService)BeanUtils.getBean(AssistExtendDimService.class);
        if (assistExtendDimService == null) {
            return;
        }
        List<AssistExtendDimVO> assistExtendDims = assistExtendDimService.getAllAssistExtendDim();
        Integer order = 1;
        LOGGER.info("\u7ef4\u5ea6\u5c5e\u6027\u8868\u6392\u5e8f\u521d\u59cb\u5316\u5f00\u59cb");
        for (AssistExtendDimVO assistExtendDimVO : assistExtendDims) {
            String sql = "UPDATE BDE_ASSISTEXTENDDIM SET ORDINAL=? WHERE ID=? ";
            jdbcTemplate.update(sql, new Object[]{order, assistExtendDimVO.getId()});
            Integer n = order;
            Integer n2 = order = Integer.valueOf(order + 1);
        }
        LOGGER.info("\u7ef4\u5ea6\u5c5e\u6027\u8868\u6392\u5e8f\u521d\u59cb\u5316\u6210\u529f");
    }
}

