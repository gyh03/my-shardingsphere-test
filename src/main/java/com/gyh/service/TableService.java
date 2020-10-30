package com.gyh.service;

import com.gyh.entity.TSharding;
import com.gyh.mapper.TableMapper;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TableService {
    @Autowired
    private TableMapper tableMapper;

    /**
     * 使用 sharding 分库分表
     *
     * @author guoyanhong5
     * @date 2020/10/30 15:51
     */
    public void saveDatas() {
        saveData("saveDatas");
    }

    /**
     * 不使用事务管理器时，发生异常时，事务不能正确回滚，导致数据错误
     *
     * @throws Exception
     * @author guoyanhong5
     * @date 2020/10/30 15:52
     */
    public void saveDataNoTransaction() throws Exception {
        saveData("saveDataWithNoTransaction");
        throw new Exception("模拟一个异常");
    }

    /**
     * 使用 @Transactional spring事务管理器，ShardingTransaction 默认为 TransactionType.LOCAL 本地事务
     * spring 托管事务，事务管理器 TransactionManager 中的 数据源 DataSource.getConnection() 获取到 ShardingConnection 对象
     * ShardingConnection.dataSourceMap 中保存多个数据源信息，使得 spring 事务管理器，能将多个数据源一起提交或回滚。
     * <p>
     * TransactionType.LOCAL 支持：
     * 完全支持非跨库事务，例如：仅分表，或分库但是路由的结果在单库中；
     * 完全支持因逻辑异常导致的跨库事务。例如：同一事务中，跨两个库更新。更新完毕后，抛出空指针，则两个库的内容都能回滚。
     *
     * @author guoyanhong5
     * @date 2020/10/29 18:25
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveDataWithSpringTx() throws Exception {
        // 一起提交
        saveData("saveDataWithSpringTx");
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveDataWithSpringTxAndNomalEx() throws Exception {
        // 一起回滚
        saveData("saveDataWithSpringTxAndNomalEx");
        throw new Exception("模拟一个异常");
    }

    /**
     * TransactionType.LOCAL 不支持：
     * 不支持因网络、硬件异常导致的跨库事务。例如：同一事务中，跨两个库更新，更新完毕后、未提交之前，第一个库宕机，则只有第二个库数据提交
     *
     * @throws Exception
     * @author guoyanhong5
     * @date 2020/10/29 18:25
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveDataWithSpringTxAndDownMysql() {
        saveData("saveDataWithSpringTxAndDownMysql");
    }

    /**
     * 使用 @ShardingTransactionType 并指定 TransactionType.XA 分布式事务
     * 支持：
     * 数据分片后的跨库事务；
     * 两阶段提交保证操作的原子性和数据的强一致性；
     * 服务宕机重启后，提交/回滚中的事务可自动恢复；
     * 支持同时使用 XA 和非 XA 的连接池。
     * <p>
     * 不支持：
     * 服务宕机后，在其它机器上恢复提交/回滚中的数据。
     *
     * @author guoyanhong5
     * @date 2020/10/29 18:25
     */
    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    public void saveDataWithXAShardingTransaction() {
        saveData("saveDataWithXAShardingTransaction");
    }

    /**
     * 两条 update sql
     *
     * @author guoyanhong5
     * @date 2020/10/29 18:25
     */
    private void saveData(String type) {
        Date data = new Date();
        TSharding tSharding = new TSharding();
        tSharding.setColumn1(type);
        tSharding.setColumn2(3);
        tSharding.setColumn_date("2019-10-09 19:56:23");
        tSharding.setCreatedTime(data);
        tSharding.setModifiedTime(data);
        tableMapper.saveOneData(tSharding);

        TSharding tSharding2 = new TSharding();
        tSharding2.setColumn1(type);
        tSharding2.setColumn2(4);
        tSharding2.setColumn_date("2019-10-09 19:56:23");
        tSharding2.setCreatedTime(data);
        tSharding2.setModifiedTime(data);
        tableMapper.saveOneData(tSharding2);
    }
}
