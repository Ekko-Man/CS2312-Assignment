public class ExProjectHasBeenMarkedAsCompletedBefore extends Exception {
    public ExProjectHasBeenMarkedAsCompletedBefore() {
        super("Project has been marked as completed before!");
    }

    public ExProjectHasBeenMarkedAsCompletedBefore(String message) {
        super(message); // Project P001 has been marked as completed before!
    }
}
