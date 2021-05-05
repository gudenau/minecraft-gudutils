package net.gudenau.minecraft.gudutils.utils;

@SuppressWarnings("PointlessBitwiseExpression")
public final class SetBlockFlags{
    private SetBlockFlags(){}
    
    /**
     * Propagates a change event to surrounding blocks.
     */
    public static final int PROPAGATE_CHANGE = 1 << 0;
    /**
     * Notifies listeners and clients who need to react when the block changes
     */
    public static final int NOTIFY_LISTENERS = 1 << 1;
    /**
     * Used in conjunction with NOTIFY_LISTENERS to suppress the render pass on clients.
     */
    public static final int NO_REDRAW = 1 << 2;
    /**
     * Forces a synchronous redraw on clients.
     */
    public static final int REDRAW_ON_MAIN_THREAD = 1 << 3;
    /**
     * Bypass virtual block state changes and forces the passed state to be stored as-is.
     */
    public static final int FORCE_STATE = 1 << 4;
    /**
     * Prevents the previous block (container) from dropping items when destroyed.
     */
    public static final int SKIP_DROPS = 1 << 5;
    /**
     * Signals that the current block is being moved to a different location, usually because of a piston.
     */
    public static final int MOVED = 1 << 6;
}
