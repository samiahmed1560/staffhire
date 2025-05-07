import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Main GUI class for the recruitment system
 */
public class RecruitmentSystem extends JFrame implements ActionListener {
    private ArrayList<StaffHire> staffList = new ArrayList<>();
    
    // GUI Components
    private JPanel mainPanel, inputPanel, buttonPanel, displayPanel;
    private JTextField[] textFields;
    private JCheckBox joinedCheckBox;
    private JTextArea displayArea;
    private JButton[] buttons;
    
    private static final String[] FIELD_LABELS = {
        "Vacancy Number", "Designation", "Job Type", "Staff Name",
        "Joining Date", "Qualification", "Appointed By",
        "Salary (FT)", "Weekly Hours (FT)",
        "Working Hours (PT)", "Wages/Hour (PT)", "Shift (PT)", "Display Index"
    };
    
    private static final String[] BUTTON_TEXTS = {
        "Add Full-Time", "Add Part-Time", "Set Salary", 
        "Set Shift", "Terminate", "Display", "Clear", "Exit"
    };

    public RecruitmentSystem(boolean guiMode) {
        if (guiMode) {
            setupGUI();
        } else {
            runCommandLineTests();
        }
    }

    private void setupGUI() {
        setTitle("Staff Recruitment System");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        createInputPanel();
        createButtonPanel();
        createDisplayPanel();
        
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(displayPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }

    private void createInputPanel() {
        inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Staff Information"));
        
        textFields = new JTextField[FIELD_LABELS.length];
        for (int i = 0; i < FIELD_LABELS.length; i++) {
            inputPanel.add(new JLabel(FIELD_LABELS[i] + ":"));
            textFields[i] = new JTextField();
            inputPanel.add(textFields[i]);
        }
        
        inputPanel.add(new JLabel("Joined:"));
        joinedCheckBox = new JCheckBox();
        inputPanel.add(joinedCheckBox);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        
        buttons = new JButton[BUTTON_TEXTS.length];
        for (int i = 0; i < BUTTON_TEXTS.length; i++) {
            buttons[i] = new JButton(BUTTON_TEXTS[i]);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }
    }

    private void createDisplayPanel() {
        displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBorder(BorderFactory.createTitledBorder("Staff Details"));
        
        displayArea = new JTextArea(15, 80);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        try {
            switch (command) {
                case "Add Full-Time":
                    addFullTimeStaff();
                    break;
                case "Add Part-Time":
                    addPartTimeStaff();
                    break;
                case "Set Salary":
                    setFullTimeSalary();
                    break;
                case "Set Shift":
                    setPartTimeShift();
                    break;
                case "Terminate":
                    terminatePartTimeStaff();
                    break;
                case "Display":
                    displayStaff();
                    break;
                case "Clear":
                    clearFields();
                    break;
                case "Exit":
                    System.exit(0);
                    break;
            }
        } catch (NumberFormatException ex) {
            showError("Invalid number format in input field");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void addFullTimeStaff() {
        int index = 0;
        int vacancyNumber = Integer.parseInt(textFields[index++].getText());
        String designation = textFields[index++].getText();
        String jobType = textFields[index++].getText();
        String staffName = textFields[index++].getText();
        String joiningDate = textFields[index++].getText();
        String qualification = textFields[index++].getText();
        String appointedBy = textFields[index++].getText();
        boolean joined = joinedCheckBox.isSelected();
        double salary = Double.parseDouble(textFields[index++].getText());
        int weeklyHours = Integer.parseInt(textFields[index++].getText());
        
        FullTimeStaffHire staff = new FullTimeStaffHire(
            vacancyNumber, designation, jobType, staffName, joiningDate,
            qualification, appointedBy, joined, salary, weeklyHours);
            
        staffList.add(staff);
        displayArea.setText("Full-time staff added successfully!\n");
        displayStaffDetails(staffList.size() - 1);
    }

    private void addPartTimeStaff() {
        int index = 0;
        int vacancyNumber = Integer.parseInt(textFields[index++].getText());
        String designation = textFields[index++].getText();
        String jobType = textFields[index++].getText();
        String staffName = textFields[index++].getText();
        String joiningDate = textFields[index++].getText();
        String qualification = textFields[index++].getText();
        String appointedBy = textFields[index++].getText();
        boolean joined = joinedCheckBox.isSelected();
        index += 2; // Skip FT fields
        int workingHours = Integer.parseInt(textFields[index++].getText());
        double wagesPerHour = Double.parseDouble(textFields[index++].getText());
        String shift = textFields[index++].getText();
        
        PartTimeStaffHire staff = new PartTimeStaffHire(
            vacancyNumber, designation, jobType, staffName, joiningDate,
            qualification, appointedBy, joined, workingHours, wagesPerHour, shift);
            
        staffList.add(staff);
        displayArea.setText("Part-time staff added successfully!\n");
        displayStaffDetails(staffList.size() - 1);
    }

    private void setFullTimeSalary() {
        int staffIndex = getValidStaffIndex();
        if (staffIndex == -1) return;
        
        StaffHire staff = staffList.get(staffIndex);
        if (staff instanceof FullTimeStaffHire) {
            double newSalary = Double.parseDouble(textFields[7].getText());
            ((FullTimeStaffHire)staff).setSalary(newSalary);
            displayArea.setText("Salary updated successfully!\n");
            displayStaffDetails(staffIndex);
        } else {
            showError("Selected staff is not full-time");
        }
    }

    private void setPartTimeShift() {
        int staffIndex = getValidStaffIndex();
        if (staffIndex == -1) return;
        
        StaffHire staff = staffList.get(staffIndex);
        if (staff instanceof PartTimeStaffHire) {
            String newShift = textFields[11].getText();
            ((PartTimeStaffHire)staff).setShift(newShift);
            displayArea.setText("Shift updated successfully!\n");
            displayStaffDetails(staffIndex);
        } else {
            showError("Selected staff is not part-time");
        }
    }

    private void terminatePartTimeStaff() {
        int staffIndex = getValidStaffIndex();
        if (staffIndex == -1) return;
        
        StaffHire staff = staffList.get(staffIndex);
        if (staff instanceof PartTimeStaffHire) {
            ((PartTimeStaffHire)staff).terminateStaff();
            displayArea.setText("Staff terminated successfully!\n");
            displayStaffDetails(staffIndex);
        } else {
            showError("Selected staff is not part-time");
        }
    }

    private void displayStaff() {
        int staffIndex = getValidStaffIndex();
        if (staffIndex == -1) return;
        
        displayStaffDetails(staffIndex);
    }

    private int getValidStaffIndex() {
        try {
            int index = Integer.parseInt(textFields[12].getText());
            if (index < 0 || index >= staffList.size()) {
                showError("Invalid index. Must be between 0 and " + (staffList.size()-1));
                return -1;
            }
            return index;
        } catch (NumberFormatException e) {
            showError("Please enter a valid number for staff index");
            return -1;
        }
    }

    private void displayStaffDetails(int index) {
        StaffHire staff = staffList.get(index);
        displayArea.append("Staff at index " + index + ":\n");
        displayArea.append("--------------------------------\n");
        
        // Capture console output
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out;
        System.setOut(ps);
        
        staff.display();
        
        System.out.flush();
        System.setOut(old);
        displayArea.append(baos.toString());
    }

    private void clearFields() {
        for (JTextField field : textFields) {
            field.setText("");
        }
        joinedCheckBox.setSelected(false);
        displayArea.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void runCommandLineTests() {
        System.out.println("=== STAFF RECRUITMENT SYSTEM TESTING ===");
        System.out.println("Running command line tests...\n");
        
        testFullTimeStaff();
        testPartTimeStaff();
        
        System.out.println("\nAll tests completed successfully!");
    }

    private void testFullTimeStaff() {
        System.out.println("TEST 1: FULL-TIME STAFF");
        FullTimeStaffHire ftStaff = new FullTimeStaffHire(
            1001, "Developer", "Permanent", "Alice Johnson", 
            "2025-06-01", "MSc Computer Science", "Tech Manager", 
            true, 52000, 37);
            
        System.out.println("\nInitial details:");
        ftStaff.display();
        
        System.out.println("\nUpdating salary...");
        ftStaff.setSalary(55000);
        ftStaff.display();
    }

    private void testPartTimeStaff() {
        System.out.println("\nTEST 2: PART-TIME STAFF");
        PartTimeStaffHire ptStaff = new PartTimeStaffHire(
            2001, "Receptionist", "Temporary", "Bob Smith", 
            "2025-06-02", "A-Levels", "Office Manager", 
            true, 6, 15.0, "Morning");
            
        System.out.println("\nInitial details:");
        ptStaff.display();
        
        System.out.println("\nChanging shift...");
        ptStaff.setShift("Evening");
        ptStaff.display();
        
        System.out.println("\nTerminating staff...");
        ptStaff.terminateStaff();
        ptStaff.display();
    }

    public static void main(String[] args) {
        boolean guiMode = true;
        if (args.length > 0 && args[0].equalsIgnoreCase("cmd")) {
            guiMode = false;
        }
        new RecruitmentSystem(guiMode);
    }
}