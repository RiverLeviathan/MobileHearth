package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

public abstract class DescribedCard extends Card {
    protected String description;

    public DescribedCard(@NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description) {
        super(name, manaCost, imgUrl);
        this.description = description;
    }

    public String getDescription() { return description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DescribedCard that = (DescribedCard) o;

        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }
}
