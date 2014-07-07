package com.example.fileuploaduser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class FileActivity extends Activity {

	private ListView ListViewAcara;
	private ArrayList<Token> ListFile = new ArrayList<Token>();
	private FileAdapter fileAdapter;
	private String myToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filepage);
		myToken = getIntent().getStringExtra("token");

		ListViewAcara = (ListView) findViewById(R.id.listView1);

		fileAdapter = new FileAdapter(this, R.layout.item_filepage, ListFile);
		ListViewAcara.setAdapter(fileAdapter);

		GetFile getEvent = new GetFile() {

			@Override
			public void respon(String respons) {
				// TODO Auto-generated method stub
				System.out.println(respons);

				try {
					JSONArray myFiles = new JSONArray(respons);
					//JSONArray arrayEvent = objEvent.getJSONArray("listEvent");

					for (int i = 0; i < myFiles.length(); i++) {
						JSONObject objectAcara = myFiles.getJSONObject(i);

						String namaFile = objectAcara.getString("filename");
						String Url = objectAcara.getString("url");
						
						Token singleAcara = new Token(namaFile,Url);
						ListFile.add(singleAcara);
					}
					fileAdapter.notifyDataSetChanged();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		getEvent.execute("https://www.qisc.us/api/v1/mobile/getFiles?token="+myToken+"&all=true");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
