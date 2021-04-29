public class CmdMarkCompletion extends RecordedCommand {
    private Project p;

    @Override
    public void execute(String[] cmdParts) {
        try {
            Company c = Company.getInstance();
            p = c.findProject(cmdParts[1]);
            if (p.getStatus() instanceof PStatus_Pending) {
                throw new ExAssignmentOfProjectHasNotBeenStarted("Assignment of project "+p.getCode()+" has not been started!");
            } else if (p.getStatus() instanceof PStatus_Completed) {
                throw new ExProjectHasBeenMarkedAsCompletedBefore("Project "+p.getCode()+" has been marked as completed before!");
            }
            p.complete();

            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExProjectIsNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExAssignmentOfProjectHasNotBeenStarted e) {
            System.out.println(e.getMessage());
        } catch (ExProjectHasBeenMarkedAsCompletedBefore e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void undoMe() {
        p.uncomplete();
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        p.complete();
        addUndoCommand(this);
    }
}
