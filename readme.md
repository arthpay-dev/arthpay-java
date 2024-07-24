# Arthpay Java SDK

Official java bindings for the [Arthpay API](https://docs.arthpay.com/docs/payments).

## Documentation

Documentation of Arthpay's API and their usage is available at <https://docs.arthpay.com>

## Requirements

Java 1.8 or later

## Installation

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.arthpay</groupId>
  <artifactId>arthpay-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Sample Usage

```java
try {
		ArthpayMerchant merchant = new ArthpayMerchant([YOUR_CLIENT_ID],[YOUR_CLIENT_SECRET]);
		
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
		
		CreateOrderRequestPojo orderObj = new CreateOrderRequestPojo();
		orderObj.setCustomerDetails(customerDetails);
		orderObj.setOrderDetails(orderDetails);
		
		
		var redirectionUrl = merchant.orders.create(orderObj);
		System.out.println("redirection Url for merchant : "+redirectionUrl);
	} catch (Exception ex) {
		System.out.println("Exception : "+ex);
	}
```