/**
 * Subclass for part-time staff members
 */
public class PartTimeStaffHire extends StaffHire {
    private int workingHours;
    private double wagesPerHour;
    private String shift;
    private boolean terminated;

    public PartTimeStaffHire(int vacancyNumber, String designation, String jobType,
                           String staffName, String joiningDate, String qualification,
                           String appointedBy, boolean joined, int workingHours,
                           double wagesPerHour, String shift) {
        super(vacancyNumber, designation, jobType, staffName, joiningDate,
              qualification, appointedBy, joined);
        this.workingHours = workingHours;
        this.wagesPerHour = wagesPerHour;
        this.shift = shift;
        this.terminated = false;
    }

    public int getWorkingHours() { return workingHours; }
    public double getWagesPerHour() { return wagesPerHour; }
    public String getShift() { return shift; }
    public boolean isTerminated() { return terminated; }

    public void setShift(String newShift) {
        if (hasJoined() && !terminated) {
            shift = newShift;
            System.out.println("Shift updated to " + shift);
        } else {
            System.out.println("Cannot update shift - staff not active");
        }
    }

    public void terminateStaff() {
        if (!terminated) {
            terminated = true;
            setStaffName("");
            setJoiningDate("");
            setQualification("");
            setAppointedBy("");
            setJoined(false);
            System.out.println("Staff terminated successfully");
        } else {
            System.out.println("Staff already terminated");
        }
    }

    @Override
    public void display() {
        super.display();
        if (hasJoined() && !terminated) {
            System.out.println("Working Hours: " + workingHours);
            System.out.println("Wages Per Hour: £" + wagesPerHour);
            System.out.println("Shift: " + shift);
            System.out.println("Daily Income: £" + (workingHours * wagesPerHour));
        }
        System.out.println("Terminated: " + (terminated ? "Yes" : "No"));
    }
}