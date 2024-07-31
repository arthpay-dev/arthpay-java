# Arthpay Java SDK

Official java bindings for the Arthpay APIs

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
	orderDetails.setCurrency("INR"); //currency : INR for Indian Rupees
	orderDetails.setAmount("500"); //5 rs in paisa i.e. 5*100;
	
	CustomerPojo customerDetails = new CustomerPojo();
	customerDetails.setFirstName("John"); //customers first name
	customerDetails.setLastName("Doe"); //customer's last name
	customerDetails.setChAddrStreet("John Nagar Road"); //customer's street address
	customerDetails.setChAddrCity("Mulund"); //customer's city
	customerDetails.setChAddrState("Maharashtra"); //customer's State
	customerDetails.setChAddrZip("4000001"); ///customer's zip code
	customerDetails.setChEmail("support@arthpay.com"); // customer's email
	customerDetails.setChMobile("+919876543210");
	
	CreateOrderRequest orderObj = new CreateOrderRequest();
	orderObj.setCustomerDetails(customerDetails);
	orderObj.setOrderDetails(orderDetails);
	
	
	var redirectionUrl = merchant.orders.create(orderObj);
	System.out.println("redirection Url for merchant : "+redirectionUrl);
} catch (Exception ex) {
	System.out.println("Exception : "+ex);
}
```
