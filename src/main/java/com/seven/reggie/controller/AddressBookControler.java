package com.seven.reggie.controller;

import com.seven.reggie.common.R;
import com.seven.reggie.entity.AddressBook;
import com.seven.reggie.servce.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:16:02
 */
@RestController
@RequestMapping("/addressBook")
public class AddressBookControler {

    @Autowired
    private AddressBookService _addressBookService;

    @GetMapping("/list")
    public R<List<AddressBook>> list(HttpSession session){
        Long userId= (Long) session.getAttribute("user");
        //根据id查询数据
        List<AddressBook> addressBookList=_addressBookService.queryAddressList(userId);
        return R.success(addressBookList);

    }

    /**
     * 新增数据
     * @param addressBook
     * @param session
     * @return
     */
    @PostMapping
    public R<AddressBook> add(@RequestBody AddressBook addressBook, HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        addressBook.setUserId(userId);
        addressBook.setCreateUser(userId);
        addressBook.setUpdateUser(userId);
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        _addressBookService.add(addressBook);
        return R.success(addressBook);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        AddressBook addressBook = _addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R<AddressBook> getDefault(HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        AddressBook addressBook = _addressBookService.getDefaultAddress(userId);

        if (null == addressBook) {
            return R.error("没有找到该对象");
        } else {
            return R.success(addressBook);
        }
    }

    @PutMapping("default")
    public R setDefault(@RequestBody AddressBook addressBook,HttpSession session){
       Long userId= (Long) session.getAttribute("user");
        addressBook.setUserId(userId);
        _addressBookService.updeteDefaultAddress(addressBook);
        return R.success(addressBook);


    }

}
