/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.output.ExportEntity
 */
package nr.midstore2.core.dataset;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.ExportEntity;
import java.util.List;
import nr.midstore2.core.dataset.MidsotreTableContext;

public interface IMidstoreEntityService
extends IEntityUpgrader {
    public IEntityTable getEntityTable(TableContext var1, String var2, ExecutorContext var3);

    public void importEntitys(TableContext var1, ExecutorContext var2, List<ExportEntity> var3);

    public FieldDefine getUnitField(TableDefine var1, TableDefine var2, List<FieldDefine> var3) throws Exception;

    public IEntityTable getIEntityTable(EntityViewDefine var1, MidsotreTableContext var2, ExecutorContext var3) throws Exception;

    public boolean isPeriod(String var1);
}

