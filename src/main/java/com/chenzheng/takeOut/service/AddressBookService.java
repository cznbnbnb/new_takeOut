package com.chenzheng.takeOut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzheng.takeOut.entity.AddressBook;

import java.util.List;


public interface AddressBookService extends IService<AddressBook> {
    AddressBook addAddress(AddressBook addressBook);
    List<AddressBook> listUserAddresses();
    boolean setDefaultAddress(Long id);
}
