package com.gyh.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 分表策略
 *
 *
 * @author guoyanhong5
 * @date 2020/10/27 18:17
 */
public class CustomShardingAlgorithm implements PreciseShardingAlgorithm, RangeShardingAlgorithm<Integer> {

    /**
     * 下划线
     */
    private String UNDER_LINE = "_";

    @Override
    public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
        String target = shardingValue.getValue().toString();
        // 截取年份
        return shardingValue.getLogicTableName() + UNDER_LINE + target.substring(0, 4);
    }


    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Integer> shardingValue) {
        Collection<String> availableList = new ArrayList<>();
        Range valueRange = shardingValue.getValueRange();
        for (String target : availableTargetNames) {
            Integer shardValue = Integer.parseInt(target.substring(target.length()-4));
            if (valueRange.hasLowerBound()) {
                String lowerStr = valueRange.lowerEndpoint().toString();
                Integer start = Integer.parseInt(lowerStr.substring(0, 4));
                if (start - shardValue > 0) {
                    continue;
                }
            }
            if (valueRange.hasUpperBound()) {
                String upperStr = valueRange.upperEndpoint().toString();
                Integer end = Integer.parseInt(upperStr.substring(0, 4));
                if (end - shardValue < 0) {
                    continue;
                }
            }
            availableList.add(target);
        }
        return availableList;
    }


}
