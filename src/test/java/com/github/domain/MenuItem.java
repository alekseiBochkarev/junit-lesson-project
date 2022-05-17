package com.github.domain;

public enum MenuItem {
    DISC("Discussions"), WIKI("Wikis"), USER("Users");
    public final String engName;

    MenuItem(String engName) {
        this.engName = engName;
    }
}
