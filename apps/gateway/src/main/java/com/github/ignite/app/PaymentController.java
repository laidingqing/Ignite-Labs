package com.github.ignite.app;

import com.node.model.Payment;
import com.node.services.PaymentService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import static com.node.CacheConstants.PAYMENT_CACHE;


/**
 * @author Bhuwan Upadhyay
 * @date 2017/05/07
 */
@RestController
public class PaymentController {

    @Autowired
    private Ignite ignite;

    private AtomicLong atomicLong = new AtomicLong();

    @PostMapping("/payment/add")
    @ResponseBody
    public String add() {
        PaymentService paymentService = ignite.services().serviceProxy(PaymentService.SERVICE_NAME, PaymentService.class, false);
        paymentService.addPayment(
                Payment.builder().purpose("Transfer-")
                .creationDate(new Date())
                .build()
        );
        return "Created";
    }

    @GetMapping("/payment/{id}")
    @ResponseBody
    public Payment find(@PathVariable("id") Long id) {
        PaymentService paymentService = ignite.services().serviceProxy(PaymentService.SERVICE_NAME, PaymentService.class, false);
        return paymentService.getPayment(id);
    }

    @DeleteMapping("/payment/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Long id) {
        IgniteCache<Long, Payment> paymentIgniteCache = ignite.getOrCreateCache(PAYMENT_CACHE);
        paymentIgniteCache.remove(id);
        return "Deleted";
    }

}
