/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.BudTenantConfig
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.budget.common.utils.LogUtil
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.config.VaMapperConfig
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.budget.autoconfigure;

import com.jiuqi.budget.autoconfigure.DimensionConst;
import com.jiuqi.budget.autoconfigure.Product;
import com.jiuqi.budget.common.domain.BudTenantConfig;
import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.common.utils.LogUtil;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.config.VaMapperConfig;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value={"com.jiuqi.va.mapper.common.ApplicationContextRegister"})
public class Gmc3ProductLineConfig
implements InitializingBean {
    @Value(value="${jiuqi.gmc3.product}")
    private String product;
    @Autowired
    private JdbcTemplate template;
    private static final Logger logger = LoggerFactory.getLogger(Gmc3ProductLineConfig.class);
    @Autowired
    VaMapperConfig vaMapperConfig;

    @Override
    public void afterPropertiesSet() throws JTableException {
        Product product = this.getProduct();
        LogUtil.logIfInfo((Logger)logger, (String)"\u5f53\u524d\u4ea7\u54c1\u7ebf\u4e3a\uff1a{}", (Object[])new Object[]{product});
        JTableModel jTableModel = new JTableModel(BudTenantConfig.getTenantName(), "BUD_PRODUCTLINE");
        jTableModel.addColumn("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jTableModel.addColumn("PRODUCTLINE").VARCHAR(Integer.valueOf(20)).notNull();
        jTableModel.addColumn("CREATTIME").TIMESTAMP().notNull();
        JDialectUtil jDialect = JDialectUtil.getInstance();
        if (!jDialect.hasTable(jTableModel)) {
            jDialect.createTable(jTableModel);
        } else {
            jDialect.updateTable(jTableModel);
        }
        String querySql = "SELECT ID as ID,PRODUCTLINE as PRODUCTLINE,CREATTIME as CREATTIME FROM BUD_PRODUCTLINE";
        List dataList = this.template.queryForList(querySql);
        if (dataList.isEmpty()) {
            String insertSql = "INSERT INTO BUD_PRODUCTLINE(ID,PRODUCTLINE,CREATTIME) VALUES (?,?,?)";
            this.template.update(insertSql, new Object[]{UUID.randomUUID().toString(), product.name(), new Date(System.currentTimeMillis())});
        } else if (dataList.size() == 1) {
            String productline = (String)((Map)dataList.get(0)).get("PRODUCTLINE");
            if (!product.name().equals(productline)) {
                throw new BudgetException("\u5f53\u524d\u6570\u636e\u5e93\u5df2\u914d\u7f6ejiuqi.gmc3.product\u4e3a" + productline + "\uff0c\u4f46\u662f\u914d\u7f6e\u6587\u4ef6\u4e2djiuqi.gmc3.product\u7684\u503c\u662f" + (Object)((Object)product));
            }
        } else {
            throw new BudgetException("BUD_PRODUCTLINE\u4e2d\u6570\u636e\u5f02\u5e38\uff01");
        }
        if (Product.BUDGET == product) {
            DimensionConst.setSystemDimensions(new DimensionConst.BudSystemDimensionDefine[]{DimensionConst.DATATIME, DimensionConst.MD_ORG, DimensionConst.MD_SCENE, DimensionConst.MD_CURRENCY, DimensionConst.MD_MGRVER, DimensionConst.MD_STAGE});
        } else if (Product.STATISTICS == product) {
            DimensionConst.setSystemDimensions(new DimensionConst.BudSystemDimensionDefine[]{DimensionConst.DATATIME, DimensionConst.MD_ORG});
        } else {
            throw new BudgetException("\u672a\u77e5\u7684product\u7c7b\u578b\uff01");
        }
    }

    public Product getProduct() {
        return Product.valueOf(this.product);
    }
}

