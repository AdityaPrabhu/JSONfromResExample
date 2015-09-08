package com.example.pricefinder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyListFragment extends Fragment {
	public ListView listView;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> arrList = new ArrayList<String>();
	private String heading;
	private MyListFragment fragment;

	public MyListFragment(String heading) {
		this.heading = heading;
		fragment = this;
	}

	public MyListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment, container, false);
		listView = (ListView) view.findViewById(R.id.listv);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				String itemValue = (String) listView
						.getItemAtPosition(position);
				MainActivity.headingTree.add(itemValue);
				MyListFragment newFragment = new MyListFragment(itemValue);
				((MainActivity) getActivity()).fragmentTransaction = ((MainActivity) getActivity()).fragmentManager
						.beginTransaction();
				((MainActivity) getActivity()).fragmentTransaction
						.setCustomAnimations(R.anim.slide_in_left,
								R.anim.slide_out_right);
				((MainActivity) getActivity()).fragmentTransaction.replace(
						R.id.fcontainer, newFragment);
				((MainActivity) getActivity()).fragmentTransaction.commit();
				getFragmentManager().executePendingTransactions();

			}

		});
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getDataFromJSONFile();
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				arrList);

	}

	private void getDataFromJSONFile() {
		InputStream inputStream = getResources().openRawResource(
				R.raw.pricefinder);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		int ctr;
		try {
			ctr = inputStream.read();
			while (ctr != -1) {
				byteArrayOutputStream.write(ctr);
				ctr = inputStream.read();
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.v("Text Data", byteArrayOutputStream.toString());
		try {

			JSONObject jObject = new JSONObject(
					byteArrayOutputStream.toString());
			int i = 0;
			int treeHeight = MainActivity.headingTree.size();
			JSONObject jObjectResult = null;
			for (; i < treeHeight; i++) {
				jObjectResult = jObject.getJSONObject(MainActivity.headingTree
						.get(i));
				jObject = jObjectResult;
			}

			Iterator<String> iter = jObjectResult.keys();
			while (iter.hasNext()) {
				String key = iter.next();
				arrList.add(key);
				/*
				 * try { Object value = jObjectResult.get(key); } catch
				 * (JSONException e) { // Something went wrong! }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
