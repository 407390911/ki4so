package com.github.ebnew.ki4so.client.key;

import com.github.ebnew.ki4so.core.key.Ki4soKey;

/**
 * Ĭ�ϵ���Կ��Ϣ��ȡʵ���࣬����ֻ��һ���򵥵�ʵ�֣��ǳ�����ȫ��
 * ������������������ʹ�ù�Կ��˽Կ�ķ�ʽ����Կ��Ϣ
 * ���м��ܣ�������Կ�ڹ���������й¶�������м�ǿ��ȫ�ԡ�
 * @author Administrator
 */
public class DefaultKeyServiceImpl implements KeyService{
	
	private String ki4soServerFetchKeyUrl;
	
	public DefaultKeyServiceImpl(String ki4soServerFetchKeyUrl) {
		super();
		this.ki4soServerFetchKeyUrl = ki4soServerFetchKeyUrl;
	}


	@Override
	public Ki4soKey findKeyByAppId(String appId) {
		return null;
	}

}
