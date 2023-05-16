package com.chenzheng.takeOut.controller;


import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.common.VerificationCodeService;
import com.chenzheng.takeOut.entity.AddressBook;
import com.chenzheng.takeOut.entity.User;
import com.chenzheng.takeOut.service.AddressBookService;
import com.chenzheng.takeOut.service.CategoryService;
import com.chenzheng.takeOut.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;
    @PostMapping
    public R<AddressBook> addAddress(@RequestBody AddressBook addressBook) {
        AddressBook savedAddressBook = addressBookService.addAddress(addressBook);
        return R.success(savedAddressBook);
    }
    @GetMapping("/list")
    public R<List<AddressBook>> listUserAddresses(){
        List<AddressBook> addressBooks = addressBookService.listUserAddresses();
        return R.success(addressBooks);
    }
    @PutMapping("/default")
    public R<Boolean> setDefaultAddress(@RequestBody Map<String, Long> body){
        Long id = body.get("id");
        boolean result = addressBookService.setDefaultAddress(id);
        if (result) {
            return R.success(true);
        } else {
            return R.error("设置默认地址失败");
        }
    }








}


