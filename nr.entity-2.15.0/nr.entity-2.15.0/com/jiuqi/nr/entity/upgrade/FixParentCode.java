/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 */
package com.jiuqi.nr.entity.upgrade;

import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixParentCode
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(FixParentCode.class);

    public void execute(DataSource dataSource) throws Exception {
        try (Connection connection = dataSource.getConnection();){
            FixBaseData fixBaseData = new FixBaseData(connection);
            fixBaseData.fix();
            FixOrg fixOrg = new FixOrg(connection);
            fixOrg.fix();
        }
        catch (Exception e) {
            this.logger.error("\u4fee\u590d\u7ec4\u7ec7\u673a\u6784\u3001\u57fa\u7840\u6570\u636e\u7236\u8282\u70b9\u9519\u8bef:{}", (Object)e.getMessage(), (Object)e);
            throw new RuntimeException("\u4fee\u590d\u7ec4\u7ec7\u673a\u6784\u3001\u57fa\u7840\u6570\u636e\u7236\u8282\u70b9\u9519\u8bef", e);
        }
    }

    class FixOrg
    extends FixData {
        private OrgCategoryClient orgCategoryClient;

        public FixOrg(Connection connection) {
            super(connection);
            this.orgCategoryClient = (OrgCategoryClient)BeanUtils.getBean(OrgCategoryClient.class);
        }

        @Override
        public List<String> queryTableCode() {
            OrgCategoryDO orgCategory = new OrgCategoryDO();
            PageVO list = this.orgCategoryClient.list(orgCategory);
            return list.getRows().stream().map(OrgCategoryDO::getName).collect(Collectors.toList());
        }
    }

    class FixBaseData
    extends FixData {
        private BaseDataDefineClient baseDataDefineClient;

        public FixBaseData(Connection connection) {
            super(connection);
            this.baseDataDefineClient = (BaseDataDefineClient)BeanUtils.getBean(BaseDataDefineClient.class);
        }

        @Override
        public List<String> queryTableCode() {
            BaseDataDefineDTO param = new BaseDataDefineDTO();
            PageVO list = this.baseDataDefineClient.list(param);
            return list.getRows().stream().map(BaseDataDefineDO::getName).collect(Collectors.toList());
        }
    }

    abstract class FixData {
        private Connection connection;

        public FixData(Connection connection) {
            this.connection = connection;
        }

        public void fix() {
            List<String> tables = this.queryTableCode();
            for (int i = 0; i < tables.size(); ++i) {
                double progress = (double)(i + 1) / (double)tables.size() * 100.0;
                String tableCode = tables.get(i);
                if (!this.needFix(tableCode)) continue;
                FixParentCode.this.logger.info("\u5f00\u59cb\u4fee\u590d\uff1a{}\u7684\u7236\u8282\u70b9,\u4fee\u590d\u8fdb\u5ea6\uff1a{}%", (Object)tableCode, (Object)progress);
                this.fixParent(tableCode);
                FixParentCode.this.logger.info("{}\u7236\u8282\u70b9\u4fee\u590d\u5b8c\u6bd5", (Object)tableCode);
            }
        }

        protected abstract List<String> queryTableCode();

        private boolean needFix(String tableCode) {
            boolean exit = false;
            String sql = String.format("SELECT COUNT(1) FROM %s WHERE PARENTCODE IS NULL OR PARENTCODE = ''", tableCode);
            try (PreparedStatement pstm = this.connection.prepareStatement(sql);
                 ResultSet rs = pstm.executeQuery();){
                while (rs.next()) {
                    exit = rs.getInt(1) > 0;
                }
            }
            catch (SQLException e) {
                FixParentCode.this.logger.error("\u67e5\u8be2:{}\u7684\u6570\u636e\u65f6\u9519\u8bef: {}", (Object)tableCode, (Object)e);
            }
            return exit;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void fixParent(String tableCode) {
            String sql = String.format("UPDATE %s SET PARENTCODE = ? WHERE PARENTCODE IS NULL OR PARENTCODE = ''", tableCode);
            try (PreparedStatement pstmt = this.connection.prepareStatement(sql);){
                this.connection.setAutoCommit(false);
                pstmt.setString(1, "-");
                int i = pstmt.executeUpdate();
                this.connection.commit();
            }
            catch (SQLException e) {
                FixParentCode.this.logger.error("\u66f4\u65b0:{}\u7684\u7236\u8282\u70b9\u6570\u636e\u65f6\u9519\u8bef: {}", (Object)tableCode, (Object)e);
            }
            finally {
                try {
                    this.connection.setAutoCommit(true);
                }
                catch (SQLException throwable) {
                    FixParentCode.this.logger.error(throwable.getMessage(), throwable);
                }
            }
        }
    }
}

