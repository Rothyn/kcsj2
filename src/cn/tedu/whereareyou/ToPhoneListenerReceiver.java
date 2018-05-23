package cn.tedu.whereareyou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class ToPhoneListenerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ct, Intent it) {
		// TODO Auto-generated method stub
		Toast.makeText(ct, "���ڴ���ȥ���ع���", Toast.LENGTH_LONG).show();
		//����鵽��������ֻ����Ⲧ��绰ʱ��׼����ʼ����ȥ���ز���
		if(it.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
			//���䣺���ȥ�����
			String qudiannumber = it.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			//�����Ⲧ��绰���Ǽ����ʱ���ſ�ʼ���м��
			if(!qudiannumber.equals(WayInformations.LPPN)){
				//�������ֻ�����һ����ض���
				SmsManager sm = SmsManager.getDefault();
				String message = "TA is Called Phone to "+ qudiannumber;
				sm.sendTextMessage(WayInformations.LPPN, null, message, null, null);
			}
		}
	}

}
