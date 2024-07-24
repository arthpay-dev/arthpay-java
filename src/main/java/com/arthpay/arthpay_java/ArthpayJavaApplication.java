package com.arthpay.arthpay_java;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArthpayJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArthpayJavaApplication.class, args);
		try {
			ArthpayMerchant merchant = new ArthpayMerchant("AP_DEV_7bYVzSdvZJrwFKvfM0EthW3E6FIWdmhU7czOaPlRVA","XkqC4PWfwtQy3Qnbk3eXbw==");
			
			OrderPojo orderDetails = new OrderPojo();
			orderDetails.setOrderInfo("MER_ORD_001"); //order id by merchant
			orderDetails.setCurrency("INR");
			orderDetails.setAmount("500"); //5 rs in paisa i.e. 5*100;

			CustomerPojo customerDetails = new CustomerPojo();
			customerDetails.setFirstName("John");
			customerDetails.setLastName("Doe");
			customerDetails.setChAddrStreet("John Nagar Road");
			customerDetails.setChAddrCity("Mulund");
			customerDetails.setChAddrState("Maharashtra");
			customerDetails.setChAddrZip("4000001");
			
			CreateOrderRequest orderObj = new CreateOrderRequest();
			orderObj.setCustomerDetails(customerDetails);
			orderObj.setOrderDetails(orderDetails);
			
			
			var redirectionUrl = merchant.orders.create(orderObj);
			System.out.println("redirection Url for merchant : "+redirectionUrl);
		} catch (Exception ex) {
			System.out.println("Exception : "+ex);
		}
	}

}
