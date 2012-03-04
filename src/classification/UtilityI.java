package classification;

public interface UtilityI {

	String TOP_LEFT_SQUARE = "top-left-square"+","+"x"+","+"o"+","+"b";
	String TOP_MIDDLE_SQUARE="top-middle-square"+","+"x"+","+"o"+","+"b";
	String TOP_RIGHT_SQUARE = "top-right-square"+","+"x"+","+"o"+","+"b";
	String MIDDLE_LEFT_SQUARE = "middle-left-square"+","+"x"+","+"o"+","+"b";
	String MIDDLE_MIDDLE_SQUARE="middle-middle-square"+","+"x"+","+"o"+","+"b";
	String MIDDLE_RIGHT_SQUARE ="middle-right-square"+","+"x"+","+"o"+","+"b";
	String BOTTOM_LEFT_SQUARE="bottom-left-square"+","+"x"+","+"o"+","+"b";
	String BOTTOM_MIDDLE_SQUARE = "bottom-middle-square"+","+"x"+","+"o"+","+"b";
	String BOTTOM_RIGHT_SQUARE= "bottom-right-square"+","+"x"+","+"o"+","+"b";
	String CLASS_LABEL = "Class-Label"+","+"positive"+","+"negative"+","+" ";
	String [] ATTRNAME = 
	{"top-left-square","top-middle-square","top-right-square",
			"middle-left-square","middle-middle-square","middle-right-square",
			"bottom-left-square","bottom-middle-square","bottom-right-square",
	"Class-Label"};

	int MAXATTR = 10;
	int MAXATTRVAL = 3;
	int MAXREC = 1000; 

}
