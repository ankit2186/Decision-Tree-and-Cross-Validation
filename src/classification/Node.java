package classification;

import java.util.ArrayList;

public class Node {

	String parentLink;
	String name;
	String classLabel;

	int nochild;
	Contingency contnode = new Contingency();
	ArrayList<Integer> plinklist[]  = new ArrayList[UtilityI.MAXATTRVAL];

	Node childNodes[] = new Node[UtilityI.MAXATTRVAL];
	
	/*
	 * Initialize the variables. 
	 */
	public Node()
	{
		parentLink = "";
		name = "";
		classLabel = "";
		nochild = UtilityI.MAXATTRVAL;

		for(int i=0;i<UtilityI.MAXATTRVAL;i++)
		{
			plinklist[i] = new ArrayList<Integer>();
		}

	}
	
	public Node(String plink , String name , String clabel ,int num)
	{
		this.parentLink = plink;
		this.name = name;
		this.classLabel = clabel;
		nochild = UtilityI.MAXATTRVAL;
	}

	/*
	 *  Set the root Information 
	 */
	public void setRoot(String parlink , String nodename , String classlabel , int num)
	{
		parentLink = parlink;
		name = nodename;
		this.classLabel = classlabel;
		nochild = num;
	}
	
	/*
	 * Set the parent link list 
	 */
	public void setParentLinkList(int position , int child)
	{
		plinklist[child] = new ArrayList<Integer>();
		plinklist[child].add(new Integer(position));    	
	}

	public String getParentLink() {
		return parentLink;
	}

	public void setParentLink(String parentLink) {
		this.parentLink = parentLink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}

	public int getNochild() {
		return nochild;
	}

	public void setNochild(int nochild) {
		this.nochild = nochild;
	}


}


