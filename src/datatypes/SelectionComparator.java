package datatypes;

import java.util.Comparator;

/**
 * Compares Selection objects
 * 
 * @author dmholtz
 * @version 1.0
 */
public class SelectionComparator implements Comparator<Selection> {

	/**
	 * Selection objects can be compared by comparing their result attribute.
	 * Returns how s1 compares to s2
	 * 
	 * @see Comparator interface and compareTo
	 * 
	 * @param: two Selection objects, must be not null
	 */
	@Override
	public int compare(Selection s1, Selection s2) {
		// TODO Auto-generated method stub
		Integer value1 = s1.getResult();
		Integer value2 = s2.getResult();
		return value1.compareTo(value2);
	}

}
