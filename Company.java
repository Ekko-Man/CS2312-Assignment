import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;

    private static Company instance = new Company();

    public static Company getInstance() {
        return instance;
    }

    private Company() {
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
        allProjects = new ArrayList<>();
    }

    public void giveAssignmentSuggestions(String pCode) throws ExProjectIsNotFound {
        ArrayList<Project> allPartProjects = new ArrayList<>();
        ArrayList<Team> allTeamPartList = new ArrayList<>();
        ArrayList<Employee> allMenberPartList = new ArrayList<>();
        allPartProjects.addAll(findRelatedProject(pCode));

        for (Project p : allPartProjects) {
            if (p.getStatus() instanceof PStatus_Pending) {
                continue;
            }
            Team tPart = p.getTeamAssigned();
            allTeamPartList.add(tPart);
            allMenberPartList.add(tPart.getLeader());
            allMenberPartList.addAll(tPart.getTeamList());
            allMenberPartList.addAll(p.getExternalSupport());
        }

        if (allTeamPartList.isEmpty()) {
            System.out.println("No team or staff has worked on related projects.");
            return;
        }

        Set<Team> tmp = new HashSet<>(allTeamPartList); // Remove duplicates Object
        allTeamPartList.clear();
        allTeamPartList.addAll(tmp);

        Set<Employee> tmp2 = new HashSet<>(allMenberPartList);
        allMenberPartList.clear();
        allMenberPartList.addAll(tmp2);

        Collections.sort(allTeamPartList);
        Collections.sort(allMenberPartList);

        System.out.println("These teams have worked on related projects:");
        for (Team t : allTeamPartList) {
            System.out.print(t + ": ");
            boolean firstOne = true;
            for (Project p : allPartProjects) {
                if (p.getTeamAssigned() == t) {
                    if (!firstOne)
                        System.out.print(", ");
                    else {
                        firstOne = false;
                    }
                    System.out.printf("%s(%s)", p.getCode(), p.getStatus());
                }
            }
            System.out.print("\n");
        }
        System.out.println("");

        System.out.println("These staff have worked on related projects:");
        for (Employee e : allMenberPartList) {
            System.out.print(e + ": ");
            boolean firstOne = true;
            for (Project p : allPartProjects) {
                if (p.getStatus() instanceof PStatus_Pending) {
                    continue;
                }

                if (p.isParticipant(e)) {
                    if (!firstOne)
                        System.out.print(", ");
                    else {
                        firstOne = false;
                    }
                    System.out.printf("%s(%s)", p.getCode(), p.getStatus());
                }
            }
            System.out.print("\n");
        }
    }

    public void listEmployees() {
        if (allEmployees.isEmpty())
            return;
        for (Employee e : allEmployees) {
            if (allTeams.isEmpty()) {
                System.out.println(e.getName());
                continue;
            }

            Team eTeam = hasTeam(e);
            if (eTeam == null)
                System.out.println(e.getName());
            else
                System.out.println(e.getName() + " (" + eTeam.getName() + ")");
        }
    }

    public void listTeams() {
        System.out.printf("%-15s%-10s%-13s%-11s\n", "Team Name", "Leader", "Setup Date", "Members");
        for (Team t : allTeams) {
            System.out.printf("%-15s%-10s%-13s", t.getName(), t.getLeader(), t.getDateSetup().toString());
            ArrayList<Employee> tList = t.getTeamList();
            if (tList.isEmpty()) {
                System.out.println("(no member)");
            }
            for (Employee e : tList) {
                System.out.printf(e.getName());
                if (e != tList.get(tList.size() - 1))
                    System.out.print(", ");
                else
                    System.out.print("\n");
            }
        }
    }

    public void listProjects() {
        System.out.printf("%-9s%-23s%-13s%-13s%-14s%-13s%-13s\n", "Code", "Project Title", "Created on", "Status",
                "Assigned to", "Assigned on", "Completed on");
        for (Project p : allProjects) {
            System.out.printf("%-9s%-23s%-13s%-13s", p.getCode(), p.getTitle(), p.getDateSetup(), p.getStatus());

            if (p.getStatus() instanceof PStatus_Pending) {
                System.out.printf("%-14s%-13s%-13s\n", "--", "--", "--");
            } else if (p.getStatus() instanceof PStatus_InProgress) {
                System.out.printf("%-14s%-13s%-13s\n", p.getTeamAssigned(), p.getDateAssigned(), "--");
            } else if (p.getStatus() instanceof PStatus_Completed) {
                System.out.printf("%-14s%-13s%-13s\n", p.getTeamAssigned(), p.getDateAssigned(), p.getDateCompleted());
            }
        }
    }

    public void listProjectStaff(String pCode) throws ExProjectIsNotFound {
        Project p = findProject(pCode);
        if (p == null) { // throw excaption
            System.out.println("Project " + pCode + " is not found!");
            return;
        }

        if (p.getStatus() instanceof PStatus_Pending) {
            System.out.println("Assignment of project " + p.getCode() + " has not been started!");
            return;
        }
        Team t = p.getTeamAssigned();
        System.out.println("Project team: " + t);

        ArrayList<Employee> fullTeamList = new ArrayList<>();
        fullTeamList.addAll(t.getTeamList());
        Collections.sort(fullTeamList);
        fullTeamList.add(0, t.getLeader());

        System.out.print("Project team members: ");
        boolean firstOne = true;
        for (Employee e : fullTeamList) {
            if (!firstOne)
                System.out.print(", ");
            else {
                firstOne = false;
            }
            if (t.getLeader() == e) {
                System.out.print(e.getName() + " (The Leader)");
                continue;
            }
            System.out.print(e.getName());
        }
        System.out.print("\n");

        System.out.print("External support: ");
        ArrayList<Employee> externalSupportList = p.getExternalSupport();
        if (externalSupportList.isEmpty()) {
            System.out.print("(none)\n");
            return;
        }

        firstOne = true;
        for (Employee e : externalSupportList) {
            if (!firstOne)
                System.out.print(", ");
            else {
                firstOne = false;
            }
            System.out.print(e.getName());
        }
        System.out.print("\n");
    }

    public void listStaffParticipations() {
        for (Employee e : allEmployees) {
            System.out.print(e.getName() + ": ");
            boolean firstOne = true;
            for (Project p : allProjects) {
                if (p.isParticipant(e)) {
                    if (!firstOne)
                        System.out.print(", ");
                    else {
                        firstOne = false;
                    }
                    System.out.printf("%s(%s)", p.getCode(), p.getStatus());

                }
            }
            if (firstOne)
                System.out.print("(no project)");
            System.out.print("\n");
        }
    }

    public void listTeamProjects() {
        for (Team t : allTeams) {
            System.out.print(t.getName() + ": ");
            boolean firstOne = true;
            for (Project p : allProjects) {
                if (p.handleBy(t)) {
                    if (!firstOne)
                        System.out.print(", ");
                    else {
                        firstOne = false;
                    }
                    System.out.printf("%s(%s)", p.getCode(), p.getStatus());
                }
            }
            System.out.print("\n");
        }

    }

    public Employee findEmployee(String ename) throws ExEmployeeIsNotFound {
        for (Employee e : allEmployees) {
            if (e.getName().equals(ename)) {
                return e;
            }
        }
        throw new ExEmployeeIsNotFound("Employee " + ename + " is not found!");
    }

    public Team findTeam(String tname) throws ExTeamIsNotFound {
        for (Team t : allTeams)
            if (t.getName().equals(tname))
                return t;
        throw new ExTeamIsNotFound("Team " + tname + " is not found!");
    }

    public Project findProject(String code) throws ExProjectIsNotFound {
        for (Project p : allProjects)
            if (p.getCode().equals(code))
                return p;
        throw new ExProjectIsNotFound("Project " + code + " is not found!");
    }

    public ArrayList<Project> findRelatedProject(String code) {
        String pCode = String.format("P%03d", ExtensionProject.projectCodeToInt(code));
        ArrayList<Project> projectList = new ArrayList<Project>();
        for (Project p : allProjects)
            if (p.getCode().contains(pCode)) {
                projectList.add(p);
            }
        Collections.sort(projectList);
        return projectList;
    }

    public Employee hasEmployee(String n) {
        for (Employee e : allEmployees) {
            if (e.getName().equals(n))
                return e;
        }
        return null;
    }

    public Team hasTeam(Employee e) {
        for (Team t : allTeams) {
            if (t.isTeamMember(e))
                return t;
        }
        return null;
    }

    public Team hasTeam(String tName) {
        for (Team t : allTeams) {
            if (t.getName().equals(tName))
                return t;
        }
        return null;
    }

    public Project hasProject(Team t) {
        for (Project p : allProjects) {
            if (p.handleBy(t))
                return p;
        }
        return null;
    }

    public void addEmployee(Employee e) {
        if (allEmployees.contains(e))
            return;
        allEmployees.add(e);
        Collections.sort(allEmployees);
    }

    public void addTeam(Team t) {
        allTeams.add(t);
        // Excaption max 1 team
        Collections.sort(allTeams);
    }

    public void addProject(Project p) {
        allProjects.add(p);
        Collections.sort(allProjects);
    }

    public void removeEmployee(Employee target) {
        allEmployees.remove(target);
        // remove from team, project, project suggestion
        // exceptions team leader, not exist
    }

    public void removeTeam(Team t) {
        allTeams.remove(t);
        // remove project related
        // exceptions not exist
    }

    public void removeProject(Project p) {
        allProjects.remove(p);
        // exceptions not exist
    }
}