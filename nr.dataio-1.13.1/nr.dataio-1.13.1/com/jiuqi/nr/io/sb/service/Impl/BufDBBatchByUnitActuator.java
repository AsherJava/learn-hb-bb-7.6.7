/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.util.DataPermissionUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.fielddatacrud.ActuatorConfig
 *  com.jiuqi.nr.fielddatacrud.ISBActuator
 *  com.jiuqi.nr.fielddatacrud.ImportInfo
 *  com.jiuqi.nr.fielddatacrud.config.FieldDataProperties
 *  com.jiuqi.nr.fielddatacrud.impl.dto.ActuatorConfigDTO
 *  com.jiuqi.nr.fielddatacrud.spi.ISBImportActuatorFactory
 */
package com.jiuqi.nr.io.sb.service.Impl;

import com.jiuqi.nr.data.access.util.DataPermissionUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.fielddatacrud.ActuatorConfig;
import com.jiuqi.nr.fielddatacrud.ISBActuator;
import com.jiuqi.nr.fielddatacrud.ImportInfo;
import com.jiuqi.nr.fielddatacrud.config.FieldDataProperties;
import com.jiuqi.nr.fielddatacrud.impl.dto.ActuatorConfigDTO;
import com.jiuqi.nr.fielddatacrud.spi.ISBImportActuatorFactory;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufDBBatchByUnitActuator
implements ISBActuator {
    protected final Logger logger = LoggerFactory.getLogger(BufDBBatchByUnitActuator.class);
    private final FieldDataProperties fieldDataProperties;
    private final ISBImportActuatorFactory factory;
    private final ActuatorConfig cfg;
    private List<DataField> fields;
    private List<ISBActuator> batchActuators;
    private Map<String, Integer> unitToBatchIndex;
    private int mdCodeIndex = -1;
    private int currentBatchSize;
    private int allBatchSize;
    private final Collection<String> mdScope = new TreeSet<String>();

    public BufDBBatchByUnitActuator(FieldDataProperties fieldDataProperties, ISBImportActuatorFactory factory, ActuatorConfig cfg) {
        this.fieldDataProperties = fieldDataProperties;
        this.factory = factory;
        this.cfg = cfg;
    }

    public int getActuatorType() {
        return SBImportActuatorType.BUF_DB.getValue();
    }

    public void setDataFields(List<DataField> fields) {
        this.fields = fields;
        for (int i = 0; i < this.fields.size(); ++i) {
            if (!"MDCODE".equals(this.fields.get(i).getCode())) continue;
            this.mdCodeIndex = i;
        }
    }

    public void setMdCodeScope(Collection<String> codes) {
        this.mdScope.addAll(codes);
    }

    public void prepare() {
        this.batchActuators = new ArrayList<ISBActuator>();
        this.unitToBatchIndex = new HashMap<String, Integer>();
    }

    public void put(List<Object> dataRow) {
        if (this.mdCodeIndex < 0 || this.mdCodeIndex > dataRow.size() - 1) {
            throw new TzCopyDataException("\u5355\u4f4d\u7f16\u7801\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        Object dwCode = dataRow.get(this.mdCodeIndex);
        if (dwCode == null) {
            this.logger.error("\u5355\u4f4d\u7f16\u7801\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
            throw new TzCopyDataException("\u5355\u4f4d\u7f16\u7801\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        ISBActuator currentActuator = this.getCurrentActuator(dwCode.toString());
        currentActuator.put(dataRow);
        if (currentActuator == this.batchActuators.get(this.batchActuators.size() - 1)) {
            ++this.currentBatchSize;
        }
        ++this.allBatchSize;
    }

    private ISBActuator getCurrentActuator(String mdCode) {
        Integer batchIndex = this.unitToBatchIndex.get(mdCode);
        if (batchIndex == null) {
            if (this.currentBatchSize > 200000 || this.batchActuators.isEmpty()) {
                ActuatorConfigDTO copy = ActuatorConfigDTO.copy((ActuatorConfig)this.cfg);
                copy.setBatchByUnit(false);
                ISBActuator nextActuator = this.factory.getActuator((ActuatorConfig)copy);
                this.batchActuators.add(nextActuator);
                nextActuator.setDataFields(this.fields);
                nextActuator.prepare();
                this.currentBatchSize = 0;
            }
            batchIndex = this.batchActuators.size() - 1;
            this.unitToBatchIndex.put(mdCode, batchIndex);
            if (!this.mdScope.isEmpty()) {
                this.mdScope.remove(mdCode);
            }
        }
        return this.batchActuators.get(batchIndex);
    }

    public ImportInfo commitData() {
        this.logger.info("\u53f0\u8d26\u6570\u636e\u5bfc\u5165\u5f00\u59cb\u63d0\u4ea4{} \u5bb6\u5355\u4f4d {}\u6761\u8bb0\u5f55 {} \u5bb6\u5355\u4f4d\u6570\u636e\u76f4\u63a5\u5220\u9664", this.unitToBatchIndex.size(), this.allBatchSize, this.mdScope.size());
        this.appendClearActuator();
        ImportInfo importInfo = new ImportInfo();
        int batchSize = 1;
        for (ISBActuator batchActuator : this.batchActuators) {
            this.logger.info("\u53f0\u8d26\u6570\u636e\u5206\u6279\u5bfc\u5165\u5f00\u59cb\u5bfc\u5165\u7b2c{}/{}\u6279\u5355\u4f4d", (Object)batchSize, (Object)this.batchActuators.size());
            ImportInfo info = batchActuator.commitData();
            batchActuator.close();
            this.logger.info("\u53f0\u8d26\u6570\u636e\u5206\u6279\u5bfc\u5165\u7b2c{}/{}\u6279\u5355\u4f4d\u5b8c\u6210", (Object)batchSize++, (Object)this.batchActuators.size());
            if (info == null) continue;
            List dimValues = info.getDimValues();
            importInfo.addDimValues(dimValues);
        }
        this.logger.info("\u53f0\u8d26\u6570\u636e\u5bfc\u5165{} \u5bb6\u5355\u4f4d {}\u6761\u8bb0\u5f55 {} \u5bb6\u5355\u4f4d\u6570\u636e\u5220\u9664\u6210\u529f", this.unitToBatchIndex.size() + this.mdScope.size(), this.allBatchSize, this.mdScope.size());
        return importInfo;
    }

    private void appendClearActuator() {
        if (!this.mdScope.isEmpty()) {
            ArrayList<Collection<String>> batches = new ArrayList<Collection<String>>();
            if (this.mdScope.size() > this.fieldDataProperties.getBatchSize()) {
                List lists = DataPermissionUtil.groupBySize(this.mdScope, (int)this.fieldDataProperties.getBatchSize());
                batches.addAll(lists);
            } else {
                batches.add(this.mdScope);
            }
            for (Collection collection : batches) {
                ActuatorConfigDTO copy = ActuatorConfigDTO.copy((ActuatorConfig)this.cfg);
                copy.setBatchByUnit(false);
                ISBActuator nextActuator = this.factory.getActuator((ActuatorConfig)copy);
                this.batchActuators.add(nextActuator);
                nextActuator.setDataFields(this.fields);
                nextActuator.prepare();
                nextActuator.setMdCodeScope(collection);
            }
        }
    }

    public void close() {
        if (this.batchActuators == null) {
            return;
        }
        for (ISBActuator batchActuator : this.batchActuators) {
            try {
                batchActuator.close();
            }
            catch (Exception exception) {}
        }
    }
}

