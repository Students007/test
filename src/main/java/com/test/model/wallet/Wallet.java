package com.test.model.wallet;

import com.test.model.abstraction.ModelAbstraction;

//用户每查询一次，保存一次
public class Wallet extends ModelAbstraction {

	// 用户信ID
	private String userId;

	// 发布中任务的支付
	private double taskPay;
	// 发布中任务的差额
	private double taskDiff;

	// 总收入
	private double taskUserAll;
	// 做单中收入
	private double taskUserDoing;
	// 待审核收入
	private double taskUserToCheck;
	// 审核完成收入,可提现
	private double taskUserChecked;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getTaskPay() {
		return taskPay;
	}

	public void setTaskPay(double taskPay) {
		this.taskPay = taskPay;
	}

	public double getTaskDiff() {
		return taskDiff;
	}

	public void setTaskDiff(double taskDiff) {
		this.taskDiff = taskDiff;
	}

	public double getTaskUserAll() {
		return taskUserAll;
	}

	public void setTaskUserAll(double taskUserAll) {
		this.taskUserAll = taskUserAll;
	}

	public double getTaskUserDoing() {
		return taskUserDoing;
	}

	public void setTaskUserDoing(double taskUserDoing) {
		this.taskUserDoing = taskUserDoing;
	}

	public double getTaskUserToCheck() {
		return taskUserToCheck;
	}

	public void setTaskUserToCheck(double taskUserToCheck) {
		this.taskUserToCheck = taskUserToCheck;
	}

	public double getTaskUserChecked() {
		return taskUserChecked;
	}

	public void setTaskUserChecked(double taskUserChecked) {
		this.taskUserChecked = taskUserChecked;
	}

}
