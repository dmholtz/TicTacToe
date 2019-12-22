package datatypes;

import java.util.Comparator;

public class SelectionComparator implements Comparator<Selection>{

	@Override
	public int compare(Selection s1, Selection s2) {
		// TODO Auto-generated method stub
		Integer value1 = s1.getResult();
		Integer value2 = s2.getResult();
		return value1.compareTo(value2);
	}
	

}
