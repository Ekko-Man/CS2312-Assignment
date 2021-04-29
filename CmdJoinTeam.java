
public class CmdJoinTeam extends RecordedCommand {
    private Team t;
    private Employee e;

    @Override
    public void execute(String[] cmdParts) {
        try {
            Company c = Company.getInstance();
            e = c.findEmployee(cmdParts[2]);
            
            Team hasTeam = c.hasTeam(e);
            if (hasTeam != null) {
                throw new ExEmployeeAlreadyBelongsToTeam(
                        "Employee " + e.getName() + " already belongs to team " + hasTeam.getName() + "!");
            }

            t = c.findTeam(cmdParts[1]);
            t.addMember(e);

            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExTeamIsNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeIsNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeAlreadyBelongsToTeam e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        t.removeMember(e);
        addRedoCommand(this);
    };

    @Override
    public void redoMe() {
        t.addMember(e);
        addUndoCommand(this);
    };
}
