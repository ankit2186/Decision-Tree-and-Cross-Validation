package classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author nirlepa
 * This class implements the decision tree classification algorithm.
 *
 */
public class DecisionTree {

	private static final String TRAININGFILE="tic-tac-toe-training.txt";
	private static final String TESTFILE = "tic-tac-toe-test.txt";

	public static void main(String args[])
	{
		if(args.length < 2)
		{
			System.out.println(" Please Enter the Training Data and Test Data File Name");
			System.exit(1);
		}
		 		

		/*
		 * The code snippet will load the require training  and test data set from the file 
		 */
		/*****************************************************************/		
		File trainingData = new File(args[0]);
		//File trainingData = new File(TRAININGFILE);

		if(!trainingData.exists())
		{
			System.err.print(" NO such File "+TRAININGFILE+" found");
			System.exit(1);
		}


		File testData = new File(args[1]);
		//File testData = new File(TESTFILE);

		if(!testData.exists())
		{
			System.err.print(" NO such File "+TESTFILE+" found");
			System.exit(1);			
		}

		try{
			BufferedReader readTrainingData = new BufferedReader( new FileReader(trainingData));
			BufferedReader readTestData = new BufferedReader( new FileReader(testData));
			String trainingLine;
			String testLine;
			RecordSet  recObj = new RecordSet();

			ArrayList<String []> trainingRecord = new ArrayList<String []>(); 
			ArrayList<String []> testRecord = new ArrayList<String []>();

			while(true)
			{
				trainingLine = readTrainingData.readLine();
				if(trainingLine != null)
				{
					if(recObj.insertRecord(trainingLine) != null)
						trainingRecord.add(recObj.insertRecord(trainingLine));
				}
				else
					break;
			}

			while(true)
			{
				testLine = readTestData.readLine();
				if(testLine != null)
				{
					testRecord.add(recObj.insertRecord(testLine));
				}
				else
					break;
			}
			trainingRecord.add(0, UtilityI.ATTRNAME);
			RecordSet recordTrain = new RecordSet();
			recordTrain.setNumRec(trainingRecord.size());
			RecordSet recordTest = new RecordSet();
			recordTest.setNumRec(testRecord.size());

			readTrainingData.close();
			readTestData.close();

			/*****************************************************************/


			BuildTree trainTree = new BuildTree();
			Node root = new Node();

			trainTree.setInfoGain(trainingRecord);
			trainTree.sortEntropy(trainingRecord);
			trainTree.levelTree(false, trainingRecord, 0);

			trainTree.constructTree(trainingRecord, 0 , root);
			trainTree.leaves(root);
			trainTree.setClassLabel(trainingRecord, root);
			double errorRate =  trainTree.calErrorRate(trainingRecord);

			System.out.println("Training Error Rate : "+errorRate);

			/*----------------------------------------------------*/

			trainTree.setClassLabel(testRecord, root);
			double testerror = trainTree.calErrorRate(testRecord);

			System.out.println("Test Error Rate : "+testerror);

		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}

	}

	public static void displayRecord(ArrayList<String []> list)
	{
		String buff[];
		for(int i=0; i<list.size();i++)
		{
			buff = (String [])list.get(i);
			System.out.println(" Record No: "+(i+1));
			for(int j=0; j <buff.length ; j++)
				System.out.println("Record Value : "+buff[j]);
		}
	}

}

