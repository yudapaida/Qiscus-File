package com.example.fileuploaduser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public abstract class GetFile extends AsyncTask<String, String, String> {

	public abstract void respon(String respon);
	ProgressDialog loading;
	private Context contex;
	
	
	
	public GetFile(Context contex) {
		super();
		this.contex = contex;	
	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String url=arg0[0];
		String respon=null;
		
		try {
			respon=ClientToServer.eksekusiHttpGet(url);
			System.out.println(respon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respon;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		respon(result);
		loading.dismiss();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		loading = new ProgressDialog(contex);
		loading.setMessage("Please wait...");
		loading.setIndeterminate(false);
		loading.setCancelable(false);
		loading.show();
		
	}

}
