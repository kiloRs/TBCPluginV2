package fun.tbcraft.play.listener;

public class BlockListener implements BaseListener{
    private final String id = "" + numberOf + 1;
    private boolean cancelEach;
    private boolean enabled;

    public BlockListener (boolean cancelAll, boolean enabledAtAll) {
        this.cancelEach = cancelAll;
        this.enabled = enabledAtAll;
    }

    @Override
    public String getID ( ) {
        return id;
    }

    @Override
    public boolean cancelAll (boolean useStop) {
        return cancelEach;
    }

    @Override
    public boolean shouldUse ( ) {
        return enabled;
    }

    @Override
    public void setUseState (Boolean be) {
        this.enabled = be;
    }


}