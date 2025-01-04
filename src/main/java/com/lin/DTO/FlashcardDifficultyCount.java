package com.lin.DTO;

public class FlashcardDifficultyCount {
    private int easyCount;
    private int mediumCount;
    private int hardCount;

    public FlashcardDifficultyCount() {
        this.easyCount = 0;
        this.mediumCount = 0;
        this.hardCount = 0;
    }

    public void incrementCount(int difficultyLevel) {
        switch (difficultyLevel) {
            case 1: 
                easyCount++;
                break;
            case 2: 
                mediumCount++;
                break;
            case 3:
                hardCount++;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level");
        }
    }

    public int getEasyCount() {
        return easyCount;
    }

    public int getMediumCount() {
        return mediumCount;
    }

    public int getHardCount() {
        return hardCount;
    }
}
