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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufDBBatchByUnitOrderActuator
implements ISBActuator {
    protected final Logger logger = LoggerFactory.getLogger(BufDBBatchByUnitOrderActuator.class);
    private final FieldDataProperties fieldDataProperties;
    private final ISBImportActuatorFactory factory;
    private final ActuatorConfig cfg;
    private List<DataField> fields;
    private ISBActuator currActuator;
    private List<ImportInfo> importInfos;
    private int mdCodeIndex = -1;
    private int currentBatchSize;
    private int allBatchSize;
    private final Collection<String> mdScope = new TreeSet<String>();
    private final Set<String> currDws = new TreeSet<String>();
    private String lastDw;

    public BufDBBatchByUnitOrderActuator(FieldDataProperties fieldDataProperties, ISBImportActuatorFactory factory, ActuatorConfig cfg) {
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
        this.importInfos = new ArrayList<ImportInfo>();
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
        String currDw = dwCode.toString();
        if (this.currActuator == null) {
            this.currActuator = this.initCurrActuator();
        }
        if (this.currentBatchSize > this.fieldDataProperties.getRowMaxSize() && !currDw.equals(this.lastDw)) {
            this.doCommit();
            this.currActuator = this.initCurrActuator();
        }
        this.currActuator.put(dataRow);
        ++this.currentBatchSize;
        ++this.allBatchSize;
        this.lastDw = currDw;
        if (!this.currDws.contains(currDw)) {
            this.currDws.add(currDw);
            if (!this.mdScope.isEmpty()) {
                this.mdScope.remove(currDw);
            }
        }
    }

    private void doCommit() {
        try {
            this.logger.info("\u53f0\u8d26\u6570\u636e\u5bfc\u5165\u5f00\u59cb\u63d0\u4ea4{} \u5bb6\u5355\u4f4d {}\u6761\u8bb0\u5f55 \u5df2\u7ecf\u7d2f\u8ba1\u63d0\u4ea4 {} \u6761\u8bb0\u5f55", this.currDws.size(), this.currentBatchSize, this.allBatchSize - this.currentBatchSize);
            ImportInfo info = this.currActuator.commitData();
            this.logger.info("\u53f0\u8d26\u6570\u636e\u5bfc\u5165\u5b8c\u6210 \u672c\u6b21\u63d0\u4ea4{} \u5bb6\u5355\u4f4d {}\u6761\u8bb0\u5f55 \u5df2\u7ecf\u7d2f\u8ba1\u63d0\u4ea4 {} \u6761\u8bb0\u5f55", this.currDws.size(), this.currentBatchSize, this.allBatchSize);
            this.importInfos.add(info);
        }
        finally {
            this.currActuator.close();
        }
    }

    private ISBActuator initCurrActuator() {
        ActuatorConfigDTO copy = ActuatorConfigDTO.copy((ActuatorConfig)this.cfg);
        copy.setBatchByUnit(false);
        ISBActuator actuator = this.factory.getActuator((ActuatorConfig)copy);
        actuator.setDataFields(this.fields);
        actuator.prepare();
        this.currentBatchSize = 0;
        return actuator;
    }

    public ImportInfo commitData() {
        if (this.currActuator != null) {
            try {
                if (this.currentBatchSize > 0) {
                    this.doCommit();
                }
            }
            finally {
                this.currActuator.close();
                this.currActuator = null;
            }
        }
        this.appendClearActuator();
        ImportInfo infos = new ImportInfo();
        for (ImportInfo info : this.importInfos) {
            infos.addDimValues(info.getDimValues());
        }
        return infos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void appendClearActuator() {
        if (!this.mdScope.isEmpty()) {
            ArrayList<Collection<String>> batches = new ArrayList<Collection<String>>();
            if (this.mdScope.size() > this.fieldDataProperties.getBatchSize()) {
                List lists = DataPermissionUtil.groupBySize(this.mdScope, (int)this.fieldDataProperties.getBatchSize());
                batches.addAll(lists);
            } else {
                batches.add(this.mdScope);
            }
            int batch = 0;
            for (Collection collection : batches) {
                this.logger.info("\u53f0\u8d26\u6570\u636e\u5bfc\u5165\u5f00\u59cb\u5bf9\u65e0\u6570\u636e\u7684\u5355\u4f4d\u505a\u6570\u636e\u5220\u9664 \u672c\u6b21\u63d0\u4ea4{} \u5bb6\u5355\u4f4d, \u6279\u6b21 {}/{}", this.currDws.size(), ++batch, batches.size());
                ActuatorConfigDTO copy = ActuatorConfigDTO.copy((ActuatorConfig)this.cfg);
                copy.setBatchByUnit(false);
                ISBActuator nextActuator = this.factory.getActuator((ActuatorConfig)copy);
                nextActuator.setDataFields(this.fields);
                nextActuator.prepare();
                nextActuator.setMdCodeScope(collection);
                try {
                    ImportInfo info = nextActuator.commitData();
                    this.logger.info("\u53f0\u8d26\u6570\u636e\u5bfc\u5165\u5bf9\u65e0\u6570\u636e\u7684\u5355\u4f4d\u505a\u6570\u636e\u5220\u9664\u6570\u636e\u5b8c\u6210");
                    if (info == null) continue;
                    this.importInfos.add(info);
                }
                finally {
                    nextActuator.close();
                }
            }
        }
    }

    public void close() {
        if (this.currActuator == null) {
            return;
        }
        try {
            this.currActuator.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

