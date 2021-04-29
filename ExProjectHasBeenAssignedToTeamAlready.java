public class ExProjectHasBeenAssignedToTeamAlready extends Exception {
    public ExProjectHasBeenAssignedToTeamAlready() {
        super("Project has been assigned to team on someday!");
    }

    public ExProjectHasBeenAssignedToTeamAlready(String message) {
        super(message); // Project P001 has been assigned to team All-rounder on 2-Feb-2021 already!
    }
}
