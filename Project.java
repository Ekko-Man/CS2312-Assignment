import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Project implements Comparable<Project> {
    private static int number_of_project;
    private String code;
    private String title;
    private Day dateSetup;
    private PStatus status;
    private Team teamAssigned;
    private Day dateAssigned;
    private Day dateCompleted;
    private ArrayList<Employee> externalSupport;

    public Project(String t) {
        if (number_of_project >= 999) {
            // throw Error
        }
        number_of_project++;
        this.code = String.format("P%03d", number_of_project);
        this.title = t;
        this.dateSetup = SystemDate.getInstance().clone();
        this.status = new PStatus_Pending();
        this.externalSupport = new ArrayList<>();
    }

    public Project(String c, String t) {
        this.code = c;
        this.title = t;
        this.dateSetup = SystemDate.getInstance().clone();
        this.status = new PStatus_Pending();
        this.externalSupport = new ArrayList<>();
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public Day getDateSetup() {
        return this.dateSetup;
    }

    public PStatus getStatus() {
        return this.status;
    }

    public Team getTeamAssigned() {
        return this.teamAssigned;
    }

    public Day getDateAssigned() {
        return this.dateAssigned;
    }

    public Day getDateCompleted() {
        return this.dateCompleted;
    }

    public ArrayList<Employee> getExternalSupport() {
        return this.externalSupport;
    }

    public static void projectNumMinusOne() {
        number_of_project--;
    }

    public static void projectNumAddOne() {
        number_of_project++;
    }

    public void assignedTo(Team t) {
        // Excaption assign date before create date : throw
        if (teamAssigned == null) {
            teamAssigned = t;
            dateAssigned = SystemDate.getInstance().clone();
            status = new PStatus_InProgress();
        } // Excaption already assgin : throw
    }

    public void unassigned() {
        if (teamAssigned != null) {
            teamAssigned = null;
            dateAssigned = null;
            status = new PStatus_Pending();
            externalSupport.clear();
        } // Excaption not assgin : throw
    }

    public void addExternalSupport(Employee e) {
        if (!externalSupport.contains(e))
            externalSupport.add(e);
        Collections.sort(externalSupport);
        // else throw already exist
    }

    public void addExternalSupport(ArrayList<Employee> externalSupports) {
        for (Employee e : externalSupports) {
            if (!externalSupport.contains(e))
                externalSupport.add(e);
            // else throw already exist
        }
        Collections.sort(externalSupport);
    }

    public void complete() {
        // Excaption assign date before create date : throw
        // Excaption even not assign : throw
        if (dateCompleted == null) {
            dateCompleted = SystemDate.getInstance().clone();
            status = new PStatus_Completed();
        } // Excaption already complete : throw
    }

    public void uncomplete() {
        if (dateCompleted != null) {
            dateCompleted = null;
            status = new PStatus_InProgress();
        } // Excaption not complete : throw
    }

    public boolean handleBy(Team t) {
        if (teamAssigned == t)
            return true;
        else
            return false;
    }

    public boolean isParticipant(Employee e) {
        if (status instanceof PStatus_Pending) return false;
        if (teamAssigned.isTeamMember(e)) return true;
        if (externalSupport.contains(e)) return true;
        return false;
    }

    @Override
    public int compareTo(Project another) {
        return this.code.compareTo(another.code);
    }
}
