package edu.kis.powp.jobs2d.events;

public class RefillPromptAction implements Runnable {

    private final String message;
    private final String title;
    private final Runnable refillAction;
    private final UserPrompt prompt;

    public RefillPromptAction(String message, String title, Runnable refillAction, UserPrompt prompt) {
        this.message = message;
        this.title = title;
        this.refillAction = refillAction;
        this.prompt = prompt;
    }

    @Override
    public void run() {
        if (prompt.confirm(message, title)) {
            refillAction.run();
        }
    }
}