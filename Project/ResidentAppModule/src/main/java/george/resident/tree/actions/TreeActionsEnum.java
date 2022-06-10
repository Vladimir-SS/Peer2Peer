package george.resident.tree.actions;

/** Enum with possible types of actions:
 * The receiving device will compare it's own files to the ones of the sender, and then it will send all new(er) files from the specified location back to the device that sent the request.
 * The receiving device will initially treat this request as a "Fetch" request. After that, it will then send a similar "Fetch" request back to the initial sender.
 * The receiving device will delete all files from a specified location.
 */
public enum TreeActionsEnum {
    Sync,
    Fetch,
    Delete
}
