package com.Austin-W-Music.fish.exceptions;

import com.Austin-W-Music.fish.baits.ApplicationResult;
import com.Austin-W-Music.fish.baits.Bait;
import org.jetbrains.annotations.NotNull;

public class MaxBaitReachedException extends Exception {

    private final ApplicationResult recoveryResult;

    public MaxBaitReachedException(@NotNull Bait bait, @NotNull ApplicationResult recoveryResult) {
        super(bait.getName() + " has reached its maximum number of uses on the fishing rod.");
        this.recoveryResult = recoveryResult;
    }

    /**
     * @return The interrupted ApplicationResult object that would have been returned if it weren't for the error.
     */
    public @NotNull ApplicationResult getRecoveryResult() {
        return recoveryResult;
    }

}
