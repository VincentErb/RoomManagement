package Utils;

import java.util.ArrayList;

import com.google.gson.JsonArray;

public class JsonUtils {

	public static ArrayList<ArrayList<String>> getArrayFromJsonArray (JsonArray json){
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		
		for(int j = 0; j < json.size(); j++){
			ArrayList<String> tmp = new ArrayList<String>();
			JsonArray tmpJson = json.get(j).getAsJsonArray();
			
			for(int k = 0; k < tmpJson.size(); k++){
				tmp.add(tmpJson.get(k).getAsString());
			}	
			res.add(tmp);
		}
		
		return res;
	}
}
