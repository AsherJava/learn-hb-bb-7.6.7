/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.event.DeleteAllRowEvent
 *  com.jiuqi.np.dataengine.event.DeleteRowEvent
 *  com.jiuqi.np.dataengine.event.InsertRowEvent
 *  com.jiuqi.np.dataengine.event.UpdateRowEvent
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.update.UpdateDataSet
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.dataservice.core.datastatus.monitor;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.nr.dataservice.core.datastatus.IDataStatusPreInitService;
import com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor;
import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMonitor
extends AbstractMonitor
implements IDataStatusMonitor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMonitor.class);
    private DataStatusPresetInfo dataStatusPresetInfo;
    private IMonitor monitor;

    public DefaultMonitor(DimensionCombination dimensionCombination, String formSchemeKey, Collection<String> formKeys) {
        try {
            IDataStatusPreInitService dataStatusPreInitService = (IDataStatusPreInitService)BeanUtil.getBean(IDataStatusPreInitService.class);
            this.dataStatusPresetInfo = dataStatusPreInitService.initInfo(dimensionCombination, formSchemeKey, formKeys);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public DataStatusPresetInfo getPresetInfo() {
        return this.dataStatusPresetInfo;
    }

    @Override
    public IMonitor getMonitor() {
        return this.monitor;
    }

    public void setMonitor(IMonitor monitor) {
        this.monitor = monitor;
    }

    public boolean isCancel() {
        if (this.monitor != null) {
            return this.monitor.isCancel();
        }
        return super.isCancel();
    }

    public void error(FormulaCheckEventImpl event) {
        if (this.monitor != null) {
            this.monitor.error(event);
        } else {
            super.error(event);
        }
    }

    public void message(String msg, Object sender) {
        if (this.monitor != null) {
            this.monitor.message(msg, sender);
        } else {
            super.message(msg, sender);
        }
    }

    public void error(String msg, Object sender) {
        if (this.monitor != null) {
            this.monitor.error(msg, sender);
        } else {
            super.error(msg, sender);
        }
    }

    public void finish() {
        if (this.monitor != null) {
            this.monitor.finish();
        } else {
            super.finish();
        }
    }

    public void exception(Exception e) {
        if (this.monitor != null) {
            this.monitor.exception(e);
        } else {
            super.exception(e);
        }
    }

    public void onProgress(double progress) {
        if (this.monitor != null) {
            this.monitor.onProgress(progress);
        } else {
            super.onProgress(progress);
        }
    }

    public void onDataChange(UpdateDataSet updateDatas) {
        if (this.monitor != null) {
            this.monitor.onDataChange(updateDatas);
        } else {
            super.onDataChange(updateDatas);
        }
    }

    public void beforeDelete(List<DimensionValueSet> delRowKeys) {
        if (this.monitor != null) {
            this.monitor.beforeDelete(delRowKeys);
        } else {
            super.beforeDelete(delRowKeys);
        }
    }

    public boolean beforeDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys) {
        if (this.monitor != null) {
            return this.monitor.beforeDeleteAll(masterKeys, deleteKeys);
        }
        return super.beforeDeleteAll(masterKeys, deleteKeys);
    }

    public void beforeUpdate(List<IDataRow> updateRows) {
        if (this.monitor != null) {
            this.monitor.beforeUpdate(updateRows);
        } else {
            super.beforeUpdate(updateRows);
        }
    }

    public void onCommitException(Exception ex, List<InsertRowEvent> insertRowEvents, List<UpdateRowEvent> updateRowEvents, List<DeleteRowEvent> deleteRowEvents, List<DeleteAllRowEvent> deleteAllRowEvents) {
        if (this.monitor != null) {
            this.monitor.onCommitException(ex, insertRowEvents, updateRowEvents, deleteRowEvents, deleteAllRowEvents);
        } else {
            super.onCommitException(ex, insertRowEvents, updateRowEvents, deleteRowEvents, deleteAllRowEvents);
        }
    }

    public void afterDelete(List<DimensionValueSet> delRowKeys) {
        if (this.monitor != null) {
            this.monitor.afterDelete(delRowKeys);
        } else {
            super.afterDelete(delRowKeys);
        }
    }

    public void afterUpdate(List<IDataRow> updateRows) {
        if (this.monitor != null) {
            this.monitor.afterUpdate(updateRows);
        } else {
            super.afterUpdate(updateRows);
        }
    }

    public void afterDeleteAll(DimensionValueSet masterKeys, DimensionValueSet deleteKeys) {
        if (this.monitor != null) {
            this.monitor.afterDeleteAll(masterKeys, deleteKeys);
        } else {
            super.afterDeleteAll(masterKeys, deleteKeys);
        }
    }

    public void start() {
        if (this.monitor != null) {
            this.monitor.start();
        } else {
            super.start();
        }
    }

    public void error(CheckExpression expression, QueryContext queryContext) throws SyntaxException, DataTypeException {
        if (this.monitor != null) {
            this.monitor.error(expression, queryContext);
        } else {
            super.error(expression, queryContext);
        }
    }

    public void debug(String msg, DataEngineConsts.DebugLogType type) {
        if (this.monitor != null) {
            this.monitor.debug(msg, type);
        } else {
            super.debug(msg, type);
        }
    }

    public IDatabase getDatabase() {
        if (this.monitor != null) {
            return this.monitor.getDatabase();
        }
        return super.getDatabase();
    }
}

