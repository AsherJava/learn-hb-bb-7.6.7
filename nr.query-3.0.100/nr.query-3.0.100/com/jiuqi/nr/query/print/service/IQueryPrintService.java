/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.print.vo.PrintAttributeVo
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.xg.process.table.TablePaginateConfig
 */
package com.jiuqi.nr.query.print.service;

import com.jiuqi.nr.definition.print.vo.PrintAttributeVo;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.xg.process.table.TablePaginateConfig;
import java.io.ByteArrayOutputStream;

public interface IQueryPrintService {
    public ByteArrayOutputStream print(Grid2Data var1, QueryBlockDefine var2, PrintAttributeVo var3, TablePaginateConfig var4) throws Exception;
}

