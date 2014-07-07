package com.example.fileuploaduser;

import java.util.List;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FileAdapter extends ArrayAdapter<Token> {

	private Context context;
	private Token file;
	private int resourceLayout;
	
	public FileAdapter(Context context, int resource, List<Token> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resourceLayout = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		file = getItem(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(resourceLayout, parent, false);
		
		TextView textNama   = (TextView) convertView.findViewById(R.id.textViewName);
		TextView textUrl = (TextView) convertView.findViewById(R.id.textViewUrl);

		textUrl.setAutoLinkMask(Linkify.ALL);
		
		textNama.setText(file.getNama());
		textUrl.setText(file.getUrl());
		
		return convertView;
	}
}
