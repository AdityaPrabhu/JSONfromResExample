package com.example.pricefinder;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {

	public static ArrayList<String> headingTree = new ArrayList<String>();
	public FragmentManager fragmentManager;
	public FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		headingTree.add("topics");
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		MyListFragment myListFragment = new MyListFragment(headingTree.get(0));
		fragmentTransaction.add(R.id.fcontainer, myListFragment);
		fragmentTransaction.commit();
	}

}
