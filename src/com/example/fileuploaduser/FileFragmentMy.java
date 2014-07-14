package com.example.fileuploaduser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FileFragmentMy extends Fragment {
	
		private ArrayList<Token> ListFile = new ArrayList<Token>();
		private FileAdapter fileAdapter;
		private String TokenToken;
		
		
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//ambil token
			MainActivity getMyToken = (MainActivity) getActivity();
			TokenToken = getMyToken.getToken();
			fileAdapter = new FileAdapter(getActivity(), R.layout.item_filepage, ListFile);
			
			GetFile getEvent = new GetFile() {

				@Override
				public void respon(String respons) {
					// TODO Auto-generated method stub
					System.out.println(respons);

					try {
						JSONArray myFiles = new JSONArray(respons);
						// JSONArray arrayEvent =
						// objEvent.getJSONArray("listEvent");

						for (int i = 0; i < myFiles.length(); i++) {
							JSONObject objectAcara = myFiles.getJSONObject(i);

							String namaFile = objectAcara.getString("filename");
							String Url = objectAcara.getString("url");

							Token singleAcara = new Token(namaFile, Url);
							ListFile.add(singleAcara);
						}
						fileAdapter.notifyDataSetChanged();

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			};
			
			getEvent.execute("https://www.qisc.us/api/v1/mobile/getFiles?token="+TokenToken);

		}
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.filepage, container, false);
			ListView lv = (ListView)v.findViewById(R.id.listView1);
			lv.setAdapter(fileAdapter);
			
			return v;
		}
}
