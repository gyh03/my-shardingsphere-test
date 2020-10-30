package test;

import com.gyh.GyhApp;
import com.gyh.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GyhApp.class)
public class ShardingTest {

    @Autowired
    private TableService tableService;

    @Test
    public void saveDatas() {
        tableService.saveDatas();
    }

    @Test
    public void saveDataWithNoTransaction() throws Exception {
        tableService.saveDataNoTransaction();
    }

    @Test
    public void saveDataWithSpringTransaction() throws Exception {
        tableService.saveDataWithSpringTx();
    }
    @Test
    public void saveDataWithSpringTransactionAndNomalEx() throws Exception {
        tableService.saveDataWithSpringTxAndNomalEx();
    }

    @Test
    public void saveDataWithSpringTransactionAndDownMysql() {
        tableService.saveDataWithSpringTxAndDownMysql();
    }

    @Test
    public void saveDataWithXAShardingTransaction() {
        tableService.saveDataWithXAShardingTransaction();
    }

}
