package cn.tedu.whereareyou;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WayMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_way_main);
	
		
		//ʵ�ְ�ť��Ч����1.����ѡ��ť��2.���ܿ���/�رհ�ť
		//1.����ѡ��ť��Ч��ʵ�֣�δѡ��/ѡ��
		//1.�Ȼ�ý����ϵİ�ť�����2.���Ӱ�ť����ĵ��Ч��
		final Button fpl_btn = (Button)this.findViewById(R.id.FPL_BIN_ID);
		final Button qpl_btn = (Button)this.findViewById(R.id.QPL_BIN_ID);
		final Button msg_btn = (Button)this.findViewById(R.id.MSG_BIN_ID);
		
		fpl_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Toast.makeText(WayMainActivity.this, "������������ع���", Toast.LENGTH_LONG).show();*/
				if(WayInformations.isFPL){
					//��ʾ��ǰ��ѡ��״̬��������Ϊδѡ��
					WayInformations.isFPL=false;
					fpl_btn.setText("������");
				}else{
					//��ʾ��ǰ��δѡ��״̬��������Ϊѡ��
					WayInformations.isFPL=true;
					fpl_btn.setText("������<��>");
				}
				
			}
			
		});
		qpl_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(WayInformations.isQPL){
					//��ʾ��ǰ��ѡ��״̬��������Ϊδѡ��
					WayInformations.isQPL=false;
					qpl_btn.setText("ȥ����");
				}else{
					//��ʾ��ǰ��δѡ��״̬��������Ϊѡ��
					WayInformations.isQPL=true;
					qpl_btn.setText("ȥ����<��>");
				}
				
			}
			
		});
		msg_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(WayInformations.isMSG){
					//��ʾ��ǰ��ѡ��״̬��������Ϊδѡ��
					WayInformations.isMSG=false;
					msg_btn.setText("���ż��");
				}else{
					//��ʾ��ǰ��δѡ��״̬��������Ϊѡ��
					WayInformations.isMSG=true;
					msg_btn.setText("���ż��<��>");
				}
				
			}
			
		});
		//2.���ܿ���/�رհ�ť��Ч��ʵ�֣������л�+���ز���
		//1.�Ȼ�ý��水ť 2.���Ӱ�ť���Ч��
		final Button gn_btn = (Button)this.findViewById(R.id.GN_BIN_ID);
		gn_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//��ȡ�����ϵļ���ߵ��ֻ����룬���汸��
				EditText et=(EditText)findViewById(R.id.ET_ID);
				WayInformations.LPPN =et.getText().toString();
				// TODO Auto-generated method stub
				/*Toast.makeText(WayMainActivity.this, "������������ع���", Toast.LENGTH_LONG).show();*/
				if(WayInformations.isGN){
					//��ʾ��ǰ�ǿ���״̬����Ҫ������״̬����>�ر�״̬����>�رղ���
					WayInformations.isGN =false;
					gn_btn.setText("������ѡ���");
					//���䣺������������+��ԭ��������
					et.setEnabled(true);
					fpl_btn.setEnabled(true);
					qpl_btn.setEnabled(true);
					msg_btn.setEnabled(true);
					et.setText("");
					WayInformations.LPPN = "";
					if(WayInformations.isFPL){
						fpl_btn.setText("������");
						//WayInformations.isFPL = false;
					}
					if(WayInformations.isQPL){
						qpl_btn.setText("ȥ����");
					}
					if(WayInformations.isMSG){
						msg_btn.setText("���ż��");
					}
					//������ת��Service���н��йرռ�ع��ܲ���
					Intent it = new Intent();
					it.setClass(WayMainActivity.this, WayService.class);
					stopService(it);
				}else{
					//��ʾ��ǰ�ǹر�״̬����Ҫ���ر�״̬����>����״̬����>��������
					//�ж��Ƿ����㿪������
					//������1.������ֻ�����Ϊ�գ�2.��ع�������ѡ��һ��
					if(!WayInformations.LPPN.equals("")
							&&(WayInformations.isFPL||WayInformations.isQPL||WayInformations.isMSG)){
						WayInformations.isGN =true;
						gn_btn.setText("�ر���ѡ���");
						//���䣺�����������ݣ��޷��޸ļ�����ֻ������Լ�ѡ���ع���
						et.setEnabled(false);
						fpl_btn.setEnabled(false);
						qpl_btn.setEnabled(false);
						msg_btn.setEnabled(false);
						//������ת��service���н��п�������
						Intent it = new Intent();
						it.setClass(WayMainActivity.this, WayService.class);
						startService(it);
					}else{
						Toast.makeText(WayMainActivity.this, "���������㣬�޷�������", Toast.LENGTH_LONG).show();
						
					}
				
					
				}
				
			}
			
		});
	}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.way_main, menu);
	return true;
}
}