package com.cavanosa.TutorialStripe.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.key.secret}")
    private String stripeApiKey;

    public PaymentIntent createPaymentIntent(int amount, String currency) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);
    }

    public PaymentIntent confirmPaymentIntent(String paymentIntentId, String paymentMethodId) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", paymentMethodId);

        paymentIntent.confirm(params);

        return paymentIntent;
    }
}

