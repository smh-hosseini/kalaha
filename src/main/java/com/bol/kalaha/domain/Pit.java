package com.bol.kalaha.domain;

public class Pit {

    private int seeds;
    private PlayerNum owner;
    private Pit next;
    private PitType type;
    private Pit opposite;
    private int index;

    Pit(PlayerNum owner, int seeds, PitType type, int index) {
        this.owner = owner;
        this.seeds = seeds;
        this.type = type;
        this.index = index;
    }

    public static Pit createHouse(PlayerNum owner, int seeds, int index) {
        return new Pit(owner, seeds, PitType.HOUSE, index);
    }

    public static Pit createStore(PlayerNum owner) {
        return new Pit(owner, 0, PitType.STORE, 0);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Integer count() {
        return seeds;
    }

    public Pit next() {
        return next;
    }

    public Pit setNext(Pit next) {
        this.next = next;
        return next;
    }

    public PlayerNum getOwner() {
        return owner;
    }

    public void addSeed() {
        this.seeds++;
    }

    public boolean isEmpty() {
        return this.seeds == 0;
    }

    public boolean isOwnerStore(PlayerNum playerNum) {
        return owner.equals(playerNum) && PitType.STORE.equals(type);
    }

    public void setOpposite(Pit opposite) {
        this.opposite = opposite;
    }

    public Pit getOppositePit() {
        return opposite;
    }

    public boolean isStore() {
        return PitType.STORE.equals(type);
    }

    public int capture() {
        return takeAllSeeds();
    }

    public Integer takeAllSeeds() {
        if (isStore()) {
            return 0;
        }
        var seeds = this.seeds;
        this.seeds = 0;
        return seeds;
    }

    public void addSeeds(int seeds) {
        this.seeds += seeds;
    }

    public int getSeeds() {
        return seeds;
    }

    public PitType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Pit{" +
                "seeds=" + seeds +
                ", owner=" + owner +
                ", type=" + type +
                ", index=" + index +
                '}';
    }
}
