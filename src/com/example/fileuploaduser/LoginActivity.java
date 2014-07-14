package com.example.fileuploaduser;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	Button loginBtn;
	EditText aemail, apass, pesan;
	private String url = "https://www.qisc.us/users/sign_in.json";
	private ProgressDialog loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);

		aemail = (EditText) findViewById(R.id.editTextUsername);
		apass = (EditText) findViewById(R.id.editTextPass);
		loginBtn = (Button) findViewById(R.id.btnLogin);

		loginBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Acction jika di Click
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		//membuat loading
		loading = ProgressDialog.show(LoginActivity.this, "", "Loading");
		new Thread(){
			public void run(){
			try{
				sleep(10000);
			}catch (Exception e){
				Log.e("tag", e.getMessage());
			}
			loading.dismiss();
			}
		}.start();
		

		// mengolah json
		KirimDataAsync kirimAsync = new KirimDataAsync() {
			@Override
			public void respon(String result) {
				// TODO Auto-generated method stub
				try {

					JSONObject objTOken = new JSONObject(result);

					String Success = objTOken.getString("success");
					String Token = objTOken.getString("token");

					// Token singleToken = new Token(Success, Token);

					if (Success == "true") {
						ShowToast("Login Success!");
						System.out.println(Token);

						PindahHalaman(Token);
					}
					if (Success == "false") {
						ShowToast("Username or Password failed!");
					}

				} catch (JSONException e) {
					// TODO: handle exception
				}
			}
		};

		kirimAsync.execute();

	}

	// membuat METHOD pesan jika gagal dan berhasil
	private void ShowToast(String pesan) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT)
				.show();
	}

	// membuat METHOD pindah halaman
	private void PindahHalaman(String Token) {
		//diganti ke main
		Intent pindah = new Intent(LoginActivity.this, MainActivity.class);
		// parsing token dari loginActivity ke FileActivity
		pindah.putExtra("token", Token);
		startActivity(pindah);
		finish();
	}

	// POST data ke dan dari API
	public abstract class KirimDataAsync extends
			AsyncTask<String, String, String> {

		public abstract void respon(String result);

		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String respon = null;
			ArrayList<NameValuePair> kirimkeapi = new ArrayList<NameValuePair>();

			kirimkeapi.add(new BasicNameValuePair("user[email]", aemail
					.getText().toString()));
			kirimkeapi.add(new BasicNameValuePair("user[password]", apass
					.getText().toString()));

			try {
				respon = ClientToServer.eksekusiHttpPost(
						"https://www.qisc.us/users/sign_in.json?", kirimkeapi);
				System.out.println(respon);

				String res = respon.toString();
				res = res.trim();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return respon;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			respon(result);
		}

	}

}
