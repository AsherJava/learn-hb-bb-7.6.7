/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.data;

import com.jiuqi.bi.file.cspro.data.AllDataProcessorListener;
import com.jiuqi.bi.file.cspro.data.DataRow;
import com.jiuqi.bi.file.cspro.data.IdItemOrderedProcessorListener;
import com.jiuqi.bi.file.cspro.data.RecordGroupProcessorListener;
import com.jiuqi.bi.file.cspro.data.SingleDataProcessorListener;
import com.jiuqi.bi.file.cspro.dict.Dictionary;
import com.jiuqi.bi.file.cspro.dict.Level;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class DataProcessor {
    protected Dictionary dictionary;

    private DataProcessor(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    abstract void processData(String var1, Object[] var2) throws Exception;

    abstract void finish() throws Exception;

    public static DataProcessor createProcess(Dictionary dictionary, Object listener) {
        if (listener instanceof SingleDataProcessorListener) {
            return new SingleDataProcessor(dictionary, (SingleDataProcessorListener)listener);
        }
        if (listener instanceof IdItemOrderedProcessorListener) {
            return new IdItemOrderedProcessor(dictionary, (IdItemOrderedProcessorListener)listener);
        }
        if (listener instanceof AllDataProcessorListener) {
            return new AllDataProcessor(dictionary, (AllDataProcessorListener)listener);
        }
        if (listener instanceof RecordGroupProcessorListener) {
            return new RecordGroupProcessor(dictionary, (RecordGroupProcessorListener)listener);
        }
        throw new IllegalArgumentException("\u4e0d\u652f\u6301\u7684\u56de\u8c03\u63a5\u53e3\uff1a" + listener.getClass());
    }

    private static class RecordGroupProcessor
    extends DataProcessor {
        private RecordGroupProcessorListener listener;
        private Map<String, List<DataRow>> rows = new HashMap<String, List<DataRow>>();

        public RecordGroupProcessor(Dictionary dictionary, RecordGroupProcessorListener listener) {
            super(dictionary);
            this.listener = listener;
        }

        @Override
        void processData(String recordType, Object[] values) throws Exception {
            DataRow row = new DataRow(recordType, values);
            List<DataRow> list = this.rows.get(recordType);
            if (list == null) {
                list = new ArrayList<DataRow>();
                this.rows.put(recordType, list);
            }
            list.add(row);
        }

        @Override
        void finish() throws Exception {
            this.listener.processGroupData(this.dictionary, this.rows);
        }
    }

    private static class IdItemOrderedProcessor
    extends DataProcessor {
        private IdItemOrderedProcessorListener listener;
        private Object[] currentKey;
        private int keyLength;
        private List<DataRow> groupData = new ArrayList<DataRow>();

        public IdItemOrderedProcessor(Dictionary dictionary, IdItemOrderedProcessorListener listener) {
            super(dictionary);
            this.listener = listener;
            Level level = dictionary.getLevel(0);
            this.keyLength = level.getIdItemCount();
        }

        @Override
        void processData(String recordType, Object[] values) throws Exception {
            Object[] key = new Object[this.keyLength];
            System.arraycopy(values, 0, key, 0, this.keyLength);
            DataRow row = new DataRow(recordType, values);
            if (this.currentKey == null) {
                this.currentKey = key;
                this.groupData.add(row);
            } else {
                int c = IdItemOrderedProcessor.compareArray(this.currentKey, key);
                if (c == 0) {
                    this.groupData.add(row);
                } else {
                    this.listener.processGroupData(this.dictionary, this.groupData);
                    this.groupData.clear();
                    this.currentKey = key;
                    this.groupData.add(row);
                }
            }
        }

        @Override
        void finish() throws Exception {
            if (this.groupData.size() != 0) {
                this.listener.processGroupData(this.dictionary, this.groupData);
            }
            this.listener.finish();
        }

        private static int compareArray(Object[] array1, Object[] array2) {
            if (array1.length != array2.length) {
                return array1.length - array2.length;
            }
            for (int i = 0; i < array1.length; ++i) {
                Object data1 = array1[i];
                Object data2 = array2[i];
                if (data1 == null) {
                    if (data2 == null) continue;
                    return -1;
                }
                if (data2 == null) {
                    return 1;
                }
                int c = ((Comparable)data1).compareTo(data2);
                if (c == 0) continue;
                return c;
            }
            return 0;
        }
    }

    private static class AllDataProcessor
    extends DataProcessor {
        private AllDataProcessorListener listener;
        private List<DataRow> allData = new ArrayList<DataRow>();

        public AllDataProcessor(Dictionary dictionary, AllDataProcessorListener listener) {
            super(dictionary);
            this.listener = listener;
        }

        @Override
        void processData(String recordType, Object[] values) throws Exception {
            DataRow row = new DataRow(recordType, values);
            this.allData.add(row);
        }

        @Override
        void finish() throws Exception {
            this.listener.processAllData(this.dictionary, this.allData);
        }
    }

    private static class SingleDataProcessor
    extends DataProcessor {
        private SingleDataProcessorListener listener;

        public SingleDataProcessor(Dictionary dictionary, SingleDataProcessorListener listener) {
            super(dictionary);
            this.listener = listener;
        }

        @Override
        void processData(String recordType, Object[] values) throws Exception {
            DataRow row = new DataRow(recordType, values);
            this.listener.processSingleData(this.dictionary, row);
        }

        @Override
        void finish() throws Exception {
            this.listener.finish();
        }
    }
}

