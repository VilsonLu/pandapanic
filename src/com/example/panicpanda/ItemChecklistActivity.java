package com.example.panicpanda;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import database.DbConnection;

public class ItemChecklistActivity extends Activity {

	private MobileServiceClient mClient;
	ListView lists;
	EditText textName;
	EditText textQuantity;
	EditText textUnit;
	Button btnAdd;

	ArrayList<ChecklistItem> items;

	MobileServiceTable<ChecklistItem> resultChecklistItem;
	ItemAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checklist);

		textName = (EditText) findViewById(R.id.txtItemName);
		textQuantity = (EditText) findViewById(R.id.txtQuantity);
		textUnit = (EditText) findViewById(R.id.txtUnit);
		btnAdd = (Button) findViewById(R.id.buttonAdd);

		mClient = DbConnection.connectToAzureService(this);

		btnAdd.setOnClickListener(new addItemListener());
	
		adapter = new ItemAdapter(this, R.layout.layout_rowitem);
		lists = (ListView) findViewById(R.id.listItem);
		lists.setAdapter(adapter);
		getChecklistItemFromTable();
	}

	public void getChecklistItemFromTable() {
		items = new ArrayList<ChecklistItem>();
		resultChecklistItem = mClient.getTable(ChecklistItem.class);
		resultChecklistItem.execute(new TableQueryCallback<ChecklistItem>(){

			@Override
			public void onCompleted(List<ChecklistItem> results, int count,
					Exception exception, ServiceFilterResponse response) {
				// TODO Auto-generated method stub
					if(exception == null){
						items = (ArrayList<ChecklistItem>) results;
						Log.e("Query status", "Success");
						adapter.clear();
						for(ChecklistItem item: items){
							adapter.add(item);
						}
					} else {
						Log.e("Query status", "Failed");
					}
				
			}
			
		});
	}

	class addItemListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String name = textName.getText().toString();
			String unit = textUnit.getText().toString();
			String quantity = textQuantity.getText().toString();

			final ChecklistItem item = new ChecklistItem();
			item.setName(name);
			item.setUnit(unit);
			item.setQuantity(Float.valueOf(quantity));
			Log.e("Add Item", name + unit + quantity);

			mClient.getTable(ChecklistItem.class).insert(item,
					new TableOperationCallback<ChecklistItem>() {

						@Override
						public void onCompleted(ChecklistItem entity,
								Exception exception,
								ServiceFilterResponse response) {
							// TODO Auto-generated method stub
							if (exception == null) {
								Log.e("Add Status", "Success");
								adapter.add(item);
							} else {
								Log.e("Add Status", "Failed");
							}
						}
					});

			items.add(item);
			adapter.notifyDataSetChanged();
		}
	}
	
	
}
