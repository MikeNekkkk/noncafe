package Entity;


public class ProductAI {

    private Long   id;
    private String name;
    private double price;
    private String detailDescription;
    private int    stockQuantity;

    private String processingMethod; // WASHED | NATURAL | HONEY
    private String roastLevel;       // LIGHT | MEDIUM | DARK
    private String grindSize;        // WHOLE_BEAN | COARSE | MEDIUM | FINE
    private String tasteNotes;       // flavor tags, cách nhau bởi dấu phẩy

    private int    flavorIntensity;  // Body/Độ đậm đà (1-5)
    private int    bitternessLevel;  // Độ đắng (1-5)
    private int    acidityLevel;     // Độ chua (1-5)
    private int    sweetnessLevel;   // Độ ngọt (1-5) - MỚI

    private String caffeineLevel;    // LOW | MEDIUM | HIGH
    private String suitableFor;      // Phù hợp cho ai
    private String recommendedTime;  // MORNING | AFTERNOON | EVENING | ANYTIME
    private String brewMethods;      // French Press, Pour Over... - MỚI

    private String descriptionText;  // Đoạn văn chatbot dùng tư vấn
    private String embeddingVector;

    public ProductAI() {}

    public boolean isBeginnerFriendly() {
        return flavorIntensity <= 3 && bitternessLevel <= 2;
    }

    public boolean isSafeForStomach() {
        return acidityLevel <= 2;
    }

    /** An toàn cho người có vấn đề tim mạch / huyết áp */
    public boolean isSafeForHeart() {
        return "LOW".equals(caffeineLevel) || "MEDIUM".equals(caffeineLevel);
    }

    /** Phù hợp uống buổi tối */
    public boolean isSuitableForEvening() {
        return "LOW".equals(caffeineLevel);
    }

    /** Phù hợp cho người thích đậm */
    public boolean isIntenseProfile() {
        return flavorIntensity >= 4 && bitternessLevel >= 3;
    }

    /** Tính điểm match với nhu cầu khách hàng (0-100) */
    public int matchScore(boolean wantsStrong, boolean wantsSweet,
                           boolean hasStomachIssue, boolean isEvening) {
        int score = 50; // base score

        if (wantsStrong  && flavorIntensity >= 4) score += 20;
        if (!wantsStrong && flavorIntensity <= 2) score += 20;
        if (wantsSweet   && sweetnessLevel  >= 3) score += 15;
        if (hasStomachIssue && isSafeForStomach()) score += 15;
        if (isEvening && isSuitableForEvening())   score += 15;

        return Math.min(100, score);
    }

    // ─────────────────────────────────────────────────────────────────────
    // DISPLAY HELPERS
    // ─────────────────────────────────────────────────────────────────────

    /** Label hiển thị cho roast level */
    public String getRoastLevelLabel() {
        if (roastLevel == null) return "Không xác định";
        switch (roastLevel) {
            case "LIGHT":  return "Rang nhạt";
            case "MEDIUM": return "Rang vừa";
            case "DARK":   return "Rang đậm";
            default:       return roastLevel;
        }
    }

    /** Label cho processing method */
    public String getProcessingMethodLabel() {
        if (processingMethod == null) return "Không xác định";
        switch (processingMethod) {
            case "WASHED":  return "Chế biến ướt (Washed)";
            case "NATURAL": return "Chế biến khô (Natural)";
            case "HONEY":   return "Mật ong (Honey)";
            default:        return processingMethod;
        }
    }

    /** Label cho caffeine level */
    public String getCaffeineLevelLabel() {
        if (caffeineLevel == null) return "Trung bình";
        switch (caffeineLevel) {
            case "LOW":    return "Thấp";
            case "MEDIUM": return "Trung bình";
            case "HIGH":   return "Cao";
            default:       return caffeineLevel;
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // GETTERS & SETTERS
    // ─────────────────────────────────────────────────────────────────────
    public String getEmbeddingVector() { return embeddingVector; } 
    public void setEmbeddingVector(String embeddingVector) { this.embeddingVector = embeddingVector; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDetailDescription() { return detailDescription; }
    public void setDetailDescription(String detailDescription) { this.detailDescription = detailDescription; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getProcessingMethod() { return processingMethod; }
    public void setProcessingMethod(String processingMethod) { this.processingMethod = processingMethod; }

    public String getRoastLevel() { return roastLevel; }
    public void setRoastLevel(String roastLevel) { this.roastLevel = roastLevel; }

    public String getGrindSize() { return grindSize; }
    public void setGrindSize(String grindSize) { this.grindSize = grindSize; }

    public String getTasteNotes() { return tasteNotes; }
    public void setTasteNotes(String tasteNotes) { this.tasteNotes = tasteNotes; }

    public int getFlavorIntensity() { return flavorIntensity; }
    public void setFlavorIntensity(int flavorIntensity) { this.flavorIntensity = flavorIntensity; }

    public int getBitternessLevel() { return bitternessLevel; }
    public void setBitternessLevel(int bitternessLevel) { this.bitternessLevel = bitternessLevel; }

    public int getAcidityLevel() { return acidityLevel; }
    public void setAcidityLevel(int acidityLevel) { this.acidityLevel = acidityLevel; }

    public int getSweetnessLevel() { return sweetnessLevel; }
    public void setSweetnessLevel(int sweetnessLevel) { this.sweetnessLevel = sweetnessLevel; }

    public String getCaffeineLevel() { return caffeineLevel; }
    public void setCaffeineLevel(String caffeineLevel) { this.caffeineLevel = caffeineLevel; }

    public String getSuitableFor() { return suitableFor; }
    public void setSuitableFor(String suitableFor) { this.suitableFor = suitableFor; }

    public String getRecommendedTime() { return recommendedTime; }
    public void setRecommendedTime(String recommendedTime) { this.recommendedTime = recommendedTime; }

    public String getBrewMethods() { return brewMethods; }
    public void setBrewMethods(String brewMethods) { this.brewMethods = brewMethods; }

    public String getDescriptionText() { return descriptionText; }
    public void setDescriptionText(String descriptionText) { this.descriptionText = descriptionText; }

    @Override
    public String toString() {
        return "ProductAI{id=" + id + ", name='" + name + "', body=" + flavorIntensity
                + ", acidity=" + acidityLevel + ", bitterness=" + bitternessLevel
                + ", sweetness=" + sweetnessLevel + ", roast=" + roastLevel + "}";
    }
}