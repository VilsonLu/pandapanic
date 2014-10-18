package com.example.panicpanda;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<ChecklistItem> {
	
	Context mContext;
	int mLayoutResourceId;
	ArrayList<ChecklistItem> listOfItems;
	
	class ViewHolder{
		TextView txtName;
		TextView txtUnit;
		TextView txtQuantity;
		CheckBox checkIsCompleted;
	}
	
	public ItemAdapter(Context context, int mLayoutResourceId){
		super(context,mLayoutResourceId);
		this.mContext = context;
		this.mLayoutResourceId = mLayoutResourceId;
	}
	
	
	/**
	 * Returns the view for a specific item on the list
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		final ChecklistItem currentItem = getItem(position);

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
		}

		row.setTag(currentItem);
		final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkIsCompleted);
		final TextView textName = (TextView) row.findViewById(R.id.textItem);
		final TextView textUnit = (TextView) row.findViewById(R.id.textUnit);
		final TextView textQuantity = (TextView) row.findViewById(R.id.textQuantity);
		
		textName.setText(currentItem.getName());
		textUnit.setText(currentItem.getUnit());
		textQuantity.setText(Float.toString(currentItem.getQuantity()));
		checkBox.setChecked(false);
		checkBox.setEnabled(true);

		
		return row;
	}

	

}
