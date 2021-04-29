import java.util.ArrayList;

public class CmdAssignProject extends RecordedCommand {
    private Project p;
    private Team t;
    private ArrayList<Employee> externalSupport;

    @Override
    public void execute(String[] cmdParts) {
        try {
            Company c = Company.getInstance();
            p = c.findProject(cmdParts[1]);
            t = c.findTeam(cmdParts[2]);
            if (!(p.getStatus() instanceof PStatus_Pending)) {
                throw new ExProjectHasBeenAssignedToTeamAlready("Project " + p.getCode() + " has been assigned to team "
                        + p.getTeamAssigned().getName() + " on " + p.getDateAssigned().toString() + " already!");
            }

            externalSupport = new ArrayList<>();
            if (cmdParts.length > 3) {
                for (int i = 3; cmdParts.length > i; i++) {
                    externalSupport.add(c.findEmployee(cmdParts[i]));
                }
            }

            p.assignedTo(t);
            p.addExternalSupport(externalSupport);

            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExEmployeeIsNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExTeamIsNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExProjectIsNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExProjectHasBeenAssignedToTeamAlready e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        p.unassigned();
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        p.assignedTo(t);
        if (!externalSupport.isEmpty()) {
            p.addExternalSupport(externalSupport);
        }

        addUndoCommand(this);
    }
}
