package com.gildedrose;

public class EnhancedItem1 extends Item {

    private final boolean shouldDegrade;
    private final int modifyRate;
    private final boolean hasVariableModifyRate;

    public EnhancedItem1(Item item, boolean shouldDegrade, int modifyRate, boolean hasVariableModifyRate) {
        super(item.name, item.sellIn, item.quality);
        this.shouldDegrade = shouldDegrade;
        this.modifyRate = modifyRate;
        this.hasVariableModifyRate = hasVariableModifyRate;
    }

    /**
     * degrade by 1 per day
     * if its a conjured item, degrade 2x speed
     * once sell by date has passed quality decrease 2x speed
     */
    private void degrade() {
        if (this.quality == 0) return;

        int degradeRate = this.modifyRate * -1;
        if(this.sellIn < 0) {
            degradeRate *= 2;
        }
        this.quality += degradeRate;
    }

    /**
     *upgrade by 1 per day
     *once sell by date has passed quality increases 2x speed
     *if "Backstage passes"
     *quality +2 when sellIn is <=10 and quality +3 when sellIn is <=5
     *quality =0 when sellIn is -1
     */
    private void upgrade() {
        if (this.quality == 50) return;

        if(this.hasVariableModifyRate) {
            if(this.sellIn <= 0) {
                this.quality = 0;
                return;
            } else if (this.sellIn <= 5) {
                this.quality += 3;
            } else if(this.sellIn <= 10) {
                this.quality += 2;
                return;
            } else {
                this.quality += this.modifyRate;
                return;
            }
        }

        int upgradeRate = this.modifyRate;
        if(this.sellIn < 0) {
            upgradeRate *= 2;
        }
        this.quality += upgradeRate;
    }


    public void modifyAfterOneDay() {
        if(this.shouldDegrade) {
            degrade();
        } else {
            upgrade();
        }
        this.sellIn -= 1;
    }

}
