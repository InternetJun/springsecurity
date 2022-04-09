package com.example.service.impl;


import com.example.service.MyService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class MyServiceImpl implements MyService {

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object object = authentication.getPrincipal();
        if (object instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) object;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
        }
        return false;
    }

//    public static void main(String[] args) throws InterruptedException {
//        int size = 100;
//        CountDownLatch countDownLatch = new CountDownLatch(size);
//        AtomicInteger atomicInteger = new AtomicInteger();
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        executorService.submit(
//        try {
//            new Runnable() {
//                @Override
//                public void run() {
//                    int result = task();
//                    atomicInteger.addAndGet(result);
//                }
//            };
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            countDownLatch.countDown();
//        });
//        countDownLatch.await();
//
//    }

    //想实现利用多线程的知识对数据的同一个调用了。
    public static int task() {
        return 1;
    }
}
