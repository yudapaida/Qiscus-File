package com.example.fileuploaduser;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.array;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class FileFragmentAll extends Fragment {
	EditText inputSearch;
	ArrayAdapter<String> adapter;
	private ListView ListViewAcara;

	private ArrayList<Token> ListFile = new ArrayList<Token>();
	private FileAdapter fileAdapter;
	private String TokenToken;
	
	private ImageView iv;

	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			System.out.println("A");

			//ambil token
			MainActivity getMyToken = (MainActivity) getActivity();
			TokenToken = getMyToken.getToken();
			

			
			
//			ambil type file
//			MainActivity getFileType = (MainActivity) getActivity();
//			type = getFileType.getType();
//			
			System.out.println("oncreate");
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
							
							//untuk file type
//							if(Url.endsWith("txt")){
//								iv = (ImageView) FindListener
//							}
							Token singleAcara = new Token(namaFile, Url);
							ListFile.add(singleAcara);
							
						}
						fileAdapter.notifyDataSetChanged();
						fileAdapter.setSecondaryData();

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			};
			
			getEvent.execute("https://www.qisc.us/api/v1/mobile/getFiles?token="+TokenToken+"&all=true");
			
			
		}
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		System.out.println("B");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		System.out.println("C");
	}

	public void onCreate(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("D");

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("E");
		View v = inflater.inflate(R.layout.filepage, container, false);
		ListView lv = (ListView) v.findViewById(R.id.listView1);
		
		fileAdapter = new FileAdapter(getActivity(), R.layout.item_filepage,
				ListFile);

		lv.setAdapter(fileAdapter);


		inputSearch = (EditText) v.findViewById(R.id.cari);
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				System.out.println(arg0);
				String text = inputSearch.getText().toString()
						.toLowerCase(Locale.getDefault());
				fileAdapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
		return v;
	}

}
