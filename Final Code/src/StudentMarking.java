import java.util.Scanner;
// DO NOT import anything else

/**
 * This class forms Java Assignment 1, 2021
 */
public class StudentMarking {
    /**
     * The message that the main menu must display --- DO NOT CHANGE THIS
     */
    public final String MENU_TEMPLATE =
            "%nWelcome to the Student System. Please enter an option 0 to 3%n"
                    + "0. Exit%n"
                    + "1. Generate a student ID%n"
                    + "2. Capture marks for students%n"
                    + "3. List student IDs and average mark%n";
    /**
     * DO NOT CHANGE THIS
     */
    public final String NOT_FOUND_TEMPLATE =
            "No student id of %s exists";
    /* Do NOT change the two templates ABOVE this comment.
      DO CHANGE the templates BELOW.
   */
    public final String ENTER_MARK_TEMPLATE = "%nPlease enter mark %d for student %s";
    public final String STUDENT_ID_TEMPLATE = "%nYour student ID is %s";
    public final String NAME_RESPONSE_TEMPLATE = "%nYou have entered a given name of %s and a surname of %s";
    public final String LOW_HIGH_TEMPLATE = "%nYour highest mark was %d and your lowest mark was %d";
    public final String AVG_MARKS_TEMPLATE = "%nYour average is %.2f";
    public final String COLUMN_1_TEMPLATE = "%n  %c";
    public final String COLUMN_2_TEMPLATE = "%n  %c     %c";
    public final String CHART_KEY_TEMPLATE = "%n high  low%n  %d     %d%n";
    public final String REPORT_PER_STUD_TEMPLATE = "ID : %s | Avg Mark : %.2f%n";

    /**
     * Creates a student ID based on user input
     *
     * @param sc Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @return a studentID according to the pattern specified in {@link StudentMarking#STUDENT_ID_TEMPLATE}
     */
    public String generateStudId(Scanner sc) {

        String studId; //
        String[] temp; //storage for split method


        System.out.printf("Please enter your given name and surname (Enter 0 to return to main menu)");

        studId = sc.nextLine(); //stores user input in studId
        if (!studId.equals("0")) {

            temp = studId.split(" "); //Splits users input at the space character and stores it in array temp
            System.out.printf(NAME_RESPONSE_TEMPLATE, temp[0], temp[1]);
            studId = String.valueOf(temp[0].toUpperCase().charAt(0));
            studId += String.valueOf(temp[1].toUpperCase().charAt(0));
            //stores first characters of both elements of temp in uppercase in studId

            if (temp[1].length() < 10) { //checks whether the length needs to be 0 padded or not
                studId += "0" + (temp[1].length());
            } else studId += String.valueOf(temp[1].length());


            studId += String.valueOf(temp[0].charAt(temp[0].length() / 2));
            studId += String.valueOf(temp[1].charAt(temp[1].length() / 2));
            //adds middle letters of first name and surname to studId

            System.out.printf(STUDENT_ID_TEMPLATE, studId);
        }
        return studId;
    }

    /**
     * Reads three marks (restricted to a floor and ceiling) for a student and returns their mean
     *
     * @param sc     Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @return the mean of the three marks entered for the student
     */
    public double captureMarks(Scanner sc, String studId) {
        // TDO (3.5) - Complete the captureMarks method which will allow a user to input three mark for a chosen student
        // DO NOT change MAX_MARK and MIN_MARK
        final int MAX_MARK = 100;
        final int MIN_MARK = 0;
        double avg = Double.MIN_VALUE;
        int markInput;
        int highest = 0, lowest = 100; //initialise highest at 0 and lowest at 100

        for (int i = 0; i < 3; i++) {

            do {
                System.out.printf(ENTER_MARK_TEMPLATE, i + 1, studId);
                markInput = sc.nextInt();
                sc.nextLine(); //removes return input from buffer
            }
            while (markInput < MIN_MARK || markInput > MAX_MARK); //makes sure the mark is in the correct range
            if (markInput > highest) highest = markInput;
            //checks whether the mark just entered is higher than the current highest
            if (markInput < lowest) lowest = markInput;
            //checks whether the mark just entered is lower than the current lowest input
            avg += markInput; //avg is holding the total marks
        }
        avg /= 3; //avg is now holding the average
        System.out.printf(LOW_HIGH_TEMPLATE, highest, lowest);
        System.out.printf(AVG_MARKS_TEMPLATE, avg);

        System.out.printf("%nWould you like to print a bar chart? [y/n]%n");
        char barC = sc.nextLine().charAt(0); //receives first character from string input
        if (barC == 'y') {
            printBarChart(studId, highest, lowest);
        }
        return avg;
    }

    /**
     * outputs a simple character-based vertical bar chart with 2 columns
     *
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @param high   a student's highest mark
     * @param low    a student's lowest mark
     */
    public void printBarChart(String studId, int high, int low) {
        // TDO (3.6) - Complete the printBarChart method which will print a bar chart of the highest and lowest results of a student
        System.out.printf("Student id statistics: %s", studId); //title of bar chart
        char bar = '|';
        for (int i = 0; i < high; i++) { //draw a maximum number of lines - high
            if ((high - i) > low) {
                System.out.printf(COLUMN_1_TEMPLATE, bar); //draw only one line until the number of lines drawn is equal to the
                //difference between high and low
            } else
                System.out.printf(COLUMN_2_TEMPLATE, bar, bar); //draw two lines for the remainder of the loop
        }
        System.out.printf(CHART_KEY_TEMPLATE, high, low); //bar chart labels
    }

    /**
     * Prints a specially formatted report, one line per student
     *
     * @param studList student IDs originally generated by {@link StudentMarking#generateStudId(Scanner)}
     * @param count    the total number of students in the system
     * @param avgArray mean (average) marks
     */
    public void reportPerStud(String[] studList,
                              int count,
                              double[] avgArray) {
        for (int i = 0; i < count; i++) {
            System.out.printf(REPORT_PER_STUD_TEMPLATE, studList[i], avgArray[i]);
            //iterate and print id and average of all elements
        }
    }

    //The main menu
    public void displayMenu() {
        System.out.printf(MENU_TEMPLATE);
    }

    /**
     * The controlling logic of the program. Creates and re-uses a {@link Scanner} that reads from {@link System#in}.
     *
     * @param args Command-line parameters (ignored)
     */
    public static void main(String[] args) {

        // DO NOT change sc, sm, EXIT_CODE, and MAX_STUDENTS
        Scanner sc = new Scanner(System.in);
        StudentMarking sm = new StudentMarking();
        final int EXIT_CODE = 0;
        final int MAX_STUDENTS = 5;
        int currentStudents = 0;

        String[] keepStudId = new String[5];
        double[] avgArray = new double[5];

        int menuInput;
        do {
            sm.displayMenu();
            menuInput = Integer.parseInt(sc.nextLine());
            switch (menuInput) {
                case 0:
                    System.out.printf("Goodbye%n");
                    break;
                case 1: //generate student id
                    if (currentStudents < MAX_STUDENTS) { //can accept a new student as long as there is space
                        keepStudId[currentStudents] = sm.generateStudId(sc);
                        currentStudents++; //current students incremented after adding student
                    } else
                        System.out.printf("%nToo many students");
                    break;
                case 2: //capture marks for given student id
                    //option for displaying bar chart
                    System.out.printf("Please enter the studId to capture their marks (Enter 0 to return to main menu)");
                    String currentID = sc.nextLine();

                    for (int i = 0; i < currentStudents; i++) { //search studentId array for inputted id
                        if (keepStudId[i].equals(currentID)) { //checks if the inputted id exists in array
                            avgArray[i] = sm.captureMarks(sc, currentID); //index mirroring of avgArray and keepStudId
                            break;
                        } else System.out.printf("%n" + sm.NOT_FOUND_TEMPLATE, currentID);
                    }

                    break;
                case 3: //list all student ids and their average marks
                    sm.reportPerStud(keepStudId, currentStudents, avgArray);
                    break;
                default: // Handle invalid main menu input
                    System.out.printf(
                            "You have entered an invalid option. Enter 0, 1, 2 or 3%n");// Skeleton: keep, unchanged
                    break;
            }
        }
        while (menuInput != EXIT_CODE);


    }
}

/*
    TODO Before you submit:
         1. ensure your code compiles
         2. ensure your code does not print anything it is not supposed to
         3. ensure your code has not changed any of the class or method signatures from the skeleton code
         4. check the Problems tab for the specific types of problems listed in the assignment document
         5. reformat your code: Code > Reformat Code
         6. ensure your code still compiles (yes, again)
 */