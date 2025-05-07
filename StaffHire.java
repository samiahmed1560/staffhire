/**
 * Base class representing a staff hiring record
 */
public class StaffHire {
    private int vacancyNumber;
    private String designation;
    private String jobType;
    private String staffName;
    private String joiningDate;
    private String qualification;
    private String appointedBy;
    private boolean joined;

    public StaffHire(int vacancyNumber, String designation, String jobType, 
                   String staffName, String joiningDate, String qualification, 
                   String appointedBy, boolean joined) {
        this.vacancyNumber = vacancyNumber;
        this.designation = designation;
        this.jobType = jobType;
        this.staffName = staffName;
        this.joiningDate = joiningDate;
        this.qualification = qualification;
        this.appointedBy = appointedBy;
        this.joined = joined;
    }

    // Getter methods
    public int getVacancyNumber() { return vacancyNumber; }
    public String getDesignation() { return designation; }
    public String getJobType() { return jobType; }
    public String getStaffName() { return staffName; }
    public String getJoiningDate() { return joiningDate; }
    public String getQualification() { return qualification; }
    public String getAppointedBy() { return appointedBy; }
    public boolean hasJoined() { return joined; }

    // Setter methods
    public void setVacancyNumber(int num) { vacancyNumber = num; }
    public void setDesignation(String des) { designation = des; }
    public void setJobType(String type) { jobType = type; }
    public void setStaffName(String name) { staffName = name; }
    public void setJoiningDate(String date) { joiningDate = date; }
    public void setQualification(String qual) { qualification = qual; }
    public void setAppointedBy(String by) { appointedBy = by; }
    public void setJoined(boolean j) { joined = j; }

    public void display() {
        System.out.println("\nStaff Hire Details:");
        System.out.println("Vacancy Number: " + vacancyNumber);
        System.out.println("Designation: " + designation);
        System.out.println("Job Type: " + jobType);
        System.out.println("Staff Name: " + staffName);
        System.out.println("Joining Date: " + joiningDate);
        System.out.println("Qualification: " + qualification);
        System.out.println("Appointed By: " + appointedBy);
        System.out.println("Joined: " + (joined ? "Yes" : "No"));
    }
}