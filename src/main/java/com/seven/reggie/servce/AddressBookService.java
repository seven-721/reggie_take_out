package com.seven.reggie.servce;

import com.seven.reggie.entity.AddressBook;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:15:08
 */
public interface AddressBookService {
    List<AddressBook> queryAddressList(Long userId);
    //新增
    void add(AddressBook addressBook);

    //根据id查询地址
    AddressBook getById(Long id);

    AddressBook getDefaultAddress(Long userId);

    //设置默认地址
    void updeteDefaultAddress(AddressBook addressBook);
}
