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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	Button loginBtn;
	EditText aemail, apass, pesan;
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

	// Action when the login button clicked
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// to processing json
		KirimDataAsync kirimAsync = new KirimDataAsync() {
			@Override
			public void respon(String result) {
				// TODO Auto-generated method stub
				try {

					long start = System.currentTimeMillis();
					JSONObject objTOken = new JSONObject(result);

					String Success = objTOken.getString("success");
					String Token = objTOken.getString("token");

					// Token singleToken = new Token(Success, Token);

					if (Success == "true") {
						ShowToast("Login Success!");
						// System.out.println(Token);
						loading.dismiss();

						PindahHalaman(Token);
					} else {
						ShowToast("Username or Password failed!");
					}

					long end = System.currentTimeMillis();
					System.out.println("respon setelah click " + (end - start));
				} catch (JSONException e) {
					// TODO: handle exception
				}
			}
		};

		kirimAsync.execute();

	}

	// Make METHOD message
	private void ShowToast(String pesan) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT)
				.show();
	}

	// Make METHOD move page
	private void PindahHalaman(String token) {
		// diganti ke main
		Intent pindah = new Intent(LoginActivity.this, MainActivity.class);

		pindah.putExtra("token", token);
		startActivity(pindah);
		finish();
	}

	// POST data from and to API
	public abstract class KirimDataAsync extends
			AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			// create the loading
			loading = new ProgressDialog(LoginActivity.this);
			loading.setMessage("Loading...");
			loading.setIndeterminate(false);
			loading.setCancelable(false);
			loading.show();
		}

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
				long start = java.lang.System.currentTimeMillis();
				respon = ClientToServer.eksekusiHttpPost(
						"https://www.qisc.us/users/sign_in.json?", kirimkeapi);
				// System.out.println(respon);

				String res = respon.toString();
				res = res.trim();
				long end = java.lang.System.currentTimeMillis();
				System.out.println("ini kirim data async " + (end - start));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// System.out.println(end-start);

			return respon;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// loading.dismiss();
			respon(result);
		}

	}

}
