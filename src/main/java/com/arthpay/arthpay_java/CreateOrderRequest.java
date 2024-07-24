package com.arthpay.arthpay_java;

public class CreateOrderRequest {

	private OrderPojo orderDetails;
	private CustomerPojo customerDetails;
	
	public OrderPojo getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(OrderPojo orderDetails) {
		this.orderDetails = orderDetails;
	}
	public CustomerPojo getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(CustomerPojo customerDetails) {
		this.customerDetails = customerDetails;
	}
}
