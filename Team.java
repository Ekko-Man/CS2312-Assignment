import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team>{
    private String teamName;
    private Employee leader;
    private Day dateSetup;
    private ArrayList<Employee> teamList;

    public Team(String n, Employee ld){
        this.teamName = n;
        this.leader = ld;
        // Excaption already in team
        this.dateSetup = SystemDate.getInstance().clone();
        this.teamList = new ArrayList<>();
    }

    public String toString() { return this.teamName; }

    public String getName() { return this.teamName; }

    public Employee getLeader() { return this.leader; }

    public Day getDateSetup() { return this.dateSetup; }

    public ArrayList<Employee> getTeamList() { return teamList; }

    public boolean isTeamMember(Employee target) {
        if (this.leader == target) return true;
        for (Employee e : teamList) if (e == target) return true;
        return false;
    }

    public void addMember(Employee e) {
        if (teamList.contains(e)) return;
        // Excaption exist?
        teamList.add(e);
        Collections.sort(teamList);
    }

    public void removeMember(Employee e) {
        teamList.remove(e);
        // Excaption not exist
    }

    @Override
    public int compareTo(Team another) 
    {
        return this.teamName.compareTo(another.teamName);
    }
}
