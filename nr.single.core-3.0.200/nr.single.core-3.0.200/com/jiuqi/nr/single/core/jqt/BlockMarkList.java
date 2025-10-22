/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.jqt;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.jqt.BlockMark;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockMarkList {
    private List<BlockMark> blockList;
    Map<Integer, BlockMark> blockMap;

    public List<BlockMark> getBlockList() {
        if (this.blockList == null) {
            this.blockList = new ArrayList<BlockMark>();
        }
        return this.blockList;
    }

    public void setBlockList(List<BlockMark> blockList) {
        this.blockList = blockList;
    }

    public void loadFromStream(Stream stream) throws StreamException {
        int nodeCount = ReadUtil.readIntValue(stream);
        if (nodeCount > 0) {
            for (int i = 0; i < nodeCount; ++i) {
                BlockMark block = new BlockMark();
                block.setStartPos(ReadUtil.readIntValue(stream));
                block.sethStartPos(ReadUtil.readIntValue(stream));
                block.setLength(ReadUtil.readIntValue(stream));
                this.getBlockList().add(block);
                this.getBlockMap().put(block.getStartPos(), block);
            }
        }
    }

    public BlockMark find(int startPos) {
        BlockMark result = null;
        if (this.getBlockMap().containsKey(startPos)) {
            result = this.getBlockMap().get(startPos);
        }
        return result;
    }

    public Map<Integer, BlockMark> getBlockMap() {
        if (this.blockMap == null) {
            this.blockMap = new HashMap<Integer, BlockMark>();
        }
        return this.blockMap;
    }

    public void setBlockMap(Map<Integer, BlockMark> blockMap) {
        this.blockMap = blockMap;
    }
}

