package com.zukemon.fight;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CriticalHitTest {

    @Test
    public void given100PercentWhenCalculatingCriticalHitItWillBeOne() {
        boolean criticalHit = new CriticalHit().isCriticalHit(100);

        assertThat(criticalHit).isEqualTo(true);
    }

    @Test
    public void given0PercentWhenCalculatingCriticalHitItWillNotBeOne() {
        boolean criticalHit = new CriticalHit().isCriticalHit(0);

        assertThat(criticalHit).isEqualTo(false);
    }
}
