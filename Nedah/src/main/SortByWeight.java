package main;

import java.util.Comparator;
import java.util.HashMap;

public class SortByWeight implements Comparator<String>{
	private HashMap<String, Integer> historyTable;
	
	public SortByWeight(HashMap<String, Integer> historyTable) {
		this.historyTable = historyTable;
	}

	@Override
	public int compare(String s, String r) {
		if(historyTable.containsKey(s) && historyTable.containsKey(r)) {
			int sn = historyTable.get(s);
			int rn = historyTable.get(r);
	
			if(sn > rn) {
				return -1;
			}
			else if(rn > sn) {
				return 1;
			}
			else {
				return 0;
			}
		}
		return 0;
	}
	
	public void setHistoryTable(HashMap<String, Integer> map) {
		this.historyTable = map;
	}

}
