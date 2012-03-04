package classification;
import java.util.ArrayList;

/*
 * Calculate the Entropy, Conditional Entropy and Information gain. 
 */

public class Entropy {

	/*
	 * Calculate entropy 
	 */
	public double findEntropy(String title , Contingency ctable , ArrayList<String []> data )
	{
		ctable.calcount(title, data);
		int total_count=0;
		int [] attrcount = ctable.getCount();
		double entropy=0.0;

		for(int i=0;i<UtilityI.MAXATTRVAL;i++)
		{ 
			total_count = total_count + attrcount[i];
		}

		for(int i=0;i<UtilityI.MAXATTRVAL;i++)
		{
			if(attrcount[i] != 0){
				entropy = entropy +
				((float)attrcount[i]/(float)total_count) * 
				(float)(Math.log((float)attrcount[i]/(float)total_count)/Math.log(2.0));
			}
		}
		entropy = (0.0 - entropy);
		return entropy;
	}

	/*
	 * Calculate conditional entropy 
	 */
	public double findConditionalEntropy(String rowName , String colName, Contingency ctable , ArrayList<String []> data)
	{
		double partialEntropy=0.0, conditionalEnt=0.0;
		int totalCount=0;
		int partialCount[] = new int [UtilityI.MAXATTRVAL];

		ctable.createContigencyTable(rowName, colName, data);
		int [][] contTable = ctable.getContingency_Table();
		for(int i=0;i<UtilityI.MAXATTRVAL;i++)
		{  
			for(int j=0;j<UtilityI.MAXATTRVAL;j++)
			{	partialCount[i]=partialCount[i] + contTable[j][i]; 
			}
		}

		for(int i=0;i<UtilityI.MAXATTRVAL;i++)
			totalCount = totalCount + partialCount[i];

		for(int i=0;i<UtilityI.MAXATTRVAL-1;i++)
		{
			for(int j=0;j<UtilityI.MAXATTRVAL-1;j++)
			{
				double temp=((double)contTable[j][i]/(double)partialCount[i]);
				partialEntropy = partialEntropy + temp*(Math.log(temp)/Math.log(2));       
			}
			conditionalEnt = conditionalEnt + ((double)partialCount[i]/(double)totalCount)*partialEntropy;
			partialEntropy=0.0;
		}
		conditionalEnt=-conditionalEnt;
		return conditionalEnt;
	}

	/*
	 * Calculate Information Gain 
	 */
	public double informationGain(String rowName, String colName , Contingency ctable , ArrayList<String []> data)
	{
		/* calculate information gain */
		double entropy = findEntropy(rowName,ctable,data);			
		double condiEntropy = findConditionalEntropy(rowName, colName, ctable, data);
		double infoGain = entropy - condiEntropy;
		return infoGain;
	}
}
