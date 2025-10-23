/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.datascheme.update;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class ReportDimUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(ReportDimUpdate.class);
    private final String querySqlDes = String.format("SELECT %s, %s, %s, %s, %s FROM %s", "DD_DS_KEY", "DD_TYPE", "DD_DIM_KEY", "DD_DIM_ATTRIBUTE", "DD_REPORT_DIM", "NR_DATASCHEME_DIM_DES");
    private final String querySql = String.format("SELECT %s, %s, %s, %s, %s FROM %s", "DD_DS_KEY", "DD_TYPE", "DD_DIM_KEY", "DD_DIM_ATTRIBUTE", "DD_REPORT_DIM", "NR_DATASCHEME_DIM");
    private final String updateSqlDes = String.format("UPDATE %s SET %s = '?' WHERE %s = '?' AND %s = '?' AND %s = '?'", "NR_DATASCHEME_DIM_DES", "DD_DIM_ATTRIBUTE", "DD_DS_KEY", "DD_DIM_KEY", "DD_TYPE");
    private final String updateSql = String.format("UPDATE %s SET %s = '?' WHERE %s = '?' AND %s = '?' AND %s = '?'", "NR_DATASCHEME_DIM", "DD_DIM_ATTRIBUTE", "DD_DS_KEY", "DD_DIM_KEY", "DD_TYPE");
    private final int orgType = DimensionType.UNIT.getValue();
    private final int dimType = DimensionType.DIMENSION.getValue();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)dataSource);
            this.update(this.querySqlDes, this.updateSqlDes, connection);
            this.update(this.querySql, this.updateSql, connection);
            this.logger.info("\u66f4\u65b0\u62a5\u9001\u60c5\u666f\u6570\u636e\u6210\u529f\uff01");
        }
        catch (SQLException e) {
            this.logger.error("\u66f4\u65b0\u62a5\u9001\u60c5\u666f\u6570\u636e\u5931\u8d25: {}", (Object)e.getMessage());
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
    }

    private void update(String querySqlDes, String updateSqlDes, Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querySqlDes);
             PreparedStatement updateStatement = connection.prepareStatement(updateSqlDes);){
            int[] res;
            ArrayList<Dim> list = new ArrayList<Dim>();
            while (resultSet.next()) {
                list.add(this.createDim(resultSet));
            }
            list.removeIf(x -> x.getType() != this.orgType && x.getType() != this.dimType);
            Map<String, String> orgKeyMap = list.stream().filter(x -> x.getType() == this.orgType).collect(Collectors.toMap(Dim::getDsKey, Dim::getDimKey));
            list.removeIf(x -> x.getType() == this.orgType || "ADJUST".equals(((Dim)x).dimKey));
            this.setAttribute(list, orgKeyMap);
            for (Dim dim : list) {
                updateStatement.setString(1, dim.getDimAttribute());
                updateStatement.setString(2, dim.getDsKey());
                updateStatement.setString(3, dim.getDimKey());
                updateStatement.setInt(4, dim.getType());
                updateStatement.addBatch();
            }
            for (int re : res = updateStatement.executeBatch()) {
                if (re >= 0) continue;
                throw new SQLException("\u66f4\u65b0\u62a5\u9001\u60c5\u666f\u6570\u636e\u5931\u8d25\uff01");
            }
        }
    }

    private void setAttribute(List<Dim> list, Map<String, String> orgKeyMap) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        HashMap<String, List> entityReferMap = new HashMap<String, List>();
        for (Map.Entry<String, String> entry : orgKeyMap.entrySet()) {
            List entityRefers;
            try {
                entityRefers = entityMetaService.getEntityRefer(entry.getValue());
            }
            catch (Exception e) {
                entityRefers = Collections.emptyList();
            }
            entityReferMap.put(entry.getKey(), entityRefers);
        }
        for (Dim dim : list) {
            IEntityRefer entityRefer;
            String dsKey = dim.getDsKey();
            String orgKey = orgKeyMap.get(dsKey);
            List entityRefers = (List)entityReferMap.get(dsKey);
            if (orgKey == null || entityRefers == null || (entityRefer = (IEntityRefer)entityRefers.stream().filter(x -> dim.getDimKey().equals(x.getReferEntityId())).findFirst().orElse(null)) == null || dim.getDimAttribute() != null) continue;
            dim.setDimAttribute(entityRefer.getOwnField());
        }
    }

    private Dim createDim(ResultSet resultSet) throws SQLException {
        Dim dim = new Dim();
        String att = resultSet.getString("DD_DIM_ATTRIBUTE");
        if ("null".equalsIgnoreCase(att) || Objects.isNull(att)) {
            dim.setDimAttribute(null);
        } else {
            dim.setDimAttribute(att);
        }
        dim.setDsKey(resultSet.getString("DD_DS_KEY"));
        dim.setType(resultSet.getInt("DD_TYPE"));
        dim.setDimKey(resultSet.getString("DD_DIM_KEY"));
        return dim;
    }

    private static class Dim {
        private String dsKey;
        private String orgKey;
        private int type;
        private String dimKey;
        private int reportDim;
        private String dimAttribute;

        private Dim() {
        }

        public String getDimAttribute() {
            return this.dimAttribute;
        }

        public void setDimAttribute(String dimAttribute) {
            this.dimAttribute = dimAttribute;
        }

        public void setDsKey(String dsKey) {
            this.dsKey = dsKey;
        }

        public String getOrgKey() {
            return this.orgKey;
        }

        public void setOrgKey(String orgKey) {
            this.orgKey = orgKey;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setDimKey(String dimKey) {
            this.dimKey = dimKey;
        }

        public String getDsKey() {
            return this.dsKey;
        }

        public int getType() {
            return this.type;
        }

        public String getDimKey() {
            return this.dimKey;
        }

        public int getReportDim() {
            return this.reportDim;
        }

        public void setReportDim(int reportDim) {
            this.reportDim = reportDim;
        }
    }
}

