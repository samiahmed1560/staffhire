/**
 * Subclass for full-time staff members
 */
public class FullTimeStaffHire extends StaffHire {
    private double salary;
    private int weeklyHours;

    public FullTimeStaffHire(int vacancyNumber, String designation, String jobType,
                           String staffName, String joiningDate, String qualification,
                           String appointedBy, boolean joined, double salary,
                           int weeklyHours) {
        super(vacancyNumber, designation, jobType, staffName, joiningDate,
              qualification, appointedBy, joined);
        this.salary = salary;
        this.weeklyHours = weeklyHours;
    }

    public double getSalary() { return salary; }
    public int getWeeklyHours() { return weeklyHours; }

    public void setSalary(double newSalary) {
        if (hasJoined()) {
            salary = newSalary;
            System.out.println("Salary updated to £" + salary);
        } else {
            System.out.println("Cannot update salary - staff not joined");
        }
    }

    public void setWeeklyHours(int hours) {
        weeklyHours = hours;
    }

    @Override
    public void display() {
        super.display();
        if (hasJoined()) {
            System.out.println("Salary: £" + salary);
            System.out.println("Weekly Hours: " + weeklyHours);
        }
    }
}