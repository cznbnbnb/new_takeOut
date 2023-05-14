package com.chenzheng.takeOut.common;

import org.springframework.stereotype.Service;
import java.util.concurrent.*;

@Service
public class VerificationCodeService {
    private final ConcurrentHashMap<String, String> codeMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public void setCode(String phone, String code) {
        codeMap.put(phone, code);
        executor.schedule(() -> codeMap.remove(phone), 60, TimeUnit.SECONDS);  // 60 秒后删除验证码
    }

    public String getCode(String phone) {
        return codeMap.get(phone);
    }
}
