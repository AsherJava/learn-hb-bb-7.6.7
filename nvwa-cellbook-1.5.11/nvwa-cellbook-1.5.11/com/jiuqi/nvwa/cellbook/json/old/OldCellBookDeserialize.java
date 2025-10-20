/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nvwa.cellbook.json.old;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import java.io.IOException;

public interface OldCellBookDeserialize {
    public String getMinVersion();

    public String getMaxVersion();

    public CellBook deserialize(CellBook var1, JsonNode var2) throws IOException, JsonProcessingException;
}

