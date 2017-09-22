package com.linkchain.DevelopUtils;

import org.apache.commons.lang3.StringUtils;

public class StringTool {
	public static final String hiddenPhoneNo(String phoneNo) {
		StringBuffer newPhoneNo = new StringBuffer();
		if (StringUtils.isNotBlank(phoneNo) && phoneNo.length() == 11) {
			newPhoneNo.append(phoneNo.substring(0, 3));
			newPhoneNo.append("****");
			newPhoneNo.append(phoneNo.substring(7));
			return newPhoneNo.toString();
		} else {
			return phoneNo;
		}
	}

	public static final String hiddenIdCardNo(String idCardNo) {
		StringBuffer newIdCardNo = new StringBuffer();
		if (StringUtils.isNotBlank(idCardNo)) {
			if (idCardNo.length() == 18) {
				newIdCardNo.append(idCardNo.substring(0, 6));
				newIdCardNo.append("********");
				newIdCardNo.append(idCardNo.substring(14));
			} else if (idCardNo.length() == 15) {
				newIdCardNo.append(idCardNo.substring(0, 6));
				newIdCardNo.append("******");
				newIdCardNo.append(idCardNo.substring(12));
			} else {
				return idCardNo;
			}

			return newIdCardNo.toString();
		} else {
			return idCardNo;
		}
	}

}
