public class CmdSetupTeam extends RecordedCommand {
    private Team t;

    @Override
    public void execute(String[] cmdParts) {
        try {
            Company c = Company.getInstance();
            Employee e = c.findEmployee(cmdParts[2]);
            Team employeeTeam = c.hasTeam(e);
            if (employeeTeam!=null) {
                throw new ExEmployeeAlreadyBelongsToTeam("Employee "+e+" already belongs to team "+employeeTeam+"!");
            }
            else if (c.hasTeam(cmdParts[1])!=null) {
                throw new ExTeamNameAlreadyExists();
            }

            this.t = new Team(cmdParts[1], e);
            c.addTeam(this.t);

            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExEmployeeIsNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeAlreadyBelongsToTeam e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNameAlreadyExists e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeTeam(this.t);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addTeam(this.t);
        addUndoCommand(this);
    }
}
