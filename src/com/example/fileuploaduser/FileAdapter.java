package com.example.fileuploaduser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileAdapter extends ArrayAdapter<Token> {

	private Context context;
	private Token file;
	private int resourceLayout;
	String a;

	ArrayList<Token> arrayListToken;
	private List<Token> listToken = null;

	public FileAdapter(Context context, int resource, List<Token> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resourceLayout = resource;
		this.listToken = objects;
	}

	public void setSecondaryData() {

		this.arrayListToken = new ArrayList<Token>();
		this.arrayListToken.addAll(listToken);
		System.out.println("data =>" + arrayListToken.size());
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		file = getItem(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(resourceLayout, parent, false);

		TextView textNama = (TextView) convertView
				.findViewById(R.id.textViewName);
		TextView textUrl = (TextView) convertView
				.findViewById(R.id.textViewUrl);

		// for make url actived
		textUrl.setAutoLinkMask(Linkify.ALL);

		textNama.setText(file.getNama());
		textUrl.setText(file.getUrl());

		// get Url
		a = file.getUrl();

		// for loading image from Url
		ImageView iv = (ImageView) convertView.findViewById(R.id.imgView);

		if (a.endsWith("jpg") || a.endsWith("png")) {
			int loader = R.drawable.loader;
			com.example.loadthumbnail.ImageLoader imgLoader = new com.example.loadthumbnail.ImageLoader(
					context);
			imgLoader.DisplayImage(a, loader, iv);
		}
		// set icons file
		if (a.endsWith("txt")) {
			iv.setImageResource(R.drawable.txt);
		}
		if (a.endsWith("zip")) {
			iv.setImageResource(R.drawable.zip);
		}
		if (a.endsWith("mp3")) {
			iv.setImageResource(R.drawable.mp3);
		}
		if (a.endsWith("pdf")) {
			iv.setImageResource(R.drawable.pdf);
		}
		if (a.endsWith("ppt") || a.endsWith("pptx")) {
			iv.setImageResource(R.drawable.ppt);
		}
		if (a.endsWith("xls")) {
			iv.setImageResource(R.drawable.xls);
		}
		if (a.endsWith("doc") || a.endsWith("docx")) {
			iv.setImageResource(R.drawable.doc);
		}
		if (a.endsWith("mp4") || a.endsWith("mpg") || a.endsWith("mpeg")) {
			iv.setImageResource(R.drawable.mp4);
		}

		return convertView;
	}

	// method for filtering
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		System.out.println("length arrayListToken::" + arrayListToken.size());
		listToken.clear();
		if (charText.length() == 0) {
			System.out.println("length0::" + charText);
			listToken.addAll(arrayListToken);
		} else {

			System.out.println("length>0::" + charText);
			for (Token token : arrayListToken) {

				System.out.println("fungsi for untuk token::" + token.Nama);
				if (token.Nama.toLowerCase(Locale.getDefault()).contains(
						charText)) {
					listToken.add(token);
					System.out.println("contains::" + charText);
				} else {

					System.out.println("not contains::" + charText);
				}
			}
		}
		notifyDataSetChanged();
	}
}
