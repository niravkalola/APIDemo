package com.nkdroid.apidemo.custom;

import com.android.volley.VolleyError;

public interface IService {
	
	public void response(String response);
	public void error(VolleyError error);

}
