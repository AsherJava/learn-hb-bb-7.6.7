/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.service.IReportDeployService
 *  com.jiuqi.nvwa.definition.common.exception.DataModelException
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.exception.CheckRunException
 *  com.jiuqi.nvwa.definition.facade.TableModel
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableType
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  io.swagger.v3.oas.annotations.servers.Servers
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.service.IReportDeployService;
import com.jiuqi.nvwa.definition.common.exception.DataModelException;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.exception.CheckRunException;
import com.jiuqi.nvwa.definition.facade.TableModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableType;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import io.swagger.v3.oas.annotations.servers.Servers;
import java.util.Arrays;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Servers
@Deprecated
public class ReportDeployServiceImpl
implements IReportDeployService {
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDeployServiceImpl.class);

    public DeployResult insertAndDeployTableModel(boolean check, DesignTableModel ... tableModels) {
        DeployResult result = new DeployResult();
        if (null == tableModels || 0 == tableModels.length) {
            return result;
        }
        if (check) {
            this.checkTableModel(result, DeployTableType.ADD, tableModels);
        }
        if (result.getCheckState()) {
            this.saveAndDeployTableModel(result, tableModels);
        }
        return result;
    }

    private void saveAndDeployTableModel(DeployResult result, DesignTableModel ... tableModels) {
        for (DesignTableModel designTableModel : tableModels) {
            try {
                this.designDataModelService.saveTableModel(designTableModel);
                LOGGER.info("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u66f4\u65b0{}[{}]\u6210\u529f\u3002", (Object)designTableModel.getTitle(), (Object)designTableModel.getCode());
                this.dataModelDeployService.deployTable(designTableModel.getID());
                LOGGER.info("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u53d1\u5e03{}[{}]\u6210\u529f\u3002", (Object)designTableModel.getTitle(), (Object)designTableModel.getCode());
            }
            catch (DataModelException | ModelValidateException e) {
                LOGGER.error("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u66f4\u65b0{}[{}]\u5931\u8d25\u3002", designTableModel.getTitle(), designTableModel.getCode(), e);
                result.addDeployDetail((DesignTableModelDefine)designTableModel, Collections.singletonList(e.getMessage()));
            }
            catch (Exception e) {
                LOGGER.error("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u53d1\u5e03{}[{}]\u5931\u8d25\u3002", designTableModel.getTitle(), designTableModel.getCode(), e);
                result.addDeployDetail((DesignTableModelDefine)designTableModel, Collections.singletonList(e.getMessage()));
            }
        }
    }

    private void checkTableModel(DeployResult result, DeployTableType deployTableType, DesignTableModel ... tableModels) {
        for (DesignTableModel designTableModel : tableModels) {
            TableModel tableModel = new TableModel(designTableModel.getID(), designTableModel.getCode());
            tableModel.setTableModelDefine((TableModelDefine)designTableModel);
            try {
                boolean checkTable = this.dataModelDeployService.checkTable(tableModel, deployTableType);
                if (!checkTable) {
                    LOGGER.info("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u68c0\u67e5{}[{}]\u672a\u901a\u8fc7\uff1a\u539f\u56e0\u672a\u77e5\uff01", (Object)designTableModel.getTitle(), (Object)designTableModel.getCode());
                    result.addCheckError((DesignTableModelDefine)designTableModel, "\u53d1\u5e03\u68c0\u67e5\u672a\u901a\u8fc7\uff0c\u539f\u56e0\u672a\u77e5\uff01");
                    continue;
                }
                LOGGER.info("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u68c0\u67e5{}[{}]\u901a\u8fc7\u3002", (Object)designTableModel.getTitle(), (Object)designTableModel.getCode());
            }
            catch (CheckRunException e) {
                LOGGER.error("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u68c0\u67e5{}[{}]\u672a\u901a\u8fc7\u3002", new Object[]{designTableModel.getTitle(), designTableModel.getCode(), e});
                result.addCheckError((DesignTableModelDefine)designTableModel, e.getMessage());
            }
        }
    }

    public DeployResult updateAndDeployTableModel(boolean check, DesignTableModel ... tableModels) {
        DeployResult result = new DeployResult();
        if (null == tableModels || 0 == tableModels.length) {
            return result;
        }
        if (check) {
            this.checkTableModel(result, DeployTableType.UPDATE, tableModels);
        }
        if (result.getCheckState()) {
            this.saveAndDeployTableModel(result, tableModels);
        }
        return result;
    }

    public DeployResult deleteAndDeployTableModel(boolean check, DesignTableModel ... tableModels) {
        DeployResult result = new DeployResult();
        if (null == tableModels || 0 == tableModels.length) {
            return result;
        }
        if (check) {
            this.checkTableModel(result, DeployTableType.DELETE, tableModels);
        }
        if (!result.getCheckState()) {
            return result;
        }
        for (DesignTableModel designTableModel : tableModels) {
            try {
                this.designDataModelService.delteTableModel(designTableModel.getID());
                LOGGER.info("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u5220\u9664{}[{}]\u6210\u529f\u3002", (Object)designTableModel.getTitle(), (Object)designTableModel.getCode());
                this.dataModelDeployService.deployTable(designTableModel.getID());
                LOGGER.info("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u53d1\u5e03{}[{}]\u6210\u529f\u3002", (Object)designTableModel.getTitle(), (Object)designTableModel.getCode());
            }
            catch (DataModelException | ModelValidateException e) {
                LOGGER.error("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u5220\u9664{}[{}]\u5931\u8d25\u3002", designTableModel.getTitle(), designTableModel.getCode(), e);
                result.addDeployDetail((DesignTableModelDefine)designTableModel, Collections.singletonList(e.getMessage()));
            }
            catch (Exception e) {
                LOGGER.error("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u53d1\u5e03{}[{}]\u5931\u8d25\u3002", designTableModel.getTitle(), designTableModel.getCode(), e);
                result.addDeployDetail((DesignTableModelDefine)designTableModel, Collections.singletonList(e.getMessage()));
            }
        }
        return result;
    }

    public void deleteAndDeployTableModel(String ... tableModelKeys) throws Exception {
        LOGGER.info("\u53c2\u6570\u670d\u52a1\u53d1\u5e03\uff1a\u5220\u9664\u5e76\u53d1\u5e03{}\u3002", (Object)Arrays.toString(tableModelKeys));
        if (null == tableModelKeys || 0 == tableModelKeys.length) {
            return;
        }
        for (String id : tableModelKeys) {
            this.designDataModelService.delteTableModel(id);
        }
        this.dataModelDeployService.deployTables(tableModelKeys);
    }
}

