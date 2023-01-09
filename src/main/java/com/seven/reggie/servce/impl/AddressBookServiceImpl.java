package com.seven.reggie.servce.impl;

import com.seven.reggie.dao.AddressBookDao;
import com.seven.reggie.entity.AddressBook;
import com.seven.reggie.servce.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:15:08
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {


    @Autowired
    private AddressBookDao _addressBookDao;


    @Override
    public List<AddressBook> queryAddressList(Long userId) {

        List<AddressBook> addressBookList=_addressBookDao.queryAddressList(userId);

        return addressBookList;
    }

    /**
     *   新增
     * @param addressBook
     */
    @Override
    public void add(AddressBook addressBook) {
        _addressBookDao.add(addressBook);
    }

    @Override
    public AddressBook getById(Long id) {
        return _addressBookDao.findById(id);
    }

    @Override
    public AddressBook getDefaultAddress(Long userId) {
        return _addressBookDao.getDefaultAddress(userId);
    }

    @Override
    public void updeteDefaultAddress(AddressBook addressBook) {
        _addressBookDao.removeDefaultAddress(addressBook.getUserId());
        _addressBookDao.updeteDefaultAddress(addressBook);
    }
}
