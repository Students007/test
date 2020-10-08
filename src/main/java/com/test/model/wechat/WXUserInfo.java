/**
 * Copyright 2019 bejson.com
 */
package com.test.model.wechat;

import java.util.List;

/**
 * Auto-generated: 2019-11-20 17:11:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class WXUserInfo {

	private String openid;
	private String nickname;
	private int sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private List<String> privilege;
	private String unionid;

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getSex() {
		return sex;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setPrivilege(List<String> privilege) {
		this.privilege = privilege;
	}

	public List<String> getPrivilege() {
		return privilege;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getUnionid() {
		return unionid;
	}

}