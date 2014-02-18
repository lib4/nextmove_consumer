package com.lib4.picmove.fragments;

import java.util.List;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.lib4.picmove.R;
import com.lib4.picmove.adapters.ChatAdapter;

public class ChatFragment extends BaseFragment{

	
	RelativeLayout chatListLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		chatListLayout = (RelativeLayout) inflater.inflate(
				R.layout.chats, container, false);
		
		ChatAdapter mChatAdapter	=	new ChatAdapter(getActivity());
		ListView chatList	=	(ListView) chatListLayout.findViewById(R.id.listchat);
		chatList.setAdapter(mChatAdapter);
		
		return chatListLayout;
	}
	
	
}
