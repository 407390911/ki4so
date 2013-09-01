package com.github.ebnew.ki4so.client.key;

import com.github.ebnew.ki4so.core.key.Ki4soKey;

/**
 * ��ȡӦ�ö�Ӧ�ļ���Key�ķ���
 * @author Administrator
 *
 */
public interface KeyService {
	
	/**
	 * ����Ӧ�õ�ID���Ҷ�Ӧ����Կ��Ϣ�����ܴ�Զ�̻�ȡ
	 * ��Կ��Ϣ������Ҫע�ⰲȫ���⡣
	 * @param appId Ӧ�õ�ID.
	 * @return Ӧ��ID��Ӧ����Կ��Ϣ��
	 */
	public Ki4soKey findKeyByAppId(String appId);

}
