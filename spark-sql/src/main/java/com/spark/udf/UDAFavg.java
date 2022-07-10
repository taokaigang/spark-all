package com.spark.udf;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class UDAFavg extends UserDefinedAggregateFunction {
    @Override
    public StructType inputSchema() {
        List structFields = new ArrayList<>();
        structFields.add(DataTypes.createStructField("arg",DataTypes.DoubleType,true));
        return DataTypes.createStructType(structFields);
    }

    @Override
    public StructType bufferSchema() {
        return null;
    }

    @Override
    public DataType dataType() {
        return null;
    }

    @Override
    public boolean deterministic() {
        return false;
    }

    @Override
    public void initialize(MutableAggregationBuffer buffer) {

    }

    @Override
    public void update(MutableAggregationBuffer buffer, Row input) {

    }

    @Override
    public void merge(MutableAggregationBuffer buffer1, Row buffer2) {

    }

    @Override
    public Object evaluate(Row buffer) {
        return null;
    }
}
