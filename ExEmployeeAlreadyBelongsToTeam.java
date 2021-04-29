public class ExEmployeeAlreadyBelongsToTeam extends Exception {
    public ExEmployeeAlreadyBelongsToTeam() {
        super("Employee already belongs to team!");
    }

    public ExEmployeeAlreadyBelongsToTeam(String message) {
        super(message); // Employee Samuel already belongs to team SW Experts!
    }
}