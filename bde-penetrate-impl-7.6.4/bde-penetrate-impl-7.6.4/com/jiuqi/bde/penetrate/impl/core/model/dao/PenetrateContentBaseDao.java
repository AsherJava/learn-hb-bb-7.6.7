/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.template.IntegerResultSetExtractor
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.penetrate.impl.core.model.dao;

import com.jiuqi.bde.common.template.IntegerResultSetExtractor;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.dao.IPenetrateContentBaseDao;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.domain.common.PageVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PenetrateContentBaseDao
implements IPenetrateContentBaseDao {
    protected static final Logger LOGGER = LoggerFactory.getLogger(PenetrateContentBaseDao.class);
    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public <C extends PenetrateBaseDTO, E> PageVO<E> query(C condi, QueryParam<E> queryParam) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            if (!queryParam.isPagination()) {
                List result = (List)this.dataSourceService.query(this.getDataSourceCode(condi), queryParam.getSql(), queryParam.getArgs(), queryParam.getRse());
                PageVO pageVO = new PageVO(result, result.size());
                return pageVO;
            }
            List result = (List)this.dataSourceService.pageQuery(this.getDataSourceCode(condi), queryParam.getSql(), queryParam.getOffset() / queryParam.getLimit() + 1, queryParam.getLimit(), queryParam.getArgs(), queryParam.getRse());
            conn = this.dataSourceService.getConnection(this.getDataSourceCode(condi));
            stmt = conn.prepareStatement(String.format("SELECT COUNT(1) FROM (%s) T ", queryParam.getSql()));
            if (queryParam.getArgs().length > 0) {
                int i = 1;
                for (Object arg : queryParam.getArgs()) {
                    stmt.setObject(i++, arg);
                }
            }
            rs = stmt.executeQuery();
            Integer total = new IntegerResultSetExtractor().extractData(rs);
            PageVO pageVO = new PageVO(result, total.intValue());
            this.dataSourceService.releaseResource(this.getDataSourceCode(condi), conn, (Statement)stmt, rs);
            return pageVO;
        }
        catch (Exception e) {
            LOGGER.error(queryParam.getSql());
            throw new BusinessRuntimeException((Throwable)e);
        }
        finally {
            this.dataSourceService.releaseResource(this.getDataSourceCode(condi), conn, stmt, rs);
        }
    }

    public <C extends PenetrateBaseDTO, E> String getDataSourceCode(C condi) {
        return condi.getDataScheme().getDataSourceCode();
    }
}

