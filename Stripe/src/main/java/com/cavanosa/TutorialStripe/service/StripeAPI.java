package com.cavanosa.TutorialStripe.service;


import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.Map;

    @RestController
    @RequestMapping("/payment")
    @CrossOrigin(origins = "*")
    public class StripeAPI {

        @Autowired
        private StripeService stripeService;

        @PostMapping("/create")
        public ResponseEntity<String> createPaymentIntent(@RequestBody Map<String, Object> requestData) throws StripeException {
            int amount = (int) requestData.get("amount");
            String currency = (String) requestData.get("currency");

            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount, currency);
            JSONObject paymentJson = new JSONObject(paymentIntent.toJson());

            return new ResponseEntity<>(paymentJson.toString(), HttpStatus.OK);
            //return new ResponseEntity<>(paymentIntent.getClientSecret(), HttpStatus.OK);
        }


    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPaymentIntent(@RequestParam("payment_intent_id") String paymentIntentId,
                                                       @RequestParam("payment_method_id") String paymentMethodId) throws StripeException {
        PaymentIntent paymentIntent = stripeService.confirmPaymentIntent(paymentIntentId, paymentMethodId);
        return new ResponseEntity<>(paymentIntent.toJson(), HttpStatus.OK);
    }
    }


