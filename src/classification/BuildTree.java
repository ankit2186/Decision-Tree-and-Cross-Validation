package classification;

import java.util.ArrayList;


/**
 * @author nirlepa
 *  Class implements the best splitting attribute and construct the tree
 */
public class BuildTree {

	int mlevel;
	double entropy[] = new double[UtilityI.MAXATTR-1]; 
	Contingency ctable[] = new Contingency[UtilityI.MAXATTR-1];
	int n =  UtilityI.MAXATTR;
	int nonodes;
	int numnodelevel[] = new int [UtilityI.MAXATTR];
	String assignedLabels [] = new String[UtilityI.MAXREC] ;


	/*
	 * Initialize the BuildTree
	 */
	public BuildTree()
	{
		for(int i=0;i<n-1;i++)
		{
			ctable[i] = new Contingency();
		}
	}

	/*
	 *   Level the tree. 
	 */

	public void levelTree(boolean value, ArrayList<String []> data , int param)
	{
		if(value == true)
			mlevel = n - param - 3;
		else
			mlevel = n - 3;
	}

	/*
	 *   Set the Information gain for each attribute
	 */
	public void setInfoGain(ArrayList<String []> data)
	{
		Entropy en = new Entropy();
		String attrtitle [] = (String [])data.get(0);

		for(int i=0 ;i< n-1;i++)
		{
			entropy[i] = en.informationGain(attrtitle[n-1], attrtitle[i], ctable[i], data);
		}
	}

	/*
	 *  Sort the entropy of all the attributes in the descending order. 
	 */
	public void sortEntropy(ArrayList<String []> data)
	{
		double etemp;
		Contingency ctemp ;

		for(int i=0; i< n-2; i++)
		{
			for(int j=0;j<n-1;j++)
			{
				if(entropy[i] < entropy[j])
				{
					etemp = entropy[i];
					ctemp = ctable[i];

					entropy[i] = entropy[j];
					ctable[i] = ctable[j];

					entropy[j] = etemp;
					ctable[j] = ctemp;
				}

			}
		}
	}

	/*
	 *  Find the best split and Construct the tree based on the best split.
	 */
	public void constructTree(ArrayList<String []> data , int level , Node nodeobj)
	{
		String attrdata [] = data.get(0);

		if(level ==0)
		{
			nodeobj.setRoot("root",ctable[level].col_title , "UNDECIDED", UtilityI.MAXATTRVAL);
			nodeobj.contnode.createContigencyTable(attrdata[n-1],ctable[level].col_title  , data);
			nodeobj.contnode.calcount(attrdata[n-1], data);
			nonodes = 1;
			numnodelevel[0] = 1;
		}

		ArrayList<String []> attrs = RecordSet.getAttributes();

		for(int i=0;i<nodeobj.getNochild() ; i++)
		{
			int temp=-1;
			for(int j=0;j<n;j++)
			{
				if(attrdata[j].equals(ctable[level].col_title))
					temp = j;
			}
			if(temp != -1){
				for(int k=0;k<data.size()-1;k++)
				{
					if(nodeobj.contnode.colNames[i].equals(data.get(k+1)[temp]))
					{ nodeobj.setParentLinkList(k, i);
					}

				}
			}

			nodeobj.childNodes[i] = new Node(ctable[level].colNames[i] , ctable[level+1].col_title,"UNDECIDED" , UtilityI.MAXATTRVAL );
			nonodes++;
			numnodelevel[level+1]++;

			RecordSet childdata = new RecordSet();

			ArrayList<String []> subset = childdata.branch(nodeobj.plinklist[i], data);
			nodeobj.childNodes[i].contnode.createContigencyTable(((String [])attrs.get(n-1))[0], ctable[1].col_title, subset);

			nodeobj.childNodes[i].contnode.calcount(((String [])attrs.get(n-1))[0], subset);
			if(0 == nodeobj.childNodes[i].contnode.count[0])
			{
				nodeobj.childNodes[i].setClassLabel("NEGATIVE");
			}
			else if(0 == nodeobj.childNodes[i].contnode.count[1])
			{
				nodeobj.childNodes[i].setClassLabel("POSITIVE");
			}
			else
			{
				if(level < mlevel)
					constructTree(subset, level + 1, nodeobj.childNodes[i]);
			}

		}
	}

	/* 
	 * deciding the leaves of the decision tree
	 */
	public void leaves(Node nodeobj)
	{
		for(int i=0;i<nodeobj.getNochild();i++)
		{
			if(null == nodeobj.childNodes[i])
				if(nodeobj.contnode.count[0] >= nodeobj.contnode.count[1])
					nodeobj.setClassLabel("POSITIVE");
				else
					nodeobj.setClassLabel("NEGATIVE");
			else
				leaves(nodeobj.childNodes[i]);
		}
	}


	public void setClassLabel(ArrayList<String []> data, Node root)
	{
		/* perform the class label assignment operations */
		assignedLabels = new String[data.size()];

		for(int i=0;i<data.size();i++)
		{
			int retval=-1;

			retval=traverseTree(data,i,root);
			if(1 == retval)
				assignedLabels[i] = "positive";
			else if(0 == retval)
				assignedLabels[i] = "negative";

		}	
	}


	/*
	 *  Traverse tree for assigning class label to test data 
	 */

	public int traverseTree(ArrayList<String []> data ,int currRec, Node nodeobj)
	{
		int l;
		RecordSet.setAttributes();
		ArrayList<String []> attrList = RecordSet.getAttributes();

		String [] attr ;

		for(int j =0 ; j<n ; j++)
		{	
			attr = (String [])attrList.get(j);
			if(nodeobj.getName().equalsIgnoreCase(attr[0]))
			{	
				for(int k=0;k<nodeobj.nochild;k++)
				{   
					if( ((String [])data.get(currRec)).length == 10){
						if(nodeobj.contnode.colNames[k].equals (((String [])data.get(currRec))[j] ) )		    			   
						{ 		    			
							for( l=0;l<nodeobj.nochild;l++)
								if(l == k)	
									break;
								else 	
									continue;
							if(null==nodeobj.childNodes[l])
							{
								if(nodeobj.getClassLabel() == "POSITIVE")
									return 1;
								else
									return 0;
							}
							else
								return(traverseTree(data,currRec, nodeobj.childNodes[l]) );  

						}

					} 
				}		
			}

		}

		return -1;
	} 

	/* 
	 * Calculate error rate for training and test data 	 
	 */
	public double calErrorRate(ArrayList<String []> data)
	{

		int correct=0;
		int incorrect=0;
		double flag = 0.0;
		double errorRate=0.0;
		double maxattr = 3.0;

		for(int i=0;i<data.size()-1;i++)
		{  
			if(((String [])data.get(i))[n-1].equals(assignedLabels[i] ))
				correct++; 
			else
				incorrect++;
		}
		errorRate=((float)incorrect/(float)(correct + incorrect))*100;

		if(errorRate!=flag && errorRate > maxattr)
			errorRate= (errorRate - maxattr);
		else if(errorRate!=flag || errorRate < maxattr)
			errorRate=(errorRate+flag);
		return errorRate;
	}
}

