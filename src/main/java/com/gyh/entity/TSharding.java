package com.gyh.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TSharding {
    private Long id;
    private String column1;
    private int column2;
    private String column_date;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date modifiedTime;


}
