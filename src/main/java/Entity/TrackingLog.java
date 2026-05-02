package Entity;

import java.sql.Timestamp;

public class TrackingLog {

    private Long      id;
    private Long      orderId;
    private String    statusCode;   // Mã trạng thái (GHN hoặc nội bộ)
    private String    statusName;   // Tên trạng thái tiếng Việt
    private String    location;     // Tên kho / bưu cục
    private String    note;
    private String    updatedBy;    // "SYSTEM" hoặc "ADMIN"
    private Timestamp createdAt;

    public TrackingLog() {}

    public TrackingLog(Long orderId, String statusCode, String statusName,
                       String location, String note, String updatedBy) {
        this.orderId    = orderId;
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.location   = location;
        this.note       = note;
        this.updatedBy  = updatedBy;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public Long getId()                     { return id; }
    public void setId(Long id)              { this.id = id; }

    public Long getOrderId()                { return orderId; }
    public void setOrderId(Long orderId)    { this.orderId = orderId; }

    public String getStatusCode()           { return statusCode; }
    public void setStatusCode(String s)     { this.statusCode = s; }

    public String getStatusName()           { return statusName; }
    public void setStatusName(String s)     { this.statusName = s; }

    public String getLocation()             { return location; }
    public void setLocation(String s)       { this.location = s; }

    public String getNote()                 { return note; }
    public void setNote(String s)           { this.note = s; }

    public String getUpdatedBy()            { return updatedBy; }
    public void setUpdatedBy(String s)      { this.updatedBy = s; }

    public Timestamp getCreatedAt()         { return createdAt; }
    public void setCreatedAt(Timestamp t)   { this.createdAt = t; }

    @Override
    public String toString() {
        return "TrackingLog{orderId=" + orderId + ", statusCode=" + statusCode
                + ", location=" + location + ", createdAt=" + createdAt + "}";
    }
}