package com.test.model.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.test.model.abstraction.ModelAbstraction;

public class Order extends ModelAbstraction {

	// 用户信息
	private String userId;
	private String userName;
	private String userPhone;
	//任务信息
	private String taskId;
	private String taskName;
	// 订单信息
	private Map<Integer, String> payStatusMap; // 构建方法中有定义
	private int payStatus;
	private String payStatusStr;

	// 标价
	private BigDecimal totalFee;
	// 实际支付
	private BigDecimal amountPay;
	// 微信预生成支付ID
	private String prepayId;

	// 此处不要动！!!
	public Order() {
		payStatusMap = new HashMap<Integer, String>();
		payStatusMap.put(1, "新建订单");
		payStatusMap.put(2, "预付单生成(微信支付)");
		payStatusMap.put(3, "支付成功(微信支付)");
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Map<Integer, String> getPayStatusMap() {
		return payStatusMap;
	}

	public void setPayStatusMap(Map<Integer, String> payStatusMap) {
		this.payStatusMap = payStatusMap;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStatusStr() {
		return payStatusStr;
	}

	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getAmountPay() {
		return amountPay;
	}

	public void setAmountPay(BigDecimal amountPay) {
		this.amountPay = amountPay;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

}
