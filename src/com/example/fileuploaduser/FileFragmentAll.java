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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FileFragmentAll extends Fragment {
	//ListAdapter listAdapter;
	
	private ListView ListViewAcara;
	private ArrayList<Token> ListFile = new ArrayList<Token>();

	private FileAdapter fileAdapter;
	private String TokenToken;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ambil token
		MainActivity getMyToken = (MainActivity) getActivity();
		TokenToken = getMyToken.getToken();
		
		//myToken = getIntent().getStringExtra("token");

		//ListViewAcara = (ListView) findViewById(R.id.listView1);

		fileAdapter = new FileAdapter(getActivity(), R.layout.item_filepage, ListFile);
		//ListViewAcara.setAdapter(fileAdapter);
		
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
		
		getEvent.execute("https://www.qisc.us/api/v1/mobile/getFiles?token="+TokenToken+"&all=true");

//		FileActivity fileActivity = new FileActivity();
//		listAdapter = new ArrayAdapter<String>(getActivity(),
//				android.R.layout.simple_list_item_1, myfriends);

	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.filepage, container, false);
		ListView lv = (ListView)v.findViewById(R.id.listView1);
		lv.setAdapter(fileAdapter);
		
		return v;
	}


}
