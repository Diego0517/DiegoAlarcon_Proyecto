import java.time.LocalDateTime;

public class Category {
    int id;
    String name;
    String image;
    LocalDateTime creationAt;
    LocalDateTime updatedAt;

    public Category(int id, String name, String image, LocalDateTime creationAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.creationAt = creationAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", creationAt=" + creationAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
