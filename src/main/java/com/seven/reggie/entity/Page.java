package com.seven.reggie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/27
 * Time:17:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    protected List<T> records;
    protected long total;
}
