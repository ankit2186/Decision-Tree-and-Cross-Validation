package classification;

import java.util.ArrayList;

/**
 * @author nirlepa
 * This class implements the contingency table of the attributes whose conditional 
 * entropy needs to be calculated. It also maintains the information about the row
 * attribute and the column attribute. The values associated with the row and columnn 
 * attribute.
 */


public class Contingency {

	String row_title;  // row title of the attribute
	String col_title;  // column title of the attribute

	String rowNames[] = new String[UtilityI.MAXATTRVAL]; // row values of attribute
	String colNames[] = new String[UtilityI.MAXATTRVAL]; // column values of attribute
	String names[] = new String[UtilityI.MAXATTRVAL];

	int contingency_Table[][] = new int[UtilityI.MAXATTRVAL][UtilityI.MAXATTRVAL]; // stores contingency table 
	int count[] = new int [UtilityI.MAXATTRVAL]; // count of attribute values in table


	public int[][] getContingency_Table() {
		return contingency_Table;
	}
	public void setContingency_Table(int[][] contingencyTable) {
		contingency_Table = contingencyTable;
	}
	public int[] getCount() {
		return count;
	}
	public void setCount(int[] count) {
		this.count = count;
	}


	/**
	 * Contingency(): initialize the variables.
	 */
	public Contingency()
	{
		row_title="";
		col_title = "";

	}


	/**
	 * Function creates contingency table
	 * @param rowName: row name
	 * @param colName: column name
	 * @param data: data set from which table is created.
	 * 
	 */
	public void createContigencyTable(String rowName, String colName , ArrayList<String []> data )	
	{

		String []attr;
		int rowindex=-1;
		int colindex=-1;

		RecordSet.setAttributes();
		ArrayList<String []> attrList = RecordSet.getAttributes();

		String []attrnames = UtilityI.ATTRNAME;

		for(int i=0;i<attrnames.length;i++)
		{

			if(rowName.equalsIgnoreCase(attrnames[i]))
			{  
				row_title = rowName;
				rowindex = i;
			}
			if(colName.equalsIgnoreCase(attrnames[i]))
			{

				col_title = colName;
				colindex = i;		
			}
		}


		if(rowindex != -1 && colindex != -1){

			int [] numAttrVal = RecordSet.getNumAttrVal();
			attr = (String [])attrList.get(rowindex);

			for(int i=0;i<numAttrVal[rowindex];i++)
			{
				rowNames[i] = attr[i+1];
			}

			attr = (String [])attrList.get(colindex);

			for(int i=0;i<numAttrVal[colindex];i++)
			{
				colNames[i] = attr[i+1];
			}

			String [] rec;
			for(int i=0;i<data.size();i++)
			{
				rec = data.get(i);
				for(int j =0; j<UtilityI.MAXATTRVAL;j++){
					for(int k =0; k<UtilityI.MAXATTRVAL;k++){

						if(rec[rowindex].equals(rowNames[j]) && rec[colindex].equals(colNames[k]))
							contingency_Table[j][k]++;
					}
				}

			}
		}	
	}


	/**
	 * Function to count the attribute values in the table
	 * @param title: name of the attribute
	 * @param data : data set
	 */
	public void calcount(String title , ArrayList<String []> data)
	{
		int rowindex = -1;
		String []attrnames = UtilityI.ATTRNAME;
		RecordSet.setAttributes();
		ArrayList<String []> attrList = RecordSet.getAttributes();

		for(int i=0;i<attrnames.length;i++)
		{
			if(title.equalsIgnoreCase(attrnames[i]))
			{  
				col_title = title;
				rowindex = i;
			}
		}

		int [] numAttrVal = RecordSet.getNumAttrVal();
		if(rowindex != -1){

			String [] attr = (String [])attrList.get(rowindex);

			for(int i=0;i<numAttrVal[rowindex];i++)
			{
				names[i] = attr[i+1];
			}
		}

		String [] rec;
		for(int i=0;i<data.size();i++)
		{
			rec = data.get(i);
			for(int j =0; j<UtilityI.MAXATTRVAL;j++){						
				if(rec[rowindex].equals(names[j]) )
					count[j]++;
			}

		}
	}
	public void displayTable()
	{
		for(int i=0;i<UtilityI.MAXATTRVAL;i++)
		{   
			System.out.println(" ");
			for(int j=0;j<UtilityI.MAXATTRVAL;j++)
			{
				System.out.print("  "+contingency_Table[i][j]);
			}
		}
	}
}
