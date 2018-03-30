package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import java.util.List;

public class Deck {
    @NonNull protected String name;
    @NonNull protected List<Card> cards;

    // To String
    @Override
    public String toString() {
        return "Deck{" +
                "name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck deck = (Deck) o;

        if (!name.equals(deck.name)) return false;
        return cards.equals(deck.cards);

    }

    // Hash
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + cards.hashCode();
        return result;
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    // Adders
    public boolean addCard(Card card) {
        return cards.add(card);
    }

    public boolean addCards(List<Card> cards) {
        return cards.addAll(cards);
    }

    // Removers
    public boolean removeCards(List<Card> cards) {
        return cards.removeAll(cards);
    }

    // Setters (rename = setName)
    public void rename(String name) {
        this.name = name;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
