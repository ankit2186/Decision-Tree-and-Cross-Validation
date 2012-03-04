package classification;

import java.util.ArrayList;


public class RecordSet {


	private static int numAttr; // Maintains the total no. of attributes.
	private  int numRec;	// Maintains the total no. of records.
	private static int numAttrVal[] = new int[UtilityI.MAXATTR]; //Maintains the count of attribute value.
	String recordBuff[];	// Store the record values.
	static ArrayList<String[]> attributeList = new ArrayList<String[]>(); // Maintains the list of attributes, their name and values.



	public static int[] getNumAttrVal() {
		return numAttrVal;
	}

	public static void setNumAttrVal(int[] numAttrVal) {
		RecordSet.numAttrVal = numAttrVal;
	}

	public int getNumRec() {
		return numRec;
	}
	public  void setNumRec(int noRec) {
		numRec = noRec;
	}

	/*
	 * Insert the record. 
	 */
	public String[] insertRecord(String record)
	{
		if(record != null)
		{
			recordBuff = record.split(",");
			return recordBuff;
		}
		else
			return null;

	}

	/*
	 * Set the required attributes 
	 */
	public static void setAttributes()
	{

		attributeList.add(UtilityI.TOP_LEFT_SQUARE.split(","));
		attributeList.add(UtilityI.TOP_MIDDLE_SQUARE.split(","));
		attributeList.add(UtilityI.TOP_RIGHT_SQUARE.split(","));
		attributeList.add(UtilityI.MIDDLE_LEFT_SQUARE.split(","));
		attributeList.add(UtilityI.MIDDLE_MIDDLE_SQUARE.split(","));	
		attributeList.add(UtilityI.MIDDLE_RIGHT_SQUARE.split(","));
		attributeList.add(UtilityI.BOTTOM_LEFT_SQUARE.split(","));
		attributeList.add(UtilityI.BOTTOM_MIDDLE_SQUARE.split(","));
		attributeList.add(UtilityI.BOTTOM_RIGHT_SQUARE.split(","));
		attributeList.add(UtilityI.CLASS_LABEL.split(","));

		// Gives the size of attribute in the given test data
		numAttr = attributeList.size();

		// Calculates no. of attribute values for each attribute in each records.
		for(int i=0 ; i<UtilityI.MAXATTR; i++)
			numAttrVal[i] = UtilityI.MAXATTRVAL;
	}


	/*
	 * branch creates a branch of the tree. 
	 */
	public ArrayList<String []> branch(ArrayList<Integer> position , ArrayList<String []> data)
	{
		int n = UtilityI.MAXATTR;

		int recsize = position.size();
		ArrayList<String []> subset = new ArrayList<String []>();

		for(int i=0 ; i<UtilityI.MAXATTR; i++)
			numAttrVal[i] = UtilityI.MAXATTRVAL;

		int val;
		for(int i=0;i<recsize;i++)
			for(int j=0;j<n;j++)
			{
				val = ((Integer)position.get(i)).intValue();
				subset.add(((String [])data.get(val)));				
			}	

		return subset;
	}

	public static ArrayList<String[]> getAttributes()
	{
		return attributeList;
	}

	public static int getNumAttr()
	{
		return numAttr;
	}

}
