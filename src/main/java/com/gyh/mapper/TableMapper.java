package com.gyh.mapper;

import com.gyh.entity.TSharding;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;


/**
 * mapper
 *
 * @author gyh
 */
//@Mapper
public interface TableMapper {

    @Select("SELECT column1,column2,column_date from t_sharding  WHERE  username = #{id}")
    TSharding getDataById(Long id);

//    @Select("SELECT id, org_name  AS orgName FROM manager_org_budget WHERE `dt` = '2020-04-30'")
//    List<TOrg> testTorg();

    @Insert("insert into t_sharding(column1,column2,column_date) values (#{column1},#{column2},#{column_date})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveOneData(TSharding user);

}
