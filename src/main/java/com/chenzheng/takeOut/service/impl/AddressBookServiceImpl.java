package com.chenzheng.takeOut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzheng.takeOut.common.BaseContext;
import com.chenzheng.takeOut.entity.AddressBook;
import com.chenzheng.takeOut.mapper.AddressBookMapper;
import com.chenzheng.takeOut.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Override
    public AddressBook addAddress(AddressBook addressBook) {
        // 设置用户id，指定当前是哪个用户的地址数据
        Long currentId = BaseContext.getCurrentId();
        addressBook.setUserId(currentId);

        // 保存地址信息
        this.save(addressBook);

        return addressBook;
    }

    @Override
    public List<AddressBook> listUserAddresses() {
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, currentId);
        return this.list(queryWrapper);
    }
    @Transactional
    @Override
    public boolean setDefaultAddress(Long id) {
        Long currentId = BaseContext.getCurrentId();
        log.info("Received ID: {}", id);

        // 先将该用户的所有地址设为非默认
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getUserId, currentId).set(AddressBook::getIsDefault, 0);
        this.update(updateWrapper);

        // 再将指定ID的地址设为默认
        AddressBook defaultAddressBook = new AddressBook();
        defaultAddressBook.setId(id);
        defaultAddressBook.setIsDefault(1);
        return this.updateById(defaultAddressBook);
    }





}
