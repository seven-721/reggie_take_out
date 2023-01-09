package com.seven.reggie.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
		//DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GHhatiznGG52T5EidAW", "xOFzvwf7G8jPhL3eNpyOmjPjFRZJfx");
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tGx5GDBJAu74jWRWZPu", "ByaPw5X5cLMEUfw7cUMP9vOtASvbcn");
		IAcsClient client = new DefaultAcsClient(profile);

		SendSmsRequest request = new SendSmsRequest();
		request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName(signName);
		request.setTemplateCode(templateCode);
		request.setTemplateParam("{\"code\":\""+"我喜欢你"+"\"}");
		try {
			SendSmsResponse response = client.getAcsResponse(request);
			System.out.println("短信发送成功"+response);
		}catch (ClientException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		//生成一个验证码
		Integer code = ValidateCodeUtils.generateValidateCode(4);

		System.out.println("生成验证码："+code);

			/*参数一：签名
			参数二： 模板
			参数三： 接收人手机号
			参数四： 验证码*/

		//sendMessage("七锦", "SMS_267075644", "18786135448", code + "");
		sendMessage("七锦", "SMS_267075644", "18286652192", code + "");

	}

}
