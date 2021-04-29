public class ExAssignmentOfProjectHasNotBeenStarted extends Exception {
    public ExAssignmentOfProjectHasNotBeenStarted() {
        super("Assignment of project has not been started!");
    }

    public ExAssignmentOfProjectHasNotBeenStarted(String message) {
        super(message); // Assignment of project P002 has not been started!
    }
}
