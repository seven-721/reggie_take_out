package com.seven.reggie.servce;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.EmployeeEntity;
import com.seven.reggie.entity.Page;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2022/12/26
 * Time:14:58
 */
public interface EmployeeService {
    R<EmployeeEntity> loginByName(EmployeeEntity employeeEntity);

    //添加用户
    R add(EmployeeEntity employeeEntity);

    //员工分页
    R<Page> page(Integer page, Integer pageSize, String name);

    R update(EmployeeEntity employeeEntity);

    R<EmployeeEntity> findById(Long id);
}

